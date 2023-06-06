package me.mynqme.plasmamessages;

import java.util.Collections;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;

public class Config
{
    public static List<String> blacklistedWorlds;
    public static List<String> blacklistedBlocks;
    public static String databaseHost;
    public static String databaseName;
    public static HashMap<String, String> MessageTypes;
    public static String databaseUser;
    public static String databasePassword;
    public static String barMessage;
    public static String barColor;
    public static String barStyle;
    public static boolean debug;
    public static double barProgress;
    public static String blacklistedBlockMessage;
    
    public static void apply() {
        Config.debug = Files.config.getBoolean("debug");
        final ConfigurationSection AdaptionSection = Files.config.getConfigurationSection("cell_adaptions");
        assert AdaptionSection != null;
        Config.blacklistedWorlds = AdaptionSection.getStringList("blacklistedWorlds");
        Config.blacklistedBlocks = AdaptionSection.getStringList("blacklistedBlocks");
        Config.blacklistedBlockMessage = AdaptionSection.getString("blacklistedBlockMessage");
        final ConfigurationSection databaseSection = Files.config.getConfigurationSection("database");
        assert databaseSection != null;
        Config.databaseHost = databaseSection.getString("host");
        Config.databaseName = databaseSection.getString("name");
        Config.databaseUser = databaseSection.getString("user");
        Config.databasePassword = databaseSection.getString("password");

        final ConfigurationSection messageTypesSection = Files.config.getConfigurationSection("messages");
        assert messageTypesSection != null;
        for (final String key : messageTypesSection.getKeys(false)) {
            Config.MessageTypes.put(key, messageTypesSection.getString("messages." + key));
        }
    }
    
    public static void reload() {
        Config.blacklistedWorlds = Collections.emptyList();
        Config.blacklistedBlocks = Collections.emptyList();
        Config.MessageTypes = new HashMap<String, String>();
        Config.databaseHost = "";
        Config.blacklistedBlockMessage = "";
        Config.databaseName = "";
        Config.databaseUser = "";
        Config.databasePassword = "";
        Config.debug = false;
        Config.barMessage = "";
        Config.barColor = "";
        Config.barStyle = "";
        Config.barProgress = 0.0;
        Files.base();
    }
    
    static {
        Config.blacklistedWorlds = Collections.emptyList();
        Config.blacklistedBlocks = Collections.emptyList();
        Config.databaseHost = "";
        Config.barProgress = 0.0;
        Config.databaseName = "";
        Config.blacklistedBlockMessage = "";
        Config.databaseUser = "";
        Config.databasePassword = "";
        Config.debug = false;
        Config.MessageTypes = new HashMap<String, String>();
        Config.barMessage = "";
        Config.barColor = "";
        Config.barStyle = "";
    }
}
