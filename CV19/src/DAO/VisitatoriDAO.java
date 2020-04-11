/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import model.Visitatore;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author turco
 */
public class VisitatoriDAO {
     private final String url = "jdbc:postgresql://database-1.cn8hhgibnvsj.eu-central-1.rds.amazonaws.com:5432/postgres";
     private final String user = "cercaviaggi";
     private final String password = "cercaviaggi";
     
     public Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public Visitatore getVisitatoreByNickname(String nickname){
        Visitatore visitatore = new Visitatore();
        String nome, email, dataDiNascita;
        int recensioniApprovate, recensioniRifiutate;
        
        //TODO codice che prende il codice dei visitatori
        
        visitatore.setNickname(nickname);
   /*     visitatore.setEmail(email);
        visitatore.setNome(nome);
        visitatore.setDataDiNascita(dataDiNascita);
        visitatore.setRecensioniApprovate(recensioniApprovate);
        visitatore.setRecensioniRifiutate(recensioniRifiutate);*/
        return visitatore;
    }

}
