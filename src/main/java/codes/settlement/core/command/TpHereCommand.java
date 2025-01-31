package codes.settlement.core.command;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TpHereCommand extends AbstractCommand {

    public TpHereCommand() {
        super("tphere", "Teleport a player to you", Permission.STAFF, "tpohere");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player!");
            return;
        }

        Player playerSender = (Player) sender;

        if (args.length == 0) {
            playerSender.sendMessage(ChatColor.RED + "Usage: /tphere <player>");
            return;
        }

        Player targetPlayer = sender.getServer().getPlayer(args[0]);
        if (targetPlayer == null) {
            playerSender.sendMessage(ChatColor.RED + "Player not found.");
            return;
        }

        targetPlayer.teleport(playerSender.getLocation());
        playerSender.sendMessage(Utils.color("&aTeleported &b" + targetPlayer.getName() + " &ato you."));
        targetPlayer.sendMessage(Utils.color("&aYou have been teleported to &b" + playerSender.getName() + "&a."));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    suggestions.add(onlinePlayer.getName());
                }
            }
        }

        return suggestions;
    }
}
