package me.mattix.the100.commands.nick;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import me.mattix.the100.GamePlayer;
import me.mattix.the100.Main;
import me.mattix.the100.utils.PlayerUtils;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NickManager {

	public static String table = "players_the100";
	public static Map<UUID, Double> playerRank = new HashMap<UUID, Double>();

	public static boolean isNick(UUID uuid) {
		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("SELECT nickName FROM " + table + " WHERE uuid_player = ?");
			preparedStatement.setString(1, uuid.toString());
			ResultSet rs = preparedStatement.executeQuery();
			boolean isNick = false;
			String nickName = "";

			while (rs.next()) {
				nickName = rs.getString("nickName");
				isNick = !Bukkit.getPlayer(uuid).getName().equals(nickName);
			}
			preparedStatement.close();

			return isNick;

		} catch (SQLException e) {
			System.out.println("[NickManager] Impossible de charger le nickname du joueur.");
			return false;
		}
	}

	public static String getNickName(UUID uuid) {
		try {
			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("SELECT nickName FROM " + table + " WHERE uuid_player = ?");
			preparedStatement.setString(1, uuid.toString());
			ResultSet rs = preparedStatement.executeQuery();
			String nickName = "";

			while (rs.next()) {
				nickName = rs.getString("nickName");
			}
			preparedStatement.close();

			return nickName;

		} catch (SQLException e) {
			System.out.println("[NickManager] Impossible de charger le nickname du joueur.");
			return "";
		}
	}

	public static void resetNickPlayer(UUID uuid) {
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(uuid));
		try {
			gp.setNickName(PlayerUtils.getPlayerName(uuid));

			PacketPlayOutPlayerInfo playerInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, ((CraftPlayer) Bukkit.getPlayer(uuid)).getHandle());
			Bukkit.getPlayer(uuid).setDisplayName(PlayerUtils.getPlayerName(uuid));
			for (Player playerOnline : Bukkit.getOnlinePlayers()) {
				((CraftPlayer) playerOnline).getHandle().playerConnection.sendPacket(playerInfo);
			}
			GameProfile profile = ((CraftPlayer) Bukkit.getPlayer(uuid)).getProfile();
			Field nameField = profile.getClass().getDeclaredField("name");
			nameField.setAccessible(true);
			int modifiers = nameField.getModifiers();
			Field modifiersField = nameField.getClass().getDeclaredField("modifiers");
			modifiers = modifiers & Modifier.FINAL;
			modifiersField.setAccessible(true);
			modifiersField.setInt(nameField, modifiers);
			nameField.set(profile, PlayerUtils.getPlayerName(uuid));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[NickManager] Impossible de reset le nick du joueur.");
		}
	}

	public static void setNickPlayer(UUID uuid, String nickName) {
		GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(uuid));
		try {
			PacketPlayOutPlayerInfo playerInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, ((CraftPlayer) Bukkit.getPlayer(uuid)).getHandle());
			Bukkit.getPlayer(uuid).setDisplayName(nickName);
			for (Player playerOnline : Bukkit.getOnlinePlayers()) {
				((CraftPlayer) playerOnline).getHandle().playerConnection.sendPacket(playerInfo);
			}
			GameProfile profile = ((CraftPlayer) Bukkit.getPlayer(uuid)).getProfile();
			Field nameField = profile.getClass().getDeclaredField("name");
			nameField.setAccessible(true);
			int modifiers = nameField.getModifiers();
			Field modifiersField = nameField.getClass().getDeclaredField("modifiers");
			modifiers = modifiers & Modifier.FINAL;
			modifiersField.setAccessible(true);
			modifiersField.setInt(nameField, modifiers);
			nameField.set(profile, nickName);

			gp.setNickName(nickName);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[NickManager] Impossible de set le nick du joueur.");
		}
	}

	public static void loadNickPlayer(UUID uuid) {
		try {
			String nickName;
			if (NickManager.isNick(uuid)) { nickName = NickManager.getNickName(uuid); } else { nickName = Bukkit.getPlayer(uuid).getName(); }
			GamePlayer gp = GamePlayer.gamePlayers.get(PlayerUtils.getPlayerName(uuid));
			PacketPlayOutPlayerInfo playerInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, ((CraftPlayer) Bukkit.getPlayer(uuid)).getHandle());
			Bukkit.getPlayer(uuid).setDisplayName(nickName);
			for (Player playerOnline : Bukkit.getOnlinePlayers()) {
				((CraftPlayer) playerOnline).getHandle().playerConnection.sendPacket(playerInfo);
			}
			GameProfile profile = ((CraftPlayer) Bukkit.getPlayer(uuid)).getProfile();
			Field nameField = profile.getClass().getDeclaredField("name");
			nameField.setAccessible(true);
			int modifiers = nameField.getModifiers();
			Field modifiersField = nameField.getClass().getDeclaredField("modifiers");
			modifiers = modifiers & Modifier.FINAL;
			modifiersField.setAccessible(true);
			modifiersField.setInt(nameField, modifiers);
			nameField.set(profile, nickName);


			gp.setPrefix(gp.getPrefix());

		} catch (Exception e) {
			System.out.println("[NickManager] Impossible de load le nick du joueur.");
			return;
		}
	}
}
