package codes.settlement.core.util;

import codes.settlement.core.Core;
import codes.settlement.core.model.Permission;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.ChatMetaNode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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

    public static void handleJoin(Player player) {
        /*
         * Set gamemode if you aren't a staff member
         */
        if (!player.hasPermission(Permission.STAFF)) {
            GameMode gamemode;
            try {
                gamemode = GameMode.valueOf(Core.getInstance().getConfig().getString("gamemode"));
            } catch (IllegalArgumentException e) {
                gamemode = GameMode.ADVENTURE;
            }
            player.setGameMode(gamemode);
        }

        // TODO: Add more join functions as features get added back.
    }
}
