package codes.settlement.core.command;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class SocialsCommand extends AbstractCommand {

    public SocialsCommand() {
        super("socials", "Our social media accounts", Permission.SETTLER);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player!");
            return;
        }

        Player player = (Player) sender;

        player.sendMessage(Utils.color("&e&lOUR SOCIALS:"));
        player.sendMessage(Utils.color("&7X: &cComing soon!"));
        player.sendMessage(Utils.color("&7Instagram: &cComing soon!"));
        player.sendMessage(Utils.color("&7YouTube: &cComing soon!"));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}