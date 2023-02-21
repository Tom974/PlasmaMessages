package me.mynqme.plasmamessages;

import org.bukkit.ChatColor;
import java.util.Iterator;
import org.bukkit.command.CommandExecutor;
import java.util.Objects;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import java.sql.SQLException;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import java.util.UUID;
import java.util.HashMap;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlasmaMessages extends JavaPlugin
{
    public static HashMap<UUID, HashMap<String, String>> playerData;
    
    public void onEnable() {
        this.getLogger().info("PlasmaMessages has been enabled!");
        Files.base();
        this.getServer().getPluginManager().registerEvents((Listener)new Events(), (Plugin)this);
        Database.getConnection();
        try {
            Database.setup();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (final Player player : this.getServer().getOnlinePlayers()) {
            try {
                Database.createPlayer(player.getUniqueId());
                final HashMap<String, String> data = Database.getPlayerData(player.getUniqueId());
                PlasmaMessages.playerData.put(player.getUniqueId(), data);
            }
            catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        Objects.requireNonNull(this.getServer().getPluginCommand("plasmamessages")).setExecutor(new Commands());
        Objects.requireNonNull(this.getServer().getPluginCommand("boosters")).setExecutor(new Commands());
    }
    
    public static void playerMessage(final Player player, final String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
    
    public void onDisable() {
        this.getLogger().info("PlasmaMessages has been disabled!");
    }
    
    static {
        PlasmaMessages.playerData = new HashMap<UUID, HashMap<String, String>>();
    }
}
