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
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import model.Admin;

/**
 *
 * @author gpepp
 */
public class LoginController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private JFXButton loginButton;

    private AdminDAO administratorDao;

    private Admin admin;
    
    public LoginController() {
        initAdminDAO();
    }

    private void initAdminDAO() {
        administratorDao = new AdminDAO();
    }

    private Scene getScene(Parent root) {
        return new Scene(root);
    }

    private void showStage(Stage appStage, Scene sceneToSet) {
        appStage.hide();
        appStage.setScene(sceneToSet);
        appStage.show();
    }

    private void loadSideMenuPanelAfterLogin(MouseEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/SideMenu.fxml")); 
        SideMenuController sideMenuController=new SideMenuController(this.admin);
        loader.setController(sideMenuController);       
        Parent parent=loader.load();
       
        Scene homePageScene = getScene(parent);

        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showStage(appStage, homePageScene);
        sideMenuController.setWindowStage();
        
        loadRecensioniView(homePageScene);
        initRecensioniButton(homePageScene);
    }

    private void showLoginErrorDialog(String messaggio) {
        Alert dg = new Alert(Alert.AlertType.INFORMATION);
        dg.setHeaderText("Errore Login");
        dg.setContentText(messaggio);
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

    private void checkLoginWithThread(MouseEvent event, Scene scena) throws InterruptedException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        Thread th = new Thread(() -> {
                if (administratorDao.tryLogin(username, password)){
                    this.admin=new Admin(username);
                    Platform.runLater(() -> {
                        try {
                            loadSideMenuPanelAfterLogin(event);
                        } catch (IOException e) {
                        }
                    });

                } else {
                    Platform.runLater(() -> {
                        loginButton.setDisable(false);
                        showLoginErrorDialog("Credenziali errate.");
                    });
                }
            scena.setCursor(Cursor.DEFAULT);
        });

        th.start();
    }

    @FXML
    public void clickLogin(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Scene scena = ((Node) event.getSource()).getScene();
            scena.setCursor(Cursor.WAIT);
            loginButton.setDisable(true);
            try {
                checkLoginWithThread(event, scena);
            } catch (InterruptedException e) {}
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
