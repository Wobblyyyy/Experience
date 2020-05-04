package me.wobblyyyy.experience;

import me.wobblyyyy.experience.ext.Interfacing;
import me.wobblyyyy.experience.ext.JavascriptInterface;
import me.wobblyyyy.experience.ext.ObjectLists;
import me.wobblyyyy.experience.ext.PlaceholderAPIInterface;
import me.wobblyyyy.experience.extensions.AutoSmelt;
import me.wobblyyyy.experience.listeners.BlockBreak;
import me.wobblyyyy.experience.listeners.Command;
import me.wobblyyyy.experience.listeners.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Objects;

public class Experience extends JavaPlugin
{
    Configuration configuration = new Configuration();
    ObjectLists objectLists = new ObjectLists();
    Interfacing interfacing = new Interfacing(getDataFolder() + File.separator);
    JavascriptInterface engine = new JavascriptInterface();
    JavascriptInterface experience = new JavascriptInterface();
    JavascriptInterface blocksInterface = new JavascriptInterface();
    JavascriptInterface loginCounterInterface = new JavascriptInterface();
    JavascriptInterface playtimeInterface = new JavascriptInterface();

    AutoSmelt autoSmelt = new AutoSmelt(new NamespacedKey("enchantments", "_"));

    @Override
    public void onEnable ()
    {
        Objects.requireNonNull(this.getCommand("exp")).setExecutor(new Command(this));
//        this.getCommand("basic").setExecutor(new MyPluginCommandExecutor(this));

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
        {
            new PlaceholderAPIInterface(this).register();
        }

        loadEnchantments();

        // Empty config is saved to ensure everything is working alright.
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
        try
        {
            Field byKeyField = Enchantment.class.getDeclaredField("byKey");
            Field byNameField = Enchantment.class.getDeclaredField("byName");

            byKeyField.setAccessible(true);
            byNameField.setAccessible(true);

            HashMap<Integer, Enchantment> byKey = (HashMap<Integer, Enchantment>) byKeyField.get(null);
            HashMap<Integer, Enchantment> byName = (HashMap<Integer, Enchantment>) byNameField.get(null);

            byKey.remove(autoSmelt.getKey());
            byName.remove(autoSmelt.getName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        writeAllJSON();
    }

    public void loadAllScripts ()
    {
        engine.loadScripts("JSONObjects/Engine.js", "JSONInterface.js", "Engine.js");
        experience.loadScripts("JSONObjects/Experience.js", "JSONInterface.js");
        blocksInterface.loadScripts("JSONObjects/BlockBreak.js", "JSONInterface.js");
        loginCounterInterface.loadScripts("JSONObjects/PlayerJoin.js", "JSONInterface.js");
        playtimeInterface.loadScripts("JSONObjects/Playtime.js", "JSONInterface.js");
    }

    public void setAllPaths ()
    {
        engine.setPath("engine.json");
        experience.setPath("experience.json");
        blocksInterface.setPath("blocks.json");
        loginCounterInterface.setPath("logins.json");
        playtimeInterface.setPath("playtime.json");
    }

    public void readAllJSON ()
    {
        readJSONFromFile(engine);
        readJSONFromFile(experience);
        readJSONFromFile(blocksInterface);
        readJSONFromFile(loginCounterInterface);
        readJSONFromFile(playtimeInterface);
    }

    public void writeAllJSON ()
    {
        writeJSONToFile(engine);
        writeJSONToFile(experience);
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

    public JavascriptInterface getExperienceInterface ()
    {
        return experience;
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

    public AutoSmelt getAutoSmelt ()
    {
        return autoSmelt;
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

    private void loadEnchantments ()
    {
        try
        {
            try
            {
                Field field = Enchantment.class.getDeclaredField("acceptingNew");
                field.setAccessible(true);
                field.set(null, true);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            try
            {
                Enchantment.registerEnchantment(autoSmelt);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
