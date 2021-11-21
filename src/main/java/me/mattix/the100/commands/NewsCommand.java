package me.mattix.the100.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NewsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if (args.length == 0) {
			player.sendMessage("§aBienenue dans cette version 3.0 du serveur =)");
			player.sendMessage("§aVoici les modifications qu'apportent cette Maj 3.0 :");
			player.sendMessage("  -§d§lNOUVEAU §r§b Ajout d'une Economie qui permettra de vous développer.");
			player.sendMessage("  -§d§lNOUVEAU §r§6Affichez votre nombre de coins dans votre scoreboard.");
			player.sendMessage("  -§d§lNOUVEAU §r§e§lSystème de quête autour de Polis. §a(/quete pour en savoir plus)");
			player.sendMessage("  -§d§lNOUVEAU §r§c§lDéfiez une Tribue ou un Joueur avec /duel.");
			player.sendMessage("  -§d§lNOUVEAU §r§eArène de Combat pour les Duels.");
			player.sendMessage("  -§d§lNOUVEAU §r§2La pluie est désormais ACIDE, prenez garde..");
			player.sendMessage("  -Correctifs de bugs et d'optimisations.");
			player.sendMessage("  -Nether regénéré.");
		}
		
		
		return true;
	}
}