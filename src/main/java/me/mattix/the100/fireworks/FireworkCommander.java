 package me.mattix.the100.fireworks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

public class FireworkCommander {
	
	public static List<Location> fireworksLocation = new ArrayList<>();
	public static List<Type> fireworksType = new ArrayList<>();
	public int duration = 22;
	
	public int durationSay = 7;
	public BukkitTask task;
	public BukkitTask particleTask;
	public BukkitTask sayTask;
	
	public static int status;
	
	public FireworkCommander() {
		this.addLocations();
		new FireworkLaunch();
	}
	
	private void addLocations() {
		fireworksLocation.add(new Location(Bukkit.getWorld("world"), 689.500, 75.0, -25.500));
		fireworksLocation.add(new Location(Bukkit.getWorld("world"), 691.500, 72.0, -68.500));
		fireworksLocation.add(new Location(Bukkit.getWorld("world"), 636.500, 70.0, -59.500));
		fireworksLocation.add(new Location(Bukkit.getWorld("world"), 638.500, 77.0, -11.500));
		fireworksLocation.add(new Location(Bukkit.getWorld("world"), 662.500, 89.0, 22.500));
		fireworksLocation.add(new Location(Bukkit.getWorld("world"), 716.500, 89.0, 1.500));
	}
}