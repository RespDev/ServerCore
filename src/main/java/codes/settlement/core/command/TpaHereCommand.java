package codes.settlement.core.command;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.TpaUtil;
import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class TpaHereCommand extends AbstractCommand {

    public TpaHereCommand() {
        super("tpahere", "Request a player to teleport to you", Permission.SETTLER);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player!");
            return;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(Utils.color("&cUsage: /tpahere <player>"));
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            player.sendMessage(Utils.color("&cThat player currently is not online!"));
            return;
        }

        if (targetPlayer.equals(player)) {
            player.sendMessage(Utils.color("&cYou cannot send a teleport request to yourself!"));
            return;
        }

        TpaUtil.addRequest(player, targetPlayer);
        player.sendMessage(Utils.color("&aRequest will expire in 15 seconds."));
        player.sendMessage(Utils.color("&aYou have sent a teleport request for &b" + targetPlayer.getName() + " &ato teleport to you."));
        targetPlayer.sendMessage(Utils.color("&aRequest will expire in 15 seconds."));
        targetPlayer.sendMessage(Utils.color("&b" + player.getName() + " &ahas requested you to teleport to them. Use /tpaccept or /tpdeny."));
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
