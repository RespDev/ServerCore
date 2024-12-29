package codes.settlement.core.listeners;

import codes.settlement.core.util.PlayerUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerLoad implements Listener {

    @EventHandler
    public void onPlayerLoad(PlayerJoinEvent e) {
        PlayerUtil.refreshPlayer(e.getPlayer());
    }
}
