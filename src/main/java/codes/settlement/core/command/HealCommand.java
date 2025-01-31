package codes.settlement.core.command;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class HealCommand extends AbstractCommand {

    public HealCommand() {
        super("heal", "Heals the player.", Permission.STAFF);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player target;

        if (args.length == 0) {
            if (sender instanceof Player) {
                target = (Player) sender;
            } else {
                sender.sendMessage(ChatColor.RED + "You cannot heal yourself from the console.");
                return;
            }
        } else {
            target = sender.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return;
            }
        }

        target.setHealth(20);
        target.setFoodLevel(20);
        target.getActivePotionEffects().forEach(effect -> target.removePotionEffect(effect.getType()));

        sender.sendMessage(ChatColor.GREEN + "Successfully healed " + target.getName() + ".");

        if (!target.equals(sender)) {
            target.sendMessage(ChatColor.GREEN + "You have been healed.");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    suggestions.add(onlinePlayer.getName());
                }
            }
        }

        return suggestions;
    }
}
