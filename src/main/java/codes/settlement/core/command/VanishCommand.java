package codes.settlement.core.command;

import codes.settlement.core.constant.Message;
import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.PlayerUtil;
import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class VanishCommand extends AbstractCommand {

    public VanishCommand() {
        super("vanish", "Vanish yourself from the view of guests", Permission.SPECIALGUEST, "v");
    }

    /*
     * Handles the vanish command.
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player targetPlayer = null;

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission(Permission.SPECIALGUEST)) {
                player.sendMessage(Message.NO_PERMISSION);
                return;
            }
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Please specify a player when executing this command from the console!");
                return;
            }

            targetPlayer = (Player) sender;
        } else {
            targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null) {
                sender.sendMessage(Utils.color("&cThat player currently is not online!"));
                return;
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
    }

    /*
     * Handles tab completion of the vanish command.
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}