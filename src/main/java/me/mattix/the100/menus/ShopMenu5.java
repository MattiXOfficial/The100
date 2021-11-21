package me.mattix.the100.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopMenu5 implements Listener {

	public String invName = "Shop > Blocs 5";
	private Inventory inventory = Bukkit.createInventory(null, (9 * 5), invName);

	public ShopMenu5() {
		for (int i = 0; i <= 15; i++) {
			inventory.addItem(new ItemStack(Material.STAINED_CLAY, 1, (short) i));
		}
		for (int u = 0; u <= 3; u++) {
			inventory.addItem(new ItemStack(Material.WOOD, 1, (short) u));
		}
	}

	/*
	 * inventory.setItem(0, new
	 * ItemBuilder().type(Material.WOOD).name("§fBois x64").lore("§7Coût: 20 Levels"
	 * ).build()); inventory.setItem(1, new
	 * ItemBuilder().type(Material.WOOD).data((byte)
	 * 1).name("§fBois Spruce x64").lore("§7Coût: 20 Levels").build()); // spruce
	 * inventory.setItem(2, new ItemBuilder().type(Material.WOOD).data((byte)
	 * 2).name("§fBois Birch x64").lore("§7Coût: 20 Levels").build());
	 * inventory.setItem(3, new ItemBuilder().type(Material.WOOD).data((byte)
	 * 3).name("§fBois Jungle x64").lore("§7Coût: 20 Levels").build());
	 * inventory.setItem(4, new ItemBuilder().type(Material.WOOD).data((byte)
	 * 4).name("§fBois Acacia x64").lore("§7Coût: 20 Levels").build());
	 * inventory.setItem(0, new
	 * ItemBuilder().type(Material.WOOD).name("§fBois x64").lore("§7Coût: 20 Levels"
	 * ).build());
	 */

	public void open(Player player) {
		player.openInventory(this.inventory);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		if (event.getAction() != null && event.getCurrentItem() == null)
			return;

		if (event.getInventory().getName().equalsIgnoreCase(invName)) {
			event.setCancelled(true);

			Player player = (Player) event.getWhoClicked();

			switch (event.getCurrentItem().getType()) {
			case PAPER:
				new ShopMenu4().open(player);
				break;

			default:
				break;
			}

			if (event.getCurrentItem().getType() != Material.PAPER && event.getCurrentItem().getType() != Material.AIR && event.getCurrentItem().getType().isBlock()) {
				if (player.getLevel() >= 20) {
					@SuppressWarnings("deprecation")
					ItemStack item = new ItemStack(event.getCurrentItem().getType(), 64, event.getCurrentItem().getData().getData());
					Bukkit.getWorld("world").dropItemNaturally(player.getLocation(), item);
					player.setLevel(player.getLevel() - 20);
				} else {
					player.sendMessage("§cVous n'avez pas assez de niveau pour acheter cet objet. (20 niveaux requis)");
				}
			}
			
		}
	}
}