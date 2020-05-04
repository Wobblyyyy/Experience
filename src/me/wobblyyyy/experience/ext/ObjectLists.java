package me.wobblyyyy.experience.ext;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObjectLists
{
    public List<String> pickaxes = new ArrayList<>()
    {{
        add("DIAMOND_PICKAXE");
        add("GOLD_PICKAXE");
        add("IRON_PICKAXE");
        add("STONE_PICKAXE");
        add("WOOD_PICKAXE");
    }};
    public List<String> axes = new ArrayList<>()
    {{
        add("DIAMOND_AXE");
        add("GOLD_AXE");
        add("IRON_AXE");
        add("STONE_AXE");
        add("WOOD_AXE");
    }};
    public List<String> shovels = new ArrayList<>()
    {{
        add("DIAMOND_SHOVEL");
        add("GOLD_SHOVEL");
        add("IRON_SHOVEL");
        add("STONE_SHOVEL");
        add("WOOD_SHOVEL");
    }};
    public HashMap<String, String> defaultToolNames = new HashMap<>()
    {{
        put("DIAMOND_PICKAXE", "Diamond Pickaxe");
        put("GOLD_PICKAXE", "Golden Pickaxe");
        put("IRON_PICKAXE", "Iron Pickaxe");
        put("STONE_PICKAXE", "Stone Pickaxe");
        put("WOODEN_PICKAXE", "Wooden Pickaxe");
        put("DIAMOND_AXE", "Diamond Axe");
        put("GOLD_AXE", "Golden Axe");
        put("IRON_AXE", "Iron Axe");
        put("STONE_AXE", "Stone Axe");
        put("WOODEN_AXE", "Wooden Axe");
        put("DIAMOND_SHOVEL", "Diamond Shovel");
        put("GOLD_SHOVEL", "Golden Shovel");
        put("IRON_SHOVEL", "Iron Shovel");
        put("STONE_SHOVEL", "Stone Shovel");
        put("WOODEN_SHOVEL", "Wooden Shovel");
    }};
    public HashMap<String, Integer> blockExperienceValues = new HashMap<>()
    {{
        put("DEFAULT", 1);
        put("STONE", 1);
        put("COBBLESTONE", 0);
        put("STONE_BRICKS", 16);
        put("COAL_ORE", 5);
        put("IRON_ORE", 12);
        put("GOLD_ORE", 22);
        put("REDSTONE_ORE", 12);
        put("DIAMOND_ORE", 80);
        put("EMERALD_ORE", 130);
        put("OAK_LOG", 2);
        put("DARK_OAK_LOG", 2);
        put("SPRUCE_LOG", 2);
        put("BIRCH_LOG", 2);
        put("OAK_PLANKS", 0);
        put("DARK_OAK_PLANKS", 0);
        put("SPRUCE_PLANKS", 0);
        put("BIRCH_PLANKS", 0);
    }};
    public HashMap<Material, Material> drops = new HashMap<>()
    {{
        put(Material.STONE, Material.COBBLESTONE);
        put(Material.COAL_ORE, Material.COAL);
        put(Material.IRON_ORE, Material.IRON_ORE);
        put(Material.DIAMOND_ORE, Material.DIAMOND);
        put(Material.EMERALD_ORE, Material.EMERALD);
        put(Material.REDSTONE_ORE, Material.REDSTONE);
        put(Material.GOLD_ORE, Material.GOLD_ORE);
        put(Material.COAL_BLOCK, Material.COAL_BLOCK);
        put(Material.IRON_BLOCK, Material.IRON_BLOCK);
        put(Material.DIAMOND_BLOCK, Material.DIAMOND_BLOCK);
        put(Material.EMERALD_BLOCK, Material.EMERALD_BLOCK);
        put(Material.GOLD_BLOCK, Material.GOLD_BLOCK);
        put(Material.NETHERRACK, Material.NETHERRACK);
        put(Material.SANDSTONE, Material.SANDSTONE);
    }};
    public HashMap<Material, Material> smeltedDrops = new HashMap<>()
    {{
        put(Material.STONE, Material.STONE);
        put(Material.IRON_ORE, Material.IRON_INGOT);
        put(Material.GOLD_ORE, Material.GOLD_INGOT);
        put(Material.NETHERRACK, Material.NETHER_BRICK);
    }};
    public HashMap<Material, Double> fortuneMultiplier = new HashMap<>()
    {{
        put(Material.COAL_ORE, 1.0);
        put(Material.IRON_ORE, 1.0);
        put(Material.DIAMOND_ORE, 0.5);
        put(Material.NETHERRACK, 1.0);
        put(Material.SANDSTONE, 1.0);
    }};
}
