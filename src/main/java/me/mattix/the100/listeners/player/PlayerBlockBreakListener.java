package me.mattix.the100.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.mattix.the100.GamePlayer;
import me.mattix.the100.Main;
import me.mattix.the100.area.RegionManager;
import me.mattix.the100.utils.PlayerUtils;

public class PlayerBlockBreakListener implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		if (event.getBlock().getType() == Material.ENDER_PORTAL_FRAME && !event.getPlayer().isOp()) {
			event.setCancelled(true);
		}
		
		for (int x = 0; x < Main.INSTANCE.regions.size(); x++) {
			if (Main.INSTANCE.regions.get(x).isInArea(event.getBlock().getLocation()) && !Main.INSTANCE.regions.get(x).hasPermission(event.getPlayer(), Main.INSTANCE.regions.get(x).getTeam())) {
				if (event.getBlock().getType() != Material.DIAMOND_ORE && event.getBlock().getType() != Material.LAPIS_ORE && event.getBlock().getType() != Material.GOLD_ORE && event.getBlock().getType() != Material.COAL_ORE && event.getBlock().getType() != Material.GLOWING_REDSTONE_ORE && event.getBlock().getType() != Material.IRON_ORE) {
					event.setCancelled(true);
					event.getPlayer().sendMessage("§cCette zone est protégée. Vous n'avez pas l'autorisation de casser ici.");
					System.out.println("§c" + event.getPlayer().getName() + " a tenté de grief §e" + Main.INSTANCE.regions.get(x).getName());
				}
			}
		}
	}
	
	@EventHandler
	public void onBLockPlace(BlockPlaceEvent event) {
		for (int x = 0; x < Main.INSTANCE.regions.size(); x++) {
			if (Main.INSTANCE.regions.get(x).isInArea(event.getBlock().getLocation()) && !Main.INSTANCE.regions.get(x).hasPermission(event.getPlayer(), Main.INSTANCE.regions.get(x).getTeam())) {
				event.setCancelled(true);
				event.getPlayer().sendMessage("§cCette zone est protégée. Vous n'avez pas l'autorisation de build ici.");
				System.out.println("§c" + event.getPlayer().getName() + " a tenté de grief §e" + Main.INSTANCE.regions.get(x).getName());
			}
		}
	}

	@EventHandler
	public void onPlace(PlayerInteractEvent event) {


		Player player = event.getPlayer();

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			for (int x = 0; x < Main.INSTANCE.regions.size(); x++) {
				if (Main.INSTANCE.regions.get(x).isInArea(event.getClickedBlock().getLocation()) && !Main.INSTANCE.regions.get(x).hasPermission(event.getPlayer(), Main.INSTANCE.regions.get(x).getTeam())) {
					event.setCancelled(true);
					player.sendMessage("§cCette zone est protégée. Vous n'avez pas l'autorisation d'intéragir ici.");
					Material itemType = player.getInventory().getItemInMainHand().getType();
					if (itemType == Material.WATER_BUCKET || itemType == Material.LAVA_BUCKET || itemType == Material.FLINT_AND_STEEL) {
						event.setCancelled(true);
						player.sendMessage("§cCette zone est protégée. Vous n'avez pas l'autorisation d'intéragir ici.");
					}
				}
			}
		} else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			// claim pos
			
			if (player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_HOE) {
				event.setCancelled(true);
				GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(player.getUniqueId()));
				
				if (gp.getPos1() == null) {
					
					if (gp.getCountClaims() >= 3) {
						player.sendMessage("§cVous avez atteint le maximum de claim.");
						return;
					}
					
					// set pos1
					gp.setPos1(event.getClickedBlock().getLocation());
					player.sendMessage("§dPosition #1 de votre claim définie.");
					
					Bukkit.getScheduler().runTaskLater(Main.INSTANCE, () -> {
						gp.setPos1(null);
						gp.setPos2(null);
					}, 20 * 60 * 5);
					
					return;
				}
				
				if (gp.getPos1() != null && gp.getPos2() == null) {
					
					gp.setCountClaims(gp.getCountClaims() + 1);
					
					gp.setPos2(event.getClickedBlock().getLocation());

					if (gp.getPos1().distance(gp.getPos2()) > 250 || !gp.getPos1().getWorld().equals(gp.getPos2().getWorld())) {
						player.sendMessage("§cVous ne pouvez pas faire une zone aussi grande. (> 250) Veuillez réessayer.");
						gp.setPos1(null);
						gp.setPos2(null);
						return;
					}
					RegionManager region = new RegionManager(gp.getPos1(), gp.getPos2(), gp.getPeopleName() + "_base" + gp.getCountClaims(), gp.getPeopleName());
					
					for (int x = 0; x < Main.INSTANCE.regions.size(); x++) {
						RegionManager rGet = Main.INSTANCE.regions.get(x);

						if (rGet.isInArea(region.maxLoc) || rGet.isInArea(region.minLoc) || rGet.isInArea(region.getMiddle())) {
							player.sendMessage("§cVous ne pouvez pas claim sur une zone appartenant à un joueur ou un peuple.");
							gp.setPos1(null);
							gp.setPos2(null);
							return;
						}
					}
					
					
					String people_restriction = (gp.getPeopleName().equals("none")) ? player.getName() : gp.getPeopleName();
					
					String[] loc = new String [] { "" + gp.getPos1().getX(), "" + gp.getPos1().getY(), "" + gp.getPos1().getZ(), "" + gp.getPos2().getX(), "" + gp.getPos2().getY(),  "" + gp.getPos2().getZ(), event.getPlayer().getLocation().getWorld().getName(), people_restriction + "_base" + gp.getCountClaims(), people_restriction};
					
					Main.INSTANCE.config.get().set(player.getUniqueId().toString() + "_" + gp.getCountClaims(), loc);
					Main.INSTANCE.config.save();
					
					Main.INSTANCE.regions.add(region);
					Main.INSTANCE.regionsid.put(player.getUniqueId(), region);
					
					player.sendMessage("§dPosition #2 de votre claim définie.");
					player.sendMessage("");
					player.sendMessage("§aVous venez de claim cette zone.");
					
					gp.setPos1(null);
					gp.setPos2(null);
					
					return;
				}
			}
		}
	}
}