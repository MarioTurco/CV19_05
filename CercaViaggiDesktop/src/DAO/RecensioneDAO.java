/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Recensione;
import model.Struttura;
import model.Utente;

/**
 *
 * @author checc
 */
public final class RecensioneDAO {

    private final String url = "jdbc:postgresql://database-1.cn8hhgibnvsj.eu-central-1.rds.amazonaws.com:5432/postgres";
    private final String user = "cercaviaggi";
    private final String password = "cercaviaggi";

    private Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            Logger.getLogger(RecensioneDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return conn;
    }

    public ObservableList<Recensione> getAllRecensioni() {
        ObservableList<Recensione> allRecensioni = FXCollections.observableArrayList();
        String query = "SELECT R.AUTORE,S.NOME,R.DATARECENSIONE,R.ID_RECENSIONE,U.NOME,R.TESTO,R.TITOLO,R.VALUTAZIONE,S.INDIRIZZO,S.CITTA FROM RECENSIONE R JOIN STRUTTURA S ON R.STRUTTURA = S.ID_Struttura JOIN UTENTE U ON U.NICKNAME=R.AUTORE WHERE STATO_RECENSIONE='In attesa'";
        Connection conn = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                Recensione recensioneDaAggiungere = new Recensione();
                Utente utenteRecensione = new Utente();
                Struttura strutturaRecensione = new Struttura(rs.getString(2),rs.getString(9),rs.getString(10));
                utenteRecensione.setNickname(rs.getString(1));
                utenteRecensione.setNome(rs.getString(5));
                recensioneDaAggiungere.setStruttura(strutturaRecensione);
                recensioneDaAggiungere.setData(rs.getString(3));
                recensioneDaAggiungere.setIdRecensione(rs.getInt(4));
                recensioneDaAggiungere.setAutore(utenteRecensione);
                recensioneDaAggiungere.setTesto(rs.getString(6));
                recensioneDaAggiungere.setTitolo(rs.getString(7));
                recensioneDaAggiungere.setValutazione(rs.getInt(8));
                allRecensioni.add(recensioneDaAggiungere);
            }
        } catch (SQLException e) {
            Logger.getLogger(RecensioneDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            statement.close();
            rs.close();
            conn.close();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(RecensioneDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return allRecensioni;
    }

    private String getStatoByBoolean(boolean accettata) {
        if (accettata) {
            return "Approvata";
        } else {
            return "Rifiutata";
        }
    }

    public void chiudiRecensioneById(int idRecensione, boolean accettata) {
        String query = "update recensione set stato_recensione=? where id_recensione=?";
        Connection conn = getConnection();
        PreparedStatement statement = null;
        String stato = getStatoByBoolean(accettata);
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, stato);
            statement.setInt(2, idRecensione);
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RecensioneDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            statement.close();
            conn.close();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(RecensioneDAO.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
