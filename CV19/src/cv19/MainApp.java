package cv19;

import DAO.RecensioneDAO;
import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
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
            
            Scene scene = new Scene(loginPane);
            primaryStage.setScene(scene);
            primaryStage.show();

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
        RecensioneDAO recDao= new RecensioneDAO();
        recDao.getConnection();
    }
    
    /*
    @FXML
    private void clickLogin(MouseEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SideMenu.fxml"));
            BorderPane mainMenuPane = loader.load();
//          stage.setWidth(1160);
//          stage.setHeight(638);
            loginPane.setCenter(mainMenuPane);
            //this.stage.setScene(new Scene(mainMenuPane));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
*/
}
