package codes.settlement.core.command.general;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (player.hasPermission("core.specialguest")) {
            player.setAllowFlight(!player.getAllowFlight());
            player.setFlying(player.getAllowFlight());
            player.sendMessage(player.getAllowFlight() ? ChatColor.GREEN + "You can now fly!" : ChatColor.RED + "You can no longer fly!");
        } else {
            player.sendMessage(ChatColor.RED + "No permission!");
        }
        return true;
    }
}
