/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import DAO.UtenteDAO;
import java.io.IOException;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
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
public class ModificaUtenteController {

    @FXML
    private Label nickNameLabel;

    @FXML
    private TextField nickNameTextField;

    @FXML
    private TextField passwordTextField;

    private final UtenteDAO utenteDAO;

    private Utente utenteDaModificare;

    private BorderPane borderPanePadre;

    public ModificaUtenteController() {
        this.utenteDAO = new UtenteDAO();
    }

    public void setUtenteDaModificare(Utente utente) {
        this.utenteDaModificare = utente;
    }

    public void riempiCampoUtente() {
        this.nickNameLabel.setText(utenteDaModificare.getNickname());
    }

    @FXML
    public void annullaClick(MouseEvent e) {
        setBorderPanePadre(e);
        viewDettagliUtente(this.utenteDaModificare);
    }

    private void setBorderPanePadre(MouseEvent e) {
        Scene scene = ((Node) e.getSource()).getScene();
        borderPanePadre = (BorderPane) scene.lookup("#borderpane");
    }

    @FXML
    public void confermaClick(MouseEvent e) {
        if (nickNameDiversi(this.utenteDaModificare.getNickname(), nickNameTextField.getText())) {
            modificaUtente(e);
        } else {
            mostraDialog("Attenzione", "Il nickname scelto corrisponde a quello vecchio", 0);
        }
    }

    private void modificaUtente(MouseEvent e) {
        try {
            setBorderPanePadre(e);
            this.utenteDAO.modifyUtente(this.utenteDaModificare.getNickname(), nickNameTextField.getText(), passwordTextField.getText());
            mostraDialog("Azione eseguita", "Utente modificato", 0);
            mostraUtentiView();
        } catch (SQLException ex) {
            mostraMessaggioDiErrore(ex.getMessage());
        }
    }

    private void mostraMessaggioDiErrore(String messaggio) {
        if (messaggio.contains("duplicate key")) {
            mostraDialog("Errore", "Nickname già in uso", 1);
        } else {
            mostraDialog("Errore", messaggio, 1);
        }
    }

    private boolean nickNameDiversi(String vecchio, String nuovo) {
        return !vecchio.equals(nuovo);
    }

    private void mostraDialog(String titolo, String testo, int tipo) {
        Alert dg = new Alert(Alert.AlertType.INFORMATION);
        dg.setHeaderText(titolo);
        dg.setContentText(testo);
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        dg.setX((bounds.getMaxX() / 2) - 150);
        dg.setY((bounds.getMaxY() / 2) - 100);

        DialogPane dialogPane = dg.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/css/dialogStyle.css").toExternalForm());
        dg.initStyle(StageStyle.UNDECORATED);
        Image icon = null;
        if (tipo == 0) {
            icon = new Image("/icons/infoIcon.png");
        } else {
            icon = new Image("/icons/errorIcon.png");
        }
        ImageView iv = new ImageView(icon);
        dg.getDialogPane().setGraphic(iv);
        dg.showAndWait();
    }

    private void viewDettagliUtente(Utente utente) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/visualizzaUtente.fxml"));
            root = loader.load();
            VisualizzaUtenteController utenteController = loader.getController();
            utenteController.setUtenteDaMostrare(utente);
            utenteController.riempiCampiDettagliUtente();

        } catch (IOException ex) {
            Logger.getLogger(SideMenuController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        borderPanePadre.setCenter(root);
    }

    private void mostraUtentiView() {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Utenti.fxml"));
            root = loader.load();

        } catch (IOException ex) {
            Logger.getLogger(SideMenuController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        borderPanePadre.setCenter(root);
    }

}
