package com.redhat.coolstore.inventory.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;

import com.redhat.coolstore.inventory.model.Inventory;

@RunWith(Arquillian.class)
public class InventoryServiceTest {

	@Inject
	private InventoryService inventoryService;

	@CreateSwarm
	public static Swarm newContainer() throws Exception {
		return new Swarm().withProfile("local");
	}

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class).addPackages(true, InventoryService.class.getPackage())
				.addPackages(true, Inventory.class.getPackage()).addAsResource("project-local.yml", "project-local.yml")
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("META-INF/test-load.sql", "META-INF/test-load.sql");
	}

	@Test
	public void testExist() {
		Inventory inventory = inventoryService.getInventory("123456");
		assertNotNull(inventory);
		assertEquals("99", inventory.getQuantity());
	}

	@Test
	public void testNotExist() {
		Inventory inventory = inventoryService.getInventory("654321");
		assertNull(inventory);
	}
}
