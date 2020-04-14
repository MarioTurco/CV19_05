/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import DAO.RecensioneDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Recensione;

/**
 *
 * @author checc
 */
public class VisualizzaRecensioneController {
    
    @FXML
    private Label nickNameLabel;
    
    @FXML
    private Label strutturaLabel;
    
    @FXML
    private Label dataLabel;
    
    @FXML
    private Label testoLabel;
    
    
    private int IdrecensioneDaMostrare;
    private final RecensioneDAO recensioneDAO;

    public VisualizzaRecensioneController(){
        this.recensioneDAO = new RecensioneDAO();
    }
    
    public void setIdRecensioneDaMostrare(int recensioneDaMostrare) {
        this.IdrecensioneDaMostrare = recensioneDaMostrare;
    }
    
    public void riempiCampiDettagliRecensione(){
        Recensione recensioneDaMostrare=recensioneDAO.getRecensioneById(IdrecensioneDaMostrare);
        nickNameLabel.setText(recensioneDaMostrare.getAutore());
        strutturaLabel.setText(recensioneDaMostrare.getStruttura());
        dataLabel.setText(recensioneDaMostrare.getData());
        testoLabel.setText(recensioneDaMostrare.getTitolo()+ "\n\n" + recensioneDaMostrare.getTesto());              
    }
    
}
