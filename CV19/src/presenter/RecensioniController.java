/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import java.io.IOException;
import model.Recensione;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.value.ChangeListener;
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
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ObservableList<Recensione> data = FXCollections.observableArrayList(
                
        );
        
        nomeTable.setCellValueFactory(new PropertyValueFactory<Recensione,String>("nome"));
        strutturaTable.setCellValueFactory(new PropertyValueFactory<Recensione,String>("struttura"));
        dataTable.setCellValueFactory(new PropertyValueFactory<Recensione,String>("data"));
        
        RecensioniTableView.setItems(data);
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
}
