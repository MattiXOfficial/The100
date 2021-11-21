package me.mattix.the100.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PluginCommandBlocked implements Listener {

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {

		Player player = event.getPlayer();
		String message = event.getMessage().replace("/", "");
		String[] args = message.split(" ");
		
		if (args[0].equalsIgnoreCase("plugins") || args[0].equalsIgnoreCase("pl") || args[0].equalsIgnoreCase("bukkit:pl") || args[0].equalsIgnoreCase("bukkit:plugins") || 
				args[0].equalsIgnoreCase("server") || args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("bukkit:version") ||
				args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("bukkit:?") || args[0].equalsIgnoreCase("bukkit:about") || args[0].equalsIgnoreCase("bukkit:help") || 
				args[0].equalsIgnoreCase("bukkit:ver") || args[0].equalsIgnoreCase("bukkit:about")) {
			event.setCancelled(true);
			player.sendMessage("§aSecrets du serveur d'Alie confidentiels.");
		}
	}
}