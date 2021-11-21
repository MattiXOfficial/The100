package me.mattix.the100.runnables;

import me.mattix.the100.utils.TitleManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class InformationsTitleRunnable extends BukkitRunnable {

    public int i = 0;
    public int index = 0;

    public List<String> infos = new ArrayList<>();

    public InformationsTitleRunnable() {
        infos.add("§aProtèges ton home avec un §e§lClique-Gauche §aavec une Houe de Diamant");
        infos.add("§bGagnes du temps et fais ton §d§l/sethome");
        infos.add("§cAttention à la pluie radio-active ! Elle est mortelle.");
        infos.add("§6Défies un joueur avec §e§l/duel §6 et gagne de l'argent !");
        infos.add("§cSeulement les chefs de clan peuvent défier un autre clan");
        infos.add("§dVous retrouverez à Polis des quêtes qui vous feront rapporter de l'argent.");
    }

    @Override
    public void run() {
        i++;

        if (index == infos.size()) index = 0;

        if (i == 10) {
            i = 0;
            index++;
        }
        for (Player players : Bukkit.getOnlinePlayers()) {
            TitleManager.sendActionBar(players, infos.get(index));
        }
    }
}
