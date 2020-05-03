package me.wobblyyyy.experience;

import me.wobblyyyy.experience.ext.Interfacing;
import me.wobblyyyy.experience.ext.JavascriptInterface;
import me.wobblyyyy.experience.ext.ObjectLists;
import me.wobblyyyy.experience.ext.PlaceholderAPIInterface;
import me.wobblyyyy.experience.listeners.BlockBreak;
import me.wobblyyyy.experience.listeners.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public class Experience extends JavaPlugin
{
    Configuration configuration = new Configuration();
    ObjectLists objectLists = new ObjectLists();
    Interfacing interfacing = new Interfacing(getDataFolder() + File.separator);
    JavascriptInterface engine = new JavascriptInterface();
    JavascriptInterface blocksInterface = new JavascriptInterface();
    JavascriptInterface loginCounterInterface = new JavascriptInterface();
    JavascriptInterface playtimeInterface = new JavascriptInterface();

    @Override
    public void onEnable ()
    {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
        {
            new PlaceholderAPIInterface(this).register();
        }

        saveConfig();

        loadAllScripts();
        setAllPaths();
        readAllJSON();

        attachListeners();
        scheduleAutosave();
        schedulePlaytimeCounter();
    }

    @Override
    public void onDisable ()
    {
        writeAllJSON();
    }

    public void loadAllScripts ()
    {
        engine.loadScripts("JSONObjects/Engine.js", "JSONInterface.js", "Engine.js");
        blocksInterface.loadScripts("JSONObjects/BlockBreak.js", "JSONInterface.js");
        loginCounterInterface.loadScripts("JSONObjects/PlayerJoin.js", "JSONInterface.js");
        playtimeInterface.loadScripts("JSONObjects/Playtime.js", "JSONInterface.js");
    }

    public void setAllPaths ()
    {
        engine.setPath("engine.json");
        blocksInterface.setPath("blocks.json");
        loginCounterInterface.setPath("logins.json");
        playtimeInterface.setPath("playtime.json");
    }

    public void readAllJSON ()
    {
        readJSONFromFile(engine);
        readJSONFromFile(blocksInterface);
        readJSONFromFile(loginCounterInterface);
        readJSONFromFile(playtimeInterface);
    }

    public void writeAllJSON ()
    {
        writeJSONToFile(engine);
        writeJSONToFile(blocksInterface);
        writeJSONToFile(loginCounterInterface);
        writeJSONToFile(playtimeInterface);
    }

    public void attachListeners ()
    {
        BlockBreak blockBreakListener = new BlockBreak(this);
        PlayerJoin playerJoinListener = new PlayerJoin(this);

        Bukkit.getPluginManager().registerEvents(blockBreakListener, this);
        Bukkit.getPluginManager().registerEvents(playerJoinListener, this);
    }

    public Configuration getConfiguration ()
    {
        return configuration;
    }

    public ObjectLists getObjectLists ()
    {
        return objectLists;
    }

    public JavascriptInterface getEngine ()
    {
        return engine;
    }

    public JavascriptInterface getBlocksInterface ()
    {
        return blocksInterface;
    }

    public JavascriptInterface getPlayerJoinInterface ()
    {
        return loginCounterInterface;
    }

    public JavascriptInterface getPlaytimeInterface ()
    {
        return playtimeInterface;
    }

    public void writeJSONToFile (JavascriptInterface javascriptInterface)
    {
        String rawJSON = javascriptInterface.executeScriptFunction("getJSONObject");
        try
        {
            interfacing.writeStringToJSON(javascriptInterface.path, rawJSON);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void readJSONFromFile (JavascriptInterface javascriptInterface)
    {
        String rawJSON = interfacing.readStringFromJSON(javascriptInterface.path);
        javascriptInterface.executeScriptFunction("setJSONObject", rawJSON);
    }

    public void scheduleAutosave ()
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Experience")), new Runnable()
        {
            @Override
            public void run ()
            {
                getLogger().info(ChatColor.GREEN + "Experience has auto-saved all JSON files.");
                writeAllJSON();
            }
        }, 1L, (long) 300 * 20);
    }

    public void schedulePlaytimeCounter ()
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Experience")), new Runnable()
        {
            @Override
            public void run ()
            {
                for (Player p : Bukkit.getServer().getOnlinePlayers())
                {
                    playtimeInterface.executeScriptFunction("incrementJSON", "[playtime][" + p.getName() + "]", 3);
                }
            }
        }, 1L, (long) 180 * 20);
    }
}
