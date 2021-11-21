package me.mattix.the100.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mattix.the100.GamePlayer;
import me.mattix.the100.Main;
import me.mattix.the100.utils.PlayerUtils;
import me.mattix.the100.utils.TeamsTagsManager;

public class LeaderCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(player.getUniqueId()));
			
		if (args.length == 0) {
			player.sendMessage("/set [player] [trikru;skaikru;trishanakru;azgeda]");
			return true;
		}
		
		if (args.length > 1) {
			Player target = Bukkit.getPlayer(args[0]);
			String people = args[1];
			GamePlayer gpt = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(target.getUniqueId()));
			
			Bukkit.getScheduler().runTaskAsynchronously(Main.INSTANCE, () -> {
				
				if (people != null) {
					if (target != null && target.isOnline()) {

						// si le joueur n'est pas dans un clan
						if (gpt.getPeopleName().equals("none")) {
						
							if (people.equalsIgnoreCase("trikru")) {
								
								if (gp.isLeader() && gp.getPeopleName().equals("trikru")) {
									
									TeamsTagsManager.setNameTag(target, "§c§1Admin", "§6[Trikru] ", "");
									sendMessage("§6" +  target.getName() + " §fest devenu §6Trikru.");
									gpt.setPeople("trikru");
									gpt.setLeader(0);
									gpt.setPrefix("§6[Trikru] ");
								} else {
									player.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande.");
								}
								
							} else if (people.equalsIgnoreCase("azgeda")) {
								
								if (gp.isLeader() && gp.getPeopleName().equals("azgeda")) {
									TeamsTagsManager.setNameTag(target, "§c§4Admin", "§b[Azgeda] ", "");
									sendMessage("§6" +  target.getName() + " §fest devenu §bAzgeda.");
									gpt.setPeople("azgeda");
									gpt.setLeader(0);
									gpt.setPrefix("§b[Azgeda] ");	
								} else {
									player.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande.");
								}
								
							} else if (people.equalsIgnoreCase("skaikru")) {
								
								if (gp.isLeader() && gp.getPeopleName().equals("skaikru")) {
									TeamsTagsManager.setNameTag(target, "§c§5Admin", "§e[Skaikru] ", "");
									sendMessage("§6" +  target.getName() + " §fest devenu §eSkaikru.");
									gpt.setPeople("skaikru");
									gpt.setLeader(0);
									gpt.setPrefix("§e[Skaikru] ");
								} else {
									player.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande.");
								}
							}  else if (people.equalsIgnoreCase("trishanakru")) {
									
								if (gp.isLeader() && gp.getPeopleName().equals("trishanakru")) {
									TeamsTagsManager.setNameTag(target, "§c§6Admin", "§2[Trisha] ", "");
									sendMessage("§6" +  target.getName() + " §fest devenu §2Trishanakru.");
									gpt.setPeople("trishanakru");
									gpt.setLeader(0);
									gpt.setPrefix("§2[Trisha] ");
								} else {
									player.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande.");
								}
							}
						} else {
							player.sendMessage("§cCe joueur est déjà dans un clan. Utilisez cette commande avec parcimonie.");
						}
					}
				}
				
			});
		}
		
		return true;
	}
	
	private final void sendMessage(String msg) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(msg);
		}
	}
	
}