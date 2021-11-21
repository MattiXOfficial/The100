package me.mattix.the100.runnables;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import me.mattix.the100.Main;

public class RessourcesRunnable extends BukkitRunnable {

	@Override
	public void run() {

		Main.INSTANCE.region.setBlock();
		Bukkit.broadcastMessage("§6§lLa Ferme à Minerais §fest désormais remplie ! §7(Rappel: Refill toutes les 5h)");
		Bukkit.broadcastMessage(" ");
		Bukkit.getOnlinePlayers().forEach(players -> {
			players.playSound(players.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1f);
		});
	}
}