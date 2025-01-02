package codes.settlement.core.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerBuild implements Listener {
    private static ArrayList<UUID> allowBuild = new ArrayList<UUID>();

    // Buildmode System
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (allowBuild.contains(event.getPlayer().getUniqueId())) return;
        if (player.hasPermission("core.mod")) player.sendMessage(ChatColor.RED + "You must be in Build Mode to break blocks!");
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (allowBuild.contains(event.getPlayer().getUniqueId())) return;
        if (player.hasPermission("core.mod")) player.sendMessage(ChatColor.RED + "You must be in Build Mode to place blocks!");
        event.setCancelled(true);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (allowBuild.contains(event.getPlayer().getUniqueId())) allowBuild.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        GameMode gameMode = event.getNewGameMode();
        if (allowBuild.contains(event.getPlayer().getUniqueId())) {
            if (!gameMode.equals(GameMode.CREATIVE) && !gameMode.equals(GameMode.SPECTATOR)) {
                event.setCancelled(true);
            }
        } else {
            if (player.hasPermission("rank.mod")) {
                if (!gameMode.equals(GameMode.SURVIVAL)) {
                    event.setCancelled(true);
                }
            } else if (!gameMode.equals(GameMode.ADVENTURE)) {
                event.setCancelled(true);
            }
        }
    }

    public static void toggleBuild(Player player) {
        UUID uuid = player.getUniqueId();
        if (allowBuild.contains(uuid)) {
            player.setGameMode(GameMode.ADVENTURE);
            allowBuild.remove(uuid);
            player.sendMessage(ChatColor.RED + "Buildmode disabled.");
        }
        else {
            player.setGameMode(GameMode.CREATIVE);
            allowBuild.add(uuid);
            player.sendMessage(ChatColor.GREEN + "Buildmode enabled.");
        }
        return;
    }
}
