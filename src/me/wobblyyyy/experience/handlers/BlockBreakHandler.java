package me.wobblyyyy.experience.handlers;

import me.wobblyyyy.experience.Experience;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
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
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        String preItem = item.getType().toString();
        boolean isTool = false;
        if (plugin.getObjectLists().pickaxes.contains(preItem))
        {
            plugin.getBlocksInterface().executeScriptFunction("incrementJSON", "[pickaxes][" + event.getPlayer().getName() + "]", 1);
            isTool = true;
        }
        else if (plugin.getObjectLists().axes.contains(preItem))
        {
            plugin.getBlocksInterface().executeScriptFunction("incrementJSON", "[axes][" + event.getPlayer().getName() + "]", 1);
            isTool = true;
        }
        else if (plugin.getObjectLists().shovels.contains(preItem))
        {
            plugin.getBlocksInterface().executeScriptFunction("incrementJSON", "[shovels][" + event.getPlayer().getName() + "]", 1);
            isTool = true;
        }

        if (isTool)
        {
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tag = nmsItem.getOrCreateTag();
            long counter = tag.getLong("counter");
            tag.setLong("counter", counter + 1);
            item = CraftItemStack.asBukkitCopy(nmsItem);

            ItemMeta meta = item.getItemMeta();
            String name = meta.hasDisplayName() ? meta.getDisplayName() : plugin.getObjectLists().defaultToolNames.get(item.getType().name());
            meta.setDisplayName(plugin.getEngine().executeScriptFunction("newName", plugin.getConfiguration().toolCounterFormat, plugin.getConfiguration().counterFormat, name, counter));
            item.setItemMeta(meta);

            event.getPlayer().getInventory().setItemInMainHand(item);
        }
    }
}
