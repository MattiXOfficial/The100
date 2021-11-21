package me.mattix.the100.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.mattix.the100.utils.ItemBuilder;

public class ShopMenu2 implements Listener {

	public String invName = "Shop > Blocs 2";
	private Inventory inventory = Bukkit.createInventory(null, (9 * 5), invName);

	public ShopMenu2() {
		Material[] m = Material.values();
		for (int i = (46 * 2) - 46; i < m.length && i < 46 * 2; i++) {

			ItemStack is = new ItemStack(m[i]);

			if (is.getType().isBlock()) {

				if (!is.getType().equals(Material.TNT) && !is.getType().equals(Material.MOB_SPAWNER)
						&& !is.getType().equals(Material.IRON_ORE) && !is.getType().equals(Material.DIAMOND_ORE)
						&& !is.getType().equals(Material.REDSTONE_ORE) && !is.getType().equals(Material.GOLD_ORE)
						&& !is.getType().equals(Material.LAPIS_ORE) && !is.getType().equals(Material.LAPIS_BLOCK)
						&& !is.getType().equals(Material.DIAMOND_BLOCK) && !is.getType().equals(Material.GOLD_BLOCK)
						&& !is.getType().equals(Material.EMERALD_BLOCK) && !is.getType().equals(Material.EMERALD_ORE)
						&& !is.getType().equals(Material.NETHER_STAR) && !is.getType().equals(Material.SKULL_ITEM)
						&& !is.getType().equals(Material.MONSTER_EGG) && !is.getType().equals(Material.MONSTER_EGGS)
						&& !is.getType().equals(Material.DRAGON_EGG) && !is.getType().equals(Material.DRAGONS_BREATH)
						&& !is.getType().equals(Material.ELYTRA) && !is.getType().equals(Material.BARRIER)) {
					inventory.addItem(is);

				}
			}
		}
		inventory.addItem(new ItemStack(Material.DIRT, 1, (byte) 2));
		inventory.setItem(44, new ItemBuilder().type(Material.PAPER).name("§fPage Suivante").build());
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
				new ShopMenu3().open(player);
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