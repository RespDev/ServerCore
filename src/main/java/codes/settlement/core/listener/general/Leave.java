package codes.settlement.core.listener.general;

import codes.settlement.core.command.general.VanishCommand;
import codes.settlement.core.manager.TpaRequestManager;
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

        // Tpa utilities
        TpaRequestManager.clearRequestsForPlayer(player);
    }
}
