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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Recensione;

/**
 *
 * @author checc
 */
public final class RecensioneDAO {

    private final String url = "jdbc:postgresql://database-1.cn8hhgibnvsj.eu-central-1.rds.amazonaws.com:5432/postgres";
    private final String user = "cercaviaggi";
    private final String password = "cercaviaggi";
    
    public Connection getConnection() {
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return conn;
    }

    public ObservableList<Recensione> getAllRecensioni() {
        ObservableList<Recensione> allRecensioni = FXCollections.observableArrayList();
        String query = "SELECT R.AUTORE,S.NOME,R.DATARECENSIONE,R.ID_RECENSIONE FROM RECENSIONE R JOIN STRUTTURA S ON R.STRUTTURA = S.ID_Struttura JOIN UTENTE U ON U.NICKNAME=R.AUTORE WHERE STATO_RECENSIONE='In attesa'";
        Connection conn=getConnection();
        PreparedStatement statement=null;
        ResultSet rs=null;
        try {
            statement = conn.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                Recensione recensioneDaAggiungere = new Recensione();
                recensioneDaAggiungere.setAutore(rs.getString(1));
                recensioneDaAggiungere.setStruttura(rs.getString(2));
                recensioneDaAggiungere.setData(rs.getString(3));
                recensioneDaAggiungere.setIdRecensione(rs.getInt(4));
                allRecensioni.add(recensioneDaAggiungere);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try{
            statement.close();
            rs.close();
            conn.close();
        }
        catch(SQLException | NullPointerException e){
            
        }
        return allRecensioni;
    }

    public Recensione getRecensioneById(int id){
        Recensione recensioneDaRitornare=null;
        String query = "SELECT R.AUTORE,U.NOME,S.NOME,R.DATARECENSIONE,R.TESTO,R.TITOLO FROM RECENSIONE R JOIN STRUTTURA S ON R.STRUTTURA = S.ID_Struttura JOIN UTENTE U ON U.NICKNAME=R.AUTORE WHERE R.ID_RECENSIONE=?";
        Connection conn=getConnection();
        PreparedStatement statement=null;
        ResultSet rs=null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1,id);
            rs = statement.executeQuery();
            if (rs.next()) {
                recensioneDaRitornare = new Recensione();
                recensioneDaRitornare.setAutore(rs.getString(1)+ " - " + rs.getString(2));
                recensioneDaRitornare.setStruttura(rs.getString(3));
                recensioneDaRitornare.setData(rs.getString(4));
                recensioneDaRitornare.setTesto(rs.getString(5));
                recensioneDaRitornare.setTitolo(rs.getString(6));
                recensioneDaRitornare.setIdRecensione(id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try{
            statement.close();
            rs.close();
            conn.close();
        }
        catch(SQLException | NullPointerException e){
            
        }
        return recensioneDaRitornare;
    }
    
    private String getStatoByBoolean(boolean accettata){
        if(accettata) return "Accettata";
        else return "Rifiutata";
    }
    
    public void chiudiRecensioneById(int idRecensione, boolean accettata){
        String query="update recensione set stato_recensione=? where id_recensione=?";
        Connection conn=getConnection();
        PreparedStatement statement=null;
        String stato=getStatoByBoolean(accettata);
        try{
            statement=conn.prepareStatement(query);
            statement.setString(1,stato);
            statement.setInt(2,idRecensione);
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        try{
            statement.close();
            conn.close();
        }
        catch(SQLException | NullPointerException e){
            
        }
        
    }
}

   