package me.mattix.the100.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.mattix.the100.Main;
import me.mattix.the100.menus.ScoreboardCustomMenu;
import me.mattix.the100.scoreboard.ScoreboardManager;
import me.mattix.the100.utils.LocationArrowUtils;

public class ScoreboardRunnable extends BukkitRunnable {

	@Override
	public void run() {
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			
			if (player.getLocation().getWorld().getName().equals("world")) {

				if (ScoreboardManager.scoreboardGame.containsKey(player)) {
					
					if ((int) player.getLocation().distance(ScoreboardCustomMenu.skaikruLoc) > 600) {
						if (ScoreboardCustomMenu.haveLines("1", player)) ScoreboardManager.scoreboardGame.get(player).setLine(1, "§7Skaikru: §bFar " + getArrowColor(player.getLocation().distance(ScoreboardCustomMenu.skaikruLoc)) + LocationArrowUtils.calculateArrow(player, ScoreboardCustomMenu.skaikruLoc));
					} else {
						if (ScoreboardCustomMenu.haveLines("1", player)) ScoreboardManager.scoreboardGame.get(player).setLine(1, "§7Skaikru: §f" + (int) player.getLocation().distance(ScoreboardCustomMenu.skaikruLoc)  + " " + getArrowColor(player.getLocation().distance(ScoreboardCustomMenu.skaikruLoc)) + LocationArrowUtils.calculateArrow(player, ScoreboardCustomMenu.skaikruLoc));
					}
					
					if ((int) player.getLocation().distance(ScoreboardCustomMenu.trikruLoc) > 600) {
						if (ScoreboardCustomMenu.haveLines("2", player)) ScoreboardManager.scoreboardGame.get(player).setLine(2, "§7Trikru: §bFar " + getArrowColor(player.getLocation().distance(ScoreboardCustomMenu.trikruLoc)) + LocationArrowUtils.calculateArrow(player, ScoreboardCustomMenu.trikruLoc));
					} else {
						if (ScoreboardCustomMenu.haveLines("2", player)) ScoreboardManager.scoreboardGame.get(player).setLine(2, "§7Trikru: §f" + (int) player.getLocation().distance(ScoreboardCustomMenu.trikruLoc) + " " + getArrowColor(player.getLocation().distance(ScoreboardCustomMenu.trikruLoc)) + LocationArrowUtils.calculateArrow(player, ScoreboardCustomMenu.trikruLoc));
					}
					
					if ((int) player.getLocation().distance(Main.INSTANCE.polisLocation) > 600) {
						if (ScoreboardCustomMenu.haveLines("3", player)) ScoreboardManager.scoreboardGame.get(player).setLine(3, "§7Polis: §bFar " + getArrowColor(player.getLocation().distance(Main.INSTANCE.polisLocation)) + LocationArrowUtils.calculateArrow(player, Main.INSTANCE.polisLocation));
					} else {
						if (ScoreboardCustomMenu.haveLines("3", player)) ScoreboardManager.scoreboardGame.get(player).setLine(3, "§7Polis: §f" + (int) player.getLocation().distance(Main.INSTANCE.polisLocation) + " " + getArrowColor(player.getLocation().distance(Main.INSTANCE.polisLocation)) + LocationArrowUtils.calculateArrow(player, Main.INSTANCE.polisLocation));
					}
					
					if ((int) player.getLocation().distance(ScoreboardCustomMenu.azgedaLoc) > 600) {
						if (ScoreboardCustomMenu.haveLines("4", player)) ScoreboardManager.scoreboardGame.get(player).setLine(4, "§7Azgedas: §bFar " + getArrowColor(player.getLocation().distance(ScoreboardCustomMenu.azgedaLoc)) + LocationArrowUtils.calculateArrow(player, ScoreboardCustomMenu.azgedaLoc));
					} else {
						if (ScoreboardCustomMenu.haveLines("4", player)) ScoreboardManager.scoreboardGame.get(player).setLine(4, "§7Azgedas: §f" + (int) player.getLocation().distance(ScoreboardCustomMenu.azgedaLoc) + " " + getArrowColor(player.getLocation().distance(ScoreboardCustomMenu.azgedaLoc)) + LocationArrowUtils.calculateArrow(player, ScoreboardCustomMenu.azgedaLoc));
					}
					
					if ((int) player.getLocation().distance(ScoreboardCustomMenu.montweatherLoc) > 600) {
						if (ScoreboardCustomMenu.haveLines("5", player)) ScoreboardManager.scoreboardGame.get(player).setLine(5, "§7Mont Weather: §bFar " + getArrowColor(player.getLocation().distance(ScoreboardCustomMenu.montweatherLoc)) + LocationArrowUtils.calculateArrow(player, ScoreboardCustomMenu.montweatherLoc));

					} else {
						if (ScoreboardCustomMenu.haveLines("5", player)) ScoreboardManager.scoreboardGame.get(player).setLine(5, "§7Mont Weather: §f" + (int) player.getLocation().distance(ScoreboardCustomMenu.montweatherLoc) + " " + getArrowColor(player.getLocation().distance(ScoreboardCustomMenu.montweatherLoc)) + LocationArrowUtils.calculateArrow(player, ScoreboardCustomMenu.montweatherLoc));

					}
				}

				if (ScoreboardCustomMenu.haveLines("6", player)) ScoreboardManager.scoreboardGame.get(player).setLine(6, "§7Mes Coins: §e§l" + Main.INSTANCE.database.getCoins(player) + " ㊣");
			}
		}
	}
	
	public String getArrowColor(double d) {
		if (d >= 100) return "§c";
		if (d > 20  && d < 100) return "§e";
		if (d <= 20) return "§a";
		return "§a";
	}
}