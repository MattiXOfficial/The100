package me.mattix.the100.listeners.player;

import java.util.ArrayList;
import java.util.List;

import me.mattix.the100.duel.DuelProtocol;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import me.mattix.the100.GamePlayer;
import me.mattix.the100.Main;
import me.mattix.the100.commands.nick.NickManager;
import me.mattix.the100.menus.ScoreboardCustomMenu;
import me.mattix.the100.scoreboard.ScoreboardManager;
import me.mattix.the100.utils.PlayerUtils;

public class PlayerJoinListener implements Listener {
	
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		event.setQuitMessage(event.getPlayer().getName() + " §7§os'est déconnecté.");

		if (DuelProtocol.into_arena.contains(event.getPlayer())) {
			Main.INSTANCE.database.updateCoins(event.getPlayer(), -50);
			DuelProtocol.into_arena.remove(event.getPlayer());
			DuelProtocol.into_arena.forEach(player -> {
				player.sendMessage("§6Félicitations, tu viens de remporter ce duel.");
				float gain = getGain(player);
				player.sendMessage("§7Gain: §e+" + gain + " "); // gain en fonction de la tribue
				Main.INSTANCE.database.updateCoins(player, gain);
				// tp
				DuelProtocol.into_arena.remove(player);
			});
		}
		
		if (ScoreboardManager.scoreboardGame.containsKey(event.getPlayer())) {
			ScoreboardManager.scoreboardGame.remove(event.getPlayer());
		}
		
		GamePlayer.gamePlayers.remove(event.getPlayer().getName());
	}

	private float getGain(Player killer) {
		GamePlayer gamePlayer = GamePlayer.gamePlayers.get(killer.getName());
		if (gamePlayer.getPeopleName().equalsIgnoreCase("none")) return 50f;
		else return 100f;
	}
	
	@EventHandler
	public void onChangeWorld(PlayerChangedWorldEvent event) {
		World world = event.getFrom();
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(event.getPlayer().getUniqueId()));
		
		if (!world.getName().equals("world")) {
			gp.setScoreboardSettings();
		}
	}
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
			final Player player = event.getPlayer();
			
			player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100);
			player.setWalkSpeed(0.2f);
			
			if (!Main.INSTANCE.database.hasAccount(player)) {
				
				if (Main.INSTANCE.database.crash) {
					player.kickPlayer("Veuillez vous reconnectez...");
					return;
				}
				
				// MSG DE join
				player.sendMessage(" ");
				player.sendMessage("§b§k!§r§f§k:§r§e§k!§r §aBienvenue dans le monde de la série The 100 §r§b§k!§r§f§k:§r§e§k!");
				player.sendMessage(" ");
				player.sendMessage("§7Si tu connais pas la série, pas d'inquiétude ! Tu n'as pas besoin d'être un pro de la série pour connaître :)");
				player.sendMessage("§dL'objectif principal est de réécrire l'histoire de la série à notre manière !");
				player.sendMessage("§7§oSur cette carte, il y a les bases des 3 premières saisons mais les build ne sont pas finis. A vous de partir de ça pour reconstruire l'Univers de la série !");
				player.sendMessage("§a§oHeda = Commandant ; Fleimkepa = Guardien de la Flamme");
				player.sendMessage("§6Des livres (à conserver) sont à votre disposition pour les détails sur ce serveur.");
				player.sendMessage("");
				player.sendMessage("§4Pour rejoindre un clan contactez le chef de tribu sur discord ou envoyez lui un message en jeu.");
				player.sendMessage("");
				player.sendMessage("§cToutes les règles figurées sont à respecter. Des sanctions peuvent être appliquées.");
				
				// stuff pour démarrer
				startingStuff(player);
				// tp
				player.teleport(new Location(Bukkit.getWorld("world"), 638.5, 66.0, 272.5, 90.0f, 0f));
				
				ItemStack book1 = new ItemStack(Material.WRITTEN_BOOK, 1);
				BookMeta book1meta = (BookMeta) book1.getItemMeta();
				book1meta.setTitle("§dThe 100 | Court résumé");
				book1meta.setAuthor("Raven Reyes");
				
				List<String> pages = new ArrayList<String>();
				pages.add("Dans un futur lointain, 97 années après une guerre nucléaire qui a dévasté la surface de la Terre, les seuls humains ayant survécu vivent dans la"); // Page 1
				pages.add("flottille de stations spatiales en orbite appelée l'Arche. 100 mineurs condamnés pour divers crimes ou trahisons sont envoyés sur Terre pour tester les chances de survie."); // Page 2
				pages.add("Il découvrent donc que la Terre est habitée et qu'il y a plusieurs clans. Une coalition existe entre tous ces clans pour obtenir la paix. Mais il y a certains tensions entre certains clans.");
				book1meta.setPages(pages);
				book1.setItemMeta(book1meta);
				player.getInventory().setItem(0, book1);
				
				ItemStack book3 = new ItemStack(Material.WRITTEN_BOOK, 1);
				BookMeta book3meta = (BookMeta) book3.getItemMeta();
				book3meta.setTitle("§cRèglement (à voir)");
				book3meta.setAuthor("Clarke Griffin");
				
				List<String> pages3 = new ArrayList<String>();
				pages3.add("- Interdiction de grief les monuments de la carte ainsi que les constructions des joueurs sauf autorisations / reconstructions."); // Page 1
				pages3.add("- Pas d'utilisation de client cheat sur le serveur."); // Page 2
				pages3.add("- Utilisation d'un langage correct dans le chat.");
				pages3.add("- Ne pas se battre avec ses alliés sauf si motif justifié.");
				pages3.add("- On obéit au chef de son clan sur le serveur, sous peine d'être banni de celui-ci.");
				pages3.add("- Si vous souhaitez protéger votre zone. Utiliser le système de claim. Vous en êtes responsables.");
				book3meta.setPages(pages3);
				book3.setItemMeta(book3meta);
				player.getInventory().setItem(2, book3);
				
				/*Main.INSTANCE.config.get().set(player.getUniqueId().toString(), player.getName());
				Main.INSTANCE.config.save();*/
				// account
				Main.INSTANCE.database.createAccount(player);
				new GamePlayer(player.getName());
				
				event.setJoinMessage("§e" + player.getName() + " joined the game.");

				Bukkit.getScheduler().runTaskLater(Main.INSTANCE, new Runnable() {
					@Override
					public void run() {
						player.sendMessage(" ");
						player.sendMessage("§6Permets-moi de me présenter, je m'appelle Raven!");
						player.sendMessage(" ");
						player.sendMessage("§bYep, c'est bien ma tête l'icone de ce serveur aha. " +
								"Je suis à l'origine de toute ses nouveautés. Je trouvais que cet endroit avait" +
								"besoin de changements. Laisses-moi t'expliquer.");
						player.sendMessage("§aTu trouveras notamment un §a§lsystème de quête §aqui te" +
								"permettra de récolter des coins, et ainsi de développer ton expérience sur" +
								"ce monde. Tu ne peux faire qu'une seule quête à la fois et une seule fois la même quête." +
						"Il y a un ordre donc cherche bien et trouves la première.");
						player.sendMessage("§dBon peut-être que t'as remarqué.. Il y a eu un souci avec les réacteurs" +
								"nucléaires, ça a provoqué des nuages pleins de radiations dans le monde entier. La pluie" +
								"est désormais acide donc, fais attention !");
						player.sendMessage(" ");
						player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1f, 1f);
					}
				}, 20L * 30L);

				return;
			}
				
			// pvp 1.8
			Main.INSTANCE.database.updateNickName(player);
			Main.INSTANCE.database.updatePseudoPlayer(player);
			
			new GamePlayer(PlayerUtils.getPlayerName(player.getUniqueId())).setPrefix(GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(player.getUniqueId())).getPrefix());
			
			if (!ScoreboardManager.scoreboardGame.containsKey(player)) {
				if (ScoreboardCustomMenu.getIDS(player.getUniqueId()).size() > 1 && ScoreboardCustomMenu.getIDS(player.getUniqueId()) != null) {
					new ScoreboardManager(player).loadScoreboard();
					
					for (String ids : ScoreboardCustomMenu.getIDS(player.getUniqueId())) {
						
						try {
							Integer id = Integer.parseInt(ids);
							if (id >= 1) ScoreboardManager.scoreboardGame.get(player).setLine(id, Main.INSTANCE.playersLines.get(id));
						} catch (NumberFormatException e) {}
						
					}
				}
			}
			
			// nickname
			NickManager.loadNickPlayer(player.getUniqueId());
			
			event.setJoinMessage("§e" + NickManager.getNickName(player.getUniqueId()) + " joined the game.");
			
			player.sendMessage("§aBienenue dans cette version 3.0 du serveur ! Pour voir les nouveautés fais §b/news§a =)");
			player.sendMessage(" ");
	}
	
	private final void startingStuff(Player player) {
		player.setGameMode(GameMode.SURVIVAL);
		player.getInventory().clear();
		player.getInventory().setItem(3, new ItemStack(Material.IRON_SWORD, 1));
		player.getInventory().setItem(4, new ItemStack(Material.IRON_PICKAXE, 1));
		player.getInventory().setItem(5, new ItemStack(Material.COOKED_BEEF, 64));
		player.getInventory().setItem(6, new ItemStack(Material.COBBLESTONE, 64));
		player.getInventory().setItem(7, new ItemStack(Material.COMPASS, 1));
		player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
		player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
	}
}