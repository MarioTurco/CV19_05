/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

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
            System.out.println("Connesso");
        } catch (SQLException e) {
            System.out.println(e.toString());
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
                utente.setDataDiNascita(resultSet.getString("dataDiNascita"));
                utente.setRecensioniApprovate(resultSet.getInt("recensioniApprovate"));
                utente.setRecensioniRifiutate(resultSet.getInt("recensioniRifiutate"));
                allUtenti.add(utente);
            }
            close(statement, resultSet, conn);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return allUtenti;
    }

    public void deleteVisitatoreByNickname(String nickname) {
        PreparedStatement statement = prepareDeleteQueryWithNickname(nickname);
        executeStatement(statement);

    }

    public Utente getVisitatoreByNickname(String nickname) {
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
            System.out.println(e.toString());
        }
        return utente;
    }   

    private PreparedStatement prepareDeleteQueryWithNickname(String nickname) {
        PreparedStatement statement = null;
        Connection con = null;
        String query = "DELETE FROM utente WHERE nickname = ?";
        try {
            con = getConnection();
            statement = con.prepareStatement(query);
            statement.setString(1, nickname);
        } catch (SQLException sqlException) {
            //TODO implementare qualcosa qui
        }

        return statement;
    }

    private ResultSet executeStatement(PreparedStatement statement) {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
        } catch (SQLException sqlException) {
            //TODO implementare exception
        }
        return resultSet;
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
