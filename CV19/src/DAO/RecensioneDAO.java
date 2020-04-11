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
import model.Recensione;

/**
 *
 * @author checc
 */
public final class RecensioneDAO {

    private final String url = "jdbc:postgresql://database-1.cn8hhgibnvsj.eu-central-1.rds.amazonaws.com:5432/postgres";
    private final String user = "admin_cv19";
    private final String password = "cvuser";
    
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

    public ArrayList<Recensione> getAllRecensioni() {
        ArrayList<Recensione> allRecensioni = new ArrayList<Recensione>();
        String query = "SELECT * FROM RECENSIONE";
        Connection conn=getConnection();
        PreparedStatement statement=null;
        ResultSet rs=null;
        try {
            statement = conn.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                //todo codice che inserisce le recensioni nell'arrayList
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

}
