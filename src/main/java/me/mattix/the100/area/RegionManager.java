package me.mattix.the100.area;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.mattix.the100.GamePlayer;
import me.mattix.the100.utils.PlayerUtils;

public class RegionManager {

	public Location minLoc, maxLoc;
	public String name, team;
	
	public RegionManager(Location firstPoint, Location secondPoint, String name, String team) {
		minLoc = new Location(firstPoint.getWorld(), min(firstPoint.getX(), secondPoint.getX()), min(firstPoint.getY(), secondPoint.getY()), min(firstPoint.getZ(), secondPoint.getZ()));
		maxLoc = new Location(firstPoint.getWorld(), max(firstPoint.getX(), secondPoint.getX()), max(firstPoint.getY(), secondPoint.getY()), max(firstPoint.getZ(), secondPoint.getZ()));
		this.name = name;
		this.team = team;
	}

	public String getName() {
		return name;
	}
	
	public double min(double a, double b) {
		return a < b ? a : b;
	}

	public double max(double a, double b) {
		return a > b ? a : b;
	}
	
	public boolean isInArea(Location loc) {
		return (minLoc.getX() <= loc.getX() && minLoc.getY() <= loc.getY() && minLoc.getZ() <= loc.getZ() && maxLoc.getX() >= loc.getX() && maxLoc.getY() >= loc.getY() && maxLoc.getZ() >= loc.getZ());
	}
	
	public Location getMiddle() {
		double a, b;
		a = (maxLoc.getX() - minLoc.getX()) / 2D + minLoc.getX();
		b = (maxLoc.getZ() - minLoc.getZ()) / 2D + minLoc.getZ();
		
		return new Location(Bukkit.getWorld("world"), a, minLoc.getY(), b);
	}
	
	public boolean hasPermission(Player player, String team) {
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(player.getUniqueId()));
		if (team.equalsIgnoreCase("everyone")) return false;
		if (gp.getPeopleName().equals(team)) return true;
		if (team.equals(player.getName())) return true;
		return false;
	}
	
	public List<Location> getArea() {
		List<Location> blocksLocation = new ArrayList<>();
		
		for (int x = minLoc.getBlockX(); x <= maxLoc.getBlockX(); x++) {
			 for (int z = minLoc.getBlockZ(); z <= maxLoc.getBlockZ(); z++) {
				 for (int y = minLoc.getBlockY(); y <= maxLoc.getBlockY(); y++)
				 blocksLocation.add(new Location(minLoc.getWorld(), x, y, z));
			 }
		}
		return blocksLocation;
	}
	
	public void setBlock() {
		List<Location> blocksLocation = new ArrayList<>();
		Random random = new Random();
		
		for (int x = minLoc.getBlockX(); x <= maxLoc.getBlockX(); x++) {
			 for (int z = minLoc.getBlockZ(); z <= maxLoc.getBlockZ(); z++) {
				  // minLoc.getWorld().getBlockAt(x, 76, z).setType(mat);
				 
				 blocksLocation.add(new Location(Bukkit.getWorld("world"), x, 59, z));
			 }
		}
		
		for (int i = 0; i <= 200; i++) {
			
			int randomloc = random.nextInt(blocksLocation.size());
			
			blocksLocation.get(randomloc).getBlock().setType(Material.IRON_ORE);
			blocksLocation.remove(blocksLocation.get(randomloc));
		}
		
		for (int o = 0; o <= 100; o++) {
			int randomloc = random.nextInt(blocksLocation.size());
			
			blocksLocation.get(randomloc).getBlock().setType(Material.DIAMOND_ORE);
			blocksLocation.remove(blocksLocation.get(randomloc));
		}
		
		for (int o = 0; o <= 110; o++) {
			int randomloc = random.nextInt(blocksLocation.size());
			
			blocksLocation.get(randomloc).getBlock().setType(Material.GOLD_ORE);
			blocksLocation.remove(blocksLocation.get(randomloc));
		}
		
		for (int o = 0; o <= 89; o++) {
			int randomloc = random.nextInt(blocksLocation.size());
			
			blocksLocation.get(randomloc).getBlock().setType(Material.REDSTONE_ORE);
			blocksLocation.remove(blocksLocation.get(randomloc));
		}
		
		for (int o = 0; o <= 100; o++) {
			int randomloc = random.nextInt(blocksLocation.size());
			
			blocksLocation.get(randomloc).getBlock().setType(Material.LAPIS_ORE);
			blocksLocation.remove(blocksLocation.get(randomloc));
		}
		
		for (int o = 0; o <= blocksLocation.size(); o++) {
			
			int randomloc = random.nextInt(blocksLocation.size());
			
			blocksLocation.get(randomloc).getBlock().setType(Material.COAL_ORE);
			blocksLocation.remove(blocksLocation.get(randomloc));
				
		}
	}

	public String getTeam() {
		return team;
	}
}