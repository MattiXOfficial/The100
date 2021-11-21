package me.mattix.the100.fireworks;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworksUtils {

	public static void createFirework(Location loc, Color color, int power, Type type) {
		Firework firework = (Firework) Bukkit.getWorld("world").spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fireworkMeta = firework.getFireworkMeta();
		
		FireworkEffect effect = FireworkEffect.builder().flicker(new Random().nextBoolean()).with(type).withColor(color).trail(false).build();
		fireworkMeta.addEffect(effect);
		fireworkMeta.setPower(power);
		firework.setFireworkMeta(fireworkMeta);
	}
}