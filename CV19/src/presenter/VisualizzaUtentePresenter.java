/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import DAO.UtenteDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import model.Utente;

/**
 *
 * @author checc
 */
public class VisualizzaUtentePresenter {

    @FXML
    private Label nomeLabel;

    @FXML
    private Label nickNameLabel;

    @FXML
    private Label dataLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label recensioniRifiutateLabel;

    @FXML
    private Label recensioniApprovateLabel;

    private final UtenteDAO utenteDAO;

    private Utente utenteDaMostrare;

    private BorderPane borderPanePadre;

    public VisualizzaUtentePresenter() {
        this.utenteDAO = new UtenteDAO();
    }

    public void setUtenteDaMostrare(Utente utente) {
        this.utenteDaMostrare = utente;
    }

    public void riempiCampiDettagliUtente() {
        nickNameLabel.setText(utenteDaMostrare.getNickname());
        nomeLabel.setText(utenteDaMostrare.getNome());
        dataLabel.setText(utenteDaMostrare.getDataDiNascita());
        emailLabel.setText(utenteDaMostrare.getEmail());
        recensioniRifiutateLabel.setText(String.valueOf(utenteDaMostrare.getRecensioniRifiutate()));
        recensioniApprovateLabel.setText(String.valueOf(utenteDaMostrare.getRecensioniApprovate()));
    }

    @FXML
    public void eliminaUtente(MouseEvent e) {
        Scene scene = ((Node) e.getSource()).getScene();
        borderPanePadre = (BorderPane) scene.lookup("#borderpane");
        this.utenteDAO.deleteUtenteByNickname(this.utenteDaMostrare.getNickname());
        mostraDialogEliminazione();
        utentiView();
    }

    private void utentiView() {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Utenti.fxml"));
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SideMenuPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPanePadre.setCenter(root);
    }
    
    @FXML
    public void modificaUtente(MouseEvent e) {
        Scene scene = ((Node) e.getSource()).getScene();
        borderPanePadre = (BorderPane) scene.lookup("#borderpane");
        modificaUtenteView(utenteDaMostrare);
    }

    private void modificaUtenteView(Utente utente) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModificaDatiUtente.fxml"));
            root = loader.load();
            ModificaUtentePresenter modificaUtenteController = loader.getController();
            modificaUtenteController.setUtenteDaModificare(utente);
            modificaUtenteController.riempiCampoUtente();

        } catch (IOException ex) {
            Logger.getLogger(SideMenuPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPanePadre.setCenter(root);
    }

    private void mostraDialogEliminazione() {
        Alert dg = new Alert(Alert.AlertType.INFORMATION);
        dg.setHeaderText("Azione Eseguita");
        dg.setContentText("Utente Eliminato");
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
}
