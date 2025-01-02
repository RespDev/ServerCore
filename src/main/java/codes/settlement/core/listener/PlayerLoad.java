package codes.settlement.core.listener;

import codes.settlement.core.Core;
import codes.settlement.core.command.VanishCommand;
import codes.settlement.core.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public final class PlayerLoad implements Listener {

    @EventHandler
    public void onPlayerLoad(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Cancel join message
        event.setJoinMessage("");

        // Refresh player
        PlayerUtil.refreshPlayer(player);

        // Vanish all staff who join
        if (player.hasPermission("core.trainee")) {
            VanishCommand.vanishPlayer(player);
        }

        // Hide all vanished players from the player who joined
        for (UUID vanishedPlayerId : VanishCommand.getVanishedPlayers()) {
            Player vanishedPlayer = Bukkit.getPlayer(vanishedPlayerId);

            if (vanishedPlayer != null && !player.hasPermission("core.trainee")) {
                player.hidePlayer(vanishedPlayer);
            }
        }

        // Set gamemode
        GameMode gamemode;
        try {
            gamemode = GameMode.valueOf(Core.getInstance().getConfig().getString("gamemode"));
        } catch (IllegalArgumentException e) {
            gamemode = GameMode.ADVENTURE;
        }
        player.setGameMode(gamemode);
    }
}
