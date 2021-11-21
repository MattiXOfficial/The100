package me.mattix.the100.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mattix.the100.GamePlayer;
import me.mattix.the100.utils.FloatingTextUtils;
import me.mattix.the100.utils.PlayerUtils;

public class FloatingTextCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(player.getUniqueId()));
		
		if (!gp.isHeda()) {
			player.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande.");
			return true;
		}
		
		
		if (args.length == 0) {
			player.sendMessage("§cUsage: /fl <id> <text>");
			return true;
		}
		
		if (args.length > 1) {
			StringBuilder broadcast = new StringBuilder("");
			
			for (String morceau : args) {
				if (broadcast.length() != 0)
					broadcast.append(" ");
				broadcast.append(morceau);
			}
			
			String id = args[1];
			FloatingTextUtils.setFloatingText(player.getLocation(), broadcast.toString().replace('&', '§'), id);
			
		}
		
		return true;
	}
}