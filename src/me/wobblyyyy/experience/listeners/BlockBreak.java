package me.wobblyyyy.experience.listeners;

import me.wobblyyyy.experience.Experience;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class BlockBreak implements Listener
{
    Experience plugin;
    public BlockBreak (Experience plugin)
    {
        this.plugin = plugin;
    }
    @EventHandler public void onBlockBreak (BlockBreakEvent event)
    {
        String item = event.getPlayer().getInventory().getItemInMainHand().getType().toString();
        if (plugin.getObjectLists().pickaxes.contains(item))
        {
            plugin.getBlocksInterface().executeScriptFunction("incrementJSON", "[pickaxes][" + event.getPlayer().getName() + "]", 1);
        }
        else if (plugin.getObjectLists().axes.contains(item))
        {
            plugin.getBlocksInterface().executeScriptFunction("incrementJSON", "[axes][" + event.getPlayer().getName() + "]", 1);
        }
        else if (plugin.getObjectLists().shovels.contains(item))
        {
            plugin.getBlocksInterface().executeScriptFunction("incrementJSON", "[shovels][" + event.getPlayer().getName() + "]", 1);
        }
    }
}
