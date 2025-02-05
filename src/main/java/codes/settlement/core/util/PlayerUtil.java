package codes.settlement.core.util;

import codes.settlement.core.Core;
import codes.settlement.core.constant.Permission;
import codes.settlement.core.manager.NametagManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class PlayerUtil {
    private static final Set<UUID> vanishedPlayers = new HashSet<>();

    /*
     * Format the tab list.
     */
    public static void sendTab(Player player) {
        player.setPlayerListHeader(ChatColor.AQUA + "Galaxy Parks - " + ChatColor.GREEN + "A Family of Servers");
        player.setPlayerListFooter(ChatColor.AQUA + "You are in the " + ChatColor.GREEN + Core.getInstance().getConfiguration().getString("server-name") + ChatColor.AQUA + " server");

        NametagManager.setupPlayer(player);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            NametagManager.updateNametag(onlinePlayer);
        }
    }

    /*
     * Handle player join.
     */
    public static void handleJoin(Player player) {
        /*
         * Set gamemode if you aren't a staff member
         */
        if (!player.hasPermission(Permission.STAFF)) {
            GameMode gamemode;
            try {
                gamemode = GameMode.valueOf(Core.getInstance().getConfig().getString("gamemode"));
            } catch (IllegalArgumentException e) {
                gamemode = GameMode.ADVENTURE;
            }
            player.setGameMode(gamemode);
        }

        /*
         * Sends a join message when you join
         */
        String message = Core.getInstance().getConfiguration().getString("welcome-message");
        if (message != null) {
            player.sendMessage(Utils.color(message));
        }

        /*
         * Teleports the player to spawn
         */
        Location spawnLocation = Core.getInstance().getSpawnConfig().getSpawnLocation();
        if (!player.hasPermission(Permission.STAFF)) {
            if (spawnLocation == null) {
                player.sendMessage(Utils.color("&cSpawn location is not set!"));
                return;
            }

            player.teleport(spawnLocation);
        }

        /*
         * Vanish staff members on join
         */
        handleVanishJoin(player);

        /*
         * Loads tab on join
         */
        sendTab(player);

        // TODO: Add more join functions as features get added back.
    }

    /*
     * Handle player leave.
     */
    public static void handleLeave(Player player) {
        handleVanishLeave(player);
        NametagManager.updateNametag(player);
        TpaUtil.clearRequestsForPlayer(player);

        // TODO: Add more leave functions as features get added back.
    }

    /*
     * Check if the specified player is vanished.
     */
    public static Boolean isVanished(Player player) {
        return vanishedPlayers.contains(player.getUniqueId());
    }

    /*
     * Unvanish the specified player.
     */
    public static void unvanishPlayer(Player player) {
        if (vanishedPlayers.contains(player.getUniqueId())) {
            vanishedPlayers.remove(player.getUniqueId());
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(Core.getInstance(), player);
            }
        }
    }

    /*
     * Vanish the specified player.
     */
    public static void vanishPlayer(Player player) {
        if (!vanishedPlayers.contains(player.getUniqueId())) {
            vanishedPlayers.add(player.getUniqueId());
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission(Permission.SPECIALGUEST))
                    onlinePlayer.hidePlayer(Core.getInstance(), player);
            }
        }
    }

    /*
     * Unvanish player on leave.
     */
    private static void handleVanishLeave(Player player) {
        if (vanishedPlayers.contains(player.getUniqueId())) {
            unvanishPlayer(player);
        }
    }

    /*
     * Vanish staff on join.
     */
    private static void handleVanishJoin(Player player) {
        if (player.hasPermission(Permission.STAFF)) {
            vanishPlayer(player);
        }
    }
}
