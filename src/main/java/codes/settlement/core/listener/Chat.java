package codes.settlement.core.listener;

import codes.settlement.core.util.Utils;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class Chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        User user = LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser(player);

        String prefix = user.getCachedData().getMetaData().getPrefix();
        String suffix = user.getCachedData().getMetaData().getSuffix();
        String message = event.getMessage();
        String username = player.getName();

        if (prefix == null) prefix = "";
        if (suffix == null) suffix = "";

        event.setCancelled(true);
        Bukkit.broadcastMessage(Utils.color(prefix + " " + username + " " + suffix + "&8: ") + message);
    }
}
