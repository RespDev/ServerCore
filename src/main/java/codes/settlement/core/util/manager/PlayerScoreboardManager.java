package codes.settlement.core.util.manager;

import java.util.UUID;

import codes.settlement.core.Core;
import org.apache.commons.lang3.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerScoreboardManager implements Runnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getScoreboard();
            if (player.getScoreboard().getObjective("Default") != null)
                updateScoreboard(player);
            else
                createScoreboard(player);
        }
    }

    private void createScoreboard(Player player) {
        UUID uuid = player.getUniqueId();

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Default", "dummy");

        objective.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "GALAXY PARKS");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore(ChatColor.DARK_BLUE + " ").setScore(10);
        // money
        objective.getScore(ChatColor.AQUA + " ").setScore(8);
        // tokens
        objective.getScore(ChatColor.GREEN + " ").setScore(6);
        // rank
        objective.getScore(ChatColor.BLUE + " ").setScore(4);
        objective.getScore(ChatColor.GREEN + "Server: " + Core.getInstance().config.getString("server-name")).setScore(2);
        objective.getScore(ChatColor.YELLOW + " ").setScore(1);
        objective.getScore(ChatColor.YELLOW + "store.galaxyparks.net").setScore(0);

        // Money
        Team money = scoreboard.registerNewTeam("money");
        money.addEntry(ChatColor.DARK_GREEN.toString());
        money.setPrefix(ChatColor.GREEN + "$ ");
        money.setSuffix(ChatColor.GREEN + "0");
        objective.getScore(ChatColor.DARK_GREEN.toString()).setScore(9);

        // Tokens
        String star = StringEscapeUtils.unescapeJava("\\u272a");
        Team tokens = scoreboard.registerNewTeam("tokens");
        tokens.addEntry(ChatColor.GREEN.toString());
        tokens.setPrefix(ChatColor.GREEN + star + " ");
        tokens.setSuffix(ChatColor.GREEN + "0");
        objective.getScore(ChatColor.GREEN.toString()).setScore(7);

        // Rank
        Team playerRank = scoreboard.registerNewTeam("playerRank");
        playerRank.addEntry(ChatColor.DARK_PURPLE.toString());
        playerRank.setPrefix(ChatColor.GREEN + "Rank: ");
        playerRank.setSuffix(ChatColor.GRAY + "Soon");
        objective.getScore(ChatColor.DARK_PURPLE.toString()).setScore(5);

        // Players
        Team players = scoreboard.registerNewTeam("players");
        players.addEntry(ChatColor.RED.toString());
        players.setPrefix(ChatColor.GREEN + "Online Players: ");
        players.setSuffix(ChatColor.GREEN + "" + Bukkit.getOnlinePlayers().size());
        objective.getScore(ChatColor.RED.toString()).setScore(3);

        player.setScoreboard(scoreboard);
    }

    private void updateScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        UUID uuid = player.getUniqueId();
        Team money = scoreboard.getTeam("money");
        Team tokens = scoreboard.getTeam("tokens");
        Team playerRank = scoreboard.getTeam("playerRank");
        Team players = scoreboard.getTeam("players");

        money.setSuffix(ChatColor.GREEN + "0");
        tokens.setSuffix(ChatColor.GREEN + "0");
        playerRank.setSuffix(ChatColor.GRAY + "Soon");
        players.setSuffix(ChatColor.GREEN + "" + Bukkit.getOnlinePlayers().size());
    }
}
