package me.mattix.the100.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mattix.the100.GamePlayer;
import me.mattix.the100.utils.PlayerUtils;

public class AnnonceCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(player.getUniqueId()));
		
		// si il est commandant
		if (gp.isHeda()) {
			
			if (args.length == 0) {
				player.sendMessage("/bc <msg>");
				return true;
			}
			
			if (args.length >= 1) {
				StringBuilder broadcast = new StringBuilder("");

				for (String morceau : args) {
					if (broadcast.length() != 0)
						broadcast.append(" ");
					broadcast.append(morceau);
				}
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage("§6Polis §f| §d" + broadcast);
				Bukkit.broadcastMessage(" ");
				Bukkit.getOnlinePlayers().forEach(players -> {
					players.playSound(players.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1f, 1f);
				});
			}
			
		} else {
			player.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande.");
		}
		
		return true;
	}
}