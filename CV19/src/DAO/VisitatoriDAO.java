/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import model.Visitatore;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author turco
 */
public class VisitatoriDAO {

    private final String url = "jdbc:postgresql://database-1.cn8hhgibnvsj.eu-central-1.rds.amazonaws.com:5432/postgres";
    private final String user = "cercaviaggi";
    private final String password = "cercaviaggi";
    private Connection conn = null;
   
    private Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connesso");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public ArrayList<Visitatore> getAllVisitatori(){
        PreparedStatement statement = prepareQuery();
        ResultSet resultSet = executeStatement(statement);
        ArrayList<Visitatore> visitatori =  getVisitatoriFromResultSet(resultSet);
        chiudiConnessione();
        return visitatori;
    }
    
    public Visitatore getVisitatoreByNickname(String nickname) {
        PreparedStatement statement = prepareQuerySelectByNickname(nickname);
        ResultSet resultSet = executeStatement(statement);
        ArrayList<Visitatore> visitatori = getVisitatoriFromResultSet(resultSet);
        chiudiConnessione();
        return visitatori.get(0);
    }
    private PreparedStatement prepareQuery(){
        PreparedStatement statement = null;
        String query = "SELECT * FROM public.visitatori ";
        try {
            statement = getConnection().prepareStatement(query);
        } catch (SQLException sqlException) {        
        }
        return statement;
    }
    
    private PreparedStatement prepareQuerySelectByNickname(String nickname) {
        PreparedStatement statement = null;
        String query = "SELECT * FROM public.visitatori WHERE nickname = ?";
        try {
            statement = getConnection().prepareStatement(query);
            statement.setString(1, nickname);
        } catch (SQLException sqlException) {
            //TODO implementare qualcosa qui
        }
        return statement;
    }
    
    private ArrayList<Visitatore> getVisitatoriFromResultSet(ResultSet resultSet) {
        ArrayList<Visitatore> visitatori = new <Visitatore>ArrayList();
        String nome = null, email = null, nickname = null, dataDiNascita = null;
        int recensioniApprovate = 0, recensioniRifiutate = 0;
        try {
            while (resultSet.next()) {
                nome = resultSet.getString("nome");
                email = resultSet.getString("email");
                nickname = resultSet.getString("nickname");
                dataDiNascita = resultSet.getString("dataDiNascita");
                recensioniApprovate = resultSet.getInt("recensioniApprovate");
                recensioniRifiutate = resultSet.getInt("recensioniRifiutate");
                visitatori.add( new Visitatore(nome, nickname, email, dataDiNascita, recensioniApprovate, recensioniRifiutate) );
            }
        } catch (SQLException sqlException) {
            //TODO implementare qualcosa
        }
        return visitatori;
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

    private void chiudiConnessione() {
        try{
            conn.close();
        }catch(SQLException sql){
             
        }
    }
}
