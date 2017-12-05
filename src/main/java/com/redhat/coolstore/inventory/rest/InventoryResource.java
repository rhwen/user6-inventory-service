package com.redhat.coolstore.inventory.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.redhat.coolstore.inventory.model.Inventory;
import com.redhat.coolstore.inventory.service.InventoryService;

@Path(value = "/inventory")
public class InventoryResource {

	@Inject
	private InventoryService inventoryService;

	@GET
	@Path("/{itemId}")
	@Produces("application/json")
	public Inventory getInventory(@PathParam("itemId") String itemId) {
		Inventory inventory = inventoryService.getInventory(itemId);
		if (inventory == null)
			throw new NotFoundException();
		return inventory;
	}
}
