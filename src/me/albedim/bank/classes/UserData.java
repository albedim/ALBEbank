package me.albedim.bank.classes;

import java.sql.*;

import static me.albedim.bank.Main.config;

/*
 *  Created by @albedim (Github: github.com/albedim) on 29/08/22
 *  Last Update 01/09/22
 */

public class UserData {

    public void addAccount(String player, double defaultBalance) {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("INSERT INTO bank_accounts VALUES(?,?,?)");
            stmt.setString(1, this.createIban());
            stmt.setString(2, player);
            stmt.setDouble(3, defaultBalance);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasAccount(String player) {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM bank_accounts WHERE owner = ?");
            stmt.setString(1, player);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getIban(String player) {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM bank_accounts WHERE owner = ?");
            stmt.setString(1, player);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return rs.getString("IBAN");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void takeMoney(String iban, int money) {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("UPDATE bank_accounts SET balance = ? WHERE IBAN = ?");
            stmt.setDouble(1, this.getBalance(iban) - money);
            stmt.setString(2, iban);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addMoney(String iban, int money) {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("UPDATE bank_accounts SET balance = ? WHERE IBAN = ?");
            stmt.setDouble(1, this.getBalance(iban) + money);
            stmt.setString(2, iban);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean hasMoney(String iban, int money) {
        return this.getBalance(iban) - money >= 0;
    }

    public double getBalance(String iban) {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM bank_accounts WHERE IBAN = ?");
            stmt.setString(1, iban);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return rs.getDouble("balance");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private String createIban() {
        String letters = "ABCDEFGHILMNOPQRSTUVZWYJKX";
        String numbers = "0123456789";
        String iban = "";

        do {
            for (int x = 0; x < 8; x++) {
                if (x % 2 != 0)
                    iban += letters.charAt((int) (Math.random() * (0 + 25)));
                else
                    iban += numbers.charAt((int) (Math.random() * (0 + 9)));
            }
        } while (this.ibanExists(iban));

        return iban;
    }

    public String getUserByIban(String iban) {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM bank_accounts WHERE IBAN = ?");
            stmt.setString(1, iban);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return rs.getString("owner");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean ibanExists(String iban) {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM bank_accounts WHERE IBAN = ?");
            stmt.setString(1, iban);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
