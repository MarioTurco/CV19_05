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

/**
 *
 * @author gpepp
 */
public class AdminDAO {
    private final String url = "jdbc:postgresql://database-1.cn8hhgibnvsj.eu-central-1.rds.amazonaws.com:5432/postgres";
    private final String user = "admin_cv19";
    private final String password = "cvuser";
    
    //private Connection dbConnection;
    
    /*
    public AdminDAO(){
        try {
            dbConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/
    
    public Connection getConnection(){
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dbConnection;
    }
    
    private boolean emptyResultSet(ResultSet rs) throws SQLException{
        return rs.next() == false;
    }
    
    
    public boolean tryLogin(String username, String password){
        Connection dbConnection = getConnection();
        String loginQuery = "SELECT * FROM ADMINISTRATOR WHERE USERNAME = ? AND PASSWORD = ?";
        ResultSet rs = null;
        boolean result = false;
        
        try{
            PreparedStatement loginPreparedStatement = dbConnection.prepareStatement(loginQuery);
            loginPreparedStatement.setString(1, username);
            loginPreparedStatement.setString(2, password);
            
            rs = loginPreparedStatement.executeQuery();
            
            result = !emptyResultSet(rs);
            System.out.println(!result);
            
            loginPreparedStatement.close();
            rs.close();
            dbConnection.close();
           
        }
        catch(NullPointerException np){
            np.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
        } 
        return result;
    }
}
