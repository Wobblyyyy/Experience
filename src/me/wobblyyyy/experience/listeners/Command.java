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
        if (label.equalsIgnoreCase("exp") || label.equalsIgnoreCase("experience"))
        {
            if (args.length > 0)
            {
                switch (args[0])
                {
                    case "enchant":
                        switch (args[1])
                        {
                            case "autosmelt":
                                Player player = Bukkit.getPlayer(args[2]);
                                assert player != null;
                                ItemStack item = player.getInventory().getItemInMainHand();
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
                                assert lore != null;
                                lore.add(ChatColor.translateAlternateColorCodes('&', "&r&7Auto Smelting"));
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cExperience &f&l> &7Enchanted item with Auto Smelting!"));
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                item.addUnsafeEnchantment(plugin.getAutoSmelt(), 1);
                                player.getInventory().setItemInMainHand(item);
                                break;
                            default:
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cExperience &f&l> &7Unknown enchantment: &c" + args[1]));
                        }
                        break;
                    default:
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cExperience &f&l> &7Unknown subcommand: &c" + args[0]));
                }
            }
        }
        return true;
    }
}
