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

public final class InvseeCommand extends AbstractCommand {

    public InvseeCommand() {
        super("invsee", "View a players inventory", Permission.STAFF, "inventorysee");
    }

    /*
     * Handles the invsee command.
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player!");
            return;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(Utils.color("&cUsage: /invsee <player>"));
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            player.sendMessage(Utils.color("&cThat player currently is not online!"));
            return;
        }

        player.openInventory(targetPlayer.getInventory());
        player.sendMessage(Utils.color("&aYou are now viewing &b" + targetPlayer.getName() + "'s &ainventory."));
    }

    /*
     * Handles tab completion of the invsee command.
     */
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