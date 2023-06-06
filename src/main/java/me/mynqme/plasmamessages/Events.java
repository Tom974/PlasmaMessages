package me.mynqme.plasmamessages;

import org.bukkit.event.player.PlayerQuitEvent;
import java.sql.SQLException;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Events implements Listener
{
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) throws SQLException {
        try {
            Database.createPlayer(event.getPlayer().getUniqueId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            PlasmaMessages.playerData.put(event.getPlayer().getUniqueId(), Database.getPlayerData(event.getPlayer().getUniqueId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent event) throws SQLException {
        Database.saveToDatabase(event.getPlayer().getUniqueId());
        PlasmaMessages.playerData.remove(event.getPlayer().getUniqueId());
    }
}
