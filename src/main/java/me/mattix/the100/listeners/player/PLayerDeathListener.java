package me.mattix.the100.listeners.player;

import me.mattix.the100.GamePlayer;
import me.mattix.the100.Main;
import me.mattix.the100.duel.DuelProtocol;
import me.mattix.the100.sql.DatabaseManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PLayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        Player victim = event.getEntity();

        if (DuelProtocol.into_arena.contains(killer) && DuelProtocol.into_arena.contains(victim)) {
            event.setKeepInventory(true);
            // gagnant
            killer.sendMessage("§6Félicitations, tu viens de remporter ce duel.");
            float gain = getGain(killer);
            killer.sendMessage("§7Gain: §e+" + gain + " "); // gain en fonction de la tribue
            Main.INSTANCE.database.updateCoins(killer, gain);

            // perdant
            victim.sendMessage(" ");
            victim.sendMessage("§cTu as perdu ce duel :( Tâches de t'améliorer pour le prochain!");

            // animation feu d'artifice
        }
    }

    private float getGain(Player killer) {
        GamePlayer gamePlayer = GamePlayer.gamePlayers.get(killer.getName());
        if (gamePlayer.getPeopleName().equalsIgnoreCase("none")) return 50f;
        else return 100f;
    }
}
