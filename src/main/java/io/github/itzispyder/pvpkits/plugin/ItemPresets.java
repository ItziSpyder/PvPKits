package io.github.itzispyder.pvpkits.plugin;

import io.github.itzispyder.pvpkits.data.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class ItemPresets {

    public static void init() {

    }

    public static final ItemStack AIR = new ItemStack(Material.AIR);

    public static final ItemStack BLANK = new ItemBuilder()
            .material(Material.GRAY_STAINED_GLASS_PANE)
            .name(" ")
            .build();

    public static final ItemStack KIT_ROOM = new ItemBuilder()
            .material(Material.NETHER_STAR)
            .name("§b§lKit Room")
            .lore("§7- Grab items for your kits!")
            .build();

    public static final ItemStack KIT_MENU = new ItemBuilder()
            .material(Material.CHEST)
            .name("§6§lKit Menu")
            .lore("§7- Go to your kits!")
            .build();

}
