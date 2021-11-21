package me.mattix.the100.fireworks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.scheduler.BukkitTask;

import me.mattix.the100.Main;

public class FireworkLaunch {
	
	public BukkitTask task;
	public BukkitTask sayTask;
	public int durationSay = 7;
	public int duration = 22;
	
	public FireworkLaunch() {
		this.addType();
		this.startFreworkLaunchPart01();
	}
	
	public void startFreworkLaunchPart01() {
		FireworkCommander.status = 1;
		launchFireworks();
	}
	
	private void addType() {
		FireworkCommander.fireworksType.add(Type.BALL_LARGE);
		FireworkCommander.fireworksType.add(Type.BALL);
	}
	
	private Color getColor() {
		Random random = new Random();
		List<Color> colors = new ArrayList<>();
		
		colors.add(Color.RED);
		colors.add(Color.FUCHSIA);
		colors.add(Color.WHITE);
		colors.add(Color.PURPLE);
		colors.add(Color.BLUE);
		
		return colors.get(random.nextInt(colors.size()));
	}

	public void launchFireworks() {
		task = Bukkit.getScheduler().runTaskTimer(Main.INSTANCE, new Runnable() {
			
			@Override
			public void run() {
				duration--;

				if (duration == -1) {
					duration = 22;
					task.cancel();
					return;
				}
				
				if (duration == 1) {
					FireworkCommander.status = 2;
					for (int x = 0; x < FireworkCommander.fireworksLocation.size(); x++) {
						FireworksUtils.createFirework(FireworkCommander.fireworksLocation.get(x), getColor(), 2, Type.STAR);
						FireworksUtils.createFirework(FireworkCommander.fireworksLocation.get(x), getColor(), 2, Type.STAR);
					}
				}
				
				if (duration != 3 && duration != 2 && duration != 1 && duration != 0) {
					for (int x = 0; x <= FireworkCommander.fireworksLocation.size(); x++) {
						FireworksUtils.createFirework(FireworkCommander.fireworksLocation.get(new Random().nextInt(FireworkCommander.fireworksLocation.size())), getColor(), 3, FireworkCommander.fireworksType.get(new Random().nextInt(FireworkCommander.fireworksType.size())));
					}
				}
			}
		}, 20, 30);
	}
}