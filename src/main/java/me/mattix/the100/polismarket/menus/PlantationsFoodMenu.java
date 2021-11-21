package me.mattix.the100.polismarket.menus;

import me.mattix.the100.polismarket.MarketInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlantationsFoodMenu extends MarketInventory {

    public String invName = "";
    public Inventory inventory = Bukkit.createInventory(null, 9 * 3, invName);

    public PlantationsFoodMenu() {

    }

    @Override
    public void open(Player player) {
        player.openInventory(inventory);
    }

    public String getName() {
        return "Fermier";
    }
}