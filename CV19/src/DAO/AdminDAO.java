/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import cv19.AmministratoreLoggatoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cv19.PasswordUtils;

/**
 *
 * @author gpepp
 */
public class AdminDAO {

    private final String url = "jdbc:postgresql://database-1.cn8hhgibnvsj.eu-central-1.rds.amazonaws.com:5432/postgres";
    private final String user = "cercaviaggi";
    private final String password = "cercaviaggi";

    private Connection getConnection() {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dbConnection;
    }

    private boolean emptyResultSet(ResultSet rs) throws SQLException {
        return rs.next() == false;
    }

    private boolean checkAdminPassword(ResultSet usernameQueryResultSet, String passwordEntered) throws SQLException {
        String password = usernameQueryResultSet.getString("PASSWORD");
        String salt = usernameQueryResultSet.getString("SALT");

        return PasswordUtils.verifyUserPassword(passwordEntered, password, salt);
    }

    public boolean tryLogin(String username, String password) throws AmministratoreLoggatoException {
        Connection dbConnection = getConnection();
        String loginQuery = "SELECT * FROM ADMINISTRATOR WHERE USERNAME = ?";
        ResultSet rs = null;
        boolean result = false;

        try {
            PreparedStatement loginPreparedStatement = dbConnection.prepareStatement(loginQuery);
            loginPreparedStatement.setString(1, username);
            rs = loginPreparedStatement.executeQuery();

            if (!emptyResultSet(rs)) {
                result = checkAdminPassword(rs, password);
            } else {
                result = false;
            }

            dbConnection.close();
            loginPreparedStatement.close();
            rs.close();

        } catch (NullPointerException np) {
            //todo: dialog connessione non avvenente
        } catch (SQLException e) {
            //todo: gestione errori sql
        }
        return result;
    }
}
