package codes.settlement.core.command;

import codes.settlement.core.constant.Permission;
import codes.settlement.core.model.AbstractCommand;
import codes.settlement.core.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class HatCommand extends AbstractCommand {

    public HatCommand() {
        super("hat", "Put the item that you are currently holding onto your head", Permission.STAFF);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player!");
            return;
        }

        Player player = (Player) sender;

        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType().isAir()) {
            player.sendMessage(Utils.color("&cYou are not holding any item!"));
            return;
        }

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemStack helmet = new ItemStack(itemInHand);

        player.getInventory().setHelmet(helmet);
        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        
        player.sendMessage(Utils.color("&aSuccessfully put the item you are holding onto your head."));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}