package me.mynqme.plasmamessages;

import org.bukkit.event.player.PlayerQuitEvent;
import java.sql.SQLException;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.ChatColor;
import java.util.Objects;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.Listener;

public class Events implements Listener
{
    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        if (Config.blacklistedWorlds.contains(Objects.requireNonNull(event.getBlockPlaced().getWorld().getName())) && Config.blacklistedBlocks.contains(event.getBlockPlaced().getType().toString())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Config.blacklistedBlockMessage));
        }
    }
    
    @EventHandler
    public void onRightClick(final PlayerInteractEvent event) {
        if ((event.getAction() == Action.PHYSICAL || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null && event.getItem().getType().toString().equals("DIAMOND_PICKAXE")) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "dm open enchant_menu " + event.getPlayer().getName());
        }
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) throws SQLException {
        Database.createPlayer(event.getPlayer().getUniqueId());
        PlasmaMessages.playerData.put(event.getPlayer().getUniqueId(), Database.getPlayerData(event.getPlayer().getUniqueId()));
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent event) throws SQLException {
        Database.saveToDatabase(event.getPlayer().getUniqueId());
        PlasmaMessages.playerData.remove(event.getPlayer().getUniqueId());
    }
}
