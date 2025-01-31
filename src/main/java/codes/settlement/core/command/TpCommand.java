package codes.settlement.core.command;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TpCommand extends AbstractCommand {

    public TpCommand() {
        super("tp", "Teleport to a player or location", Permission.STAFF);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            Player targetPlayer = sender.getServer().getPlayer(args[0]);
            if (targetPlayer == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return;
            }

            if (sender instanceof Player) {
                Player playerSender = (Player) sender;
                playerSender.teleport(targetPlayer.getLocation());
                sender.sendMessage(Utils.color("&aTeleported you to &b" + targetPlayer.getName() + "&a."));
            } else {
                sender.sendMessage(ChatColor.RED + "The console cannot teleport a player to another player.");
            }
        } else if (args.length == 2) {
            Player sourcePlayer = sender.getServer().getPlayer(args[0]);
            Player targetPlayer = sender.getServer().getPlayer(args[1]);

            if (sourcePlayer == null || targetPlayer == null) {
                sender.sendMessage(ChatColor.RED + "One or both players not found.");
                return;
            }

            sourcePlayer.teleport(targetPlayer.getLocation());
            sender.sendMessage(Utils.color("&aTeleported &b" + sourcePlayer.getName() + " &ato &b" + targetPlayer.getName() + "&a."));
        } else if (args.length >= 4) {
            Player targetPlayer = sender.getServer().getPlayer(args[0]);
            if (targetPlayer == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return;
            }

            try {
                double x = Double.parseDouble(args[1]);
                double y = Double.parseDouble(args[2]);
                double z = Double.parseDouble(args[3]);
                float pitch = args.length > 4 ? Float.parseFloat(args[4]) : 0;
                float yaw = args.length > 5 ? Float.parseFloat(args[5]) : 0;

                targetPlayer.teleport(new Location(targetPlayer.getWorld(), x, y, z, yaw, pitch));
                sender.sendMessage(Utils.color("&aTeleported &b" + targetPlayer.getName() + "&a to the location."));
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid number format for coordinates or angles.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /tp <player> or /tp <player> <player> or /tp <player> <x> <y> <z> <pitch> <yaw>");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            for (Player player : sender.getServer().getOnlinePlayers()) {
                suggestions.add(player.getName());
            }
        } else if (args.length == 2) {
            for (Player player : sender.getServer().getOnlinePlayers()) {
                suggestions.add(player.getName());
            }
        } else if (args.length >= 2) {
            if (args.length == 2 || args.length == 3 || args.length == 4) {
                suggestions.add("<x>");
            }
            if (args.length == 3 || args.length == 4) {
                suggestions.add("<y>");
            }
            if (args.length == 4 || args.length == 5) {
                suggestions.add("<z>");
            }
            if (args.length == 5 || args.length == 6) {
                suggestions.add("<pitch>");
            }
            if (args.length == 6 || args.length == 7) {
                suggestions.add("<yaw>");
            }
        }

        return suggestions;
    }
}
