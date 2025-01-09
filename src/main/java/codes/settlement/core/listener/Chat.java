package codes.settlement.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class Chat implements Listener {

    @EventHandler
    public final void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
    }
}
