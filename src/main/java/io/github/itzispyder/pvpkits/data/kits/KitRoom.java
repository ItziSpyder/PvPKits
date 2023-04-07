package io.github.itzispyder.pvpkits.data.kits;

import io.github.itzispyder.pvpkits.PvPKits;
import io.github.itzispyder.pvpkits.data.builder.ItemBuilder;
import io.github.itzispyder.pvpkits.plugin.ItemPresets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class KitRoom {

    private static final UUID ID = UUID.fromString("cdd88396-d4f3-11ed-a1a0-325096b39f47");
    private static KitProfile profile = KitProfile.load(ID);

    public static void reload() {
        profile = KitProfile.load(ID);
    }

    public static Kit get() {
        ItemStack a = ItemPresets.AIR;
        ItemStack x = ItemPresets.BLANK;
        ItemStack g = new ItemBuilder(x.clone())
                .material(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .build();
        return profile != null && !profile.getKits()[1].isEmpty() ? profile.getKits()[1] : new Kit(new ItemStack[]{
                g,g,g,g,g,g,g,g,g,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                g,g,g,g,g,g,g,g,g
        });
    }

    public static void clear() {
        save(new ItemStack[]{});
    }

    public static void save(ItemStack[] contents) {
        profile.getKits()[1] = new Kit(contents);
        profile.save();
        reload();
    }

    public static Inventory getEditingGui() {
        final Inventory inv = Bukkit.createInventory(null,54, PvPKits.prefix + "§bEditing Kit Room");
        inv.setContents(get().getContents());
        return inv;
    }

    public static Inventory getGui() {
        final Inventory inv = Bukkit.createInventory(null,54, PvPKits.prefix + "§bKit Room");
        ItemStack kr = ItemPresets.KIT_ROOM;
        ItemStack km = ItemPresets.KIT_MENU;
        inv.setContents(get().getContents());
        inv.setItem(inv.getSize() - 1,km);
        inv.setItem(inv.getSize() - 2,kr);
        return inv;
    }
}
