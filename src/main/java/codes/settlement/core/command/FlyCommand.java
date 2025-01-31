package codes.settlement.core.command;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class FlyCommand extends AbstractCommand {

    public FlyCommand() {
        super("fly", "Toggle your flight state", Permission.SPECIALGUEST);
    }

    /*
     * Handles the fly command.
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player!");
            return;
        }

        Player player = (Player) sender;

        player.setAllowFlight(!player.getAllowFlight());
        player.sendMessage(player.getAllowFlight() ? ChatColor.GREEN + "Flight enabled." : ChatColor.RED + "Flight disabled.");
    }

    /*
     * Handles tab completion of the fly command.
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}