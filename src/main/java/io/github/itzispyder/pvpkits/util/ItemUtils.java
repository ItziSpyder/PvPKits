package io.github.itzispyder.pvpkits.util;

import org.apache.commons.lang.StringUtils;
import org.bukkit.inventory.ItemStack;

public abstract class ItemUtils {

    public static boolean matchDisplay(ItemStack a, ItemStack b) {
        return getDisplay(a).equals(getDisplay(b));
    }

    public static boolean matchDisplay(ItemStack a, String b) {
        return getDisplay(a).equals(b);
    }

    public static boolean matchDisplay(String a, ItemStack b) {
        return a.equals(getDisplay(b));
    }

    public static String getDisplay(ItemStack i) {
        return i.hasItemMeta() && i.getItemMeta().hasDisplayName() ? i.getItemMeta().getDisplayName() : StringUtils.capitalize(i.getType().name().toLowerCase());
    }
}
