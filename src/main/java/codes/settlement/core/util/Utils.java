package codes.settlement.core.util;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;

public final class Utils {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String getCurrentTimeInEST() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        String time = simpleDateFormat.format(new Date());
        return time;
    }

    /**
     * Serializes an array of ItemStacks into a Base64 string.
     *
     * @param items the ItemStacks to serialize
     * @return the Base64-encoded string representing the serialized data
     */
    public static String serializeItemStackArray(ItemStack[] items) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {

            // Write the length of the array first to track it during deserialization
            dataOutput.writeInt(items.length);
            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }

            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deserializes a Base64-encoded string into an array of ItemStacks.
     *
     * @param base64 the Base64 string to deserialize
     * @return the array of ItemStacks
     */
    public static ItemStack[] deserializeItemStackArray(String base64) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {

            int length = dataInput.readInt(); // First read the length of the array
            ItemStack[] items = new ItemStack[length];

            for (int i = 0; i < length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return new ItemStack[0]; // Return an empty array in case of an error
        }
    }

    /**
     * Capitalize first letter of a string.
     *
     * @param input the input
     * @return the string
     */
    public static String capitalizeFirstLetter(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
