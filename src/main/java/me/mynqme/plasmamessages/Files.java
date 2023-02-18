package me.mynqme.plasmamessages;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;

public class Files
{
    public static File configFile;
    public static FileConfiguration config;
    
    public static void base() {
        final PlasmaMessages main = (PlasmaMessages)PlasmaMessages.getPlugin((Class)PlasmaMessages.class);
        Files.configFile = new File(main.getDataFolder(), "config.yml");
        if (!Files.configFile.exists()) {
            main.saveResource("config.yml", false);
        }
        Files.config = (FileConfiguration)YamlConfiguration.loadConfiguration(Files.configFile);
        Config.apply();
    }
}
