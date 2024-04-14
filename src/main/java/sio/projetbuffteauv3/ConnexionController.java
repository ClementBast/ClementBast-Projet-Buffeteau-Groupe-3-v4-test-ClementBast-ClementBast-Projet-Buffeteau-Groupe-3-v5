package sio.projetbuffteauv3;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sio.projetbuffteauv3.entities.Utilisateur;
import sio.projetbuffteauv3.tools.ConnexionBDD;
import sio.projetbuffteauv3.tools.ServiceConnexion;
import sio.projetbuffteauv3.tools.ServiceUtilisateur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnexionController implements Initializable {
    ConnexionBDD maCnx;
    ServiceConnexion serviceConnexion;
    ServiceUtilisateur serviceUtilisateur;

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtMdp;
    @FXML
    private Button btnConnexion;
    @FXML
    private AnchorPane apConnexion;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            maCnx = new ConnexionBDD();
            serviceConnexion = new ServiceConnexion();
            serviceUtilisateur = new ServiceUtilisateur();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean estAdresseEmailValide(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }

    @FXML
    public void onBtnConnexionClicked(Event event) throws SQLException, IOException {

        String unEmail = txtEmail.getText();
        String unMdp = txtMdp.getText();

        if (txtEmail.getText().isEmpty() || !estAdresseEmailValide(unEmail)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur mail");
            alert.setContentText("Merci de rentrer une adresse mail valide");
            alert.setHeaderText("");
            alert.showAndWait();
        } else if (txtMdp.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur mot de passe");
            alert.setContentText("Merci de rentrer un mot de passe");
            alert.setHeaderText("");
            alert.showAndWait();
        } else {
            if (estAdresseEmailValide(unEmail)) {
                String role = serviceUtilisateur.getRoleUtilisateurByMail(unEmail);
                if (serviceConnexion.verifEmailMdpUtilisateur(unEmail, unMdp) == 1) {
                    int id = serviceUtilisateur.getIdUtilisateurByMail(unEmail);
                    Utilisateur unUtilisateur = new Utilisateur(unEmail, unMdp, id);

                    FXMLLoader fxmlLoader;
                    if ("Admin".equals(role)) {
                        fxmlLoader = new FXMLLoader(getClass().getResource("admin-view.fxml"));
                    } else {
                        fxmlLoader = new FXMLLoader(getClass().getResource("etudiant-view.fxml"));
                    }

                    Parent root = fxmlLoader.load();

                    if ("Admin".equals(role)) {
                        // Charger les données pour la vue admin si nécessaire
                    } else {
                        EtudiantController etudiantController = fxmlLoader.getController();
                        etudiantController.initDatas(unUtilisateur);
                    }

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Identifiants incorrects");
                    alert.setContentText("Veuillez vérifier vos informations et ré-essayer");
                    alert.setHeaderText("");
                    alert.showAndWait();
                }
            }
        }
    }
}
