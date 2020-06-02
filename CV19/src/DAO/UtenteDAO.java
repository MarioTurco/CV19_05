/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import cv19.PasswordUtils;
import model.Utente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author turco
 */
public class UtenteDAO {

    private final String url = "jdbc:postgresql://database-1.cn8hhgibnvsj.eu-central-1.rds.amazonaws.com:5432/postgres";
    private final String user = "cercaviaggi";
    private final String password = "cercaviaggi";

    private Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            Logger.getLogger(UtenteDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return conn;
    }

    public ObservableList<Utente> getAllUtente() {
        ObservableList<Utente> allUtenti = FXCollections.observableArrayList();
        String query = "SELECT * FROM utente";
        Connection conn = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Utente utente = new Utente();
                utente.setNome(resultSet.getString("nome"));
                utente.setEmail(resultSet.getString("email"));
                utente.setNickname(resultSet.getString("nickname"));
                utente.setDataDiNascita(resultSet.getString("data_di_nascita"));
                utente.setRecensioniApprovate(resultSet.getInt("recensioniApprovate"));
                utente.setRecensioniRifiutate(resultSet.getInt("recensioniRifiutate"));
                allUtenti.add(utente);
            }
            close(statement, resultSet, conn);
        } catch (SQLException e) {
            Logger.getLogger(UtenteDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return allUtenti;
    }

    public void modifyUtente(String vecchioNickname, String nuovoNickname, String nuovaPassword) throws SQLException {
        if (!nuovaPassword.equals("")) {
            modifyPassword(vecchioNickname, nuovaPassword);
        }
        if (!nuovoNickname.equals("")) {
            modifyNickname(vecchioNickname, nuovoNickname);
        }
    }

    private void modifyPassword(String nickname, String password) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement statement = null;
        String salt = PasswordUtils.getSalt(30);
        String passwordCriptata = PasswordUtils.generateSecurePassword(password, salt);
        String query = "UPDATE utente SET password=?, salt=? WHERE nickname=?";
        statement = conn.prepareStatement(query);
        statement.setString(1, passwordCriptata);
        statement.setString(2, salt);
        statement.setString(3, nickname);
        statement.executeUpdate();

    }

    private void modifyNickname(String vecchioNickname, String nuovoNickname) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement statement = null;
        String query = "UPDATE utente SET nickname=? WHERE nickname=?";
        statement = conn.prepareStatement(query);
        statement.setString(1, nuovoNickname);
        statement.setString(2, vecchioNickname);
        statement.executeUpdate();
    }

    public void deleteUtenteByNickname(String nickname) {
        String query = "DELETE FROM utente WHERE nickname = ?";
        PreparedStatement statement = null;
        try {
            Connection con = getConnection();
            statement = con.prepareStatement(query);
            statement.setString(1, nickname);
            statement.executeUpdate();
        } catch (SQLException sql) {
            Logger.getLogger(UtenteDAO.class.getName()).log(Level.SEVERE, null, sql);
        }
    }

    public Utente getUtenteByNickname(String nickname) {
        Utente utente = new Utente();
        String query = "SELECT * FROM utente WHERE nickname=?";
        Connection conn = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, nickname);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                utente.setNome(resultSet.getString("nome"));
                utente.setEmail(resultSet.getString("email"));
                utente.setNickname(resultSet.getString("nickname"));
                utente.setDataDiNascita(resultSet.getString("dataDiNascita"));
                utente.setRecensioniApprovate(resultSet.getInt("recensioniApprovate"));
                utente.setRecensioniRifiutate(resultSet.getInt("recensioniRifiutate"));
            }
            close(statement, resultSet, conn);
        } catch (SQLException e) {
            Logger.getLogger(UtenteDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return utente;
    }

    private void close(PreparedStatement statement, ResultSet resultSet, Connection conn) {

        try {
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
