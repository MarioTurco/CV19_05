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
public class RecensioniController implements Initializable {

    @FXML
    private TableView RecensioniTableView;

    @FXML
    private TableColumn nomeTable;

    @FXML
    private TableColumn strutturaTable;

    @FXML
    private TableColumn dataTable;

    private final RecensioneDAO recensioneDAO;
    
    private BorderPane borderPanePadre;


    public RecensioniController() {
        this.recensioneDAO = new RecensioneDAO();
    }

    @FXML
    private void recensioneClick(MouseEvent e) {
        Scene scene = ((Node) e.getSource()).getScene();
        borderPanePadre = (BorderPane)scene.lookup("#borderpane");
        
        if (e.getClickCount() == 2) {
            Recensione recensione = (Recensione) RecensioniTableView.getSelectionModel().getSelectedItem();
            if(recensione!=null) viewRecensione(recensione);
        }
    }

    private void viewRecensione(Recensione recensione) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VisualizzaRecensione.fxml"));
            root = loader.load();
            VisualizzaRecensioneController recensioneController=loader.getController();
            recensioneController.setIdRecensioneDaMostrare(recensione.getIdRecensione());
            recensioneController.riempiCampiDettagliRecensione();
                    
        } catch (IOException ex) {
            Logger.getLogger(SideMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPanePadre.setCenter(root);
    }

    private void riempiTableViewConRecensioni() {
        RecensioniTableView.setItems(recensioneDAO.getAllRecensioni());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomeTable.setCellValueFactory(
                new PropertyValueFactory<Recensione, String>("autore"));
        strutturaTable.setCellValueFactory(
                new PropertyValueFactory<Recensione, String>("struttura"));
        dataTable.setCellValueFactory(
                new PropertyValueFactory<Recensione, String>("data"));

        riempiTableViewConRecensioni();
    }

}
