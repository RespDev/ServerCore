package codes.settlement.core.listener;

import codes.settlement.core.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class Leave implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        event.setQuitMessage("");
        Player player = event.getPlayer();

        PlayerUtil.handleLeave(player);
    }
}
