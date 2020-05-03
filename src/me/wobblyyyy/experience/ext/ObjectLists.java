package me.wobblyyyy.experience.ext;

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
        add("DIAMOND_SPADE");
        add("GOLD_SPADE");
        add("IRON_SPADE");
        add("STONE_SPADE");
        add("WOOD_SPADE");
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
        put("DIAMOND_SPADE", "Diamond Shovel");
        put("GOLD_SPADE", "Golden Shovel");
        put("IRON_SPADE", "Iron Shovel");
        put("STONE_SPADE", "Stone Shovel");
        put("WOODEN_SPADE", "Wooden Shovel");
    }};
}
