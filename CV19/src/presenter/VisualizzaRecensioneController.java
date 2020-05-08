/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import DAO.RecensioneDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import model.Recensione;
import org.controlsfx.control.Rating;

/**
 *
 * @author checc
 */
public class VisualizzaRecensioneController {
    
    @FXML
    private Label nickNameLabel;
    
    @FXML
    private Label strutturaLabel;
    
    @FXML
    private Rating ratingStars;
    
    @FXML
    private Label dataLabel;
    
    @FXML
    private Label testoLabel;
    
    @FXML
    private Label titoloRecensione;
    
    private BorderPane borderPanePadre;
    private Recensione recensioneDaMostrare;
    private final RecensioneDAO recensioneDAO;

    public VisualizzaRecensioneController(){
        this.recensioneDAO = new RecensioneDAO();
    }
    
    public void setRecensioneDaMostrare(Recensione recensioneDaMostrare) {
        this.recensioneDaMostrare = recensioneDaMostrare;
    }
    
    public void riempiCampiDettagliRecensione(){
        nickNameLabel.setText(recensioneDaMostrare.getNomeAutore()+" - "+recensioneDaMostrare.getNickNameAutore());
        strutturaLabel.setText(recensioneDaMostrare.getStruttura());
        dataLabel.setText(recensioneDaMostrare.getData());
        testoLabel.setText(recensioneDaMostrare.getTesto()); 
        titoloRecensione.setText(recensioneDaMostrare.getTitolo());
        ratingStars.setRating(recensioneDaMostrare.getValutazione());
    }
    
    @FXML
    public void accettaRecensione(ActionEvent e){
        recensioneDAO.chiudiRecensioneById(this.recensioneDaMostrare.getIdRecensione(),true);
        mostraDialogAzioneEseguita("accettata");
        loadListaRecensioni(e);
    }
    
    @FXML
    public void rifiutaRecensione(ActionEvent e){
        recensioneDAO.chiudiRecensioneById(this.recensioneDaMostrare.getIdRecensione(),false);
        mostraDialogAzioneEseguita("rifiutata");
        loadListaRecensioni(e);
    }
    
    private void mostraDialogAzioneEseguita(String stato){
        Alert dg = new Alert(Alert.AlertType.INFORMATION);
        dg.setHeaderText("Azione Eseguita");
        dg.setContentText("Recensione "+ " "+ stato);
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
    
    private void loadListaRecensioni(ActionEvent e){
        Scene scene = ((Node) e.getSource()).getScene();
        borderPanePadre = (BorderPane)scene.lookup("#borderpane");
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Recensioni.fxml"));
            root = loader.load();                  
        } catch (IOException ex) {
            Logger.getLogger(SideMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPanePadre.setCenter(root);
    }
        
    
}
