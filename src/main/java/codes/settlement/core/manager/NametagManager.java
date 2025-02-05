package codes.settlement.core.manager;

import codes.settlement.core.util.Utils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NametagManager {
    private static final LuckPerms luckPerms = Bukkit.getServer().getServicesManager().load(LuckPerms.class);
    private static final Map<UUID, Scoreboard> playerScoreboards = new HashMap<>();
    private static final Map<String, String> teamPrefixes = new HashMap<>();

    public static void setupPlayer(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        playerScoreboards.put(player.getUniqueId(), scoreboard);
        player.setScoreboard(scoreboard);
        updateNametag(player);
    }

    public static void updateNametag(Player player) {
        String prefix = getLuckPermsPrefix(player);
        int weight = getLuckPermsWeight(player);
        String teamName = String.format("%03d", 1000 - weight);
        teamPrefixes.put(player.getName(), prefix);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = playerScoreboards.get(onlinePlayer.getUniqueId());
            if (scoreboard == null) continue;

            Team team = scoreboard.getTeam(teamName);
            if (team == null) {
                team = scoreboard.registerNewTeam(teamName);
                team.setPrefix(Utils.color(prefix));
                team.setColor(ChatColor.GRAY);
            }
            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);

            team.addEntry(player.getName());
        }
    }

    public static Scoreboard getScoreboard(Player player) {
        return playerScoreboards.getOrDefault(player.getUniqueId(), Bukkit.getScoreboardManager().getNewScoreboard());
    }

    private static String getLuckPermsPrefix(Player player) {
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null) return "";
        return user.getCachedData().getMetaData(QueryOptions.defaultContextualOptions()).getPrefix();
    }

    private static int getLuckPermsWeight(Player player) {
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null) return 0;

        Group group = luckPerms.getGroupManager().getGroup(user.getPrimaryGroup());
        if (group == null) return 0;

        return group.getWeight().orElse(0);
    }
}
