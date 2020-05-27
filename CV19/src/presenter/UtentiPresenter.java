/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import DAO.UtenteDAO;
import java.io.IOException;
import model.Utente;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author checc
 */
public class UtentiPresenter implements Initializable {
    
    @FXML
    private TableView VisitatoriTableView;
    
    @FXML
    private TableColumn nomeCol;
    
    @FXML
    private TableColumn nicknameCol;
    
    private final UtenteDAO utenteDAO;
    
    private BorderPane borderPanePadre;
    
    public UtentiPresenter(){
        this.utenteDAO = new UtenteDAO();
    }
    
    @FXML
    private void utenteClick(MouseEvent e) {
        Scene scene = ((Node) e.getSource()).getScene();
        borderPanePadre = (BorderPane)scene.lookup("#borderpane");
        
        if (e.getClickCount() == 2) {
            Utente utente = (Utente) VisitatoriTableView.getSelectionModel().getSelectedItem();
            if(utente!=null) viewUtente(utente);
        }
    }

    
    private void viewUtente(Utente utente) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/visualizzaUtente.fxml"));
            root = loader.load();
            VisualizzaUtentePresenter utenteController=loader.getController();
            utenteController.setUtenteDaMostrare(utente);
            utenteController.riempiCampiDettagliUtente();
                    
        } catch (IOException ex) {
            Logger.getLogger(SideMenuPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPanePadre.setCenter(root);
    }
    
    private void riempiTabellaUtenti(){
        VisitatoriTableView.setItems(utenteDAO.getAllUtente());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {     
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nicknameCol.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        riempiTabellaUtenti();
    }
    
}
