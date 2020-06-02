package cv19;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class MainApp extends Application {

    
    private Stage stage;
    private Scene mainMenuScene;
    
    @FXML
    private JFXButton loginButton;
    
    @FXML
    private BorderPane loginPane;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            this.stage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Login.fxml"));
            loginPane = loader.load();
            primaryStage.setResizable(false);
            Scene scene = new Scene(loginPane);
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image("/icons/logo4.png"));
            primaryStage.setTitle("CV19 Admin Panel");
            primaryStage.show();          
        } catch (IOException e) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
