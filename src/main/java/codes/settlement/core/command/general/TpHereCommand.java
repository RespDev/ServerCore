package codes.settlement.core.command.general;

import codes.settlement.core.model.CustomCommand;
import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpHereCommand extends CustomCommand {

    public TpHereCommand() {
        super("tphere.use");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.color("&cThis command can only be used by players."));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.color("&cUsage: /tphere <player>"));
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null || !target.isOnline()) {
            player.sendMessage(Utils.color("&cPlayer not found or not online."));
            return true;
        }

        target.teleport(player);
        player.sendMessage(Utils.color("&aYou teleported &b" + target.getName() + " &ato yourself."));
        target.sendMessage(Utils.color("&aYou have been teleported to &b" + player.getName() + "&a."));

        return true;
    }
}