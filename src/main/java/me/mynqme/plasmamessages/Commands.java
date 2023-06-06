package me.mynqme.plasmamessages;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import java.sql.SQLException;
import java.util.Arrays;

public class Commands implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!command.getName().equalsIgnoreCase("plasmamessages")) {
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            Config.reload();
            PlasmaMessages.playerMessage((Player)sender, "§aConfig reloaded!");
            return true;
        }
        if (args.length < 3) {
            sender.sendMessage("Usage: /plasmamessages <togglesetting,sendmessage, custommessage> <type> <player>");
            return true;
        }
        final String arg0 = args[0].toLowerCase();
        final String arg2 = args[1].toLowerCase();
        final Player player2 = Bukkit.getPlayer(args[2]);
        assert player2 != null;
        if (sender instanceof ConsoleCommandSender || (sender instanceof Player && sender.hasPermission("*"))) {
            if (arg0.equalsIgnoreCase("custommessage")) {
                // get everything after the first 2 args and put it in 1 long string
                Player plyer = Bukkit.getPlayer(args[1]);
                if (plyer == null) {
                    sender.sendMessage("You didn't specify an online player!");
                }
                String msg = Arrays.toString(args)
                        .replace("[", "")
                        .replace("]", "")
                        .replace(",", "")
                        .replace(args[0], "")
                        .replace(args[1], "")
                .trim();
                PlasmaMessages.playerMessage(plyer, msg);
                return true;
            }
            if (arg0.equals("togglesetting")) {
                final String string = arg2;
                if (!PlasmaMessages.playerData.containsKey(player2.getUniqueId())) {
                    try {
                        Database.createPlayer(player2.getUniqueId());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        PlasmaMessages.playerData.put(player2.getUniqueId(), Database.getPlayerData(player2.getUniqueId()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (!PlasmaMessages.playerData.containsKey(player2.getUniqueId())) {
                    Bukkit.getLogger().warning("Something went wrong, playerData does not contain user: " + player2.getUniqueId());
                    return true;
                }
                if (!PlasmaMessages.playerData.get(player2.getUniqueId()).containsKey(string)) {
                    Bukkit.getLogger().warning("Something went wrong, playerData does not contain string: " + string + " for player: " + player2.getName());
                    for (final String s : PlasmaMessages.playerData.get(player2.getUniqueId()).keySet()) {
                        Bukkit.getLogger().warning("Key set: " + s);
                    }
                    return true;
                }
                if (PlasmaMessages.playerData.get(player2.getUniqueId()).get(string) == null) {
                    Bukkit.getLogger().warning("Something went wrong, playerData string is null: " + string + " for player: " + player2.getName() + " value: " + PlasmaMessages.playerData.get(player2.getUniqueId()).get(string));
                    for (final String s : PlasmaMessages.playerData.get(player2.getUniqueId()).keySet()) {
                        Bukkit.getLogger().warning("Key set: " + s);
                    }
                    return true;
                }
                if (PlasmaMessages.playerData.get(player2.getUniqueId()).get(string).equals("true")) {
                    PlasmaMessages.playerData.get(player2.getUniqueId()).put(string, "false");
                    PlasmaMessages.playerMessage(player2, Files.config.getString("messages." + string + ".setting").replace("%args%", "&cdisabled"));
                } else {
                    PlasmaMessages.playerData.get(player2.getUniqueId()).put(string, "true");
                    PlasmaMessages.playerMessage(player2, Files.config.getString("messages." + string + ".setting").replace("%args%", "&aenabled"));
                }
                return true;
            }

            StringBuilder extraargs = new StringBuilder();
            for (int i = 3; i < args.length; ++i) {
                extraargs.append(args[i]).append(" ");
                if (i == args.length - 1) {
                    extraargs = new StringBuilder(extraargs.substring(0, extraargs.length() - 1));
                }
            }
            if (arg0.equals("sendmessage")) {
                if (!PlasmaMessages.playerData.containsKey(player2.getUniqueId())) {
                    Bukkit.getLogger().warning("Something went wrong, playerData does not contain user: " + player2.getName());
                    return true;
                }
                final String s2 = arg2;
                if (Files.config.getString("messages." + s2 + ".message") == null) {
                    Bukkit.getLogger().warning("&8[&4&lSettings&8] &cThe "+s2+" message doesn't exist in the config!");
                    return true;
                }
                if (PlasmaMessages.playerData.get(player2.getUniqueId()).containsKey(s2)) {
                    if (PlasmaMessages.playerData.get(player2.getUniqueId()).get(s2) == null) {
                        Bukkit.getLogger().warning("Something went wrong, playerData does not contain string: " + s2 + " for player: " + player2.getName());
                        return true;
                    }
                    if (PlasmaMessages.playerData.get(player2.getUniqueId()).get(s2).equals("true")) {
                        PlasmaMessages.playerMessage(player2, Files.config.getString("messages." + s2 + ".message").replace("%args%", extraargs.toString()));
                    }
                }

                return true;
            }

        }
        return true;

    }
}
