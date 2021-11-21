package me.mattix.the100;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.mattix.the100.commands.*;
import me.mattix.the100.duel.DuelProtocol;
import me.mattix.the100.polismarket.MarketPlaceManager;
import me.mattix.the100.runnables.InformationsTitleRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.mattix.the100.area.RegionManager;
import me.mattix.the100.commands.nick.NickCommand;
import me.mattix.the100.listeners.ListenersManager;
import me.mattix.the100.runnables.RessourcesRunnable;
import me.mattix.the100.runnables.ScoreboardRunnable;
import me.mattix.the100.sql.DatabaseManager;
import me.mattix.the100.utils.FloatingTextUtils;

public class Main extends JavaPlugin implements Listener {

	public Connection connection;
	public int port;
	public String host, db, username, password;

	public ConfigFile config;
	public static Main INSTANCE;

	public Location polisLocation;
	public DatabaseManager database;

	// regions
	public RegionManager mineprotect, skaikru, region;
	public MarketPlaceManager marketPlaceManager;
	public List<RegionManager> regions;
	public Map<UUID, RegionManager> regionsid;
	
	public Map<Integer, String> playersLines;

	// on enable
	@Override
	public void onEnable() {
		INSTANCE = this;
		reloadConfig();

		config = new ConfigFile(this, "config.yml");
		playersLines = new HashMap<>();
		regions = new ArrayList<RegionManager>();
		regionsid = new HashMap<>();
		marketPlaceManager = new MarketPlaceManager();

		// commands
		this.getCommand("people").setExecutor(new PeopleCommand());
		this.getCommand("bc").setExecutor(new AnnonceCommand());
		this.getCommand("set").setExecutor(new LeaderCommand());
		this.getCommand("fl").setExecutor(new FloatingTextCommand());
		this.getCommand("flr").setExecutor(new FloatingTextRCommand());
		this.getCommand("settings").setExecutor(new SettingsCommand());
		this.getCommand("sethome").setExecutor(new SetHomeCommand());
		this.getCommand("home").setExecutor(new HomeCommand());
		this.getCommand("nick").setExecutor(new NickCommand());
		this.getCommand("news").setExecutor(new NewsCommand());
		this.getCommand("shop").setExecutor(new ShopCommand());
		this.getCommand("help").setExecutor(new HelpCommand());
		this.getCommand("tpc").setExecutor(new ClaimTPCommand());
		this.getCommand("duel").setExecutor(new DuelCommand());

		// listeners
		new ListenersManager(this).registerListeners();

		this.polisLocation = new Location(Bukkit.getWorld("world"), 682.5, 76, -16);

		this.region = new RegionManager(new Location(Bukkit.getWorld("world"), 669.0, 59.0, -37.0), new Location(Bukkit.getWorld("world"), 694.0, 59.0, -10.0), "ressources", "everyone");
		this.mineprotect = new RegionManager(new Location(Bukkit.getWorld("world"), 667.0, 76, -41.0), new Location(Bukkit.getWorld("world"), 698.0, 59.0, -7), "mine", "everyone");
		this.skaikru = new RegionManager(new Location(Bukkit.getWorld("world"), 512.0, 9.0, 712.0), new Location(Bukkit.getWorld("world"), 704.0, 145, 561.0), "skaikru", "skaikru");
		
		regions.add(this.mineprotect);
		regions.add(this.region);
		regions.add(this.skaikru);
		
		
		config = new ConfigFile(this, "config.yml");
		
		for (String uuidStr : config.get().getKeys(false)) {
			List<String> coo = config.get().getStringList(uuidStr);
			
			Location pos1 = new Location(Bukkit.getWorld(coo.get(6)), Double.parseDouble(coo.get(0)), Double.parseDouble(coo.get(1)), Double.parseDouble(coo.get(2)));
			Location pos2 = new Location(Bukkit.getWorld(coo.get(6)), Double.parseDouble(coo.get(3)), Double.parseDouble(coo.get(4)), Double.parseDouble(coo.get(5)));
			
			RegionManager region = new RegionManager(pos1, pos2, coo.get(7), coo.get(8));
			
			regions.add(region);
			regionsid.put(UUID.fromString(uuidStr.substring(0, uuidStr.length() - 3)), region);
		}
		
		Bukkit.getWorld("world").setPVP(true);
		WorldBorder wb = Bukkit.getWorld("world").getWorldBorder();
		// v2
		wb.setSize(1850);
		wb.setCenter(518.500, 222.500);
		wb.setDamageAmount(3f);

		marketPlaceManager.initializeVillagerMarketLocations();

		try {
			DatabaseManager.openConnection();
		} catch (SQLException | ClassNotFoundException throwable) {
			throwable.printStackTrace();
		}

		new RessourcesRunnable().runTaskTimer(this, 0L, 20 * (3600 * 5L));
		new ScoreboardRunnable().runTaskTimer(this, 0L, 10L);
		new InformationsTitleRunnable().runTaskTimer(this, 0L, 20L);

		spawnFloatingText();

	}

	private void spawnFloatingText() {
		FloatingTextUtils.setFloatingText(new Location(Bukkit.getWorld("world"), 669.413, 76, -38.914), "§e§lFerme à Minerais", "fermeminerais");
		FloatingTextUtils.setFloatingText(new Location(Bukkit.getWorld("world"), 678.5, 76, -14.5), "§dElevage de Montures", "montures");
	}

	private void deleteFloatingText() {
		for (org.bukkit.Chunk chunk : Bukkit.getWorld("world").getLoadedChunks()) {
			for (org.bukkit.entity.Entity entity : chunk.getEntities()) {
				if (entity instanceof ArmorStand) {
					if (!((ArmorStand) entity).isVisible()) {
						entity.remove();
					}
				}
			}
		}
	}

	@Override
	public void onDisable() {
		deleteFloatingText();
		try {
			this.connection.close();
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
	}

	@EventHandler
	public void onServerInfo(ServerListPingEvent event) {
		event.setMaxPlayers(100);
		// V2 
		if (Bukkit.getServer().hasWhitelist()) {
			event.setMotd("           §a§lTHE 100 §7| §dServeur Survie §a§lV2.0 §6§l1.12 \n          §cServeur en maintenance...");
		} else {
			event.setMotd("           §a§lTHE 100 V2 §7| §eServeur Survie §e§l1.12 \n       §d§k!§r§b§k!§r §dReconstruisez le monde de la série §b§k!§r§d§k!");
		}
	}


	public class ConfigFile {
	    private File file;
	    private YamlConfiguration conf;

	    public ConfigFile(JavaPlugin plugin, String fileName) {
	        this.file = new File(plugin.getDataFolder(), fileName);
	        if (!file.exists())
	            try {
	                if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
	                InputStream in = plugin.getResource(fileName);
	                if (in != null) {
	                    OutputStream out = new FileOutputStream(file);

	                    byte[] buf = new byte[1024 * 4];
	                    int len = in.read(buf);
	                    while (len != -1) {
	                        out.write(buf, 0, len);
	                        len = in.read(buf);
	                    }
	                    out.close();
	                    in.close();
	                }
	                else file.createNewFile();
	            } catch(Exception e) {e.printStackTrace();}
	        reload();
	    }

	    public void reload() {
	        try {
	            conf = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(file), "UTF-8"));
	        }
	        catch (UnsupportedEncodingException | FileNotFoundException e) { e.printStackTrace(); }
	    }

	    public YamlConfiguration get() {
	        return conf;
	    }

	    public void save() {
	        try {
	            conf.save(file);
	        } catch (IOException e) { e.printStackTrace(); }
	    }
	}
}
