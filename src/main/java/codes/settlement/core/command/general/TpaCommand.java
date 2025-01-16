package codes.settlement.core.command.general;

import codes.settlement.core.manager.TpaRequestManager;
import codes.settlement.core.model.CustomCommand;
import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand extends CustomCommand {

    public TpaCommand() {
        super("tpa.use");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.color("&cThis command can only be used by players."));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.color("&cUsage: /tpa <player>"));
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null || !target.isOnline()) {
            player.sendMessage(Utils.color("&cPlayer not found or not online."));
            return true;
        }

        if (target == player) {
            player.sendMessage(Utils.color("&cYou cannot send a teleport request to yourself."));
            return true;
        }

        TpaRequestManager.addRequest(target, player);
        target.sendMessage(Utils.color("&aRequest will expire in 15 seconds."));
        target.sendMessage(Utils.color("&b" + player.getName() + " &awants to teleport to you. Use /tpaccept or /tpdeny."));
        player.sendMessage(Utils.color("&aRequest will expire in 15 seconds."));
        player.sendMessage(Utils.color("&aTeleport request sent to &b" + target.getName() + "&a."));

        return true;
    }
}