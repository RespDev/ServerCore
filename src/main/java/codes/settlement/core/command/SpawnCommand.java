package codes.settlement.core.command;

import codes.settlement.core.Core;
import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.Utils;
import codes.settlement.core.util.config.SpawnConfig;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class SpawnCommand extends AbstractCommand {

    public SpawnCommand() {
        super("spawn", "Teleport to spawn", Permission.SETTLER);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player!");
            return;
        }

        SpawnConfig spawnConfig = Core.getInstance().getSpawnConfig();
        Player player = (Player) sender;
        Location spawnLocation = spawnConfig.getSpawnLocation();

        if (args.length > 0 && args[0].equalsIgnoreCase("set")) {
            if (!player.hasPermission(Permission.DEVELOPER)) {
                if (spawnLocation == null) {
                    player.sendMessage(Utils.color("&cSpawn location is not set!"));
                    return;
                }

                player.teleport(spawnLocation);
                player.sendMessage(Utils.color("&aYou have been teleported to spawn!"));
                return;
            }
            spawnConfig.setSpawnLocation(player.getLocation());
            player.sendMessage(Utils.color("&aSpawn location has been set!"));
            return;
        }

        if (spawnLocation == null) {
            player.sendMessage(Utils.color("&cSpawn location is not set!"));
            return;
        }

        player.teleport(spawnLocation);
        player.sendMessage(Utils.color("&aYou have been teleported to spawn!"));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1 && sender.hasPermission(Permission.DEVELOPER)) {
            return List.of("set");
        }
        return null;
    }
}