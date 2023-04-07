package io.github.itzispyder.pvpkits;

import io.github.itzispyder.pvpkits.commands.commands.KitCommand;
import io.github.itzispyder.pvpkits.commands.commands.KitRoomCommand;
import io.github.itzispyder.pvpkits.events.InventoryActionListener;
import io.github.itzispyder.pvpkits.plugin.InventoryPresets;
import io.github.itzispyder.pvpkits.plugin.ItemPresets;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PvPKits extends JavaPlugin {

    public static final PluginManager pm = Bukkit.getPluginManager();
    public static final String prefix = "§7[§6PvPKits§7] ";
    public static PvPKits instance;

    @Override
    public void onEnable() {
        instance = this;
        this.init();
        ItemPresets.init();
        InventoryPresets.init();
    }

    @Override
    public void onDisable() {

    }

    public void init() {
        pm.registerEvents(new InventoryActionListener(),this);

        getCommand("kit").setExecutor(new KitCommand());
        getCommand("kit").setTabCompleter(new KitCommand());
        getCommand("kitroom").setExecutor(new KitRoomCommand());
        getCommand("kitroom").setTabCompleter(new KitRoomCommand());
    }
}
