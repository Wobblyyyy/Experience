package me.wobblyyyy.experience.handlers;

import me.wobblyyyy.experience.Experience;
import me.wobblyyyy.experience.ext.ObjectLists;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
    ObjectLists list;

    public BlockBreakHandler (Experience plugin)
    {
        this.plugin = plugin;
        list = plugin.getObjectLists();
    }

    public boolean isTool (Material item)
    {
        String toFind = item.toString();
        return list.pickaxes.contains(toFind) || list.axes.contains(toFind) || list.shovels.contains(toFind);
    }

    public int getEnchant (ItemStack item, Enchantment enchant)
    {
        return Objects.requireNonNull(item.getItemMeta()).hasEnchant(enchant) ? item.getEnchantmentLevel(enchant) : 0;
    }

    public ItemStack getDrops (BlockBreakEvent event)
    {
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        ItemStack drop = null;
        Block block = event.getBlock();
        if (isTool(item.getType()))
        {
            int fortune = getEnchant(item, Enchantment.LOOT_BONUS_BLOCKS);
            int smelting = getEnchant(item, plugin.getAutoSmelt());
            if (smelting != 0 && list.smeltedDrops.containsKey(block.getType()))
            {
                drop = new ItemStack(list.smeltedDrops.get(block.getType()));
            }
            else
            {
                drop = list.drops.containsKey(block.getType()) ? new ItemStack(list.drops.get(block.getType())) : new ItemStack(block.getType());
            }
            if (fortune != 0 && list.fortuneMultiplier.containsKey(block.getType()))
            {
                drop.setAmount((int) Math.ceil(ThreadLocalRandom.current().nextInt(1, fortune + 1) * list.fortuneMultiplier.get(block.getType())));
            }

            return drop;
        }
        for (ItemStack i : event.getBlock().getDrops())
        {
            return i;
        }
        return null;
    }

    public ItemMeta updateTool (ItemStack item)
    {
        net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsItem.getOrCreateTag();
        tag.setLong("counter", tag.getLong("counter") + 1);
        item = CraftItemStack.asBukkitCopy(nmsItem);

        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        String name = meta.hasDisplayName() ? meta.getDisplayName() : plugin.getObjectLists().defaultToolNames.get(item.getType().name());
        meta.setDisplayName(plugin.getEngine().executeScriptFunction("newName", plugin.getConfiguration().toolCounterFormat, plugin.getConfiguration().counterFormat, name, tag.getLong("counter") - 1));
        return meta;
    }

    public void handleBlockBreak (BlockBreakEvent event)
    {
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (isTool(item.getType()))
        {
            item.setItemMeta(updateTool(item));
            ItemStack drops = getDrops(event);
            event.getPlayer().getInventory().addItem(drops);
            event.setDropItems(false);
        }
    }
}
