package me.mattix.the100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.mattix.the100.commands.nick.NickManager;
import me.mattix.the100.menus.ScoreboardCustomMenu;
import me.mattix.the100.sql.DatabaseManager;
import me.mattix.the100.utils.LocationArrowUtils;
import me.mattix.the100.utils.PlayerUtils;
import me.mattix.the100.utils.TeamsTagsManager;


public class GamePlayer {

	private String name;
	private String people_name;
	private String prefix;
	private int lastLine;
	private int leader, heda, countClaims;
	private boolean sethome;
	private Player player;
	private String nickName;
	
	private Location pos1;
	private Location pos2;

	private float coins;
	
	public static Map<String, GamePlayer> gamePlayers = new HashMap<>();
	public List<String> ids;
	
	public GamePlayer(String playerName) {
		this.player = Bukkit.getPlayer(playerName);
		this.name = PlayerUtils.getPlayerName(player.getUniqueId());
		this.people_name = Main.INSTANCE.database.getPeople(Bukkit.getPlayer(playerName));
		this.leader = Main.INSTANCE.database.getLeader(Bukkit.getPlayer(playerName));
		this.heda = Main.INSTANCE.database.getHeda(Bukkit.getPlayer(playerName));
		this.prefix = Main.INSTANCE.database.getPrefix(Bukkit.getPlayer(playerName));
		this.lastLine = 1;
		this.sethome = false;
		this.coins = Main.INSTANCE.database.getCoins(player);
		
		for (String uuidStr : Main.INSTANCE.config.get().getKeys(false)) {
			if (player.getUniqueId().toString().equals(uuidStr.substring(0, uuidStr.length() - 2))) {
				countClaims++;
			}
		}
		
		this.nickName = NickManager.getNickName(player.getUniqueId());
		this.ids = getIDS(player.getUniqueId());
		gamePlayers.put(this.name, this);
		
		this.setScoreboardSettings();
	}
	
	public void setScoreboardSettings() {
		if (player.getWorld().getName().equals("world")) {
			Main.INSTANCE.playersLines.put(1, "§7Skaikru: §f" + (int) player.getLocation().distance(ScoreboardCustomMenu.skaikruLoc) + " " + getArrowColor(player.getLocation().distance(ScoreboardCustomMenu.skaikruLoc)) + LocationArrowUtils.calculateArrow(player, ScoreboardCustomMenu.skaikruLoc));
			Main.INSTANCE.playersLines.put(2, "§7Trikru: §f" + (int) player.getLocation().distance(ScoreboardCustomMenu.trikruLoc) + " " + getArrowColor(player.getLocation().distance(ScoreboardCustomMenu.trikruLoc)) + LocationArrowUtils.calculateArrow(player, ScoreboardCustomMenu.trikruLoc));
			Main.INSTANCE.playersLines.put(3, "§7Polis: §f" + (int) player.getLocation().distance(Main.INSTANCE.polisLocation) + " " + getArrowColor(player.getLocation().distance(Main.INSTANCE.polisLocation)) + LocationArrowUtils.calculateArrow(player, Main.INSTANCE.polisLocation));
			Main.INSTANCE.playersLines.put(4, "§7Azgeda: §f" + (int) player.getLocation().distance(ScoreboardCustomMenu.azgedaLoc) + " " + getArrowColor(player.getLocation().distance(ScoreboardCustomMenu.azgedaLoc)) + LocationArrowUtils.calculateArrow(player, ScoreboardCustomMenu.azgedaLoc));
			Main.INSTANCE.playersLines.put(5, "§7Mont Weather: §f" + (int) player.getLocation().distance(ScoreboardCustomMenu.montweatherLoc) + " " + getArrowColor(player.getLocation().distance(ScoreboardCustomMenu.montweatherLoc)) + LocationArrowUtils.calculateArrow(player, ScoreboardCustomMenu.montweatherLoc));
			Main.INSTANCE.playersLines.put(6, "§7Mes Coins: §e§l" + Main.INSTANCE.database.getCoins(player) + " ㊣");
		}
	}

	public float getCoins() { return coins; }

	public void setCoins(float coins) { this.coins = coins; }

	public String getPeopleName() {
		return people_name;
	}

	public void setCountClaims(int countClaims) {
		this.countClaims = countClaims;
	}
	
	public int getCountClaims() {
		return countClaims;
	}
	
	public Location getPos1() {
		return pos1;
	}

	public Location getPos2() {
		return pos2;
	}
	
	public void setPos1(Location pos1) {
		this.pos1 = pos1;
	}
	
	public void setPos2(Location pos2) {
		this.pos2 = pos2;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setSethome(boolean sethome) {
		this.sethome = sethome;
	}
	
	public List<String> getIDS(UUID uuid) {
		Object cache;
		
		cache = DatabaseManager.SQL_Receive("select uuid_player FROM players_the100 WHERE pseudo_player = '" + getName() + "'", "uuid_player");

		if (cache != null) {
			String ids = (String) DatabaseManager.SQL_Receive("SELECT scoreboardlines FROM players_the100 WHERE uuid_player = '" + uuid.toString() + "'", "scoreboardlines");
			List<String> l = new ArrayList<>(Arrays.asList(ids.split(";")));
			return l;
		}
		return null;
	}
	
	public boolean isSethome() {
		return sethome;
	}
	
	public int getLastLine() {
		return lastLine;
	}
	
	public String getPcode() {
		
		if (people_name.equalsIgnoreCase("heda")) return "§c§1Admin";
		if (people_name.equalsIgnoreCase("hedaflame") && !isLeader()) return "§c§2Admin";
		if (people_name.equalsIgnoreCase("hedagarde") && !isLeader()) return "§c§3Admin";
		if (people_name.equalsIgnoreCase("skaikru") && isLeader()) return "§c§4Admin";
		if (people_name.equalsIgnoreCase("skaikru") && !isLeader()) return "§c§5Admin";
		if (people_name.equalsIgnoreCase("trikru") && isLeader()) return "§c§6Admin";
		if (people_name.equalsIgnoreCase("trikru") && !isLeader()) return "§c§7Admin";
		if (people_name.equalsIgnoreCase("trishanakru") && isLeader()) return "§c§8Admin";
		if (people_name.equalsIgnoreCase("trishanakru") && !isLeader()) return "§c§9Admin";
		if (people_name.equalsIgnoreCase("azgeda") && isLeader()) return "§d§1Admin";
		if (people_name.equalsIgnoreCase("azgeda") && !isLeader()) return "§d§2Admin";
		if (people_name.equalsIgnoreCase("none")) return "§d§3Admin";
		
	    return "§d§3Admin";
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String prefix) {
		TeamsTagsManager.setNameTag(Bukkit.getPlayer(name), getPcode(), prefix, "");
		Main.INSTANCE.database.setPrefix(Bukkit.getPlayer(name), prefix);
		this.prefix = prefix;
	}
	
	public boolean isLeader() {
		return leader == 1;
	}
	
	public boolean isHeda() {
		return heda == 1;
	}
	
	public void setHeda(int heda) {
		this.heda = heda;
		
		Main.INSTANCE.database.setHeda(Bukkit.getPlayer(name), heda);
	}
	
	public void setLeader(int leader) {
		this.leader = leader;
	
		Main.INSTANCE.database.setLeader(Bukkit.getPlayer(name), leader);
	}
	
	public void setPeople(String people_name) {
		this.people_name = people_name;
		
		Main.INSTANCE.database.setPeople(Bukkit.getPlayer(name), people_name);
	}
	
	public String getArrowColor(double d) {
		if (d >= 100) return "§c";
		if (d > 20  && d < 100) return "§e";
		if (d <= 20) return "§a";
		return "§a";
	}

	public String getName() {
		return name;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
		Main.INSTANCE.database.setNickName(player, nickName);
	}
}