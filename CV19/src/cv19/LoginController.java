/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv19;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author gpepp
 */
public class LoginController {
    
    @FXML
    public void clickLogin(ActionEvent event) throws IOException{
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("SideMenu.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }
}
