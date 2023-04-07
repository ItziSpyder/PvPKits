package io.github.itzispyder.pvpkits.commands.commands;

import io.github.itzispyder.pvpkits.PvPKits;
import io.github.itzispyder.pvpkits.commands.CmdExHandler;
import io.github.itzispyder.pvpkits.data.builder.TabComplBuilder;
import io.github.itzispyder.pvpkits.data.kits.KitRoom;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class KitRoomCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.openInventory(KitRoom.getGui());
                return true;
            }

            switch (args[0]) {
                case "edit" -> {
                    p.openInventory(KitRoom.getEditingGui());
                }
                case "test" -> {
                    sender.sendMessage("§6The kit room is §o" + (KitRoom.get().isEmpty() ? "empty" : "full") + ".");
                }
                case "help" -> {
                    sender.sendMessage(PvPKits.prefix + "§6Kit rooms are where members can grab loot infinitely to make their own kits. " +
                            "If the kit room is empty, type §7'/kitroom edit' §6then start adding new items!");
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
        return new TabComplBuilder(sender,command,alias,args)
                .add(1,new String[]{
                        "edit",
                        "test",
                        "help"
                })
                .build();
    }
}
