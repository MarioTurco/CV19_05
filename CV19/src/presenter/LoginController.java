/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import DAO.AdminDAO;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Screen;
import javafx.stage.StageStyle;

/**
 *
 * @author gpepp
 */
public class LoginController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    private AdminDAO administratorDao;

    
    
    public LoginController() {
        initAdminDAO();
    }

    private void initAdminDAO() {
        administratorDao = new AdminDAO();
    }

    
    
    
    private Parent getParent() throws IOException {
        return FXMLLoader.load(getClass().getResource("/view/SideMenu.fxml"));
    }

    private Scene getScene(Parent root) {
        return new Scene(root);
    }

    private void showStage(Stage appStage, Scene sceneToSet) {
        appStage.hide(); //optional
        appStage.setScene(sceneToSet);
        appStage.show();
    }

    private void loadSideMenuPanelAfterLogin(ActionEvent event) throws IOException {
        Parent homePageParent = getParent();
        Scene homePageScene = getScene(homePageParent);

        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showStage(appStage, homePageScene);

        loadRecensioniView(homePageScene);
        initRecensioniButton(homePageScene);
    }

    private boolean tryLogin() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        return administratorDao.tryLogin(username, password);
    }

    private void showLoginErrorDialog() {
        Alert dg = new Alert(Alert.AlertType.INFORMATION);
        dg.setHeaderText("Errore Login");
        dg.setContentText("Credenziali errate.");
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        dg.setX((bounds.getMaxX() / 2) - 150);
        dg.setY((bounds.getMaxY() / 2) - 100);

        DialogPane dialogPane = dg.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/css/dialogStyle.css").toExternalForm());
        dg.initStyle(StageStyle.UNDECORATED);
        Image icon = new Image("/icons/infoIcon.png");
        ImageView iv = new ImageView(icon);
        dg.getDialogPane().setGraphic(iv);
        dg.showAndWait();
    }

    @FXML
    public void clickLogin(ActionEvent event) {

        try {
            if (tryLogin()) {
                loadSideMenuPanelAfterLogin(event);
            } else {
                showLoginErrorDialog();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadRecensioniView(Scene homePageScene) {
        Parent root = null;
        BorderPane borderpane = (BorderPane) homePageScene.lookup("#borderpane");
        try {
            root = FXMLLoader.load(getClass().getResource("/view/Recensioni.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(SideMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderpane.setCenter(root);
    }

    private void initRecensioniButton(Scene homePageScene) {
        JFXButton recensioniButton = (JFXButton) homePageScene.lookup("#recensioniButton");
        ImageView recensioniImageView = (ImageView) homePageScene.lookup("#recensioniImageView");

        setBlueIcon(recensioniImageView);

        changeRecensioniButtonTextColourToBlue(recensioniButton);

    }

    private void setBlueIcon(ImageView recensioniImageView) {
        Image image = new Image(getClass().getResourceAsStream("/icons/recensioni-blue.png"));
        recensioniImageView.setImage(image);
    }

    private void changeRecensioniButtonTextColourToBlue(JFXButton recensioniButton) {
        recensioniButton.setStyle("-fx-text-fill: #3282B8");
    }
}
