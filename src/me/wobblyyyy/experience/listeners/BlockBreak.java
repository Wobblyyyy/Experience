package me.wobblyyyy.experience.listeners;

import me.wobblyyyy.experience.Experience;
import me.wobblyyyy.experience.handlers.BlockBreakHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener
{
    Experience plugin;
    BlockBreakHandler handler;
    public BlockBreak (Experience plugin)
    {
        this.plugin = plugin;
        handler = new BlockBreakHandler(plugin);
    }
    @EventHandler public void onBlockBreak (BlockBreakEvent event)
    {
        handler.handleBlockBreak(event);
    }
}
