package me.mattix.the100.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class FloatingTextUtils {
	
	private static Map<String, ArmorStand> armorStands = new HashMap<>();

	public static void setFloatingText(Location location, String text, String name) {
		
		Location customLocation = new Location(location.getWorld(), location.getX(), location.getY() + 0.02d, location.getZ());
		ArmorStand armorStand = (ArmorStand) Bukkit.getWorld("world").spawnEntity(customLocation, EntityType.ARMOR_STAND);
		armorStand.setVisible(false);
		armorStand.setCustomName(text);
		armorStand.setCustomNameVisible(true);
		armorStand.setGravity(false);
		
		armorStands.put(name, armorStand);
	}
	
	public static void addSubFloatingText(Location location, String text, String name) {
		
		Location customLocation = new Location(location.getWorld(), location.getX(), location.getY() - 0.23d, location.getZ());
		ArmorStand armorStand = (ArmorStand) Bukkit.getWorld("world").spawnEntity(customLocation, EntityType.ARMOR_STAND);
		armorStand.setVisible(false);
		armorStand.setCustomName(text);
		armorStand.setCustomNameVisible(true);
		armorStand.setGravity(false);
		
		armorStands.put(name, armorStand);
	}
	
	public static void deleteFloatingText(String name) {
		if (armorStands.containsKey(name)) {
			ArmorStand armorStand = armorStands.get(name);
			armorStand.remove();
			armorStands.remove(name);
		}
	}
	
	public static ArmorStand getFloatingText(String name) {
		return armorStands.get(name);
	}
}