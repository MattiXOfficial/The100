package me.mattix.the100.listeners.player;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomRecipeListener implements Listener {

	@EventHandler
	public void changeCraft(PrepareItemCraftEvent e) {

		// LE CRAFT SE PASSE BIEN DANS UNE TABLE DE CRAFT
		if (e.getInventory() instanceof CraftingInventory) {

			// ON RECUPERE L INVENTAIRE SOUS FORME DUNE VARIABLE
			CraftingInventory inv = (CraftingInventory) e.getInventory();

			// SI LE RESULTAT EST UNE PIOCHE EN BOIS ON CHANGE LE CRAFT
			
			if (inv.getResult() != null) {

				switch (inv.getResult().getType()) {
	
				case WOOD_AXE:
					ItemStack customResult = new ItemStack(Material.STONE_AXE, 1);
					ItemMeta customM = customResult.getItemMeta();
					customM.addEnchant(Enchantment.DIG_SPEED, 2, true);
					customM.addEnchant(Enchantment.DURABILITY, 3, true);
					customResult.setItemMeta(customM);
	
					inv.setResult(customResult);
	
					break;
					
				case STONE_AXE:
					ItemStack customResult2 = new ItemStack(Material.STONE_AXE, 1);
					ItemMeta customM2 = customResult2.getItemMeta();
					customM2.addEnchant(Enchantment.DIG_SPEED, 2, true);
					customM2.addEnchant(Enchantment.DURABILITY, 3, true);
					customResult2.setItemMeta(customM2);
	
					inv.setResult(customResult2);
	
					break;
	
				default:
					break;
	
				}
			}
		}
	}
}