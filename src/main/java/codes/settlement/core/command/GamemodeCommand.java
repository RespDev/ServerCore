package codes.settlement.core.command;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GamemodeCommand extends AbstractCommand {

    private static final Map<String, GameMode> GAMEMODE_ALIASES = new HashMap<>();

    static {
        GAMEMODE_ALIASES.put("s", GameMode.SURVIVAL);
        GAMEMODE_ALIASES.put("c", GameMode.CREATIVE);
        GAMEMODE_ALIASES.put("a", GameMode.ADVENTURE);
        GAMEMODE_ALIASES.put("sp", GameMode.SPECTATOR);
    }

    public GamemodeCommand() {
        super("gamemode", "Changes the players gamemode", Permission.GAMEMODE_COMMAND, "gm");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.color("&cThis command can only be executed by a player!"));
            return;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(Utils.color("&cUsage: /gamemode <gamemode> [player]"));
            return;
        }

        GameMode gameMode = parseGameMode(args[0]);
        if (gameMode == null) {
            player.sendMessage(Utils.color("&cInvalid gamemode!"));
            return;
        }

        Player targetPlayer = player;
        if (args.length > 1) {
            targetPlayer = Bukkit.getPlayerExact(args[1]);
            if (targetPlayer == null) {
                player.sendMessage(Utils.color("&cPlayer not found!"));
                return;
            }
        }

        targetPlayer.setGameMode(gameMode);
        if (targetPlayer == player) {
            player.sendMessage(Utils.color("&aYour gamemode has been set to: &b" + gameMode.name().toUpperCase() + "&a."));
        } else {
            player.sendMessage(Utils.color("&b" + targetPlayer.getName() + "'s &agamemode has been set to: &b" + gameMode.name().toUpperCase() + "&a."));
            targetPlayer.sendMessage(Utils.color("&aYour gamemode has been set to: &b" + gameMode.name().toUpperCase() + "&a."));
        }
    }

    private GameMode parseGameMode(String input) {
        input = input.toLowerCase();
        if (GAMEMODE_ALIASES.containsKey(input)) {
            return GAMEMODE_ALIASES.get(input);
        }

        try {
            return GameMode.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            for (GameMode gameMode : GameMode.values()) {
                if (gameMode.name().toLowerCase().startsWith(args[0].toLowerCase())) {
                    suggestions.add(gameMode.name().toUpperCase());
                }
            }
        } else if (args.length == 2) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                    suggestions.add(onlinePlayer.getName());
                }
            }
        }

        return suggestions;
    }
}