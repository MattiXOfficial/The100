package me.mattix.the100.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mattix.the100.GamePlayer;
import me.mattix.the100.Main;
import me.mattix.the100.sql.DatabaseManager;
import me.mattix.the100.utils.PlayerUtils;

public class SetHomeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(player.getUniqueId()));
		
		System.out.println(gp.getName());
		
		if (args.length == 0) {
			if (!gp.isSethome()) {
				if (player.getWorld().getName().equals("world")) {
					// Sethome
					
					DatabaseManager.SQL_Update("UPDATE players_the100 SET home = '" + (int) player.getLocation().getX() + ";" + (int) player.getLocation().getY() + ";" + (int) player.getLocation().getZ() +  "' WHERE uuid_player = '" + player.getUniqueId().toString() + "'");
					player.sendMessage("§aVotre home a été défini.");
					gp.setSethome(true);
				} else {
					player.sendMessage("§cVous ne pouvez effectuer cette commande que dans l'Overworld.");
				}
			} else {
				player.sendMessage("§cVous ne pourrez redéfinir votre home que dans 1h.");
			}
			
			Bukkit.getScheduler().runTaskLater(Main.INSTANCE, new Runnable() {
				
				@Override
				public void run() {
					gp.setSethome(false);
				}
			}, 20 * 3600);
		}
		
		
		return true;
	}
}