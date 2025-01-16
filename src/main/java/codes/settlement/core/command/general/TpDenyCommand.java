package codes.settlement.core.command.general;

import codes.settlement.core.manager.TpaRequestManager;
import codes.settlement.core.model.CustomCommand;
import codes.settlement.core.util.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpDenyCommand extends CustomCommand {

    public TpDenyCommand() {
        super("tpa.deny");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.color("&cThis command can only be used by players."));
            return true;
        }

        Player player = (Player) sender;
        Player requester = TpaRequestManager.denyRequest(player);

        if (requester == null) {
            player.sendMessage(Utils.color("&cYou have no pending teleport requests."));
            return true;
        }
        
        player.sendMessage(Utils.color("&aYou denied the teleport request."));
        requester.sendMessage(Utils.color("&cYour teleport request has been denied."));

        return true;
    }
}