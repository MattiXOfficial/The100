package me.mattix.the100.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import me.mattix.the100.utils.ItemBuilder;

public class SettingsMenu implements Listener {

	public String invName = "Paramètres";
	private Inventory inventory = Bukkit.createInventory(null, (9 * 3), invName);
	
	
	public SettingsMenu() {
		
		inventory.setItem(13, new ItemBuilder().type(Material.BOOK).name("§dCustom Scoreboard").lore("§7Customise ton scoreboard", "§7selon tes besoins.").build());
		
	}

	public void open(Player player) {
		player.openInventory(this.inventory);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		if (event.getAction() != null && event.getCurrentItem() == null) return;
		
		if (event.getInventory().getName().equalsIgnoreCase(invName)) {
			event.setCancelled(true);
			
			Player player = (Player) event.getWhoClicked();
			
			switch (event.getCurrentItem().getType()) {
			case BOOK:
				
				new ScoreboardCustomMenu().open(player);
				
				break;

			default:
				break;
			}
			
		}
	}
}