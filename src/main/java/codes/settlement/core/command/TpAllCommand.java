package codes.settlement.core.command;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TpAllCommand extends AbstractCommand {

    public TpAllCommand() {
        super("tpall", "Teleport all players to you", Permission.DIRECTOR);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.color("&cThis command can only be executed by a player!"));
            return;
        }

        Player player = (Player) sender;

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.teleport(player.getLocation());
        }

        player.sendMessage(ChatColor.GREEN + "Teleported everyone to you.");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;  // You can implement tab completion for coordinates or angles if needed
    }
}
