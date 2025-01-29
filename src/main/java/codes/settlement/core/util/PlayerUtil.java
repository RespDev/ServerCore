package codes.settlement.core.util;

import codes.settlement.core.Core;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.ChatMetaNode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerUtil {
    private static final LuckPerms luckPerms = LuckPermsProvider.get();

    public static void sendTab(Player player) {
        User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);

        int rankWeight = user.resolveInheritedNodes(QueryOptions.nonContextual()).stream()
                .filter(node -> node instanceof ChatMetaNode)
                .mapToInt(node -> ((ChatMetaNode) node).getPriority())
                .max()
                .orElse(0);
        String prefix = user.getCachedData().getMetaData().getPrefix();

        player.setPlayerListHeader(ChatColor.AQUA + "Galaxy Parks - " + ChatColor.GREEN + "A Family of Servers");
        player.setPlayerListFooter(ChatColor.AQUA + "You are in the " + ChatColor.GREEN + Core.getInstance().getConfiguration().getString("server-name") + ChatColor.AQUA + " server");

        player.setPlayerListOrder(rankWeight);
        player.setDisplayName(Utils.color((prefix != null ? prefix : "")) + player.getName());
        player.setPlayerListName(Utils.color((prefix != null ? prefix : "")) + player.getName());
    }
}
