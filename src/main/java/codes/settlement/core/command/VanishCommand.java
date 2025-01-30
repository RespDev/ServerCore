package codes.settlement.core.command;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.util.PlayerUtil;
import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class VanishCommand implements CommandExecutor {

    /*
     * Handle the vanish command.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player targetPlayer = null;

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission(Permission.SPECIALGUEST)) {
                player.sendMessage(ChatColor.RED + "Sorry, you do not have the required permission to execute this command!");
                return true;
            }
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Please specify a player when executing this command from the console!");

                return true;
            }

            targetPlayer = (Player) sender;
        } else {
            targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null) {
                sender.sendMessage(Utils.color("&cThat player currently is not online!"));

                return true;
            }
        }

        boolean isVanished = PlayerUtil.isVanished(targetPlayer);

        if (isVanished) {
            PlayerUtil.unvanishPlayer(targetPlayer);
        } else {
            PlayerUtil.vanishPlayer(targetPlayer);
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.hasPermission(Permission.SPECIALGUEST))
                onlinePlayer.sendMessage(Utils.color("&e" + targetPlayer.getName() + (isVanished ? " has become visible." : " has vanished. Poof.")));
        }

        if (!(sender instanceof Player))
            sender.sendMessage(Utils.color("&e") + targetPlayer.getName() + " is now " + (isVanished ? "visible" : "invisible") + ".");

        return true;
    }
}