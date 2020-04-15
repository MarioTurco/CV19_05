/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import DAO.UtenteDAO;
import model.Utente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author checc
 */
public class VisitatoriController implements Initializable {
    
    @FXML
    private TableView VisitatoriTableView;
    
    @FXML
    private TableColumn nomeCol;
    
    @FXML
    private TableColumn nicknameCol;
    
    private UtenteDAO utenteDAO;
    
    public VisitatoriController(){
        this.utenteDAO = new UtenteDAO();
    }
    
    private void riempiTabellaUtenti(){
        VisitatoriTableView.setItems(utenteDAO.gettAllUtente());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {     
        nomeCol.setCellValueFactory(new PropertyValueFactory<Utente,String>("nome"));
        nicknameCol.setCellValueFactory(new PropertyValueFactory<Utente,String>("nickname"));
        riempiTabellaUtenti();
        //VisitatoriTableView.setItems(data);
    }
    
}
