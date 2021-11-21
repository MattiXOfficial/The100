package me.mattix.the100.commands.nick;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mattix.the100.Main;

public class NickCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player) sender;
		
		if (args.length == 0) {
			displayHelp(player);
			return true;
		}
		
		if (args.length == 1) {
				// Rank //
			if (args[0].equalsIgnoreCase("infos")) {
				// Infos //
				player.sendMessage("");
				player.sendMessage("§6[Nick] §fVotre surnom: §b" + NickManager.getNickName(player.getUniqueId()) + "§f.");
				player.sendMessage("");
				player.sendMessage("§6[Nick] §fPour §cdésactivé §fvotre surnom, faites §b/nick reset§f.");
				player.sendMessage("");
			} else if (args[0].equalsIgnoreCase("reset")) {
				// Reset //
				NickManager.resetNickPlayer(player.getUniqueId());
				player.sendMessage("§6[Nick] §fVous venez de §cdésactiver §fvotre surnom.");
				player.sendMessage("§6[Nick] §cReconnectez vous pour appliquer les changements.");
			} else {
				String nickName = args[0];
				if (nickName.length() <= 15) {
				if (!getAllNickNames().contains(nickName) && !getAllPseudos().contains(nickName)) {
						NickManager.setNickPlayer(player.getUniqueId(), nickName);
						player.sendMessage("§6[Nick] §fVous venez d'§aactivé §fvotre surnom, votre nouveau pseudo est maintenant: §b" + nickName + "§f.");
						player.sendMessage("§6[Nick] §cReconnectez vous pour appliquer les changements.");
					} else {
						player.sendMessage("§6[Nick] §cUn joueur possède déjà ce pseudo ou ce surnom.");
					}
				} else {
					player.sendMessage("§6[Nick] §cVotre surnom doit contenir 15 caractères maximum.");
				}
			}
		}
	
		return true;
	}
	
	private List<String> getAllPseudos() {
		List<String> elements = new ArrayList<>();
		try {
			PreparedStatement p = Main.INSTANCE.connection.prepareStatement("SELECT pseudo_player FROM players_the100");
			
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				elements.add(rs.getString("pseudo_player"));
			}
			p.close();
			
			return elements;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<String> getAllNickNames() {
		List<String> elements = new ArrayList<>();
		try {
			PreparedStatement p = Main.INSTANCE.connection.prepareStatement("SELECT nickName FROM players_the100");
			
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				elements.add(rs.getString("nickName"));
			}
			p.close();
			
			return elements;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void displayHelp(Player player) {
		player.sendMessage("§7-------------- §e§lAide §r§8» §e§lNickName §r§7--------------");
		player.sendMessage("§6/nick [pseudo] §7» §eActiver votre surnom.");
		player.sendMessage("§6/nick infos §7» §eInformations de votre surnom.");
		player.sendMessage("§6/nick reset §7» §eDésactiver le surnom.");
		player.sendMessage(" ");
	}
}
