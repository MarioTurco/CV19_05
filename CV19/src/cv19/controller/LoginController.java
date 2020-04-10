/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv19.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author gpepp
 */
public class LoginController {
    
    @FXML
    public void clickLogin(ActionEvent event) throws IOException{
        Parent homePageParent = FXMLLoader.load(getClass().getResource("/fxml/SideMenu.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.hide(); //optional
        appStage.setScene(homePageScene);
        appStage.show();
        loadRecensioniView(homePageScene);
        initRecensioniButton(homePageScene);
    }
    
    private void loadRecensioniView(Scene homePageScene){
        Parent root = null;
        BorderPane borderpane = (BorderPane)homePageScene.lookup("#borderpane");
        try {
           root = FXMLLoader.load(getClass().getResource("/fxml/Recensioni.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(SideMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderpane.setCenter(root);
    }
    
    private void initRecensioniButton(Scene homePageScene){
        JFXButton recensioniButton = (JFXButton)homePageScene.lookup("#recensioniButton");
        ImageView recensioniImageView = (ImageView)homePageScene.lookup("#recensioniImageView");
        
        setBlueIcon(recensioniImageView);
        
        changeRecensioniButtonTextColourToBlue(recensioniButton);
        
    }
    
    private void setBlueIcon(ImageView recensioniImageView){
        Image image = new Image(getClass().getResourceAsStream("/icons/recensioni-blue.png"));
        recensioniImageView.setImage(image);
    }
    
    private void changeRecensioniButtonTextColourToBlue(JFXButton recensioniButton){
        recensioniButton.setStyle("-fx-text-fill: #3282B8");
    }
}
