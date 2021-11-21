package me.mattix.the100.listeners.player;

import me.mattix.the100.duel.DuelProtocol;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class DuelListener implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {

		// gain d'argent pour le gagnant

		event.setKeepInventory(true);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();



	}

}