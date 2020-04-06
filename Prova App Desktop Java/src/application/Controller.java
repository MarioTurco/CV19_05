package application;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class Controller {

	@FXML
	private BorderPane borderpane;

	@FXML
	public void writeAction(ActionEvent e){

	}

	@FXML
	public void close(MouseEvent event){
		Stage stage = (Stage) borderpane.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void clear(MouseEvent event){
		borderpane.setCenter(null);
	}

	@FXML
	public void loadUI(String ui){
		Parent root = null;
		try{
			root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
		}
		catch(IOException ex){
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
		}
		borderpane.setCenter(root);
	}
}
