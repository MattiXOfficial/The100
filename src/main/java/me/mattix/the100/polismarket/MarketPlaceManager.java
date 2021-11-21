package me.mattix.the100.polismarket;

import me.mattix.the100.polismarket.menus.PlantationsFoodMenu;
import me.mattix.the100.utils.FloatingTextUtils;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.IPlayerFileData;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MarketPlaceManager {

    public Map<UUID, MarketInventory> market_villagers = new HashMap<>();

    public void createVillager(Location loc, String name, String display_name, MarketInventory c) {
        Villager villager = (Villager) Bukkit.getWorld("world").spawnEntity(loc, EntityType.VILLAGER);
        Entity villagernms = ((CraftEntity) villager).getHandle();
        villager.setAI(false);
        villagernms.setInvisible(true);

        FloatingTextUtils.setFloatingText(loc, display_name, name);

        market_villagers.put(villager.getUniqueId(), c);
    }

    public void initializeVillagerMarketLocations() {
        World world = Bukkit.getWorld("world");
        // plantations food villager
        this.createVillager(new Location(world, 0, 0, 0), "plantation_v", "§6Fermier", new PlantationsFoodMenu());
        // bucher villager
        // tools villager
        // chimist villager
        // magician villager
    }
}