package me.mattix.the100.listeners;

import me.mattix.the100.listeners.player.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import me.mattix.the100.Main;
import me.mattix.the100.menus.ScoreboardCustomMenu;
import me.mattix.the100.menus.SettingsMenu;
import me.mattix.the100.menus.ShopMenu;
import me.mattix.the100.menus.ShopMenu2;
import me.mattix.the100.menus.ShopMenu3;
import me.mattix.the100.menus.ShopMenu4;
import me.mattix.the100.menus.ShopMenu5;

public class ListenersManager {

	public Plugin plugin;
	public PluginManager pluginManager;
	
	public ListenersManager(Plugin plugin) {
		this.plugin = plugin;
		this.pluginManager = Bukkit.getPluginManager();
	}
	
	public void registerListeners() {
		pluginManager.registerEvents(new PlayerJoinListener(), plugin);
		pluginManager.registerEvents(new PlayerChatListener(), plugin);
		pluginManager.registerEvents(new PLayerDeathListener(), plugin);
		pluginManager.registerEvents(Main.INSTANCE, plugin);
		pluginManager.registerEvents(new PluginCommandBlocked(), plugin);
		pluginManager.registerEvents(new DuelListener(), plugin);
		pluginManager.registerEvents(new PlayerBlockBreakListener(), plugin);
		pluginManager.registerEvents(new SettingsMenu(), plugin);
		pluginManager.registerEvents(new ScoreboardCustomMenu(), plugin);
		pluginManager.registerEvents(new ShopMenu(), plugin);
		pluginManager.registerEvents(new ShopMenu2(), plugin);
		pluginManager.registerEvents(new ShopMenu3(), plugin);
		pluginManager.registerEvents(new ShopMenu4(), plugin);
		pluginManager.registerEvents(new ShopMenu5(), plugin);
		// pluginManager.registerEvents(new AutoLeafDecayListener(), plugin);
		pluginManager.registerEvents(new CustomRecipeListener(), plugin);
	}
}