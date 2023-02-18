package me.mynqme.plasmamessages;

import org.bukkit.inventory.meta.ItemMeta;
import java.util.Iterator;
import net.luckperms.api.model.user.User;
import net.luckperms.api.LuckPerms;
import me.clip.autosell.objects.Multiplier;
import java.util.HashMap;
import org.bukkit.command.ConsoleCommandSender;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import net.luckperms.api.node.Node;
import net.luckperms.api.LuckPermsProvider;
import me.clip.autosell.AutoSellAPI;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class Commands implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length == 1 && command.getName().equalsIgnoreCase("plasmamessages") && args[0].equalsIgnoreCase("reload")) {
            Config.reload();
            PlasmaMessages.playerMessage((Player)sender, "§aConfig reloaded!");
            return true;
        }
        if ((command.getName().equalsIgnoreCase("booster") || command.getName().equalsIgnoreCase("boosters")) && sender instanceof Player) {
            final Player player = (Player)sender;
            final Multiplier multiplier = AutoSellAPI.getMultiplier(player);
            String sellbooster = "";
            if (multiplier == null) {
                sellbooster = " &f&l\u27a5 &c&lSell Booster: &f1.0x";
            }
            else {
                sellbooster = " &f&l\u27a5 &c&lSell Booster: &f" + multiplier.getMultiplier() + "x";
            }
            double tokenbooster = 1.0;
            double pickaxebooster = 1.0;
            final LuckPerms api = LuckPermsProvider.get();
            final User user = api.getPlayerAdapter((Class)Player.class).getUser((Object)player);
            for (final Node node : user.getNodes()) {
                if (node.getKey().startsWith("plasmaprison.tokens.multiplier.")) {
                    final float val = Integer.valueOf(node.getKey().split("plasmaprison.tokens.multiplier.")[1]);
                    if (val > tokenbooster) {
                        tokenbooster = val;
                    }
                }
                if (node.getKey().startsWith("plasmaprison.tokens.multiplier.")) {
                    final float val = Integer.valueOf(node.getKey().split("leveltools.multiplier.")[1]);
                    if (val <= pickaxebooster) {
                        continue;
                    }
                    pickaxebooster = val;
                }
            }
            final String pickaxestring = "\n &l\u27a5 &a&lPickaxe Booster: &f" + pickaxebooster + "x";
            final String tokenstring = "\n &l\u27a5 &6&lToken Booster: &f" + tokenbooster + "x";
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lPlasma&f&lMC &8» &r&dYour current active boosters:\n" + sellbooster + tokenstring + pickaxestring));
            return true;
        }
        if (command.getName().equalsIgnoreCase("givepick")) {
            final ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
            final ItemMeta meta = item.getItemMeta();
            assert meta != null;
            if (args.length >= 1) {
                meta.setDisplayName("§d" + Objects.requireNonNull(Bukkit.getPlayer(args[0])).getName() + "'s Pickaxe");
            }
            else {
                meta.setDisplayName("§d" + sender.getName() + "'s Pickaxe");
            }
            meta.setUnbreakable(true);
            meta.setLore((List)Arrays.asList(" ", "§5Enchantments"));
            item.setItemMeta(meta);
            ((Player)sender).getInventory().addItem(new ItemStack[] { item });
            return true;
        }
        else {
            if (args.length < 3) {
                sender.sendMessage("Usage: /plasmamessages <togglesetting,sendmessage> <type> <player>");
                return true;
            }
            final String arg0 = args[0].toLowerCase();
            final String arg2 = args[1].toLowerCase();
            final Player player2 = Bukkit.getPlayer(args[2]);
            assert player2 != null;
            if (sender instanceof ConsoleCommandSender || (sender instanceof Player && sender.hasPermission("*"))) {
                if (arg0.equals("togglesetting")) {
                    final String s = arg2;
                    switch (s) {
                        case "expansion": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("expansion").equals("true")) {
                                PlasmaMessages.playerData.get(player2.getUniqueId()).put("expansion", "false");
                                PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &cdisabled&f Expansion messages.");
                                break;
                            }
                            PlasmaMessages.playerData.get(player2.getUniqueId()).put("expansion", "true");
                            PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &aenabled&f Expansion messages.");
                            break;
                        }
                        case "pickaxe": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("pickaxe").equals("true")) {
                                PlasmaMessages.playerData.get(player2.getUniqueId()).put("pickaxe", "false");
                                PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &cdisabled&f Pickaxe Levelup messages.");
                                break;
                            }
                            PlasmaMessages.playerData.get(player2.getUniqueId()).put("pickaxe", "true");
                            PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &aenabled&f Pickaxe Levelup messages.");
                            break;
                        }
                        case "minelevel": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("minelevel").equals("true")) {
                                PlasmaMessages.playerData.get(player2.getUniqueId()).put("minelevel", "false");
                                PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &cdisabled&f Minelevel Levelup messages.");
                                break;
                            }
                            PlasmaMessages.playerData.get(player2.getUniqueId()).put("minelevel", "true");
                            PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &aenabled&f Minelevel Levelup messages.");
                            break;
                        }
                        case "prestige": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("prestige").equals("true")) {
                                PlasmaMessages.playerData.get(player2.getUniqueId()).put("prestige", "false");
                                PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &cdisabled&f Prestige messages.");
                                break;
                            }
                            PlasmaMessages.playerData.get(player2.getUniqueId()).put("prestige", "true");
                            PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &aenabled&f Prestige messages.");
                            break;
                        }
                        case "lucky": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("lucky").equals("true")) {
                                PlasmaMessages.playerData.get(player2.getUniqueId()).put("lucky", "false");
                                PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &cdisabled&f Lucky messages.");
                                break;
                            }
                            PlasmaMessages.playerData.get(player2.getUniqueId()).put("lucky", "true");
                            PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &aenabled&f Lucky messages.");
                            break;
                        }
                        case "treasurehunt": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("treasurehunt").equals("true")) {
                                PlasmaMessages.playerData.get(player2.getUniqueId()).put("treasurehunt", "false");
                                PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &cdisabled&f TreasureHunt messages.");
                                break;
                            }
                            PlasmaMessages.playerData.get(player2.getUniqueId()).put("treasurehunt", "true");
                            PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &aenabled&f TreasureHunt messages.");
                            break;
                        }
                        case "keyfinder": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("keyfinder").equals("true")) {
                                PlasmaMessages.playerData.get(player2.getUniqueId()).put("keyfinder", "false");
                                PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &cdisabled&f KeyFinder messages.");
                                break;
                            }
                            PlasmaMessages.playerData.get(player2.getUniqueId()).put("keyfinder", "true");
                            PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &aenabled&f KeyFinder messages.");
                            break;
                        }
                        case "valuehunter": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("valuehunter").equals("true")) {
                                PlasmaMessages.playerData.get(player2.getUniqueId()).put("valuehunter", "false");
                                PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &cdisabled&f ValueHunter messages.");
                                break;
                            }
                            PlasmaMessages.playerData.get(player2.getUniqueId()).put("valuehunter", "true");
                            PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &aenabled&f ValueHunter messages.");
                            break;
                        }
                        case "lottery": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("lottery").equals("true")) {
                                PlasmaMessages.playerData.get(player2.getUniqueId()).put("lottery", "false");
                                PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &cdisabled&f Lottery messages.");
                                break;
                            }
                            PlasmaMessages.playerData.get(player2.getUniqueId()).put("lottery", "true");
                            PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &aenabled&f Lottery messages.");
                            break;
                        }
                        case "multifinder": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("multifinder").equals("true")) {
                                PlasmaMessages.playerData.get(player2.getUniqueId()).put("multifinder", "false");
                                PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &cdisabled&f MultiFinder messages.");
                                break;
                            }
                            PlasmaMessages.playerData.get(player2.getUniqueId()).put("multifinder", "true");
                            PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &aenabled&f MultiFinder messages.");
                            break;
                        }
                        case "jackpot": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("jackpot").equals("true")) {
                                PlasmaMessages.playerData.get(player2.getUniqueId()).put("jackpot", "false");
                                PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &cdisabled&f JackPot messages.");
                                break;
                            }
                            PlasmaMessages.playerData.get(player2.getUniqueId()).put("jackpot", "true");
                            PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &aenabled&f JackPot messages.");
                            break;
                        }
                        case "gemseeker": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("gemseeker").equals("true")) {
                                PlasmaMessages.playerData.get(player2.getUniqueId()).put("gemseeker", "false");
                                PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &cdisabled&f GemSeeker messages.");
                                break;
                            }
                            PlasmaMessages.playerData.get(player2.getUniqueId()).put("gemseeker", "true");
                            PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &aenabled&f GemSeeker messages.");
                            break;
                        }
                        case "safe": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("safe").equals("true")) {
                                PlasmaMessages.playerData.get(player2.getUniqueId()).put("safe", "false");
                                PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &cdisabled&f Safe messages.");
                                break;
                            }
                            PlasmaMessages.playerData.get(player2.getUniqueId()).put("safe", "true");
                            PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &aenabled&f Safe messages.");
                            break;
                        }
                        case "all": {
                            for (final String key : PlasmaMessages.playerData.get(player2.getUniqueId()).keySet()) {
                                PlasmaMessages.playerData.get(player2.getUniqueId()).put(key, "false");
                            }
                            PlasmaMessages.playerMessage(player2, "&8[&4&lSettings&8] &fYou have &cdisabled&f all enchant messages.");
                            break;
                        }
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
                    final String s2 = arg2;
                    switch (s2) {
                        case "safe": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("safe").equals("true")) {
                                PlasmaMessages.playerMessage(player2, "&8[&3&lSafe&8] &fYou won " + (Object)extraargs + " from the safe!");
                                break;
                            }
                            break;
                        }
                        case "expansion": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("expansion").equals("true")) {
                                PlasmaMessages.playerMessage(player2, "&8[&d&lP-Mine&8] &fYour mine has expanded by 1 block!");
                                break;
                            }
                            break;
                        }
                        case "pickaxe": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("pickaxe").equals("true")) {
                                PlasmaMessages.playerMessage(player2, "&8[&b&lPickaxe&8] &fYour pickaxe leveled up to level &3" + args[3]);
                                break;
                            }
                            break;
                        }
                        case "minelevel": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("minelevel").equals("true")) {
                                PlasmaMessages.playerMessage(player2, "&8[&a&lMine&8] &fYour mine leveled up to level &2" + args[3]);
                                break;
                            }
                            break;
                        }
                        case "prestige": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("prestige").equals("true")) {
                                PlasmaMessages.playerMessage(player2, "&8[&a&lPrestige&8] &fYou have prestiged");
                                break;
                            }
                            break;
                        }
                        case "gemseeker": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("gemseeker").equals("true")) {
                                PlasmaMessages.playerMessage(player2, "&8[&a&lGem Seeker&8] &fYou found &2" + (Object)extraargs + " &aGems&f.");
                                break;
                            }
                            break;
                        }
                        case "jackpot": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("jackpot").equals("true")) {
                                PlasmaMessages.playerMessage(player2, "&8[&4&lJ&6&la&e&lc&a&lk&b&lp&9&lo&5&lt&8] &fYou found a &4Reward&f.");
                                break;
                            }
                            break;
                        }
                        case "multifinder": {
                            if (args.length < 4) {
                                PlasmaMessages.playerMessage(player2, "Usage: /plasmamessages sendmessage multifinder <player> <type>");
                                return true;
                            }
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("multifinder").equals("false")) {
                                return true;
                            }
                            final String typeMulti = args[3].toLowerCase();
                            if (typeMulti.equals("sellmulti15min")) {
                                PlasmaMessages.playerMessage(player2, "&8[&c&lMultiFinder&8] &fYou found a &41.25x Sell Multiplier &ffor &415min&f.");
                                break;
                            }
                            if (typeMulti.equals("tokenmulti15min")) {
                                PlasmaMessages.playerMessage(player2, "&8[&c&lMultiFinder&8] &fYou found a &41.25x Token Multiplier &ffor &415min&f.");
                                break;
                            }
                            if (typeMulti.equals("xpmulti15min")) {
                                PlasmaMessages.playerMessage(player2, "&8[&c&lMultiFinder&8] &fYou found a &41.25x XP Multiplier &ffor &415min&f.");
                                break;
                            }
                            if (typeMulti.equals("1sellmulti15min")) {
                                PlasmaMessages.playerMessage(player2, "&8[&c&lMultiFinder&8] &fYou found a &41.5x Sell Multiplier &ffor &415min&f.");
                                break;
                            }
                            if (typeMulti.equals("1tokenmulti15min")) {
                                PlasmaMessages.playerMessage(player2, "&8[&c&lMultiFinder&8] &fYou found a &41.5x Token Multiplier &ffor &415min&f.");
                                break;
                            }
                            if (typeMulti.equals("1xpmulti15min")) {
                                PlasmaMessages.playerMessage(player2, "&8[&c&lMultiFinder&8] &fYou found a &41.5x XP Multiplier &ffor &415min&f.");
                                break;
                            }
                            PlasmaMessages.playerMessage(player2, "&8[&5&lPlasma&f&lMessages&8] &cAn error has occured. Please create a ticket using this message.");
                            break;
                        }
                        case "lottery": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("lottery").equals("true")) {
                                PlasmaMessages.playerMessage(player2, "&8[&6&lLottery&8] &fYou found a &eLottery Ticket&f.");
                                break;
                            }
                            break;
                        }
                        case "valuehunter": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("valuehunter").equals("false")) {
                                return true;
                            }
                            final String typeValue = args[3].toLowerCase();
                            if (typeValue.equals("sponge")) {
                                PlasmaMessages.playerMessage(player2, "&8[&5&lValue Hunter&8] &fYou received &e" + args[4] + "x Sponge&f.");
                                break;
                            }
                            if (typeValue.equals("beacon")) {
                                PlasmaMessages.playerMessage(player2, "&8[&5&lValue Hunter&8] &fYou received &b" + args[4] + "x&f Beacon&f.");
                                break;
                            }
                            if (typeValue.equals("dragonegg")) {
                                PlasmaMessages.playerMessage(player2, "&8[&5&lValue Hunter&8] &fYou received &5" + args[4] + "x&f Dragon egg&f.");
                                break;
                            }
                            if (typeValue.equals("sealantern")) {
                                PlasmaMessages.playerMessage(player2, "&8[&5&lValue Hunter&8] &fYou received &3" + args[4] + "x&f Sea Lantern&f.");
                                break;
                            }
                            PlasmaMessages.playerMessage(player2, "&8[&5&lPlasma&f&lMessages&8] &cAn error has occured. Please create a ticket using this message.");
                            break;
                        }
                        case "keyfinder": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("keyfinder").equals("false")) {
                                return true;
                            }
                            final String typeKeyFinder = args[3].toLowerCase();
                            if (typeKeyFinder.equals("mine1")) {
                                PlasmaMessages.playerMessage(player2, "&8[&4&lKeyFinder&8] &fYou received a &bMine Key&7 (Tier 1)");
                                break;
                            }
                            if (typeKeyFinder.equals("mine2")) {
                                PlasmaMessages.playerMessage(player2, "&8[&4&lKeyFinder&8] &fYou received a &bMine Key&7 (Tier 2)");
                                break;
                            }
                            if (typeKeyFinder.equals("mine3")) {
                                PlasmaMessages.playerMessage(player2, "&8[&4&lKeyFinder&8] &fYou received a &bMine Key&7 (Tier 3)");
                                break;
                            }
                            if (typeKeyFinder.equals("affix")) {
                                PlasmaMessages.playerMessage(player2, "&8[&4&lKeyFinder&8] &fYou received a &eAffix Key");
                                break;
                            }
                            if (typeKeyFinder.equals("lithium")) {
                                PlasmaMessages.playerMessage(player2, "&8[&4&lKeyFinder&8] &fYou received a &4Lithium Key");
                                break;
                            }
                            if (typeKeyFinder.equals("plasma")) {
                                PlasmaMessages.playerMessage(player2, "&8[&4&lKeyFinder&8] &fYou received a &5Plasma Key");
                                break;
                            }
                            break;
                        }
                        case "treasurehunt": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("treasurehunt").equals("true")) {
                                PlasmaMessages.playerMessage(player2, "&8[&9&lTreasure Hunt&8] &fYou found a &6" + (Object)extraargs + " Treasure");
                                break;
                            }
                            break;
                        }
                        case "lucky": {
                            if (PlasmaMessages.playerData.get(player2.getUniqueId()).get("lucky").equals("true")) {
                                PlasmaMessages.playerMessage(player2, "&8[&3&lLucky&8] &fYou received &e" + (Object)extraargs);
                                break;
                            }
                            break;
                        }
                    }
                    return true;
                }
            }
            return true;
        }
    }
}
