/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import DAO.UtenteDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Utente;

/**
 *
 * @author checc
 */
public class VisualizzaUtenteController {
    @FXML
    private Label nomeLabel;
    
    @FXML
    private Label nickNameLabel;
    
    @FXML
    private Label dataLabel;
    
    @FXML
    private Label emailLabel;
    
    @FXML
    private Label recensioniRifiutateLabel;
    
    @FXML
    private Label recensioniApprovateLabel;
    
    private final UtenteDAO utenteDAO;
    
    private Utente utenteDaMostrare;
    
    public VisualizzaUtenteController(){
        this.utenteDAO = new UtenteDAO();
    }
    
    public void setUtenteDaMostrare(Utente utente){
        this.utenteDaMostrare = utente;
    }
    
    public void riempiCampiDettagliUtente(){
        nickNameLabel.setText(utenteDaMostrare.getNickname());
        nomeLabel.setText(utenteDaMostrare.getNome());
        dataLabel.setText(utenteDaMostrare.getDataDiNascita());
        emailLabel.setText(utenteDaMostrare.getEmail()); 
        recensioniRifiutateLabel.setText(String.valueOf(utenteDaMostrare.getRecensioniRifiutate()));
        recensioniApprovateLabel.setText(String.valueOf(utenteDaMostrare.getRecensioniApprovate()));
    }
    
}
