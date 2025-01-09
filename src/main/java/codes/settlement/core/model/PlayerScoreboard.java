package codes.settlement.core.model;

import codes.settlement.core.Core;
import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public final class PlayerScoreboard implements Runnable {

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
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Default", "dummy");

        // Set the scoreboard title
        objective.setDisplayName(Utils.color(Core.getInstance().getConfig().getString("server-name")));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Retrieve lines from the config
        List<String> lines = Core.getInstance().getConfig().getStringList("scoreboard.lines");

        if (lines == null || lines.isEmpty()) {
            Bukkit.getLogger().warning("The 'scoreboard.lines' section in the config is missing or empty.");
            return;
        }

        // Create teams for each line
        int score = lines.size();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String teamName = "line" + i;

            // Ensure the team name is unique (up to 16 characters)
            Team team = scoreboard.getTeam(teamName);
            if (team == null) {
                team = scoreboard.registerNewTeam(teamName);
            }

            // Add a unique entry for the team to display the line
            String entry = ChatColor.values()[i % ChatColor.values().length] + "" + ChatColor.RESET; // Use unique colors as entries
            if (!team.hasEntry(entry)) {
                team.addEntry(entry);
            }

            // Set the text for the team
            team.setPrefix(Utils.color(line));

            // Set the score for this line
            objective.getScore(entry).setScore(score--);
        }

        // Apply the scoreboard to the player
        player.setScoreboard(scoreboard);
    }

    private void updateScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        // Retrieve lines from the config
        List<String> lines = Core.getInstance().getConfig().getStringList("scoreboard.lines");

        if (lines == null || lines.isEmpty()) return;

        // Update each team's prefix dynamically
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String teamName = "line" + i;

            Team team = scoreboard.getTeam(teamName);
            if (team != null) {
                team.setPrefix(Utils.color(line));
            }
        }
    }

}