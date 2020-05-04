package me.wobblyyyy.experience.extensions;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class AutoSmelt extends Enchantment implements Listener
{
    public AutoSmelt (NamespacedKey key)
    {
        super(key);
    }

    @Override
    public String getName ()
    {
        return "Block Fortune";
    }

    @Override
    public int getMaxLevel ()
    {
        return 1;
    }

    @Override
    public int getStartLevel ()
    {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget ()
    {
        return null;
    }

    @Override
    public boolean isTreasure ()
    {
        return false;
    }

    @Override
    public boolean isCursed ()
    {
        return false;
    }

    @Override
    public boolean conflictsWith (Enchantment enchantment)
    {
        return false;
    }

    @Override
    public boolean canEnchantItem (ItemStack itemStack)
    {
        return false;
    }
}
