/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv19.controller;

import cv19.model.Visitatore;
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
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ObservableList<Visitatore> data = FXCollections.observableArrayList(
                new Visitatore("Giuseppe", "peppOPazz"),
                new Visitatore("Francesco", "pizzeriamemeriello"),
                new Visitatore("Mario", "marioTurbo"),
                new Visitatore("Giuseppe", "peppOPazz"),
                new Visitatore("Francesco", "pizzeriamemeriello"),
                new Visitatore("Mario", "marioTurbo"),
                new Visitatore("Giuseppe", "peppOPazz"),
                new Visitatore("Francesco", "pizzeriamemeriello"),
                new Visitatore("Mario", "marioTurbo"),
                new Visitatore("Giuseppe", "peppOPazz"),
                new Visitatore("Francesco", "pizzeriamemeriello"),
                new Visitatore("Mario", "marioTurbo")
        );
        
        nomeCol.setCellValueFactory(new PropertyValueFactory<Visitatore,String>("nome"));
        nicknameCol.setCellValueFactory(new PropertyValueFactory<Visitatore,String>("nickname"));
        
        VisitatoriTableView.setItems(data);
    }
    
}
