package me.mattix.the100.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mattix.the100.GamePlayer;
import me.mattix.the100.Main;
import me.mattix.the100.utils.PlayerUtils;
import me.mattix.the100.utils.TeamsTagsManager;

public class PeopleCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(player.getUniqueId()));
		
		if (gp.isHeda()) {
			
			if (args.length == 0) {
				player.sendMessage("§c/people <player> <people>");
				return true;
			}
			
			if (args.length > 1) {
				Player target = Bukkit.getPlayer(args[0]);
				String people = args[1];
				GamePlayer gpt = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(target.getUniqueId()));
				
				Bukkit.getScheduler().runTaskAsynchronously(Main.INSTANCE, () -> {
					if (people != null) {
						if (target != null && target.isOnline()) {
							
							if (people.equalsIgnoreCase("heda")) {
								sendMessage("§6" +  target.getName() + " §fest devenu §4Commandant.");
								
								gpt.setHeda(1);
								gpt.setPeople("heda");
								gpt.setLeader(0);
								
								gpt.setPrefix("§4[Heda] ");
								
							} else if (people.equalsIgnoreCase("flamegarden")) {
								
								sendMessage("§6" +  target.getName() + " §fest devenu §cLe Gardien de la Flamme.");
								
								
								// msg de bienvenue
								target.sendMessage(" ");
								target.sendMessage("§fVous êtes désormais gardien de la flamme !");
								target.sendMessage("§bVotre rôle est de protéger la flamme, objet sacré où résident tous les esprits des précédents commandants.");
								target.sendMessage("§cVous devez dévouer votre vie à la flamme et être la personne la plus proche du commandant.");
								target.sendMessage(" ");
								target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
								
								gpt.setPeople("hedaflame");
								gpt.setLeader(0);
								gpt.setPrefix("§c[Fleimkepa] ");
								
							} else if (people.equalsIgnoreCase("garde")) {
									
									sendMessage("§6" +  target.getName() + " §fest devenu §cLe Garde du Commandant.");
									
									// msg de bienvenue
									target.sendMessage(" ");
									target.sendMessage("§fVous êtes désormais gardien du commandant !");
									target.sendMessage("§bVotre rôle est de protéger le commandant, de se sacrifier pour lui et de lui obéir.");
									target.sendMessage("§cVous devez dévouer votre vie au commandant et lui faire honneur.");
									target.sendMessage(" ");
									target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
									
									gpt.setPeople("hedagarde");
									gpt.setLeader(0);
									gpt.setPrefix("§c[Garde] ");
									
								} else if (people.equalsIgnoreCase("trikru")) {
								
								sendMessage("§6" +  target.getName() + " §fest devenu §6Trikru.");
								
								// msg de bienvenue
								target.sendMessage(" ");
								target.sendMessage("§fVous êtes désormais Trikru !");
								target.sendMessage("§bVous devez respecter absolument votre chef. Lui désobéir pourrait vous coûter la vie ou même un bannissement du clan."
										+ " Votre village a été détruite par le Mont Weather. Vous pouvez soit reconstruire tout votre le village, soit vous installer ailleurs.");
								target.sendMessage("§cVous devez être fidèle à votre clan, être utile, efficace et donner le meilleur de vous même.");
								target.sendMessage(" ");
								target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
								
								gpt.setPeople("trikru");
								gpt.setLeader(0);
								gpt.setPrefix("§6[Trikru] ");
								
							} else if (people.equalsIgnoreCase("azgeda")) {
								
								sendMessage("§6" +  target.getName() + " §fest devenu §bAzgeda.");
								
								// msg de bienvenue
								target.sendMessage(" ");
								target.sendMessage("§fVous êtes désormais Azgeda !");
								target.sendMessage("§bVous devez respecter absolument votre chef. Lui désobéir pourrait vous coûter la vie ou même un bannissement du clan."
										+ " Vous n'a pas pas de village. Il faut tout construire. Votre espace se situe dans les espace gelés ! Vous manier à la perfection les arcs.");
								target.sendMessage("§cVous devez être fidèle à votre clan, être utile, efficace et donner le meilleur de vous même.");
								target.sendMessage(" ");
								target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
								

								gpt.setPeople("azgeda");
								gpt.setLeader(0);
								gpt.setPrefix("§b[Azgeda] ");
								
							} else if (people.equalsIgnoreCase("skaikru")) {
								
								sendMessage("§6" +  target.getName() + " §fest devenu §eSkaikru.");
								
								target.sendMessage(" ");
								target.sendMessage("§fVous êtes désormais Skaikru !");
								target.sendMessage("§bVous devez respecter absolument votre Chancelier. Lui désobéir pourrait vous coûter la vie ou même un bannissement du clan."
										+ " Votre village est les restes du crash ce la station de l'Arche. Il faut redonner vie à ce village. Y'a du boulot !");
								target.sendMessage("§cVous devez être fidèle à votre clan, être utile, efficace et donner le meilleur de vous même.");
								target.sendMessage(" ");
								target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);

								gpt.setPeople("skaikru");
								gpt.setLeader(0);
								gpt.setPrefix("§e[Skaikru] ");
								
							} else if (people.equalsIgnoreCase("Trishanakru")) {
								
								sendMessage("§6" +  target.getName() + " §fest devenu §2Trishanakru.");
								
								target.sendMessage(" ");
								target.sendMessage("§fVous êtes désormais Trishanakru !");
								target.sendMessage("§bVous devez respecter absolument votre chef. Lui désobéir pourrait vous coûter la vie ou même un bannissement du clan."
										+ " Votre clan a été divisé par le précédent commandant. A vous de vous installer là où bon vous semble et faire mieux qu'avant.");
								target.sendMessage("§cVous devez être fidèle à votre clan, être utile, efficace et donner le meilleur de vous même.");
								target.sendMessage(" ");
								target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
								

								gpt.setPeople("trishanakru");
								gpt.setLeader(0);
								gpt.setPrefix("§2[Trisha] ");
								
							} else if (people.equalsIgnoreCase("Trishanakruchef")) {
								
								sendMessage("§6" +  target.getName() + " §fest devenu §2Chef des Trishanakru.");
								
								target.sendMessage(" ");
								target.sendMessage("§fVous êtes désormais le Chef des Trishanakru !");
								target.sendMessage("§bDiriger votre clan honorablement. Respecter vos coutumes. Protéger votre peuple. Rebâtissez un réel avenir pour eux.");
								target.sendMessage("§cVous devez être fidèle à votre clan, être utile, efficace et donner le meilleur de vous même.");
								target.sendMessage(" ");
								target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
								

								gpt.setPeople("trishanakru");
								gpt.setLeader(1);
								gpt.setPrefix("§2[C.Trisha] ");
								
							} else if (people.equalsIgnoreCase("trikruchef")) {
								
								sendMessage("§6" +  target.getName() + " §fest devenu §6Chef des Trikru.");
								
								target.sendMessage(" ");
								target.sendMessage("§fVous êtes désormais le Chef des Trikru !");
								target.sendMessage("§bDiriger votre clan honorablement. Respecter vos coutumes. Protéger votre peuple. Rebâtissez un réel avenir pour eux.");
								target.sendMessage("§cVous devez être fidèle à votre clan, être utile, efficace et donner le meilleur de vous même.");
								target.sendMessage(" ");
								target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
								
								gpt.setPeople("trikru");
								gpt.setLeader(1);
								gpt.setPrefix("§6[C.Trikru] ");
								
							} else if (people.equalsIgnoreCase("azgedachef")) {
								
								sendMessage("§6" +  target.getName() + " §fest devenu §bChef des Azgeda.");
								
								target.sendMessage(" ");
								target.sendMessage("§fVous êtes désormais le Chef des Azgedas !");
								target.sendMessage("§bDiriger votre clan honorablement. Respecter vos coutumes. Protéger votre peuple. Rebâtissez un réel avenir pour eux.");
								target.sendMessage("§cVous devez être fidèle à votre clan, être utile, efficace et donner le meilleur de vous même.");
								target.sendMessage(" ");
								target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
								
								gpt.setPeople("azgeda");
								gpt.setLeader(1);
								gpt.setPrefix("§b[C.Azgeda] ");
								
							} else if (people.equalsIgnoreCase("skaikruchef")) {
								
								TeamsTagsManager.setNameTag(target, "§c§5Admin", "§e[C.Skaikru] ", "");
								sendMessage("§6" +  target.getName() + " §fest devenu §eChancelier des Skaikru.");
								
								target.sendMessage(" ");
								target.sendMessage("§fVous êtes désormais Chancelier du peuple du ciel (Skaikru) !");
								target.sendMessage("§bDiriger votre clan honorablement. Respecter vos coutumes. Protéger votre peuple. Rebâtissez un réel avenir pour eux.");
								target.sendMessage("§cVous devez être fidèle à votre clan, être utile, efficace et donner le meilleur de vous même.");
								target.sendMessage(" ");
								target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
								

								gpt.setPeople("skaikru");
								gpt.setLeader(1);
								gpt.setPrefix("§e[C.Skaikru] ");
								
							} else if (people.equalsIgnoreCase("none")) {
								sendMessage("§6" +  target.getName() + " §fa été rétrogradé.");
								gpt.setPeople("none");
								gpt.setLeader(0);
								gpt.setPrefix("§f");
							}
						}
					}
				});
			}
		} else {
			player.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande.");
		}
		
		return true;
	}
	
	private final void sendMessage(String msg) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(msg);
		}
	}
}