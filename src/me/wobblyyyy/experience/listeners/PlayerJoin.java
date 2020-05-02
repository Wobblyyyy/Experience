package me.wobblyyyy.experience.listeners;

import me.wobblyyyy.experience.Experience;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener
{
    Experience plugin;
    public PlayerJoin (Experience plugin)
    {
        this.plugin = plugin;
    }
    @EventHandler public void onPlayerJoin (PlayerJoinEvent event)
    {
        plugin.getPlayerJoinInterface().executeScriptFunction("incrementJSON", "[onPlayerJoin][" + event.getPlayer().getName() + "]", 1);
    }
}
