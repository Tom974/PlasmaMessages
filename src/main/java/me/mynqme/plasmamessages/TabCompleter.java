package me.mynqme.plasmamessages;

import java.util.Iterator;
import java.util.Collections;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TabCompleter implements org.bukkit.command.TabCompleter
{
    public List<String> onTabComplete(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        final List<String> list = new ArrayList<String>();
        if (sender instanceof Player && cmd.getName().equalsIgnoreCase("command")) {
            if (args.length == 0) {
                list.add("togglesetting");
                list.add("sendmessage");
                Collections.sort(list);
                return list;
            }
            if (args.length == 1) {
                list.add("expansion");
                list.add("pickaxe");
                list.add("minelevel");
                list.add("prestige");
                list.add("lucky");
                list.add("treasurehunt");
                list.add("keyfinder");
                list.add("valuehunter");
                list.add("lottery");
                list.add("multifinder");
                list.add("jackpot");
                list.add("gemseeker");
                list.add("safe");
                for (final String s : list) {
                    if (!s.toLowerCase().startsWith(args[0].toLowerCase())) {
                        list.remove(s);
                    }
                }
                Collections.sort(list);
                return list;
            }
            if (args.length == 2) {
                for (final Player p : sender.getServer().getOnlinePlayers()) {
                    list.add(p.getName());
                }
                for (final String s : list) {
                    if (!s.toLowerCase().startsWith(args[0].toLowerCase())) {
                        list.remove(s);
                    }
                }
                Collections.sort(list);
                return list;
            }
            if (args.length == 3) {
                list.add("amount");
                list.add("type");
                for (final String s : list) {
                    if (!s.toLowerCase().startsWith(args[0].toLowerCase())) {
                        list.remove(s);
                    }
                }
                Collections.sort(list);
                return list;
            }
        }
        return null;
    }
}
