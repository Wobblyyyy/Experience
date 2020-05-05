package me.wobblyyyy.experience.listeners;

import me.wobblyyyy.experience.Experience;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Command implements CommandExecutor
{
    Experience plugin;

    public Command (Experience plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand (CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args)
    {
        if (args.length > 0)
        {
            switch (args[0])
            {
                case "player":
                    break;
                case "admin":
                    switch (args[1])
                    {
                        case "enchant":
                            switch (args[2])
                            {
                                case "autosmelt":
                                case "autosmelting":
                                    Player player = args.length > 3 && args[3] != null ? Bukkit.getPlayer(args[3]) : (Player) commandSender;
                                    assert player != null;
                                    ItemStack item = player.getInventory().getItemInMainHand();
                                    ItemMeta meta = item.getItemMeta();
                                    assert meta != null;
                                    List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
                                    assert lore != null;
                                    if (!lore.contains(plugin.getAutoSmelt().lore))
                                    {
                                        lore.add(plugin.getAutoSmelt().lore);
                                    }
                                    meta.setLore(lore);
                                    item.setItemMeta(meta);
                                    item.addUnsafeEnchantment(plugin.getAutoSmelt(), 1);
                                    player.getInventory().setItemInMainHand(item);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cExperience &f&l> &7Enchanted item with:&c " + args[2]));
                                    break;
                                default:
                                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cExperience &f&l> &7Unknown enchantment:&c " + args[2]));
                            }
                            break;
                        case "deenchant":
                            break;
                        default:
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cExperience &f&l> &7Unknown subcommand: &c" + args[0]));
                    }
                    break;
                default:
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cExperience &f&l> &7Unknown subcommand: &c" + args[0]));
                    break;
            }
        }
        return true;
    }
}
