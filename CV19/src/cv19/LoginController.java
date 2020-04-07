package cv19;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class LoginController extends Application {

    
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
            loader.setLocation(getClass().getResource("SideMenu.fxml"));
            loginPane = loader.load();
            
            Scene scene = new Scene(loginPane);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            /*
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            primaryStage.setScene(scene);
            primaryStage.show();
            */
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void launchSideMenu(){
        try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SideMenu.fxml"));
        BorderPane mainMenuPane = loader.load();
        mainMenuScene = new Scene(mainMenuPane, 1160, 638);
        stage.setScene(mainMenuScene);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    /*
    @FXML
    private void clickLogin(MouseEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SideMenu.fxml"));
            BorderPane mainMenuPane = loader.load();
            mainMenuScene = new Scene(mainMenuPane, 1160, 638);
            this.stage.setScene(mainMenuScene);
            //this.stage.setScene(new Scene(mainMenuPane));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
*/
}
