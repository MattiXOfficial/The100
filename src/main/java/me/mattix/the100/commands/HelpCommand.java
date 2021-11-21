package me.mattix.the100.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if (args.length == 0) {
			sendHelp(player);
			return true;
		}
		
		return true;
	}
	
	private void sendHelp(Player player) {
		player.sendMessage("§7-------------- §e§lAide §r§8» §e§lCommandes §r§7--------------");
		player.sendMessage("§8● §6/shop §8» §fOuvrir la boutique de blocs.");
		player.sendMessage("§8● §6/sethome §8» §fDéfinir votre home.");
		player.sendMessage("§8● §6/home §8» §fSe téléporter à votre home.");
		player.sendMessage("§8● §6/settings §8» §fParamètrer votre interface.");
		player.sendMessage("§8● §6/nick §8» §fChanger votre pseudo.");
		player.sendMessage(" ");
	}
}