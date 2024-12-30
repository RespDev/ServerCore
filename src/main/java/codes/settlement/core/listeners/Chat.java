package codes.settlement.core.listeners;

import codes.settlement.core.rank.Rank;
import codes.settlement.core.util.SqlUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        //e.setFormat();
        //String test = new Rank().getScoreboardName(new SqlUtil().getUserRank(e.getPlayer().getUniqueId())).toString();

        //e.getPlayer().sendMessage("Hey your rank is: " + new Rank().getScoreboardName(new SqlUtil().getUserRank(e.getPlayer().getUniqueId())));
    }
}
