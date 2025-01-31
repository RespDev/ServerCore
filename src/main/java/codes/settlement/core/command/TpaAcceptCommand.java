package codes.settlement.core.command;

import codes.settlement.core.Core;
import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.TpaUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class TpaAcceptCommand extends AbstractCommand {

    public TpaAcceptCommand() {
        super("tpaccept", "Accept a pending teleport request", Permission.SETTLER);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player!");
            return;
        }

        Player player = (Player) sender;

        Player requester = TpaUtil.acceptRequest(player);
        if (requester != null) {
            Location targetLocation = player.getLocation();
            Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> {
                requester.teleport(targetLocation);
            }, 1L);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
