package me.wobblyyyy.experience.handlers;

import me.wobblyyyy.experience.Experience;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class BlockBreakHandler
{
    Experience plugin;

    public BlockBreakHandler (Experience plugin)
    {
        this.plugin = plugin;
    }

    public void handleBlockBreak (BlockBreakEvent event)
    {
        Material blockType = event.getBlock().getType();
        if (plugin.getObjectLists().drops.containsKey(blockType))
        {
            ItemStack item = null;
            boolean notSmeltable = true;
            if (plugin.getObjectLists().smeltedDrops.containsKey(blockType))
            {
                if (Objects.requireNonNull(event.getPlayer().getInventory().getItemInMainHand().getItemMeta()).hasEnchant(plugin.getAutoSmelt()))
                {
                    Material mat = plugin.getObjectLists().smeltedDrops.get(blockType);
                    item = new ItemStack(mat);
                    notSmeltable = false;
                }
            }
            if (plugin.getObjectLists().drops.containsKey(blockType) && notSmeltable)
            {
                Material mat = plugin.getObjectLists().drops.get(blockType);
                item = new ItemStack(mat);
            }
            if (plugin.getObjectLists().fortunes.containsKey(blockType) || plugin.getObjectLists().blockFortunes.containsKey(blockType))
            {
                int fortuneLevel = Objects.requireNonNull(event.getPlayer().getInventory().getItemInMainHand().getItemMeta()).hasEnchant(Enchantment.LOOT_BONUS_BLOCKS) ? event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + 1 : 1;
                double fortuneMultiplier = plugin.getObjectLists().fortuneMultiplier.containsKey(blockType) ? plugin.getObjectLists().fortuneMultiplier.get(blockType) : 1;
                int random = ThreadLocalRandom.current().nextInt(1, fortuneLevel + 1);
                int toGive = fortuneLevel != 0 ? (int) Math.ceil(random * fortuneMultiplier) : 1;
                assert item != null;
                item.setAmount(toGive);
            }
            event.getPlayer().getInventory().addItem(item);
        }
        else
        {
            for (ItemStack i : event.getBlock().getDrops())
            {
                event.getPlayer().getInventory().addItem(i);
            }
        }
        event.setDropItems(false);

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
            assert meta != null;
            String name = meta.hasDisplayName() ? meta.getDisplayName() : plugin.getObjectLists().defaultToolNames.get(item.getType().name());
            meta.setDisplayName(plugin.getEngine().executeScriptFunction("newName", plugin.getConfiguration().toolCounterFormat, plugin.getConfiguration().counterFormat, name, counter));
            item.setItemMeta(meta);

            event.getPlayer().getInventory().setItemInMainHand(item);
        }
    }
}
