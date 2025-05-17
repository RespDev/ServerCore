package codes.settlement.core.command;

import codes.settlement.core.Core;
import codes.settlement.core.constant.Permission;
import codes.settlement.core.manager.HotbarManager;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class ConfigCommand extends AbstractCommand {

    public ConfigCommand() {
        super("config", "The link to our discord server", Permission.DEVELOPER);
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Utils.color("&cUsage: /config reload [hotbar]"));
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (args.length == 1) {
                Core.getInstance().reloadConfig();
                sender.sendMessage(Utils.color("&aAll configs reloaded."));
            } else if (args.length == 2 && args[1].equalsIgnoreCase("hotbar")) {
                HotbarManager.reloadFiles();
                sender.sendMessage(Utils.color("&aHotbar config reloaded."));
            } else {
                sender.sendMessage(Utils.color("&cUnknown config section: " + args[1]));
            }
            return;
        }

        sender.sendMessage(Utils.color("&cUnknown subcommand."));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return List.of("reload");
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("reload")) {
            return List.of("hotbar");
        }

        return List.of();
    }
}