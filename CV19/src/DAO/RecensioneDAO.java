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
import java.util.ArrayList;
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
                System.out.println("Connected to the PostgreSQL server successfully.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return conn;
    }

    public ObservableList<Recensione> getAllRecensioni() {
        ObservableList<Recensione> allRecensioni = FXCollections.observableArrayList();
        String query = "SELECT * FROM RECENSIONE WHERE STATO='In Approvazione'";
        Connection conn=getConnection();
        PreparedStatement statement=null;
        ResultSet rs=null;
        try {
            statement = conn.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                Recensione recensioneDaAggiungere=new Recensione(rs.getString(1),rs.getString(4),rs.getString(2),rs.getInt(5));
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
        System.out.println(allRecensioni);
        return allRecensioni;
    }

}
