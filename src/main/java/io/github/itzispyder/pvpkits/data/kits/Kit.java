package io.github.itzispyder.pvpkits.data.kits;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Kit implements Serializable, ConfigurationSerializable {

    private ItemStack[] contents;

    public Kit(Player p) {
        this.contents = p.getInventory().getContents();
    }

    public Kit(ItemStack[] contents) {
        this.contents = contents;
    }

    public ItemStack[] getContents() {
        return contents;
    }

    public boolean isEmpty() {
        return Arrays.stream(contents).noneMatch(Objects::nonNull);
    }

    public void setContents(ItemStack[] contents) {
        this.contents = contents;
    }

    @Override
    public Map<String, Object> serialize() {
        return new HashMap<>();
    }
}
