package codes.settlement.core.menu;

import codes.settlement.core.Core;
import codes.settlement.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private final List<Button> buttons = new ArrayList<>();
    private int size = 9 * 3;
    private String title = "Default Title";

    public final List<Button> getButtons() {
        return buttons;
    }

    protected final void addButton(Button button) {
        this.buttons.add(button);
    }

    protected final void setSize(int size) {
        this.size = size;
    }

    protected final void setTitle(String title) {
        this.title = title;
    }

    public final void displayTo(Player player) {
        Inventory inventory = Bukkit.createInventory(player, this.size, Utils.color(this.title));

        for (Button button : this.buttons)
            inventory.setItem(button.getSlot(), button.getItem());

        player.openInventory(inventory);
        player.setMetadata("ServerCoreMenu", new FixedMetadataValue(Core.getInstance(), this));
    }
}
