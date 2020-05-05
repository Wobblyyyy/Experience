package me.wobblyyyy.experience;

import org.bukkit.ChatColor;

public class Configuration
{
    public String toolCounterFormat = ChatColor.translateAlternateColorCodes('&', "&r&b%name% &f&l| &7%counter%");
    public String counterFormat = ChatColor.translateAlternateColorCodes('&', "&7<&b%counter%&7>");

    public String getToolCounterFormat ()
    {
        return toolCounterFormat;
    }

    public void setToolCounterFormat (String toolCounterFormat)
    {
        this.toolCounterFormat = toolCounterFormat;
    }
}
