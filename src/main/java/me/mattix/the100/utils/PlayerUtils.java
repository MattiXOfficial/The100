package me.mattix.the100.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import me.mattix.the100.Main;

public class PlayerUtils {

	/**
	 * On clear totalement le joueur. #OPTIMISATION
	 * @param player
	 */

	public static void clearInventory(Player player) {
		player.getInventory().clear();
		player.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
		player.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
		player.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
		player.getInventory().setBoots(new ItemStack(Material.AIR, 1));
	}


	/**
	 * On reset les statistique ig du joueur.
	 * @param player
	 */

	public static void resetStatistic(Player player) {
		player.setStatistic(Statistic.PLAYER_KILLS, 0);
		player.setStatistic(Statistic.DEATHS, 0);
	}

	/**
	 * On retire TOUT les effets de potions du joueur.
	 * @param player
	 */

	public static void clearAllPotionEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	/**
	 * On récupère l'UUID du joueur dans la base de données.
	 * @param pseudo
	 * @return
	 */

	public static String getPlayerUUID(String pseudo) {
		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("SELECT uuid_player FROM players_the100 WHERE pseudo_player = ?");
			preparedStatement.setString(1, pseudo);
			ResultSet rs = preparedStatement.executeQuery();
			String uuid = "";

			while (rs.next()) {
				uuid = rs.getString("uuid_player");
			}
			preparedStatement.close();

			return uuid;

		} catch (SQLException e) {
			System.out.println("");
			return "";
		}
	}

	/**
	 * On récupère le pseudo du joueur dans la base de données.
	 * @param player
	 * @return
	 */

	public static String getPlayerName(UUID uuid) {
		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("SELECT pseudo_player FROM players_the100 WHERE uuid_player = ?");
			preparedStatement.setString(1, uuid.toString());
			ResultSet rs = preparedStatement.executeQuery();
			String pseudo = "";

			while (rs.next()) {
				pseudo = rs.getString("pseudo_player");
			}
			preparedStatement.close();

			return pseudo;

		} catch (SQLException e) {
			System.out.println("");
			return "";
		}
	}
}
