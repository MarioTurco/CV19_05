package cv19;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.stage.Screen;
import javafx.stage.StageStyle;

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
        visitatoriButton.setStyle("-fx-text-fill: black");
        logoutButton.setStyle("-fx-text-fill: black");
        loadUI("Recensioni");
    }

    @FXML
    public void visitatoriClick(MouseEvent e) {
        setButtonColor(recensioniButton, "recensioni", "gray");
        setButtonColor(visitatoriButton, "visitatori", "blue");
        setButtonColor(logoutButton, "logout", "gray");
        recensioniButton.setStyle("-fx-text-fill: black");
        visitatoriButton.setStyle("-fx-text-fill: #3282B8");
        logoutButton.setStyle("-fx-text-fill: black");
        loadUI("Visitatori");
    }

    @FXML
    public void logoutClick(MouseEvent e) {
        setButtonColor(recensioniButton, "recensioni", "gray");
        setButtonColor(visitatoriButton, "visitatori", "gray");
        setButtonColor(logoutButton, "logout", "blue");
        recensioniButton.setStyle("-fx-text-fill: black");
        visitatoriButton.setStyle("-fx-text-fill: black");
        logoutButton.setStyle("-fx-text-fill: #3282B8");
        //loadUI("logoutDialog");
        showLogoutDialog(e);

    }

    @FXML
    public void close(MouseEvent event) {
        Stage stage = (Stage) borderpane.getScene().getWindow();
        stage.close();
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

    private void setButtonColor(JFXButton button, String nomeIcona, String colore) {
        Image image = new Image(getClass().getResourceAsStream("/icons/" + nomeIcona + "-" + colore + ".png"));
        //button.setGraphic(new ImageView(image));
        switch (nomeIcona) {
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

       
    ///////////////////////////////////////////////////////////////////////////////////
    private void showLogoutDialog(InputEvent event) {
        Alert dg = new Alert(Alert.AlertType.INFORMATION);
        dg.setHeaderText("Azione Eseguita");
        dg.setContentText("Logout effettuato.");
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        dg.setX((bounds.getMaxX()/2)-150);
        dg.setY((bounds.getMaxY()/2)-100);
        
        DialogPane dialogPane = dg.getDialogPane();
        dialogPane.getStylesheets().add(
        getClass().getResource("dialogStyle.css").toExternalForm());
        dg.initStyle(StageStyle.UNDECORATED);
        dg.getDialogPane().setGraphic(null);
        
        
        Optional<ButtonType> result = dg.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);
        
        if (button == ButtonType.OK) {
            loadLoginView(event);
        } else {
            System.out.println("canceled");
        }
    }

    private void loadLoginView(InputEvent event) {
        try {
            Parent homePageParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(homePageParent);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.hide(); //optional
            appStage.setScene(scene);
            appStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
