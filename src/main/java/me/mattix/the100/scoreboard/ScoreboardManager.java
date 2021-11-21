package me.mattix.the100.scoreboard;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.mattix.the100.utils.PlayerUtils;

public class ScoreboardManager {
	
	public Player player;
	public ScoreboardSign scoreboardSign;
	public static Map<Player, ScoreboardSign> scoreboardGame = new HashMap<>();
  
  	public ScoreboardManager(Player player) {
  		this.player = player;
  		this.scoreboardSign = new ScoreboardSign(player, PlayerUtils.getPlayerName(player.getUniqueId()));
  		scoreboardGame.put(player, this.scoreboardSign);
  	}
  
  	public void loadScoreboard() {
  		this.scoreboardSign.setObjectiveName(ChatColor.YELLOW + " " + ChatColor.GOLD + ChatColor.BOLD + "Carte des Expéditions");
  		this.scoreboardSign.create();
    
  		((ScoreboardSign) scoreboardGame.get(this.player)).setLine(0, "§2");
  	}
}