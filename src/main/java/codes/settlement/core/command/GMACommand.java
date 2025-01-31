package codes.settlement.core.command;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.Utils;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class GMACommand extends AbstractCommand {
    public GMACommand() {
        super("gma", "Changes the players gamemode to Adventure", Permission.GAMEMODE_COMMAND);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.color("&cThis command can only be executed by a player!"));
            return;
        }

        Player player = (Player) sender;

        player.setGameMode(GameMode.ADVENTURE);
        player.sendMessage(Utils.color("&aYour gamemode has been set to: &bADVENTURE&a."));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
