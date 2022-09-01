package me.albedim.bank.classes;

import me.albedim.bank.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 *  Created by @albedim (Github: github.com/albedim) on 01/09/22
 *  Last Update -
 */

public class Config {

    private Connection connection;
    private String host = Main.getInstance().getConfig().getString("database.db-host");
    private String port = Main.getInstance().getConfig().getString("database.db-port");
    private String database = Main.getInstance().getConfig().getString("database.db-name");
    private String username = Main.getInstance().getConfig().getString("database.db-username");
    private String password = Main.getInstance().getConfig().getString("database.db-password");

    public boolean isConnected() {
        return connection != null;
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
            this.createUsersTable();
        }
    }

    public void disconnect() {
        if (!isConnected()) {
            return;
        }
        try {
            this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    private void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS `bank_accounts` (`IBAN` VARCHAR(255) NOT NULL, `owner` VARCHAR(255) NOT NULL, `balance` double NOT NULL);";
        Statement st = this.connection.createStatement();
        st.execute(sql);
    }

}
