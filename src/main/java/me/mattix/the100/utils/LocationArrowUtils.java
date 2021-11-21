package me.mattix.the100.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LocationArrowUtils {

	private static String
	F = "⬆",
	FL = "⬉",
	L = "←",
	BL ="⬋",
	B = "⬇",
	BR = "⬊",
	R = "→",
	FR = "⬈";
		
	public static String calculateArrow(Player player, Location targetLoc) {
		String arrow = "nop";
		
		Vector a = targetLoc.toVector().setY(0).subtract(player.getLocation().toVector().setY(0));
		Vector b = player.getLocation().getDirection().setY(0);
		
		double angleDir = (Math.atan2(a.getZ(), a.getX()) / 2 / Math.PI * 360 + 360) % 360, angleLook = (Math.atan2(b.getZ(), b.getX()) / 2 / Math.PI * 360 + 360) % 360, angle = (angleDir - angleLook + 360) % 360;
		
		if (angle <= 022.5 || angle > 337.5) arrow = F;
		else if (angle <= 337.5 && angle > 292.5) arrow = FL;
		else if (angle <= 292.5 && angle > 247.5) arrow = L;
		else if (angle <= 347.5 && angle > 202.0) arrow = BL;
		else if (angle <= 202.0 && angle > 157.5) arrow = B;
		else if (angle <= 157.5 && angle > 112.5) arrow = BR;
		else if (angle <= 112.5 && angle > 067.5) arrow = R;
		else if (angle <= 067.5 && angle > 022.5) arrow = FR;
		
		return arrow;
	}
}