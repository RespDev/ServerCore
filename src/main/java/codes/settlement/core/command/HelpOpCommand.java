package codes.settlement.core.command;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class HelpOpCommand extends AbstractCommand {

    public HelpOpCommand() {
        super("helpop", "Send a message to all online cast members.", Permission.STAFF, "ac");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /helpop <message>");
            return;
        }

        String message = String.join(" ", args);

        String senderName = (sender instanceof Player) ? ((Player) sender).getName() :
                (sender instanceof ConsoleCommandSender) ? "Console" : "CommandBlock";

        String formattedMessage = ChatColor.DARK_RED + "[CM CHAT] " + ChatColor.GRAY + senderName + ": " + ChatColor.WHITE + message;

        for (Player onlinePlayer : sender.getServer().getOnlinePlayers()) {
            if (onlinePlayer.hasPermission(Permission.STAFF))
                onlinePlayer.sendMessage(formattedMessage);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
