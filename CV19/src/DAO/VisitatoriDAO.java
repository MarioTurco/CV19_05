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

/**
 *
 * @author turco
 */
public class VisitatoriDAO {

    private final String url = "jdbc:postgresql://database-1.cn8hhgibnvsj.eu-central-1.rds.amazonaws.com:5432/postgres";
    private final String user = "cercaviaggi";
    private final String password = "cercaviaggi";
    
    public VisitatoriDAO(){
        
    }
    private Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connesso");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public Visitatore getVisitatoreByNickname(String nickname) {
        PreparedStatement statement = prepareQuerySelectByNickname(nickname);
        ResultSet resultSet = executeStatement(statement);
        Visitatore visitatore = getVisitatoreFromResultSet(resultSet);
        return visitatore;
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

    private Visitatore getVisitatoreFromResultSet(ResultSet resultSet) {
        Visitatore visitatore = null;
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
            }
        } catch (SQLException sqlException) {
            //TODO implementare qualcosa
        }
        return new Visitatore(nome, nickname, email, dataDiNascita, recensioniApprovate, recensioniRifiutate);
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
}
