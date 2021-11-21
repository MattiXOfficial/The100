package me.mattix.the100.utils;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

public class TitleManager {

	public static void setPlayerList(Player player, String header, String footer) {
		IChatBaseComponent hj = ChatSerializer.a("{\"text\":\"" + header + "\"}");
		IChatBaseComponent fj = ChatSerializer.a("{\"text\":\"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter packet = (PacketPlayOutPlayerListHeaderFooter) construcHeaderAndFooterPacket(
				hj, fj);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	private static Object construcHeaderAndFooterPacket(Object header, Object footer) {
		try {
			Object packet = PacketPlayOutPlayerListHeaderFooter.class.newInstance();
			if (header != null) {
				Field field = PacketPlayOutPlayerListHeaderFooter.class.getDeclaredField("a");
				field.setAccessible(true);
				field.set(packet, header);
				field.setAccessible(false);
			}
			if (footer != null) {
				Field field = PacketPlayOutPlayerListHeaderFooter.class.getDeclaredField("b");
				field.setAccessible(true);
				field.set(packet, footer);
				field.setAccessible(false);
			}
			return packet;
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final void sendTitle(Player player, String msgTitle, String msgSubTitle, int ticks) {
		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\":\"" + msgTitle + "\"}");
		IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\":\"" + msgSubTitle + "\"}");
		PacketPlayOutTitle p = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		PacketPlayOutTitle p2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(p);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(p2);
		sendTime(player, ticks);
	}

	public static final void sendTime(Player player, int ticks) {
		PacketPlayOutTitle p = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, 20, ticks, 20);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(p);

	}

	public static final void sendActionBar(Player player, String message) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
	}
}