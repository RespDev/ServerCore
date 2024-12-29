package codes.settlement.core.util;

import codes.settlement.core.Core;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.lang.reflect.Field;

public class TabUtil {

    public static void setHeaderFooter(Player player) {
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

		try {
			Field a = packet.getClass().getDeclaredField("a");
			a.setAccessible(true);

			Field b = packet.getClass().getDeclaredField("b");
			b.setAccessible(true);

			a.set(packet, new ChatComponentText(Core.getInstance().tabHeader));
			b.set(packet, new ChatComponentText(Core.getInstance().tabFooter));

			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
