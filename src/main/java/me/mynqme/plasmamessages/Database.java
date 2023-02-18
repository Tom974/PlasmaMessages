package me.mynqme.plasmamessages;

import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.UUID;
import java.sql.Statement;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

public class Database
{
    
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            final String dbUrl = "jdbc:mysql://"+Config.databaseHost+"/"+Config.databaseName+"?autoReconnect=true&useUnicode=yes";
            if (Config.debug) {
                console("&aConnecting to database...");
                console("&7 \u2022&f Using Database User: " + Config.databaseUser);
                console("&7 \u2022&f Using Database Password: " + Config.databasePassword);
            }
            connection = DriverManager.getConnection(dbUrl, Config.databaseUser, Config.databasePassword);
        }
        catch (SQLException e2) {
            console("&7 \u2022&c Failed to connect to the MySQL database.");
            e2.printStackTrace();
        }
        console("&7 \u2022&f Connected to the MySQL database.");
        return connection;
    }
    
    public static void console(final String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    
    public static void setup() throws SQLException {
        Connection conn = getConnection();
        final Statement statement = conn.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS plasmamessages (`id` int NOT NULL AUTO_INCREMENT, `uuid` VARCHAR(36), `setting` VARCHAR(255), `value` VARCHAR(255), PRIMARY KEY (`id`))");
        conn.close();
    }
    
    public static void createPlayer(final UUID uuid) throws SQLException {
        Connection conn = getConnection();
        final PreparedStatement statement = conn.prepareStatement("SELECT * FROM `s6_prison_server`.`plasmamessages` WHERE uuid = ?");
        statement.setString(1, uuid.toString());
        final ResultSet results = statement.executeQuery();
        if (!results.next()) {
            final HashMap<String, String> temp2 = new HashMap<String, String>();
            temp2.put("expansion", "true");
            temp2.put("pickaxe", "true");
            temp2.put("minelevel", "true");
            temp2.put("prestige", "true");
            temp2.put("lucky", "true");
            temp2.put("treasurehunt", "true");
            temp2.put("keyfinder", "true");
            temp2.put("valuehunter", "true");
            temp2.put("lottery", "true");
            temp2.put("multifinder", "true");
            temp2.put("jackpot", "true");
            temp2.put("gemseeker", "true");
            temp2.put("safe", "true");
            for (final String key : temp2.keySet()) {
                final PreparedStatement insert = conn.prepareStatement("INSERT INTO `s6_prison_server`.`plasmamessages` (`uuid`, `setting`, `value`) VALUES (?, ?, ?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, key);
                insert.setString(3, temp2.get(key));
                insert.executeUpdate();
            }
        }
        conn.close();
    }
    
    public static void saveToDatabase(final UUID uuid) throws SQLException {
        Connection conn = getConnection();
        for (final String key : PlasmaMessages.playerData.get(uuid).keySet()) {
            final PreparedStatement statement = conn.prepareStatement("UPDATE `s6_prison_server`.`plasmamessages` SET `value` = ? WHERE uuid = ? AND setting = ?");
            statement.setString(1, PlasmaMessages.playerData.get(uuid).get(key));
            statement.setString(2, uuid.toString());
            statement.setString(3, key);
            statement.executeUpdate();
        }
        conn.close();
    }
    
    public static HashMap<UUID, HashMap<String, String>> getAllPlayerData() throws SQLException {
        Connection conn = getConnection();
        final PreparedStatement statement = conn.prepareStatement("SELECT * FROM `s6_prison_server`.`plasmamessages` ORDER BY `id` DESC");
        final ResultSet results = statement.executeQuery();
        final HashMap<UUID, HashMap<String, String>> tempmap = new HashMap<UUID, HashMap<String, String>>();
        if (results.next()) {
            for (int i = 0; i < results.getFetchSize(); ++i) {
                final UUID uuid = UUID.fromString(results.getString("uuid"));
                final String setting = results.getString("setting");
                final String value = results.getString("value");
                tempmap.put(uuid, new HashMap<String, String>() {
                    {
                        this.put(setting, value);
                    }
                });
            }
        }
        conn.close();
        return tempmap;
    }
    
    public static HashMap<String, String> getPlayerData(final UUID uuid) throws SQLException {
        Connection conn = getConnection();
        final PreparedStatement statement = conn.prepareStatement("SELECT * FROM `s6_prison_server`.`plasmamessages` WHERE `uuid` = ? ORDER BY `id` DESC");
        statement.setString(1, uuid.toString());
        final ResultSet results = statement.executeQuery();
        final HashMap<String, String> tempmap = new HashMap<String, String>();
        if (!results.next()) {
            createPlayer(uuid);
            final HashMap<String, String> temp2 = new HashMap<String, String>();
            temp2.put("expansion", "true");
            temp2.put("pickaxe", "true");
            temp2.put("minelevel", "true");
            temp2.put("prestige", "true");
            temp2.put("lucky", "true");
            temp2.put("treasurehunt", "true");
            temp2.put("keyfinder", "true");
            temp2.put("valuehunter", "true");
            temp2.put("lottery", "true");
            temp2.put("multifinder", "true");
            temp2.put("jackpot", "true");
            temp2.put("gemseeker", "true");
            temp2.put("safe", "true");
            return temp2;
        }
        do {
            final String setting = results.getString("setting");
            final String value = results.getString("value");
            tempmap.put(setting, value);
        } while (results.next());
        conn.close();
        return tempmap;
    }
}
