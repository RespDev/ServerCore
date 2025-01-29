package codes.settlement.core.util;

import codes.settlement.core.Core;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Utility functions for interacting with MySQL.
 */
public class SqlUtil {
    private String host;
    private String port;
    private String databaseName;
    private String user;
    private String password;
    private String url;

    /**
     * Instantiates a new Sql util.
     */
    public SqlUtil() {
        loadLogin();
    }

    /**
     * Load login.
     */
    private void loadLogin() {
        host = Core.getInstance().getConfiguration().getString("db.host");
        port = Core.getInstance().getConfiguration().getString("db.port");
        databaseName = Core.getInstance().getConfiguration().getString("db.database");
        user = Core.getInstance().getConfiguration().getString("db.username");
        password = Core.getInstance().getConfiguration().getString("db.password");

        url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?useSSL=true&autoReconnect=true";
    }

    /**
     * Gets sql connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            LoggingUtil.logMessage("Core", "Failed to connect to the database");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Create the backpack_items table.
     */
    public void createBackpackTable() {
        String createBackpackTableQuery = "CREATE TABLE IF NOT EXISTS backpack_items (" +
                "player_uuid VARCHAR(36) NOT NULL PRIMARY KEY, " +
                "backpack_data TEXT NOT NULL);"; // Modified to store serialized data

        try (Connection conn = getConnection(); Statement statement = conn.createStatement()) {
            statement.executeUpdate(createBackpackTableQuery);
            LoggingUtil.logMessage("Database", "Table 'backpack_items' has been created or already exists.");
        } catch (SQLException e) {
            LoggingUtil.logMessage("Database", "Failed to create the table 'backpack_items'");
            e.printStackTrace();
        }
    }

    /**
     * Load backpack items for a specific player.
     *
     * @param playerUUID the player's UUID
     * @return the list of items
     */
    public List<ItemStack> loadBackpackItems(UUID playerUUID) {
        List<ItemStack> items = new ArrayList<>();
        String query = "SELECT backpack_data FROM backpack_items WHERE player_uuid = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();

            // Check if the player has no backpack saved
            if (!resultSet.next()) {
                // No backpack found, insert an empty one
                ItemStack[] emptyItems = new ItemStack[54];
                for (int i = 0; i < 54; i++) {
                    emptyItems[i] = new ItemStack(Material.AIR); // Empty item
                }
                saveBackpackItems(playerUUID, emptyItems);
                return items; // Return an empty list as the backpack
            }

            // Deserialize the backpack data
            String serializedData = resultSet.getString("backpack_data");
            ItemStack[] loadedItems = Utils.deserializeItemStackArray(serializedData);

            for (ItemStack item : loadedItems) {
                items.add(item); // Add each item to the list
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Save backpack items for a specific player.
     *
     * @param playerUUID the player's UUID
     * @param items      the items to save
     */
    public void saveBackpackItems(UUID playerUUID, ItemStack[] items) {
        String insertQuery = "REPLACE INTO backpack_items (player_uuid, backpack_data) VALUES (?, ?)"; // REPLACE will update if the row exists

        try (Connection conn = getConnection();
             PreparedStatement insertStatement = conn.prepareStatement(insertQuery)) {

            // Serialize the entire backpack into a single string
            String serializedData = Utils.serializeItemStackArray(items);

            insertStatement.setString(1, playerUUID.toString());
            insertStatement.setString(2, serializedData); // Store serialized data

            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
