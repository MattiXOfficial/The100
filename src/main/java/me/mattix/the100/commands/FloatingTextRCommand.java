package me.mattix.the100.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mattix.the100.GamePlayer;
import me.mattix.the100.utils.FloatingTextUtils;
import me.mattix.the100.utils.PlayerUtils;

public class FloatingTextRCommand implements CommandExecutor {

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
			player.sendMessage("§cUsage: /flr <id>");
			return true;
		}
		
		if (args.length == 1) {
			String id = args[0];
			FloatingTextUtils.deleteFloatingText(id);
			player.sendMessage("§e" + id + " §aa été supprimé.");
			
			/*for (Chunk chunk : Bukkit.getWorld("world").getLoadedChunks()) {
				for (org.bukkit.entity.Entity entity : chunk.getEntities()) {
					if (entity instanceof ArmorStand) {
						if (!((ArmorStand) entity).isVisible()) {
							entity.remove();
						}
					}
				}
			}*/
			
			return true;
			
		}
		
		return true;
	}
}