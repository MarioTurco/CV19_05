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
    private JFXButton visitatoriButton;
    
    @FXML
    private JFXButton logoutButton;

    @FXML
    private ImageView recensioniImageView;
    
    @FXML
    private ImageView visitatoriImageView;
    
    @FXML
    private ImageView logoutImageView;

    @FXML
    public void recensioniClick(MouseEvent e) {
        setButtonColor(recensioniButton, "recensioni", "blue");
        setButtonColor(visitatoriButton, "visitatori", "gray");
        setButtonColor(logoutButton, "logout", "gray");
        recensioniButton.setStyle("-fx-text-fill: #3282B8");
        loadUI("Recensioni");
    }
    
    @FXML
    public void visitatoriClick(MouseEvent e) {
        setButtonColor(recensioniButton, "recensioni", "gray");
        setButtonColor(visitatoriButton, "visitatori", "blue");
        setButtonColor(logoutButton, "logout", "gray");
        loadUI("Visitatori");
    }

    @FXML
    public void logoutClick(MouseEvent e) {
        setButtonColor(recensioniButton, "recensioni", "gray");
        setButtonColor(visitatoriButton, "visitatori", "gray");
        setButtonColor(logoutButton, "logout", "blue");
        loadUI("logoutDialog");
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
    
    private void setButtonColor(JFXButton button, String nomeIcona, String colore){
        Image image = new Image(getClass().getResourceAsStream("/icons/" + nomeIcona + "-" + colore + ".png"));
        //button.setGraphic(new ImageView(image));
        switch(nomeIcona){
            case "recensioni":
                recensioniImageView.setImage(image);
                break;
            case "visitatori":
                visitatoriImageView.setImage(image);
                break;
            case "logout":
                logoutImageView.setImage(image);
                break;
        }
    }
    
    
}


