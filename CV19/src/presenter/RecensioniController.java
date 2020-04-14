/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import DAO.RecensioneDAO;
import java.io.IOException;
import model.Recensione;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 *
 * @author checc
 */
public class RecensioniController implements Initializable{
    
    @FXML
    private TableView RecensioniTableView;
    
    @FXML
    private TableColumn nomeTable;
    
    @FXML
    private TableColumn strutturaTable;
    
    @FXML
    private TableColumn dataTable;
    
    private final RecensioneDAO recensioneDAO;
    
    public RecensioniController(){
        this.recensioneDAO=new RecensioneDAO();       
    }
    
    private void viewRecensione(){
       Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/VisualizzaRecensione.fxml"));

        } catch (IOException ex) {
            Logger.getLogger(SideMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
       // borderpane.setCenter(root);
    }
    
    public void riempiTableViewConRecensioni(){
        ObservableList<Recensione> allRecensioni=recensioneDAO.getAllRecensioni();
        System.out.println(allRecensioni);
        RecensioniTableView.setItems(allRecensioni);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    nomeTable.setCellValueFactory(
        new PropertyValueFactory<Recensione,String>("autore"));        
    strutturaTable.setCellValueFactory(                
        new PropertyValueFactory<Recensione,String>("struttura"));
    dataTable.setCellValueFactory(
        new PropertyValueFactory<Recensione,String>("data")); 
    
    riempiTableViewConRecensioni();
    }
}
