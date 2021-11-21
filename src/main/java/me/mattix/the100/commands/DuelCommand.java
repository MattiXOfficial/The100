package me.mattix.the100.commands;

import me.mattix.the100.duel.DuelProtocol;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DuelCommand implements CommandExecutor {

    public Map<Player, Player> players_transaction = new HashMap<>();

    protected String PREFIX = "§d§lDUEL §f| ";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        /**
         * /duel <joueur : accept : refuse> | <tribu>
         *
         */
        if (args.length == 0) {
            player.sendMessage("§e/duel §6<joueur | accept | refuse> §f| §6<tribu>");
            return true;
        }

        String target = args[0];

        if (args[0].equalsIgnoreCase("accept")) {

            // si le joueur défié est bien dans notre liste.
            if (!(players_transaction.containsKey(player))) {
                player.sendMessage(PREFIX + "§cErreur: Aucun joueur ne vous a défié.");
                return true;
            }

            if (players_transaction.get(player) == null) {
                player.sendMessage(PREFIX + "§cErreur: L'adversaire n'existe pas.");
                return true;
            }

            if (!players_transaction.get(player).isOnline()) {
                player.sendMessage(PREFIX + "§cErreur: L'adversaire n'est plus en ligne");
                players_transaction.remove(player);
                return true;
            }

            player.sendMessage(PREFIX + "§aDuel accepté contre " + players_transaction.get(player).getName());
            players_transaction.get(player).sendMessage(ChatColor.RED + player.getName() + " §aaccepte §fvotre duel.");
            // début du prog du combat
            new DuelProtocol(player, players_transaction.get(player));
            System.out.println(player.getName() + " " + players_transaction.get(player).getName());
            players_transaction.remove(player);

        // le joueur refuse la demande de duel.
        } else if (args[0].equalsIgnoreCase("refuse")) {
            // si le joueur n'a pas reçu une demande de duel.
            if (!(players_transaction.containsKey(player))) {
                player.sendMessage(PREFIX + "§cErreur: Aucun joueur ne vous a défié.");
                return true;
            }

            if (players_transaction.get(player) == null) {
                player.sendMessage(PREFIX + "§cErreur: L'adversaire n'existe pas.");
                return true;
            }

            if (!players_transaction.get(player).isOnline()) {
                player.sendMessage(PREFIX + "§cErreur: L'adversaire n'est plus en ligne");
                players_transaction.remove(player);
                return true;
            }

            player.sendMessage(PREFIX + "§cVous avez refusé de combattre face à " + players_transaction.get(player).getName());
            players_transaction.get(player).sendMessage(ChatColor.RED + player.getName() + " §ca refusé §fvotre duel.");
            players_transaction.remove(player);
        }

        if (args[0].equalsIgnoreCase("triku") || args[0].equalsIgnoreCase("azgeda") || args[0].equalsIgnoreCase("skaikru") || args[0].equalsIgnoreCase("trishanakru")) {
            // le joueur défie un clan
        } else {
            if (target != null) {
                Player target_player = Bukkit.getPlayer(target);

                if (target_player != player) {

                    if (target_player != null) {
                        if (target_player.isOnline()) {

                            // on envoie une requête de duel au joueur
                            player.sendMessage(" ");
                            player.sendMessage(PREFIX + "§bVous venez de défier §c" + target_player.getName() + "§b. §7§oAttendez sa réponse !");
                            target_player.sendMessage(" ");
                            target_player.sendMessage(PREFIX + ChatColor.GOLD + player.getName() + " §bvous défie au combat, §6§lPrenez une décision !");
                            target_player.sendMessage(PREFIX + "§a/duel accept §fOU §c/duel refuse");

                            players_transaction.put(target_player, player);
                        }

                    } else {

                    }
                } else {

                }
            } else {

            }
        }

        return true;
    }
}