package me.mattix.the100.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mattix.the100.GamePlayer;
import me.mattix.the100.utils.PlayerUtils;

public class PlayerChatListener implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(player.getUniqueId()));
		
		event.setCancelled(true);
		
		if (event.getMessage().startsWith("!") && !gp.getPeopleName().equals("none")) {
			event.setFormat("§7" + player.getName() + ": §7" + event.getMessage().replace("!", ""));
			for (Player players : Bukkit.getOnlinePlayers()) {
				players.sendMessage(event.getFormat());
			}
			return;
		}
		
		if (gp.getPeopleName().equals("heda") || gp.getPeopleName().equals("hedaflame") || gp.getPeopleName().equals("hedagarde")) {
			
			event.setFormat("§cPolis " + player.getName() + ": §f" + event.getMessage());
			for (Player trikrus : Bukkit.getOnlinePlayers()) {
				GamePlayer gpt = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(trikrus.getUniqueId()));
				if (gpt.getPeopleName().equals("heda") || gpt.getPeopleName().equals("hedaflame") || gpt.getPeopleName().equals("hedagarde")) {
					trikrus.sendMessage(event.getFormat());
					return;
				}
			}
		} else if (gp.getPeopleName().equals("trikru")) {
			event.setFormat("§6Trikru " + player.getName() + ": §f" + event.getMessage());
			for (Player trikrus : Bukkit.getOnlinePlayers()) {
				GamePlayer gpt = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(trikrus.getUniqueId()));
				if (gpt.getPeopleName().equals("trikru")) {
					trikrus.sendMessage(event.getFormat());
					return;
				}
			}
		} else if (gp.getPeopleName().equals("skaikru")) {
			event.setFormat("§eSkaikru " + player.getName() + ": §f" + event.getMessage());
			for (Player skaikru : Bukkit.getOnlinePlayers()) {
				GamePlayer gpt = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(skaikru.getUniqueId()));
				if (gpt.getPeopleName().equals("skaikru")) {
					skaikru.sendMessage(event.getFormat());
					return;
				}
			}
		}  else if (gp.getPeopleName().equals("trishanakru")) {

			event.setFormat("§2Trishanakru " + player.getName() + ": §f" + event.getMessage());
			for (Player trishana : Bukkit.getOnlinePlayers()) {
				GamePlayer gpt = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(trishana.getUniqueId()));
				if (gpt.getPeopleName().equals("trishanakru")) {
					trishana.sendMessage(event.getFormat());
					return;
				}
			}
		} else if (gp.getPeopleName().equals("azgeda")) {
			event.setFormat("§bAzgeda " + player.getName() + ": §f" + event.getMessage());
			for (Player azgeda : Bukkit.getOnlinePlayers()) {
				GamePlayer gpt = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(azgeda.getUniqueId()));
				if (gpt.getPeopleName().equals("azgeda")) {
					azgeda.sendMessage(event.getFormat());
					return;
				}
			}
		} else if (gp.getPeopleName().equals("none")) {
			event.setFormat("§7[Sans clan] " + player.getName() + ": §7" + event.getMessage());
			for (Player players : Bukkit.getOnlinePlayers()) {
				players.sendMessage(event.getFormat());
			}
		}
	}
}