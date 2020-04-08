package cv19;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SideMenuController {

    @FXML
    private JFXButton recensioniButton;
    
    @FXML
    private BorderPane borderpane;


    @FXML
    public void recensioniClick(MouseEvent e) {
        setButtonBlueColor(recensioniButton);
        loadUI("Recensioni");
    }
    
    @FXML
    public void visitatoriClick(MouseEvent e) {
        loadUI("Visitatori");
    }

    @FXML
    public void logoutClick(MouseEvent e) {
        loadUI("Logout");
    }


    @FXML
    public void close(MouseEvent event) {
        Stage stage = (Stage) borderpane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void clear(MouseEvent event) {
        borderpane.setCenter(null);
    }

    public void loadUI(String ui) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(ui + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(SideMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderpane.setCenter(root);
    }
    
    private void setButtonBlueColor(JFXButton button){
        Image image = new Image(getClass().getResourceAsStream("/icons/Icon material-rate-review-blue.png"));
        button.setGraphic(new ImageView(image));
    }
}


