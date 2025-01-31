package codes.settlement.core.command;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class GamemodeCommand extends AbstractCommand {

    public GamemodeCommand() {
        super("gamemode", "Changes the players gamemode", Permission.MOD, "gm");
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

        GameMode gameMode;
        try {
            gameMode = GameMode.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
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
        }

        if (!targetPlayer.equals(player)) {
            targetPlayer.sendMessage(Utils.color("&aYour gamemode has been set to: &b" + gameMode.name().toUpperCase() + "&a."));
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
