package me.mattix.the100.duel;

import me.mattix.the100.Main;
import me.mattix.the100.utils.TitleManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class DuelProtocol extends BukkitRunnable {

    protected int timer = 11;

    private static Player player_one, player_two;

    private Random random;

    public static List<Player> into_arena = new ArrayList<>();
    public static List<Player> freeze_mode = new ArrayList<>();

       public DuelProtocol(Player player_one, Player player_two) {
            this.player_one = player_one;
            this.player_two = player_two;
            into_arena.add(player_one);
            into_arena.add(player_two);
            this.random = new Random();
            this.runTaskTimer(Main.INSTANCE, 0L, 20L);
       }

    @Override
    public void run() {
        timer--;

        if (timer == 0) {
            this.cancel();
            // début du combat.

            for (Player fighters : into_arena) {
                fighters.setWalkSpeed(0.2f);
                fighters.playSound(fighters.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
            }
            return;
        }

        if (timer == 10) {
            // tp des joueurs dans l'arene
            List<Location> locs = new ArrayList<>();
            locs.add(new Location(Bukkit.getWorld("world"), 738.5, 70, -98.5));
            locs.add(new Location(Bukkit.getWorld("world"), 746.5, 70, -97.5));

            for (int x = 0; x < locs.size(); x++) {
                for (Player player : into_arena) {
                    player.teleport(locs.get(x));
                    player.setWalkSpeed(0f);
                }
            }
            return;
        }

        for (Player player : into_arena) {
            TitleManager.sendTitle(player, "", "§cCombat dans §e" + timer, 20);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1f, 1f);
        }
    }
}