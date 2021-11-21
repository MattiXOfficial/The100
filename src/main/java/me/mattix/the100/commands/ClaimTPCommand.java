package me.mattix.the100.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mattix.the100.Main;
import me.mattix.the100.utils.PlayerUtils;

public class ClaimTPCommand implements CommandExecutor {

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if (args.length == 1) {
			String claim = args[0];
			
			if (claim.equalsIgnoreCase("list")) {
				for (String uuidStr : Main.INSTANCE.config.get().getKeys(false)) {
					player.sendMessage(PlayerUtils.getPlayerName(UUID.fromString(uuidStr)));
					
				}
				return true;
			}
			
			player.teleport(Main.INSTANCE.regionsid.get(PlayerUtils.getPlayerUUID(claim)).getMiddle());
			
			return true;
		}
		
		return true;
	}
}