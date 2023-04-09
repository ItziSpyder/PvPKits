package io.github.itzispyder.pvpkits.events;

import io.github.itzispyder.pvpkits.PvPKits;
import io.github.itzispyder.pvpkits.data.kits.KitProfile;
import io.github.itzispyder.pvpkits.data.kits.KitRoom;
import io.github.itzispyder.pvpkits.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryActionListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        final Player p = (Player) e.getWhoClicked();
        final Inventory inv = e.getClickedInventory();
        final String title = e.getView().getTitle();
        final int slot = e.getSlot();

        try {
            final ItemStack item = e.getCurrentItem();
            final String d = ItemUtils.getDisplay(item);
            if (!title.contains(PvPKits.prefix)) return;
            if (inv.getType() == InventoryType.PLAYER) return;
            e.setCancelled(true);
            if (ItemUtils.matchDisplay(item," ")) return;

            if (title.contains("§6Your Kits")) {
                if (slot >= 9 && slot <= 17) Bukkit.dispatchCommand(p,"kit load " + (slot - 8));
                else if (d.contains("§c§lClear Inventory")) p.getInventory().clear();
                else if (d.contains("§b§lKit Room")) p.openInventory(KitRoom.getGui());
            }
            else if (title.contains("§bKit Room") || title.contains("§bEditing Kit Room")) {
                if (d.contains("§b§lKit Room")) p.openInventory(KitRoom.getGui());
                else if (d.contains("§6§lKit Menu")) {
                    KitProfile profile = KitProfile.load(p.getUniqueId());
                    p.openInventory(profile.getGui());
                }
                else e.setCancelled(false);
            }
        }
        catch (Exception ignore) {}
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        final Player p = (Player) e.getPlayer();
        final Inventory inv = e.getInventory();
        final String title = e.getView().getTitle();

        try {
            if (title.contains("§bEditing Kit Room")) {
                KitRoom.save(inv.getContents());
                p.sendMessage("§6Saved the kit room!");
                Bukkit.broadcastMessage("§7Kit room has been updated!");
            }
        }
        catch (Exception ignore) {}
    }
}
