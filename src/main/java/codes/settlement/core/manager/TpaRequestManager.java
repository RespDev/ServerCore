package codes.settlement.core.manager;

import codes.settlement.core.Core;
import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class TpaRequestManager {

    private static final Map<Player, RequestData> requests = new HashMap<>();

    public static void addRequest(Player target, Player requester) {
        requests.put(target, new RequestData(requester, System.currentTimeMillis()));
        Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), () -> {
            if (requests.containsKey(target) && requests.get(target).requester.equals(requester)) {
                requests.remove(target);
                target.sendMessage(Utils.color("&aThe teleport request from &b" + requester.getName() + " &ahas expired."));
                requester.sendMessage(Utils.color("&aYour teleport request to &b" + target.getName() + " &ahas expired."));
            }
        }, 300L);
    }

    public static void cancelRequests(Player player) {
        Iterator<Map.Entry<Player, RequestData>> iterator = requests.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Player, RequestData> entry = iterator.next();
            if (entry.getValue().requester.equals(player)) {
                iterator.remove();
            }
        }
    }

    public static Player acceptRequest(Player player) {
        RequestData requestData = requests.remove(player);
        return requestData != null ? requestData.requester : null;
    }

    public static Player denyRequest(Player player) {
        RequestData requestData = requests.remove(player);
        return requestData != null ? requestData.requester : null;
    }

    public static void clearRequestsForPlayer(Player player) {
        requests.remove(player); // Remove any requests targeting this player

        // Remove any requests made by this player
        Iterator<Map.Entry<Player, RequestData>> iterator = requests.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Player, RequestData> entry = iterator.next();
            if (entry.getValue().requester.equals(player)) {
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