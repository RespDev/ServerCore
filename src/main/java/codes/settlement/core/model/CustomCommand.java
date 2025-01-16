package codes.settlement.core.model;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class CustomCommand {
    private final String permission;

    public CustomCommand(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);
}