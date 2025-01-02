package codes.settlement.core.listeners;

import codes.settlement.core.util.Utils;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        // Old chat system before it was network-wide
        /*Player player = event.getPlayer();

        User user = LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser(player);
        String prefix = user.getCachedData().getMetaData().getPrefix();
        String suffix = user.getCachedData().getMetaData().getSuffix();

        String message = event.getMessage();
        String username = player.getName();

        if (prefix == null) prefix = "";
        if (suffix == null) suffix = "";

        event.setFormat(Utils.color(prefix + "&r&7" + username +  ": " + suffix) + message);*/
    }
}
