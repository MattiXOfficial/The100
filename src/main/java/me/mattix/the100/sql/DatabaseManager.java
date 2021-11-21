package me.mattix.the100.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import me.mattix.the100.GamePlayer;
import org.bukkit.entity.Player;

import me.mattix.the100.Main;
import me.mattix.the100.utils.TeamsTagsManager;

public class DatabaseManager {

	public String urlBase;
	public String host;
	public String database;
	public String username;
	public String password;
	public Connection connection;

	public boolean crash = false;

	protected String table = "players_the100";

	/**
	 * Cr�ation de la base de donn�es.
	 *
	 * @param urlBase
	 * @param host
	 * @param database
	 * @param username
	 * @param password
	 */

	public DatabaseManager(String urlBase, String host, String database, String username, String password) {
		this.urlBase = urlBase;
		this.host = host;
		this.database = database;
		this.username = username;
		this.password = password;
	}

	public Connection getConnexion() {
		if (connection == null)
			this.connexion();
		return connection;
	}

	public void connexion() {
		if (!isOnline()) {
			try {
				/* Old MySQL connection
				final Properties prop = new Properties();
				prop.setProperty("user", username);
				prop.setProperty("password", password);
				prop.setProperty("useSLL", "false");
				prop.setProperty("autoReconnect", "true");

				// connection = DriverManager.getConnection(this.urlBase + this.host + "/" + this.database, this.username, this.password);
				connection = DriverManager.getConnection(this.urlBase + host + "/" + database, prop);
				*/
				// Connection to SQLite
				Class.forName("org.sqlite.JDBC");
				final File file = new File(Main.INSTANCE.getDataFolder().getAbsolutePath(), this.database + ".db");
				connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
				//connection.prepareStatement("ALTER TABLE players_the100 ADD coins float;").executeUpdate();
				connection.prepareStatement("CREATE TABLE IF NOT EXISTS `players_the100` (`id` INTEGER PRIMARY KEY AUTOINCREMENT,`uuid_player` VARCHAR(255),`pseudo_player` VARCHAR(255),`people_name` VARCHAR(255),`prefix` VARCHAR(255) DEFAULT '§f',`isleader` INTEGER(11) DEFAULT '0',`isheda` INTEGER(11) DEFAULT '0',`scoreboardlines` TEXT DEFAULT '-1',`home` TEXT DEFAULT 'null',`nickName` VARCHAR(15) DEFAULT 'none', 'coins' FLOAT);").executeUpdate();
				System.out.println("§a[DataBase] Succefully connected.");
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("[DataBase] Connection to database failed.");
			}
		}
	}

	public void deconnexion() {
		if (isOnline()) {
			try {
				connection.close();
				System.out.println("�a[DataBase] Succefully disconnected.");
				return;
			} catch (SQLException e) {
				System.out.println("");
			}
		}
	}

	public boolean isOnline() {
		try {
			if (connection == null || connection.isClosed()) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			System.out.println("[DataBase] Check online database failed.");
		}
		return false;
	}

	public void setPeople(Player player, String people) {
		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("UPDATE " + table + " SET people_name = ? WHERE uuid_player = ?");
			preparedStatement.setString(1, people);
			preparedStatement.setString(2, player.getUniqueId().toString());
			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("[IPlayer | Update Connexion date] Operation failed.");
		}
	}

	public String getPeople(Player player) {
		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("SELECT people_name FROM " + table + " WHERE uuid_player = ?");
			preparedStatement.setString(1, player.getUniqueId().toString());
			ResultSet rs = preparedStatement.executeQuery();
			String people = "";

			while (rs.next()) {
				people = rs.getString("people_name");
			}
			preparedStatement.close();

			return people;

		} catch (SQLException e) {
			return "";
		}
	}

	public void setLeader(Player player, int lead) {
		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("UPDATE " + table + " SET isleader = ? WHERE uuid_player = ?");
			preparedStatement.setInt(1, lead);
			preparedStatement.setString(2, player.getUniqueId().toString());
			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("[IPlayer | Update Connexion date] Operation failed.");
		}
	}

	public int getLeader(Player player) {

		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("SELECT isleader FROM " + table + " WHERE uuid_player = ?");
			preparedStatement.setString(1, player.getUniqueId().toString());
			ResultSet rs = preparedStatement.executeQuery();
			int lead = 0;

			while (rs.next()) {
				lead = rs.getInt("isleader");
			}
			preparedStatement.close();

			return lead;

		} catch (SQLException e) {
			return 0;
		}
	}

	public void setHeda(Player player, int lead) {
		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("UPDATE " + table + " SET isheda = ? WHERE uuid_player = ?");
			preparedStatement.setInt(1, lead);
			preparedStatement.setString(2, player.getUniqueId().toString());
			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("[IPlayer | Update Connexion date] Operation failed.");
		}


	}

	public int getHeda(Player player) {
		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("SELECT isheda FROM " + table + " WHERE uuid_player = ?");
			preparedStatement.setString(1, player.getUniqueId().toString());
			ResultSet rs = preparedStatement.executeQuery();
			int lead = 0;

			while (rs.next()) {
				lead = rs.getInt("isheda");
			}
			preparedStatement.close();

			return lead;

		} catch (SQLException e) {
			return 0;
		}
	}

	public void updateCoins(Player player, float amount) {
		try {

			PreparedStatement preparedStatement = getConnexion().prepareStatement("UPDATE players_the100 SET coins = coins + ? WHERE uuid_player = ?");
			preparedStatement.setFloat(1, amount);
			preparedStatement.setString(2, player.getUniqueId().toString());
			preparedStatement.executeUpdate();
			preparedStatement.close();

			GamePlayer.gamePlayers.get(player.getName()).setCoins(getCoins(player) + amount);

		} catch (SQLException e) { e.printStackTrace(); }
	}

	public float getCoins(Player player) {
		try {

			PreparedStatement preparedStatement = getConnexion().prepareStatement("SELECT coins FROM players_the100 WHERE uuid_player = ?");
			preparedStatement.setString(1, player.getUniqueId().toString());
			float coins = 0;
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				coins = rs.getFloat("coins");
			}
			preparedStatement.close();

			return coins;


		} catch (SQLException e) {
			e.printStackTrace();
			return 0.0f;
		}
	}

	public void createAccount(Player player) {
		if (!hasAccount(player)) {
			try {

				PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("INSERT INTO " + table + " (uuid_player, pseudo_player, people_name, isleader, isheda, nickName, coins) VALUES (?, ?, ?, ?, ?, ?, ?)");
				preparedStatement.setString(1, player.getUniqueId().toString());
				preparedStatement.setString(2, player.getName());
				preparedStatement.setString(3, "none");
				preparedStatement.setInt(4, 0);
				preparedStatement.setInt(5, 0);
				preparedStatement.setString(6, player.getName());
				preparedStatement.setFloat(7, 100.0f);
				preparedStatement.execute();
				preparedStatement.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			TeamsTagsManager.setNameTag(player, "§d§3Admin", "§f", "");
		}
	}

	public void updateNickName(Player player) {
		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("SELECT nickName FROM players_the100 WHERE uuid_player = ?");
			preparedStatement.setString(1, player.getUniqueId().toString());
			ResultSet rs = preparedStatement.executeQuery();
			String nick = "";

			if (rs.next()) {
				nick = rs.getString("nickName");
			}
			preparedStatement.close();

			if (nick == null || nick.equalsIgnoreCase("none")) {
				preparedStatement = Main.INSTANCE.connection.prepareStatement("UPDATE players_the100 SET nickName = ? WHERE uuid_player = ?");
				preparedStatement.setString(1, player.getName());
				preparedStatement.setString(2, player.getUniqueId().toString());
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean hasAccount(Player player) {

		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("SELECT people_name FROM " + table + " WHERE uuid_player = ?");
			preparedStatement.setString(1, player.getUniqueId().toString());

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				return true;
			}

			preparedStatement.close();

			this.crash = false;

		} catch (SQLException e) {
			e.printStackTrace();

			this.crash = true;
			return false;
		}

		return false;
	}

	public final void updatePseudoPlayer(Player player) {
		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("SELECT pseudo_player FROM " + table + " WHERE uuid_player = ?");
			preparedStatement.setString(1, player.getUniqueId().toString());

			ResultSet rs = preparedStatement.executeQuery();
			String pseudo_received = "";

			while (rs.next()) {
				pseudo_received = rs.getString("pseudo_player");
			}
			preparedStatement.close();

			if (pseudo_received != player.getName()) {
				preparedStatement = Main.INSTANCE.connection.prepareStatement("UPDATE " + table + " SET pseudo_player = ? WHERE uuid_player = ?");
				preparedStatement.setString(1, player.getName());
				preparedStatement.setString(2, player.getUniqueId().toString());
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}
		} catch (SQLException e) {
			System.out.println("[IPlayer | Update Pseudo Player] Operation failed.");
			//e.printStackTrace();
		}
	}

	public String getPrefix(Player player) {
		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("SELECT prefix FROM " + table + " WHERE uuid_player = ?");
			preparedStatement.setString(1, player.getUniqueId().toString());
			ResultSet rs = preparedStatement.executeQuery();
			String people = "";

			while (rs.next()) {
				people = rs.getString("prefix");
			}
			return people;

		} catch (SQLException e) {
			return "";
		}
	}

	public void setPrefix(Player player, String prefix) {
		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("UPDATE " + table + " SET prefix = ? WHERE uuid_player = ?");
			preparedStatement.setString(1, prefix);
			preparedStatement.setString(2, player.getUniqueId().toString());
			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("[IPlayer | Update Connexion date] Operation failed.");
		}

	}

	public void setNickName(Player player, String nickName) {
		try {

			PreparedStatement preparedStatement = Main.INSTANCE.connection.prepareStatement("UPDATE " + table + " SET nickName = ? WHERE uuid_player = ?");
			preparedStatement.setString(1, nickName);
			preparedStatement.setString(2, player.getUniqueId().toString());
			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void openConnection() throws SQLException, ClassNotFoundException {
		if(Main.INSTANCE.connection != null && !Main.INSTANCE.connection.isClosed()) return;
        Class.forName("com.mysql.jdbc.Driver");
        
        if(Main.INSTANCE == null) Main.INSTANCE = new Main();
        
        if(Main.INSTANCE.host == null || Main.INSTANCE.database == null || Main.INSTANCE.username == null || Main.INSTANCE.password == null) {
    		//localhost", "serveurmc", "root", ""
        	Main.INSTANCE.port = 3306;
        	Main.INSTANCE.host = "172.18.0.5";
        	Main.INSTANCE.db = "s1403_the1002";
        	Main.INSTANCE.username = "u1403_Crqf6OYdYB";
        	Main.INSTANCE.password = "dy@kq^Zk1j4JZmiTf+N!JEqO";
    		
        }
        
        final Properties prop = new Properties();
		prop.setProperty("user", Main.INSTANCE.username);
		prop.setProperty("password", Main.INSTANCE.password);
		prop.setProperty("useSLL", "false");
		prop.setProperty("autoReconnect", "true");
		
		// connection = DriverManager.getConnection(this.urlBase + this.host + "/" + this.database, this.username, this.password);
        
        // Main.INSTANCE.connection = DriverManager.getConnection("jdbc:mysql://" + Main.INSTANCE.host + "/" + Main.INSTANCE.db, prop);
		Main.INSTANCE.database = new DatabaseManager("", Main.INSTANCE.host, Main.INSTANCE.db, Main.INSTANCE.username, Main.INSTANCE.password);
        Main.INSTANCE.connection = new DatabaseManager("", Main.INSTANCE.host, Main.INSTANCE.db, Main.INSTANCE.username, Main.INSTANCE.password).getConnexion();
		System.out.println("DataBase connect� SQL");
	}
	
	public static Object SQL_Receive(String query, String table) {
		Object obj = null;
		try {
			
			openConnection();
			Statement sql = Main.INSTANCE.connection.createStatement();
			ResultSet rs = sql.executeQuery(query);

			while(rs.next())
				obj = rs.getObject(table);
			
			sql.close();
			
		} catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }
		
		return obj;
	}
	
	public static List<Object> SQL_ReceiveL(String query, String table) {
		List<Object> obj = new ArrayList<Object>();
		try {
			
			openConnection();
			Statement sql = Main.INSTANCE.connection.createStatement();
			ResultSet rs = sql.executeQuery(query);

			while(rs.next())
				obj.add(rs.getObject(table));
			
			sql.close();
			
		} catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }
		
		return obj;
	}
	
	public static void SQL_Update(String query) {
		
		try {
			
			openConnection();
			Statement statement = Main.INSTANCE.connection.createStatement();
			statement.executeUpdate(query);
			
		} catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }
	}
	
}
