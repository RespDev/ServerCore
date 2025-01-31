package codes.settlement.core.command.disabled;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public final class OPCommand extends AbstractCommand {

    public OPCommand() {
        super("op", "Disabled", Permission.SETTLER);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.RED + "Disabled!");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}