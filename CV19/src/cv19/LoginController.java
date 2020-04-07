package cv19;

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
    
    @FXML
    private BorderPane loginPane;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            this.stage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Login.fxml"));
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

    public static void main(String[] args) {
        launch(args);
    }
    
    @FXML
    private void clickLogin(MouseEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SideMenu.fxml"));
            BorderPane mainMenuPane = loader.load();
            stage.setWidth(1160);
            stage.setHeight(638);
            loginPane.setCenter(mainMenuPane);
            //this.stage.setScene(new Scene(mainMenuPane));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
