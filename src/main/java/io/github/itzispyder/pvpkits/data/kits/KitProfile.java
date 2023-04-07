package io.github.itzispyder.pvpkits.data.kits;

import io.github.itzispyder.pvpkits.PvPKits;
import io.github.itzispyder.pvpkits.data.builder.ItemBuilder;
import io.github.itzispyder.pvpkits.plugin.ItemPresets;
import io.github.itzispyder.pvpkits.util.FileValidationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class KitProfile implements Serializable, ConfigurationSerializable {

    public static final int MAX_KITS = 9;
    public static final int MIN_KITS = 1;
    private final UUID owner;
    private Kit[] kits;

    public KitProfile(UUID owner) {
        this.owner = owner;
        this.kits = new Kit[9];

        for (int i = 0; i < kits.length; i++) {
            if (kits[i] == null) kits[i] = new Kit(new ItemStack[]{});
        }

        this.save();
    }

    public OfflinePlayer owner() {
        return Bukkit.getOfflinePlayer(owner);
    }

    public void ifOwnerOnlineRun(Consumer<Player> consumer) {
        if (!owner().isOnline()) return;
        Player p = owner().getPlayer();
        consumer.accept(p);
    }

    /**
     * Loads a kit
     * @param kitNumber should be a number between 1 and 9
     */
    public int loadKit(int kitNumber) {
        if (!owner().isOnline()) return -1;
        Player p = owner().getPlayer();
        if (p == null) return -1;
        kitNumber = Math.min(9,Math.max(1,kitNumber));
        Kit kit = this.kits[kitNumber - 1];
        if (kit.isEmpty()) return -1;
        p.getInventory().setContents(kit.getContents());
        return kitNumber;
    }

    /**
     * Saves a kit
     * @param kitNumber should be a number between 1 and 9
     */
    public void saveKit(int kitNumber, Kit kit) {
        if (!owner().isOnline()) return;
        Player p = owner().getPlayer();
        if (p == null) return;
        kitNumber = Math.min(9,Math.max(1,kitNumber));
        this.kits[kitNumber - 1] = kit;
    }

    public Kit[] getKits() {
        return kits;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setKits(Kit[] kits) {
        this.kits = kits;
    }

    @Override
    public Map<String, Object> serialize() {
        return new HashMap<>();
    }

    public void save() {
        File file = new File("plugins/PvPKits/kits/" + owner + ".dat");
        if (!FileValidationUtils.validate(file)) return;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            GZIPOutputStream gos = new GZIPOutputStream(bos);
            ObjectOutputStream oos = new ObjectOutputStream(gos);
            BukkitObjectOutputStream boos = new BukkitObjectOutputStream(oos);

            boos.writeObject(this);
            boos.close();
        }
        catch (Exception ex) {
            Bukkit.getLogger().warning("Failed to save kit for '" + owner + "' in file '" + file.getPath() + "' !");
        }
    }

    public static KitProfile load(UUID owner) {
        try {
            File file = new File("plugins/PvPKits/kits/" + owner + ".dat");
            if (!FileValidationUtils.validate(file)) return null;

            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            GZIPInputStream gis = new GZIPInputStream(bis);
            ObjectInputStream ois = new ObjectInputStream(gis);
            BukkitObjectInputStream bois = new BukkitObjectInputStream(ois);

            KitProfile profile = (KitProfile) bois.readObject();
            bois.close();
            return profile;
        }
        catch (Exception ex) {
            return new KitProfile(owner);
        }
    }

    public Inventory getGui() {
        Inventory inv = Bukkit.createInventory(null,54, PvPKits.prefix + "§6Your Kits");
        ItemStack x = ItemPresets.BLANK;
        ItemStack g = new ItemBuilder(x.clone())
                .material(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .build();
        ItemStack kr = ItemPresets.KIT_ROOM;
        ItemStack cl = new ItemBuilder()
                .material(Material.BARRIER)
                .name("§c§lClear Inventory")
                .lore("§7- Clear your inventory to start over")
                .build();

        inv.setContents(new ItemStack[]{
                g,g,g,g,g,g,g,g,g,
                x,x,x,x,x,x,x,x,x,
                x,x,x,x,x,x,x,x,x,
                x,x,x,x,x,x,x,x,x,
                x,x,x,kr,x,cl,x,x,x,
                g,g,g,g,g,g,g,g,g,
        });

        for (int i = 0; i < 9; i++) {
            Kit kit = this.kits[i];
            ItemStack c = new ItemBuilder()
                    .material(kit.isEmpty() ? Material.CHEST : Material.ENDER_CHEST)
                    .name(kit.isEmpty() ? "§c§lKit §7§l" + (i + 1) : "§a§lKit §7§l" + (i + 1))
                    .lore("§7- LEFT-CLICK to load")
                    .lore("")
                    .lore("§7- '/kit save " + (i + 1) + "' to save")
                    .lore("§7   contents of this kit from your")
                    .lore("§7   inventory")
                    .build();
            inv.setItem(i + 9,c);
        }

        return inv;
    }
}
