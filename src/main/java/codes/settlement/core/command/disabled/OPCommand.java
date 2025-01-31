package codes.settlement.core.command.disabled;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class OPCommand extends AbstractCommand {

    public OPCommand() {
        super("op", "Disabled", Permission.SETTLER);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ConsoleCommandSender)) {
            sender.sendMessage(ChatColor.RED + "Disabled!");
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /op <player>");
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);

        if (targetPlayer == null) {
            sender.sendMessage(Utils.color("&cThat player currently is not online!"));
            return;
        }

        targetPlayer.setOp(true);
        sender.sendMessage(Utils.color("&b" + targetPlayer.getName() + " &ahas been granted operator permissions!"));
        targetPlayer.sendMessage(Utils.color("&aYou have been granted operator permissions by &bConsole&a."));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}