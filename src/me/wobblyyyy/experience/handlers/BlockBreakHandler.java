package me.wobblyyyy.experience.handlers;

import me.wobblyyyy.experience.Experience;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockBreakHandler
{
    Experience plugin;
    public BlockBreakHandler(Experience plugin)
    {
        this.plugin = plugin;
    }

    public void handleBlockBreak (BlockBreakEvent event)
    {
        String item = event.getPlayer().getInventory().getItemInMainHand().getType().toString();
        boolean isTool = false;
        if (plugin.getObjectLists().pickaxes.contains(item))
        {
            plugin.getBlocksInterface().executeScriptFunction("incrementJSON", "[pickaxes][" + event.getPlayer().getName() + "]", 1);
            isTool = true;
        }
        else if (plugin.getObjectLists().axes.contains(item))
        {
            plugin.getBlocksInterface().executeScriptFunction("incrementJSON", "[axes][" + event.getPlayer().getName() + "]", 1);
            isTool = true;
        }
        else if (plugin.getObjectLists().shovels.contains(item))
        {
            plugin.getBlocksInterface().executeScriptFunction("incrementJSON", "[shovels][" + event.getPlayer().getName() + "]", 1);
            isTool = true;
        }

        if (isTool)
        {
            ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
            meta.setDisplayName("This is a test");
            event.getPlayer().getInventory().getItemInMainHand().setItemMeta(meta);
        }
    }
}
