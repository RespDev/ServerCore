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
        Player player = event.getPlayer();

        User user = LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser(player);
        String prefix = user.getCachedData().getMetaData().getPrefix();
        String suffix = user.getCachedData().getMetaData().getSuffix();

        String message = event.getMessage();
        String username = player.getName();

        event.setFormat(Utils.color(prefix + username + suffix + (player.hasPermission("servercore.hasrank") ? "&f: " : "&7: ") + message));
    }
}
