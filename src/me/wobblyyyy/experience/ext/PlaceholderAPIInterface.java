package me.wobblyyyy.experience.ext;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.wobblyyyy.experience.Experience;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class PlaceholderAPIInterface extends PlaceholderExpansion
{
    private Experience plugin;
    public PlaceholderAPIInterface (Experience plugin)
    {
        this.plugin = plugin;
    }

    @Override public boolean persist ()
    {
        return true;
    }
    @Override public boolean canRegister ()
    {
        return true;
    }
    @Override public String getAuthor ()
    {
        return plugin.getDescription().getAuthors().toString();
    }
    @Override public String getIdentifier ()
    {
        return "experience";
    }
    @Override public String getVersion ()
    {
        return plugin.getDescription().getVersion();
    }

    private static final NavigableMap <Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "m");
        suffixes.put(1_000_000_000L, "b");
        suffixes.put(1_000_000_000_000L, "t");
        suffixes.put(1_000_000_000_000_000L, "q");
        suffixes.put(1_000_000_000_000_000_000L, "Q");
    }
    public static String format (long value)
    {
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value);

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10);
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    @Override public String onPlaceholderRequest (Player player, String identifier)
    {
        if (player == null) return "";
        if (identifier.equals("blocksmined_pickaxes"))
        {
            return format(Integer.parseInt(plugin.getBlocksInterface().executeScriptFunction("readJSON", "[pickaxes][" + player.getName() + "]")));
        }
        if (identifier.equals("blocksmined_axes"))
        {
            return format(Integer.parseInt(plugin.getBlocksInterface().executeScriptFunction("readJSON", "[axes][" + player.getName() + "]")));
        }
        if (identifier.equals("playtime"))
        {
            int s = Integer.parseInt(plugin.getPlaytimeInterface().executeScriptFunction("readJSON", "[playtime][" + player.getName() + "]"));
            int days = (s / 60) / 24;
            return days + " days";
        }
        if (identifier.equals("blocksmined_shovels"))
        {
            return format(Integer.parseInt(plugin.getBlocksInterface().executeScriptFunction("readJSON", "[shovels][" + player.getName() + "]")));
        }
        return identifier;
    }
}
