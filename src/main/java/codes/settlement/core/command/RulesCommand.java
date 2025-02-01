package codes.settlement.core.command;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class RulesCommand extends AbstractCommand {

    public RulesCommand() {
        super("rules", "View the rules of our server", Permission.SETTLER, "rule");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player!");
            return;
        }

        Player player = (Player) sender;

        player.sendMessage(Utils.color("&e&lOUR RULES:"));
        player.sendMessage(Utils.color("&bGalaxy Parks's &7goal is to be a fun environment that is safe and family friendly. Below is the rules of our server."));
        player.sendMessage(Utils.color("&71. No swearing. &a- Keep chat family friendly and safe for everyone!"));
        player.sendMessage(Utils.color("&72. No spamming. &a- Do not spam chat with mentions or any messages."));
        player.sendMessage(Utils.color("&73. No inappropriate content of any kind. &a- Keep everything family friendly."));
        player.sendMessage(Utils.color("&74. No fighting. &a- Fighting among guests and staff is not tolerated."));
        player.sendMessage(Utils.color("&75. No sharing personal information. &a- Leaking your or other peoples personal information is not allowed."));
        player.sendMessage(Utils.color("&76. No drama allowed. &a- &bGalaxy Parks &ais a fun place and please keep frustration to yourselves."));
        player.sendMessage(Utils.color("&77. No unfair advantages. &a- We do not tolerate cheats or unfair exploits of any kind."));
        player.sendMessage(Utils.color("&7The staff team reserves the right to moderate users. If you believe you were falsely moderated please open a support ticket in our Discord server."));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}