package codes.settlement.core.command.general;

import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class VanishCommand implements CommandExecutor {

    private static final Set<UUID> vanishedPlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player targetPlayer = null;

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("core.specialguest")) {
                player.sendMessage(ChatColor.RED + "No permission!");
                return true;
            }
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Please specify a player when executing this command from the console!");

                return true;
            }

            targetPlayer = (Player) sender;
        } else {
            targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null) {
                sender.sendMessage(Utils.color("&cThat player currently is not online!"));

                return true;
            }
        }

        UUID uniqueId = targetPlayer.getUniqueId();
        boolean isVanished = vanishedPlayers.contains(uniqueId);

        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if (otherPlayer.equals(targetPlayer))
                continue;

            if (isVanished) {
                otherPlayer.showPlayer(targetPlayer);
            } else {
                if (!otherPlayer.hasPermission("core.trainee"))
                    otherPlayer.hidePlayer(targetPlayer);
            }
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.hasPermission("core.specialguest"))
                onlinePlayer.sendMessage(Utils.color("&e" + targetPlayer.getName() + (isVanished ? " has become visible." : " has vanished. Poof.")));
        }

        if (!(sender instanceof Player))
            sender.sendMessage(Utils.color("&e") + targetPlayer.getName() + " is now " + (isVanished ? "visible" : "invisible") + ".");

        if (isVanished)
            vanishedPlayers.remove(uniqueId);
        else
            vanishedPlayers.add(uniqueId);

        return true;
    }

    public static Set<UUID> getVanishedPlayers() {
        return Collections.unmodifiableSet(vanishedPlayers);
    }

    public static void unvanishPlayer(Player player) {
        vanishedPlayers.remove(player.getUniqueId());
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.showPlayer(player);
        }
    }

    public static void vanishPlayer(Player player) {
        vanishedPlayers.add(player.getUniqueId());
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission("core.trainee"))
                onlinePlayer.hidePlayer(player);
        }
    }
}