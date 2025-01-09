package codes.settlement.core.command.disabled;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TabCompletion implements Listener {
    private static List<String> blockedCompletions = new ArrayList<>(Arrays.asList("/minecraft:", "/bukkit:", "/worldedit",
            "/ncp", "/nocheatplus", "//", "/luckperms:", "/servercore:"));

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().startsWith("/minecraft:") || event.getMessage().startsWith("/bukkit:")) {
            Player player = event.getPlayer();
            if (player == null || !player.hasPermission("core.trainee")) {
                event.getPlayer().sendMessage(ChatColor.RED + "Disabled");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        List<String> newCompletions = new ArrayList<>();
        List<String> completions = event.getCompletions();
        if (event.getSender() instanceof Player) {
            Player player = (Player) event.getSender();
            if (player == null || !player.hasPermission("core.trainee")) {
                for (String completion : completions) {
                    boolean block = false;
                    for (String s : blockedCompletions) {
                        if (completion.startsWith(s)) {
                            block = true;
                            break;
                        }
                    }

                    if (!block && completion.contains(":")) {
                        int start = completion.startsWith("/") ? 1 : 0;
                        String prefix = completion.substring(start, completion.indexOf(":")).toLowerCase();
                        String suffix = completion.substring(completion.indexOf(":") + 1);
                        if (!prefix.contains(" ")) {
                            for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
                                if (p.getName().equalsIgnoreCase(prefix)) {
                                    blockedCompletions.add((start == 1 ? "/" : "") + prefix);
                                    block = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (block) continue;

                    newCompletions.add(completion);
                }
                newCompletions.sort(String.CASE_INSENSITIVE_ORDER);
                event.setCompletions(newCompletions);
            }
        }
    }
}
