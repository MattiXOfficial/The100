package me.mattix.the100.runnables;

import org.bukkit.scheduler.BukkitRunnable;

import me.mattix.the100.Main;

public class DatabaseCheckRunnable extends BukkitRunnable {

	@Override
	public void run() {
		if (!Main.INSTANCE.database.isOnline()) {
			Main.INSTANCE.database.connexion();
		}
	}
}