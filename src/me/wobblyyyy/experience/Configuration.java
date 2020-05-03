package me.wobblyyyy.experience;

import org.bukkit.ChatColor;

public class Configuration
{
    public String toolCounterFormat = ChatColor.translateAlternateColorCodes('&', "&r&f&l%name%&7");
    public String counterFormat = ChatColor.translateAlternateColorCodes('&', "&8&l | &7%counter%");

    public void setToolCounterFormat (String toolCounterFormat)
    {
        this.toolCounterFormat = toolCounterFormat;
    }
    public String getToolCounterFormat ()
    {
        return toolCounterFormat;
    }
}
