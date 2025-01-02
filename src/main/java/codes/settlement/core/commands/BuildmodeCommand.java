package codes.settlement.core.commands;

import codes.settlement.core.listeners.PlayerBuild;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildmodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (player.hasPermission("core.mod")) {
            PlayerBuild.toggleBuild(player);
        } else {
            player.sendMessage(ChatColor.RED + "No permission!");
        }
        return true;
    }
}
