package codes.settlement.core.model;

import codes.settlement.core.constant.Message;
import codes.settlement.core.util.LoggingUtil;
import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommand extends BukkitCommand {

    public AbstractCommand(String command, String description, String permission, String... aliases) {
        super(command);
        this.setAliases(Arrays.asList(aliases));
        this.setDescription(description);
        this.setPermission(permission);

        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap map = (CommandMap) field.get(Bukkit.getServer());
            map.register("servercore", this);

            LoggingUtil.logMessage("AbstractCommand", "Registered the " + command + " command!");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LoggingUtil.logMessage(Utils.color("&cAbstractCommand"), "Failed to register the " + command + " command!");
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        execute(commandSender, strings);
        return false;
    }

    public abstract void execute(CommandSender sender, String[] args);

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return onTabComplete(sender, args);
    }

    @Override
    public String getPermissionMessage() {
        return Message.NO_PERMISSION;
    }

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

}