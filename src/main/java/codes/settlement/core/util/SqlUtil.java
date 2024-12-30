package codes.settlement.core.util;

import codes.settlement.core.Core;
import codes.settlement.core.rank.Rank;
import codes.settlement.core.util.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class SqlUtil {

    // Driver
    Config config = Core.getInstance().config;
    private String host = config.getString("database.host");
    private Integer port = config.getInt("database.port");
    private String database = config.getString("database.database");
    private String username = config.getString("database.username");
    private String password = config.getString("database.password");

    private Connection connection;

    // Driver
    public void connect() {
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
            try {
                if (!isConnected()) {
                    connection = DriverManager.getConnection(
                            "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true", username, password);
                    LoggingUtil.logMessage("Database", "&aSuccessfully connected to the database!");

                    // Create tables if they don't already exist
                    createUserTable();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                LoggingUtil.logMessage("Database", "&cFailed to connect to the database!");
            }
        });
    }

    public void disconnect() {
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
            if (isConnected()) {
                try {
                    connection.close();
                    LoggingUtil.logMessage("Database", "&aSuccessfully disconnected from the database!");
                } catch (SQLException e) {
                    e.printStackTrace();
                    LoggingUtil.logMessage("Database", "&cFailed to disconnect from the database!");
                }
            }
        });
    }

    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public Connection getConnection() {
        return connection;
    }

    // Getter
    public void createUserTable() {
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
            PreparedStatement statement;
            try {
                statement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS profile "
                        + "(USERNAME VARCHAR(100),UUID VARCHAR(100),RANK VARCHAR(100),GEMS INT(100),ONLINE BOOLEAN,PRIMARY KEY (UUID))");
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void createPlayer(Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
            try {
                UUID uuid = p.getUniqueId();
                if (!doesExist(uuid)) {
                    PreparedStatement statement2 = getConnection()
                            .prepareStatement("INSERT IGNORE profile (UUID,USERNAME,RANK,GEMS,ONLINE) VALUES (?,?,?,?,?)");
                    statement2.setString(1, uuid.toString());
                    statement2.setString(2, p.getName());
                    statement2.setString(3, "DEFAULT");
                    statement2.setInt(4, 0);
                    statement2.setBoolean(5, true);
                    statement2.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean doesExist(UUID uuid) {
        final boolean[] exists = {false};
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
            try {
                PreparedStatement statement = getConnection()
                        .prepareStatement("SELECT * FROM profile WHERE UUID=?");
                statement.setString(1, uuid.toString());
                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    exists[0] = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return exists[0];
    }

    public void setUserRank(UUID uuid, Rank rank) {
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
            try {
                PreparedStatement statement = getConnection()
                        .prepareStatement("UPDATE profile SET RANK=? WHERE UUID=?");
                statement.setString(1, rank.name());
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public Rank getUserRank(UUID uuid) {
        final Rank[] rank = {Rank.DEFAULT};
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
            try {
                PreparedStatement statement = getConnection()
                        .prepareStatement("SELECT RANK FROM profile WHERE UUID=?");
                statement.setString(1, uuid.toString());
                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    String rankString = results.getString("RANK");
                    rank[0] = Rank.valueOf(rankString);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return rank[0];
    }

    public Integer getUserGems(UUID uuid) {
        final Integer[] gems = {0};
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
            try {
                PreparedStatement statement = getConnection()
                        .prepareStatement("SELECT GEMS FROM profile WHERE UUID=?");
                statement.setString(1, uuid.toString());
                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    gems[0] = results.getInt("GEMS");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return gems[0];
    }

    public Boolean isUserOnline(UUID uuid) {
        final Boolean[] online = {false};
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
            try {
                PreparedStatement statement = getConnection()
                        .prepareStatement("SELECT ONLINE FROM profile WHERE UUID=?");
                statement.setString(1, uuid.toString());
                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    online[0] = results.getBoolean("ONLINE");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return online[0];
    }

}
