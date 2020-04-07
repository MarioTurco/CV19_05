/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv19;

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
public class RecensioniView implements Initializable{
    
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
                new Recensione("Giuseppe", "Struttura1", "tanto tempo fa"),
                new Recensione("Francesco", "Struttura1", "tanto tempo fa"),
                new Recensione("Mario", "Struttura2", "tanto tempo fa")
        );
        
        nomeTable.setCellValueFactory(new PropertyValueFactory<Recensione,String>("nome"));
        strutturaTable.setCellValueFactory(new PropertyValueFactory<Recensione,String>("struttura"));
        dataTable.setCellValueFactory(new PropertyValueFactory<Recensione,String>("data"));
        
        RecensioniTableView.setItems(data);
    }
    
}
