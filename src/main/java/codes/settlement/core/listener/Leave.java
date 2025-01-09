package codes.settlement.core.listener;

import codes.settlement.core.command.VanishCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class Leave implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // Cancel quit message
        event.setQuitMessage("");

        // Vanish utilities
        if (VanishCommand.getVanishedPlayers().contains(player.getUniqueId())) {
            VanishCommand.unvanishPlayer(player);
        }
    }
}
