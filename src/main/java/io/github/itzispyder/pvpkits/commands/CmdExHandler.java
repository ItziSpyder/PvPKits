package io.github.itzispyder.pvpkits.commands;

import org.bukkit.command.Command;

public record CmdExHandler(Exception ex, Command cmd) {

    public String getHelp() {
        String help = "§cError: §7";
        if (ex instanceof IndexOutOfBoundsException) help += "Unknown or incomplete command.";
        else if (ex instanceof NullPointerException) help += "Command contains a null value.";
        else help += ex.getMessage();
        return help + "\n§cCaused by: §7" + ex.getClass().getSimpleName() + "\n" + "§cUsage: §7" + cmd.getUsage();
    }
}
