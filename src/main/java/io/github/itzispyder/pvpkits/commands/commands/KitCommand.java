package io.github.itzispyder.pvpkits.commands.commands;

import io.github.itzispyder.pvpkits.PvPKits;
import io.github.itzispyder.pvpkits.commands.CmdExHandler;
import io.github.itzispyder.pvpkits.data.builder.TabComplBuilder;
import io.github.itzispyder.pvpkits.data.kits.Kit;
import io.github.itzispyder.pvpkits.data.kits.KitProfile;
import io.github.itzispyder.pvpkits.util.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KitCommand implements TabExecutor {

    private static HashMap<String,Long> cooldown = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (!label.equalsIgnoreCase("kit") && !label.equalsIgnoreCase("k") && !label.equalsIgnoreCase("kits")) {
                int number = Integer.parseInt(label.replaceAll("[^0-9]","").trim());
                Bukkit.dispatchCommand(sender,"kit load " + number);
                return true;
            }

            if (cooldown.containsKey(sender.getName()) && cooldown.get(sender.getName()) > System.currentTimeMillis()) {
                sender.sendMessage("§cThis command is on cooldown!");
                return true;
            }
            cooldown.put(sender.getName(),System.currentTimeMillis() + (1000));
            KitProfile profile = KitProfile.load(((Player) sender).getUniqueId());

            if (args.length == 0) {
                profile.ifOwnerOnlineRun(p -> {
                    p.openInventory(profile.getGui());
                });
                return true;
            }

            switch (args[0]) {
                case "load" -> {
                    int kitNumber = Integer.parseInt(args[1]);
                    int loaded = profile.loadKit(kitNumber);
                    profile.ifOwnerOnlineRun(p -> {
                        if (loaded == -1) {
                            p.sendMessage("§cCannot load empty kit!");
                            return;
                        }
                        p.sendMessage("§6Loaded kit " + loaded + ".");
                        Bukkit.broadcastMessage("§7" + p.getName() + " has loaded a kit.");
                    });
                }
                case "save" -> {
                    int kitNumber = Integer.parseInt(args[1]);
                    profile.ifOwnerOnlineRun(p -> {
                        Kit kit = new Kit(p);
                        profile.saveKit(kitNumber,kit);
                        p.sendMessage("§6Saved kit " + kitNumber + ".");
                    });
                    profile.save();
                }
                case "clear" -> {
                    int kitNumber = Integer.parseInt(args[1]);
                    profile.ifOwnerOnlineRun(p -> {
                        Kit kit = new Kit(new ItemStack[]{});
                        profile.saveKit(kitNumber,kit);
                        p.sendMessage("§6Cleared kit " + kitNumber + ".");
                    });
                    profile.save();
                }
                case "help" -> {
                    sender.sendMessage(PvPKits.prefix + "§6Do '/kit' §6to open up kit selection menu. Click on the §7nether star §6to open" +
                            " the kit room where you can grab loot to create your kits. Once you're done, do §7'/kit save <number>'" +
                            " §6to save your newly created kit! Do §7'/kit load <number>' §6to load it back!");
                }
            }
        }
        catch (Exception ex) {
            CmdExHandler handler = new CmdExHandler(ex,command);
            sender.sendMessage(handler.getHelp());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!alias.equalsIgnoreCase("kit") && !alias.equalsIgnoreCase("k") && !alias.equalsIgnoreCase("kits")) return new ArrayList<>();
        return new TabComplBuilder(sender,command,alias,args)
                .add(1,new String[]{
                        "save",
                        "load",
                        "clear",
                        "help"
                })
                .add(2,new String[]{
                        "1",
                        "2",
                        "3",
                        "4",
                        "5",
                        "6",
                        "7",
                        "8",
                        "9"
                },arg -> ArrayUtils.matchAllIgnoreCase(args,"save","load","clear"))
                .build();
    }
}
