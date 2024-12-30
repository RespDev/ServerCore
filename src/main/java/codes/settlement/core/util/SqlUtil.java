package codes.settlement.core.util;

import codes.settlement.core.Core;
import codes.settlement.core.util.config.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlUtil {

    // Driver
    Config config = Core.getInstance().config;
    private String host = config.getString("database.host");
    private Integer port = config.getInt("database.port");
    private String database = config.getString("database.database");
    private String username = config.getString("database.username");
    private String password = config.getString("database.password");

    private Connection connection;

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true", username, password);
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
                LoggingUtil.logMessage("Database", "&aSUCCESS &fnow disconnected from the database!");
            } catch (SQLException e) {
                e.printStackTrace();
                LoggingUtil.logMessage("Database", "&cFAILED &fto disconnect from the database!");
            }
        }
    }

    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public Connection getConnection() {
        return connection;
    }

    // Getter
}
