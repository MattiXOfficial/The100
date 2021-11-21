package me.mattix.the100.menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import me.mattix.the100.GamePlayer;
import me.mattix.the100.Main;
import me.mattix.the100.scoreboard.ScoreboardManager;
import me.mattix.the100.sql.DatabaseManager;
import me.mattix.the100.utils.ItemBuilder;
import me.mattix.the100.utils.LocationArrowUtils;
import me.mattix.the100.utils.PlayerUtils;

public class ScoreboardCustomMenu implements Listener {

	public String invName = "Customiser votre scoreboard";
	private Inventory inventory = Bukkit.createInventory(null, (9 * 3), invName);
	
	// coordonnées
	public static Location skaikruLoc = new Location(Bukkit.getWorld("world"), 628, 67, 557);
	public static Location trikruLoc = new Location(Bukkit.getWorld("world"), 897, 155, 181);
	public static Location azgedaLoc = new Location(Bukkit.getWorld("world"), 1286, 72, 520);
	public static Location montweatherLoc = new Location(Bukkit.getWorld("world"), 328, 72, 351);
	
	public ScoreboardCustomMenu() {
		
		inventory.setItem(11, new ItemBuilder().type(Material.PAPER).name("§bBase des Skaikru").lore("§7Ajoute une flèche directionnelle", "§7dans ton scoreboard", "", "§fClic-Gauche: §aAjouter", "§fClic-Droit: §cRetirer").build());
		inventory.setItem(12, new ItemBuilder().type(Material.PAPER).name("§bBase des Trikru").lore("§7Ajoute une flèche directionnelle", "§7dans ton scoreboard", "", "§fClic-Gauche: §aAjouter", "§fClic-Droit: §cRetirer").build());
		inventory.setItem(13, new ItemBuilder().type(Material.PAPER).name("§bCapitale Polis").lore("§7Ajoute une flèche directionnelle", "§7dans ton scoreboard", "", "§fClic-Gauche: §aAjouter", "§fClic-Droit: §cRetirer").build());
		inventory.setItem(14, new ItemBuilder().type(Material.PAPER).name("§bBase des Azgeda").lore("§7Ajoute une flèche directionnelle", "§7dans ton scoreboard", "", "§fClic-Gauche: §aAjouter", "§fClic-Droit: §cRetirer").build());
		inventory.setItem(15, new ItemBuilder().type(Material.PAPER).name("§bMont Weather").lore("§7Ajoute une flèche directionnelle", "§7dans ton scoreboard", "", "§fClic-Gauche: §aAjouter", "§fClic-Droit: §cRetirer").build());

		inventory.setItem(17, new ItemBuilder().type(Material.GOLD_INGOT).name("§eMon trésor").lore("§7Ajoute le montant de votre trésor", "§7dans ton scoreboard", "", "§fClic-Gauche: §aAjouter", "§fClic-Droit: §cRetirer").build());
	}

	public void open(Player player) {
		player.openInventory(this.inventory);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		if (event.getAction() != null && event.getCurrentItem() == null) return;
		
		if (event.getInventory().getName().equalsIgnoreCase(invName)) {
			event.setCancelled(true);
			
			Player player = (Player) event.getWhoClicked();
			
			switch (event.getCurrentItem().getType()) {
			case PAPER:
				
				switch (event.getSlot()) {
				case 11:
					
					if (event.getClick() == ClickType.LEFT) {
						// add
							
						if (!haveLine(1, player)) {
							
							if (ScoreboardManager.scoreboardGame.containsKey(player)) {
								ScoreboardManager.scoreboardGame.get(player).setLine(1, "§7Skaikru: §f" + (int) player.getLocation().distance(skaikruLoc) + " " + getArrowColor(player.getLocation().distance(skaikruLoc)) + LocationArrowUtils.calculateArrow(player, skaikruLoc));
								
								player.sendMessage("§aElément ajouté à votre scoreboard.");
								player.closeInventory();
								addLine(player.getUniqueId(), 1);
							} else {
								new ScoreboardManager(player).loadScoreboard();
								ScoreboardManager.scoreboardGame.get(player).setLine(1, "§7Skaikru: §f" + (int) player.getLocation().distance(skaikruLoc) + " " + getArrowColor(player.getLocation().distance(skaikruLoc)) + LocationArrowUtils.calculateArrow(player, skaikruLoc));
								player.sendMessage("§aElément ajouté à votre scoreboard.");
								player.closeInventory();
								addLine(player.getUniqueId(), 1);
							}
						} else {
							player.sendMessage("§cVous avez déjà ajouté cet élément à votre scoreboard.");
						}
						
					} else if (event.getClick() == ClickType.RIGHT) {
						
						if (haveLine(1, player)) {
							removeLine(player.getUniqueId(), 1);
							if (ScoreboardManager.scoreboardGame.containsKey(player)) ScoreboardManager.scoreboardGame.get(player).removeLine(1);
							player.sendMessage("§cVous avez supprimé cet élément de votre scoreboard.");
							player.closeInventory();
						} else {
							player.sendMessage("§cVous n'avez pas cet élément dans votre scoreboard.");
						}
					}
					
					break;
					
				case 12:
					
					if (event.getClick() == ClickType.LEFT) {
						// add
							
						if (!haveLine(2, player)) {
							
							if (ScoreboardManager.scoreboardGame.containsKey(player)) {
								ScoreboardManager.scoreboardGame.get(player).setLine(2, "§7Trikru: §f" + (int) player.getLocation().distance(trikruLoc) + " " + getArrowColor(player.getLocation().distance(trikruLoc)) + LocationArrowUtils.calculateArrow(player, trikruLoc));
								
								player.sendMessage("§aElément ajouté à votre scoreboard.");
								player.closeInventory();
								addLine(player.getUniqueId(), 2);
							} else {
								new ScoreboardManager(player).loadScoreboard();
								ScoreboardManager.scoreboardGame.get(player).setLine(2, "§7Trikru: §f" + (int) player.getLocation().distance(trikruLoc) + " " + getArrowColor(player.getLocation().distance(trikruLoc)) + LocationArrowUtils.calculateArrow(player, trikruLoc));
								player.sendMessage("§aElément ajouté à votre scoreboard.");
								player.closeInventory();
								addLine(player.getUniqueId(), 2);
							}
						} else {
							player.sendMessage("§cVous avez déjà ajouté cet élément à votre scoreboard.");
						}
						
					} else if (event.getClick() == ClickType.RIGHT) {
						
						if (haveLine(2, player)) {
							removeLine(player.getUniqueId(), 2);
							if (ScoreboardManager.scoreboardGame.containsKey(player)) ScoreboardManager.scoreboardGame.get(player).removeLine(2);
							player.sendMessage("§cVous avez supprimé cet élément de votre scoreboard.");
							player.closeInventory();
						} else {
							player.sendMessage("§cVous n'avez pas cet élément dans votre scoreboard.");
						}
					}
					
					break;
					
				case 13:
					
					if (event.getClick() == ClickType.LEFT) {
						// add
							
						if (!haveLine(3, player)) {
							
							if (ScoreboardManager.scoreboardGame.containsKey(player)) {
								ScoreboardManager.scoreboardGame.get(player).setLine(3, "§7Polis: §f" + (int) player.getLocation().distance(Main.INSTANCE.polisLocation) + " " + getArrowColor(player.getLocation().distance(Main.INSTANCE.polisLocation)) + LocationArrowUtils.calculateArrow(player, Main.INSTANCE.polisLocation));
								
								player.sendMessage("§aElément ajouté à votre scoreboard.");
								player.closeInventory();
								addLine(player.getUniqueId(), 3);
							} else {
								new ScoreboardManager(player).loadScoreboard();
								ScoreboardManager.scoreboardGame.get(player).setLine(3, "§7Polis: §f" + (int) player.getLocation().distance(Main.INSTANCE.polisLocation) + " " + getArrowColor(player.getLocation().distance(Main.INSTANCE.polisLocation)) + LocationArrowUtils.calculateArrow(player, Main.INSTANCE.polisLocation));
								player.sendMessage("§aElément ajouté à votre scoreboard.");
								player.closeInventory();
								addLine(player.getUniqueId(), 3);
							}
						} else {
							player.sendMessage("§cVous avez déjà ajouté cet élément à votre scoreboard.");
						}
						
					} else if (event.getClick() == ClickType.RIGHT) {
						
						if (haveLine(3, player)) {
							removeLine(player.getUniqueId(), 3);
							if (ScoreboardManager.scoreboardGame.containsKey(player)) ScoreboardManager.scoreboardGame.get(player).removeLine(3);
							// ou destroy
							player.sendMessage("§cVous avez supprimé cet élément de votre scoreboard.");
							player.closeInventory();
						} else {
							player.sendMessage("§cVous n'avez pas cet élément dans votre scoreboard.");
						}
					}
					
					break;
					
				case 14:
					
					if (event.getClick() == ClickType.LEFT) {
						// add
							
						if (!haveLine(4, player)) {
							
							if (ScoreboardManager.scoreboardGame.containsKey(player)) {
								ScoreboardManager.scoreboardGame.get(player).setLine(4, "§7Azgedas: §f" + (int) player.getLocation().distance(azgedaLoc) + " " + getArrowColor(player.getLocation().distance(azgedaLoc)) + LocationArrowUtils.calculateArrow(player, azgedaLoc));
								
								player.sendMessage("§aElément ajouté à votre scoreboard.");
								player.closeInventory();
								addLine(player.getUniqueId(), 4);
							} else {
								new ScoreboardManager(player).loadScoreboard();
								ScoreboardManager.scoreboardGame.get(player).setLine(4, "§7Azgedas: §f" + (int) player.getLocation().distance(azgedaLoc) + " " + getArrowColor(player.getLocation().distance(azgedaLoc)) + LocationArrowUtils.calculateArrow(player, azgedaLoc));
								player.sendMessage("§aElément ajouté à votre scoreboard.");
								player.closeInventory();
								addLine(player.getUniqueId(), 4);
							}
						} else {
							player.sendMessage("§cVous avez déjà ajouté cet élément à votre scoreboard.");
						}
						
					} else if (event.getClick() == ClickType.RIGHT) {
						
						if (haveLine(4, player)) {
							removeLine(player.getUniqueId(), 4);
							if (ScoreboardManager.scoreboardGame.containsKey(player)) ScoreboardManager.scoreboardGame.get(player).removeLine(4);
							// ou destroy
							player.sendMessage("§cVous avez supprimé cet élément de votre scoreboard.");
							player.closeInventory();
						} else {
							player.sendMessage("§cVous n'avez pas cet élément dans votre scoreboard.");
						}
					}
					
					break;
					
				case 15:
					
					if (event.getClick() == ClickType.LEFT) {
						// add
							
						if (!haveLine(5, player)) {
							
							if (ScoreboardManager.scoreboardGame.containsKey(player)) {
								ScoreboardManager.scoreboardGame.get(player).setLine(5, "§7Mont Weather: §f" + (int) player.getLocation().distance(montweatherLoc) + " " + getArrowColor(player.getLocation().distance(montweatherLoc)) + LocationArrowUtils.calculateArrow(player, montweatherLoc));
								
								player.sendMessage("§aElément ajouté à votre scoreboard.");
								player.closeInventory();
								addLine(player.getUniqueId(), 5);
							} else {
								new ScoreboardManager(player).loadScoreboard();
								ScoreboardManager.scoreboardGame.get(player).setLine(5, "§7Mont Weather: §f" + (int) player.getLocation().distance(montweatherLoc) + " " + getArrowColor(player.getLocation().distance(montweatherLoc)) + LocationArrowUtils.calculateArrow(player, montweatherLoc));
								player.sendMessage("§aElément ajouté à votre scoreboard.");
								player.closeInventory();
								addLine(player.getUniqueId(), 5);
							}
						} else {
							player.sendMessage("§cVous avez déjà ajouté cet élément à votre scoreboard.");
						}
						
					} else if (event.getClick() == ClickType.RIGHT) {
						
						if (haveLine(5, player)) {
							removeLine(player.getUniqueId(), 5);
							if (ScoreboardManager.scoreboardGame.containsKey(player)) ScoreboardManager.scoreboardGame.get(player).removeLine(5);
							// ou destroy
							player.sendMessage("§cVous avez supprimé cet élément de votre scoreboard.");
							player.closeInventory();
						} else {
							player.sendMessage("§cVous n'avez pas cet élément dans votre scoreboard.");
						}
					}
					
					break;
					
				default:
					break;
				}
				
				break;

				case GOLD_INGOT:

					if (event.getClick() == ClickType.LEFT) {
						// add

						if (!haveLine(6, player)) {

							if (ScoreboardManager.scoreboardGame.containsKey(player)) {
								ScoreboardManager.scoreboardGame.get(player).setLine(6, "§7Mes Coins: §e§l" + Main.INSTANCE.database.getCoins(player) + " ㊣");

								player.sendMessage("§aElément ajouté à votre scoreboard.");
								player.closeInventory();
								addLine(player.getUniqueId(), 6);
							} else {
								new ScoreboardManager(player).loadScoreboard();
								ScoreboardManager.scoreboardGame.get(player).setLine(6, "§7Mes Coins: §e§l" + Main.INSTANCE.database.getCoins(player) + " ㊣");
								player.sendMessage("§aElément ajouté à votre scoreboard.");
								player.closeInventory();
								addLine(player.getUniqueId(), 6);
							}
						} else {
							player.sendMessage("§cVous avez déjà ajouté cet élément à votre scoreboard.");
						}

					} else if (event.getClick() == ClickType.RIGHT) {

						if (haveLine(6, player)) {
							removeLine(player.getUniqueId(), 6);
							if (ScoreboardManager.scoreboardGame.containsKey(player)) ScoreboardManager.scoreboardGame.get(player).removeLine(6);
							// ou destroy
							player.sendMessage("§cVous avez supprimé cet élément de votre scoreboard.");
							player.closeInventory();
						} else {
							player.sendMessage("§cVous n'avez pas cet élément dans votre scoreboard.");
						}
					}

					break;

			default:
				break;
			}
		}
	}
	
	/**
	 * 
	 * id 1 = skaikru
	 * id 2 = trikru
	 * id 3 = polis
	 * id 4 = azgeda
	 * id 5 = mont weather
	 * id 6 = coins
	 * 
	 * 
	 * @param id
	 * @param player
	 * @return
	 */
	
	public static boolean haveLine(int id, Player player) {
		String ids = "-1";
		String o = (String) DatabaseManager.SQL_Receive("SELECT scoreboardlines FROM players_the100 WHERE uuid_player = '" + player.getUniqueId().toString() + "'", "scoreboardlines");
		if (o != null) ids = o;
		List<Integer> mounts = new ArrayList<>();
		for (String iD : ids.split(";")) {
			try {
				mounts.add(Integer.parseInt(iD));
			} catch (NumberFormatException e) { }
		}
		boolean haveMount = mounts.contains(id);
		return haveMount;
	}
	
	public static boolean haveLines(String id, Player player) {
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(player.getUniqueId()));
		return gp.getIDS(player.getUniqueId()).contains(id);
	}
	
	public String lineEditing(Player player, String ids, int id, boolean adding) {
		List<String> l = new ArrayList<>(Arrays.asList(ids.split(";")));
		String idstr = Integer.toString(id);
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(player.getUniqueId()));
		if (adding) {
			if (!l.contains(idstr)) {
				l.add(idstr);
				gp.ids.add(idstr);
			}
		} else {
			l.remove(idstr);
			gp.ids.remove(idstr);
		}
		String result = "";
		for (int i = 0; i < l.size(); i++) {
			result += (i == 0 ? "" : ";") + l.get(i);
		}
		return result;
	}
	
	public static List<String> getIDS(UUID uuid) {
		Object cache;
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(uuid));
		
		cache = DatabaseManager.SQL_Receive("select uuid_player FROM players_the100 WHERE pseudo_player = '" + gp.getName() + "'", "uuid_player");

		if (cache != null) {
			String ids = (String) DatabaseManager.SQL_Receive("SELECT scoreboardlines FROM players_the100 WHERE uuid_player = '" + uuid.toString() + "'", "scoreboardlines");
			List<String> l = new ArrayList<>(Arrays.asList(ids.split(";")));
			return l;
		}
		return null;
	}
	
	public void removeLine(UUID uuid, int id) {
		Object cache;
		
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(uuid));
		
		cache = DatabaseManager.SQL_Receive("select uuid_player FROM players_the100 WHERE pseudo_player = '" + gp.getName() + "'", "uuid_player");

		if (cache != null) {
			String ids = (String) DatabaseManager.SQL_Receive("SELECT scoreboardlines FROM players_the100 WHERE uuid_player = '" + uuid.toString() + "'", "scoreboardlines");
			DatabaseManager.SQL_Update("UPDATE players_the100 SET scoreboardlines = '" + lineEditing(Bukkit.getPlayer(uuid), ids, id, false) + "' WHERE uuid_player = '" + uuid.toString() + "'");
		} else {
			DatabaseManager.SQL_Update("INSERT INTO players_the100 (uuid_player, scoreboardlines) VALUES('" + uuid.toString() + "', '" + id + ";')");
		}
	}
	
	public void addLine(UUID uuid, int id) {
		Object cache;
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(uuid));
		
		cache = DatabaseManager.SQL_Receive("select uuid_player FROM players_the100 WHERE pseudo_player = '" + gp.getName() + "'", "uuid_player");

		if (cache != null) {
			String ids = (String) DatabaseManager.SQL_Receive("SELECT scoreboardlines FROM players_the100 WHERE uuid_player = '" + uuid.toString() + "'", "scoreboardlines");
			DatabaseManager.SQL_Update("UPDATE players_the100 SET scoreboardlines = '" + lineEditing(Bukkit.getPlayer(uuid), ids, id, true) + "' WHERE uuid_player = '" + uuid.toString() + "'");
		} else {
			DatabaseManager.SQL_Update("INSERT INTO players_the100 (uuid_player, scoreboardlines) VALUES('" + uuid.toString() + "', '" + id + ";')");
		}
	}
	
	public String getArrowColor(double d) {
		if (d >= 100) return "§c";
		if (d > 20  && d < 100) return "§e";
		if (d <= 20) return "§a";
		return "§a";
	}
}