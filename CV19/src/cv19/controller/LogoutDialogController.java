/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv19.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author turco
 */
public class LogoutDialogController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void clickOk(ActionEvent event) throws IOException{
       
        Parent homePageParent = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.hide(); //optional
        appStage.setScene(scene);
        appStage.show();
        loadLoginView(scene);
    }

    private void loadLoginView(Scene scene) {
        Parent root = null;
        BorderPane borderpane = (BorderPane)scene.lookup("#borderpane");
        try {
           root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        } catch (IOException ex) {
        }
    }

    
    
    
    
    
}
