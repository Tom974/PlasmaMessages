package me.mynqme.plasmamessages;

import java.util.Collections;
import org.bukkit.configuration.ConfigurationSection;
import java.util.List;

public class Config
{
    public static List<String> blacklistedWorlds;
    public static List<String> blacklistedBlocks;
    public static String databaseHost;
    public static String databaseName;
    public static String databaseUser;
    public static String databasePassword;
    public static boolean debug;
    public static String blacklistedBlockMessage;
    
    public static void apply() {
        Config.debug = Files.config.getBoolean("debug");
        final ConfigurationSection AdaptionSection = Files.config.getConfigurationSection("cell_adaptions");
        assert AdaptionSection != null;
        Config.blacklistedWorlds = (List<String>)AdaptionSection.getStringList("blacklistedWorlds");
        Config.blacklistedBlocks = (List<String>)AdaptionSection.getStringList("blacklistedBlocks");
        Config.blacklistedBlockMessage = AdaptionSection.getString("blacklistedBlockMessage");
        final ConfigurationSection databaseSection = Files.config.getConfigurationSection("database");
        assert databaseSection != null;
        Config.databaseHost = databaseSection.getString("host");
        Config.databaseName = databaseSection.getString("name");
        Config.databaseUser = databaseSection.getString("user");
        Config.databasePassword = databaseSection.getString("password");
    }
    
    public static void reload() {
        Config.blacklistedWorlds = Collections.emptyList();
        Config.blacklistedBlocks = Collections.emptyList();
        Config.databaseHost = "";
        Config.blacklistedBlockMessage = "";
        Config.databaseName = "";
        Config.databaseUser = "";
        Config.databasePassword = "";
        Config.debug = false;
        Files.base();
    }
    
    static {
        Config.blacklistedWorlds = Collections.emptyList();
        Config.blacklistedBlocks = Collections.emptyList();
        Config.databaseHost = "";
        Config.databaseName = "";
        Config.blacklistedBlockMessage = "";
        Config.databaseUser = "";
        Config.databasePassword = "";
        Config.debug = false;
    }
}
