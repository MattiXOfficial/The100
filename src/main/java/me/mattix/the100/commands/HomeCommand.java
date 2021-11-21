package me.mattix.the100.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mattix.the100.GamePlayer;
import me.mattix.the100.Main;
import me.mattix.the100.sql.DatabaseManager;
import me.mattix.the100.utils.PlayerUtils;

public class HomeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if (args.length == 0) {
			System.out.println("" +getHome(player.getUniqueId()).size() );
			if (getHome(player.getUniqueId()).size() == 3) {
				// tp le joueur à son home
				player.sendMessage("§bVous allez être dirigé vers votre home dans 5 secondes.");
				Bukkit.getScheduler().runTaskLater(Main.INSTANCE, new Runnable() {
					
					@Override
					public void run() {
						player.teleport(new Location(Bukkit.getWorld("world"), Double.parseDouble(getHome(player.getUniqueId()).get(0)), Double.parseDouble(getHome(player.getUniqueId()).get(1)), Double.parseDouble(getHome(player.getUniqueId()).get(2))));
					}
				}, 20 * 5L);
			} else {
				player.sendMessage("§cVous ne possédez pas de home.");
			}
		}
		
		return true;
	}
	
	public List<String> getHome(UUID uuid) {
		Object cache;
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(uuid));
		
		cache = DatabaseManager.SQL_Receive("select uuid_player FROM players_the100 WHERE pseudo_player = '" + gp.getName() + "'", "uuid_player");

		if (cache != null) {
			String ids = (String) DatabaseManager.SQL_Receive("SELECT home FROM players_the100 WHERE uuid_player = '" + uuid.toString() + "'", "home");
			List<String> l = new ArrayList<>(Arrays.asList(ids.split(";")));
			return l;
		}
		return null;
	}
}