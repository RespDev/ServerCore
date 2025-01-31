package codes.settlement.core.util;

import codes.settlement.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class TpaUtil {

    private static final Map<Player, RequestData> requests = new HashMap<>();

    public static void addRequest(Player target, Player requester) {
        if (requests.containsKey(target) && requests.get(target).requester.equals(requester)) {
            requester.sendMessage(Utils.color("&cYou already have a pending teleport request from &b" + target.getName() + "&c."));
            return;
        }

        requests.put(target, new RequestData(requester, System.currentTimeMillis()));
        target.sendMessage(Utils.color("&aYou have received a teleport request from &b" + requester.getName() + "&a."));
        requester.sendMessage(Utils.color("&aYou sent a teleport request to &b" + target.getName() + "&a."));

        Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), () -> {
            if (requests.containsKey(target) && requests.get(target).requester.equals(requester)) {
                requests.remove(target);
                target.sendMessage(Utils.color("&aThe teleport request from &b" + requester.getName() + " &ahas expired."));
                requester.sendMessage(Utils.color("&aYour teleport request to &b" + target.getName() + " &ahas expired."));
            }
        }, 300L);
    }

    public static void cancelRequests(Player player) {
        if (requests.containsKey(player)) {
            player.sendMessage(Utils.color("&aYou have cancelled all outgoing teleport requests."));
        } else {
            player.sendMessage(Utils.color("&cYou have no outgoing teleport requests."));
            return;
        }

        Iterator<Map.Entry<Player, RequestData>> iterator = requests.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Player, RequestData> entry = iterator.next();

            if (entry.getValue().requester.equals(player)) {
                entry.getKey().sendMessage(Utils.color("&cThe teleport request from &b" + player.getName() + " &cwas canceled."));
                iterator.remove();
            }
        }
    }

    public static Player acceptRequest(Player player) {
        RequestData requestData = requests.remove(player);
        if (requestData != null) {
            Player requester = requestData.requester;
            player.sendMessage(Utils.color("&aYou accepted the teleport request from &b" + requester.getName() + "&a."));
            requester.sendMessage(Utils.color("&aYour teleport request to &b" + player.getName() + " &awas accepted."));
            return requester;
        }
        player.sendMessage(Utils.color("&cYou have no pending teleport requests."));
        return null;
    }

    public static void denyRequest(Player player) {
        if (!requests.containsKey(player)) {
            player.sendMessage(Utils.color("&cYou have no pending teleport requests."));
            return;
        }

        RequestData requestData = requests.remove(player);
        if (requestData != null) {
            Player requester = requestData.requester;
            player.sendMessage(Utils.color("&cYou denied the teleport request from &b" + requester.getName() + "&c."));
            requester.sendMessage(Utils.color("&cYour teleport request to &b" + player.getName() + " &cwas denied."));
        }
    }

    public static void clearRequestsForPlayer(Player player) {
        requests.remove(player);

        Iterator<Map.Entry<Player, RequestData>> iterator = requests.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Player, RequestData> entry = iterator.next();
            if (entry.getValue().requester.equals(player)) {
                entry.getKey().sendMessage(Utils.color("&cThe teleport request from &b" + player.getName() + " &cwas canceled."));
                iterator.remove();
            }
        }
    }

    public static void clearAllRequests() {
        requests.clear();
    }

    private static class RequestData {
        private final Player requester;
        private final long timestamp;

        public RequestData(Player requester, long timestamp) {
            this.requester = requester;
            this.timestamp = timestamp;
        }

        public Player getRequester() {
            return requester;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}
