package codes.settlement.core.util;

import org.bukkit.entity.Player;

public class PlayerUtil {

    public static void refreshPlayer(Player player) {
        new SqlUtil().createPlayer(player);
        refreshNametag(player);
        TabUtil.setHeaderFooter(player);
    }

    private static void refreshNametag(Player player) {

    }
}
