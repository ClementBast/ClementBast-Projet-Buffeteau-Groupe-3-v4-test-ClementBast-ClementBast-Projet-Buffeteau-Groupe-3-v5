package sio.projetbuffteauv3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sio.projetbuffteauv3.entities.Demande;
import sio.projetbuffteauv3.entities.Matiere;
import sio.projetbuffteauv3.entities.Salle;
import sio.projetbuffteauv3.entities.Soutien;
import sio.projetbuffteauv3.tools.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class AdminController implements Initializable {
    @javafx.fxml.FXML
    private Button btnMatAdmin;
    @javafx.fxml.FXML
    private Button btnSousMatAdmin;
    @javafx.fxml.FXML
    private Button btnSalleAdmin;
    @javafx.fxml.FXML
    private Button btnSoutienAdmin;
    @javafx.fxml.FXML
    private AnchorPane apMatAdmin;
    @javafx.fxml.FXML
    private AnchorPane apSousMatAdmin;
    @javafx.fxml.FXML
    private AnchorPane apSalleAdmin;
    @javafx.fxml.FXML
    private AnchorPane apSoutienAdmin;
    @javafx.fxml.FXML
    private AnchorPane apStatistiquesAdmin;
    @javafx.fxml.FXML
    private Button btnStatistiquesAdmin;
    @javafx.fxml.FXML
    private Button btnCreerMatiereAdmin;
    @javafx.fxml.FXML
    private Button btnModifierMatiereAdmin;
    @javafx.fxml.FXML
    private Button btnModifSousMatAdmin;
    @javafx.fxml.FXML
    private Button btnCreerSousMatAdmin;
    @javafx.fxml.FXML
    private TableView tvListeMatAdmin;
    @javafx.fxml.FXML
    private TableColumn tcListeMatAdmin;
    @javafx.fxml.FXML
    private TableView tvListeSousMatAdmin;
    @javafx.fxml.FXML
    private TableColumn tcListeSousMatAdmin;
    @javafx.fxml.FXML
    private Button btnCreerSalleAdmin;
    @javafx.fxml.FXML
    private Button btnModifSalleAdmin;
    @javafx.fxml.FXML
    private TableView tvListeSalleAdmin;
    @javafx.fxml.FXML
    private TableColumn tcListeSalleAdmin;
    @javafx.fxml.FXML
    private TableView tvListeSoutienAdmin;
    @javafx.fxml.FXML
    private TableColumn tcIdSoutienAdmin;

    ServicesDemandes servicesDemandes = new ServicesDemandes();
    ServiceConnexion serviceConnexion = new ServiceConnexion();
    ServicesCompetences servicesCompetences = new ServicesCompetences();
    ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
    ServicesMatieres servicesMatieres = new ServicesMatieres();
    ServicesAdministrateur servicesAdministrateur = new ServicesAdministrateur();
    ServiceSoutien serviceSoutien = new ServiceSoutien();
    ConnexionBDD maCnx;
    @javafx.fxml.FXML
    private TableView tvListeMatieresSousMat;
    @javafx.fxml.FXML
    private TableColumn tcListeMatieresSousMat;
    @javafx.fxml.FXML
    private AnchorPane apCreerSousMatiere;
    @javafx.fxml.FXML
    private TextField tfMatSelectCreerSousMat;
    @javafx.fxml.FXML
    private TextField tfSousMatACreer;
    @javafx.fxml.FXML
    private Button btnCreerSousMat;
    @javafx.fxml.FXML
    private AnchorPane apCreerMatiere;
    @javafx.fxml.FXML
    private TableView tvAffMatieresApCreerMat;
    @javafx.fxml.FXML
    private TableColumn tcAffMatieresApCreerMat;
    @javafx.fxml.FXML
    private TextField tfCreerMatiere;
    @javafx.fxml.FXML
    private Button btnAjouterMatiere;
    @javafx.fxml.FXML
    private AnchorPane apModifSousMatiere;
    @javafx.fxml.FXML
    private AnchorPane apModifMatiere;
    @javafx.fxml.FXML
    private TextField tfNomMatiereAModif;
    @javafx.fxml.FXML
    private TextField tfNewNomMatiere;
    @javafx.fxml.FXML
    private Button btnUpdateMat;
    @javafx.fxml.FXML
    private AnchorPane apCreerSalle;
    @javafx.fxml.FXML
    private TextField tfCreerSalle;
    @javafx.fxml.FXML
    private Button btnAjouterSalle;
    @javafx.fxml.FXML
    private AnchorPane apModifSalle;
    @javafx.fxml.FXML
    private TextField tfNumSalle;
    @javafx.fxml.FXML
    private TextField tfNouveauNumSalle;
    @javafx.fxml.FXML
    private Button btnModifNumSalle;
    @javafx.fxml.FXML
    private TextField tfMatSelec2;
    @javafx.fxml.FXML
    private TextField tfSousMatAModif;
    @javafx.fxml.FXML
    private Button btnRenommerSousMat;
    @javafx.fxml.FXML
    private TextField tfNewSousMat;
    @javafx.fxml.FXML
    private TableColumn tcSousMatAdminSoutient;
    @javafx.fxml.FXML
    private Button btnTraiterSoutient;
    @javafx.fxml.FXML
    private TableView tvSalleSoutient;
    @javafx.fxml.FXML
    private TableColumn tcSalleSoutient;
    @javafx.fxml.FXML
    private TextField tfCommentaireSoutient;

    @javafx.fxml.FXML
    public void btnMatAdminClicked(Event event) throws SQLException {
        tcListeMatAdmin.setCellValueFactory(new PropertyValueFactory<Matiere, String>("matiere"));
        tcListeMatieresSousMat.setCellValueFactory(new PropertyValueFactory<Matiere, String>("matiere"));
        tvListeMatAdmin.setItems(servicesMatieres.getAllMatieres());
        tvListeMatieresSousMat.setItems(servicesMatieres.getAllMatieres());
        apMatAdmin.toFront();
    }

    @javafx.fxml.FXML
    public void btnSousMatAdminClicked(Event event) {
        apSousMatAdmin.toFront();

    }
    // test

    @javafx.fxml.FXML
    public void btnSalleAdminClicked(Event event) throws SQLException {
        apSalleAdmin.toFront();
        tcListeSalleAdmin.setCellValueFactory(new PropertyValueFactory<Salle, String>("codeSalle"));
        tvListeSalleAdmin.setItems(servicesAdministrateur.getAllSalles());
    }

    @javafx.fxml.FXML
    public void btnSoutienClicked(Event event) throws SQLException {
        apSoutienAdmin.toFront();

        tcSalleSoutient.setCellValueFactory(new PropertyValueFactory<Salle, String>("codeSalle"));
        tvSalleSoutient.setItems(servicesAdministrateur.getAllSalles());

        tcIdSoutienAdmin.setCellValueFactory(new  PropertyValueFactory<Demande, String>("id"));
        tcSousMatAdminSoutient.setCellValueFactory(new  PropertyValueFactory<Demande, String>("sousMatiereDem"));
        ObservableList<Demande> demandes = servicesAdministrateur.getDemandesWithStatusOne();

        tvListeSoutienAdmin.setItems(demandes);

//a avancer

        }

    @javafx.fxml.FXML
    public void btnStatistiquesAdminClicked(Event event) {
        apStatistiquesAdmin.toFront();
    }

    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try {

            maCnx = new ConnexionBDD();
            tcListeMatAdmin.setCellValueFactory(new PropertyValueFactory<Matiere, String>("matiere"));
            tcListeMatieresSousMat.setCellValueFactory(new PropertyValueFactory<Matiere, String>("matiere"));



            servicesMatieres = new ServicesMatieres();
            tvListeMatAdmin.setItems(servicesMatieres.getAllMatieres());
            tvListeMatieresSousMat.setItems(servicesMatieres.getAllMatieres());

        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void btnListeMatClicked(Event event) throws SQLException {
        // Récupérer la matière sélectionnée
        String matiereSelectionnee = ((Matiere)tvListeMatieresSousMat.getSelectionModel().getSelectedItem()).getMatiere();

        ObservableList<Matiere> lesSousMatieres = FXCollections.observableArrayList(servicesMatieres.getAllSousMatieresByMatieres(matiereSelectionnee));

            System.out.println(matiereSelectionnee);
            for (Matiere sousMatiere : lesSousMatieres) {
                System.out.println(sousMatiere.getSousMatiere());

            }

            // Afficher les sous-matières dans la table view
            tcListeSousMatAdmin.setCellValueFactory(new PropertyValueFactory<>("sousMatiere"));
            tvListeSousMatAdmin.setItems(lesSousMatieres);
            tvListeSousMatAdmin.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }




    @javafx.fxml.FXML
    public void btnCreerMatiereAdminClicked(Event event) {apCreerMatiere.toFront();
    }


    @javafx.fxml.FXML
    public void btnModifSousMatAdminClicked(Event event) {apModifSousMatiere.toFront();
        Matiere matiereSelectionnee = (Matiere) tvListeMatieresSousMat.getSelectionModel().getSelectedItem();
        Matiere sousMatSelec = (Matiere) tvListeSousMatAdmin.getSelectionModel().getSelectedItem();
        tfMatSelec2.setText(matiereSelectionnee.getMatiere());
        tfSousMatAModif.setText(sousMatSelec.getSousMatiere());




    }

    @javafx.fxml.FXML
    public void btnCreerSousMatAdminClicked(Event event) {apCreerSousMatiere.toFront();

        Matiere matiereSelectionnee = (Matiere) tvListeMatieresSousMat.getSelectionModel().getSelectedItem();
        tfMatSelectCreerSousMat.setText(matiereSelectionnee.getMatiere());
    }

    @javafx.fxml.FXML
    public void btnCreerSousMatClicked(Event event) throws SQLException {

        String matSelec = tfMatSelectCreerSousMat.getText();
        String sousMatACreer = tfSousMatACreer.getText();

        servicesAdministrateur.creerSousMatiere(matSelec, sousMatACreer);
    }

    @javafx.fxml.FXML
    public void btnAjouterMatiereClicked(Event event) throws SQLException {
        String designation = tfCreerMatiere.getText();
        servicesAdministrateur.creerMatiere(designation);
    }

    @javafx.fxml.FXML
    public void btnUpdateMatClicked(Event event) throws SQLException {
        String newMat = tfNewNomMatiere.getText();
        String ancMat = tfNomMatiereAModif.getText();
        servicesAdministrateur.renommerMatiere(ancMat, newMat);
    }

    @javafx.fxml.FXML
    public void btnModifierMatiereAdminClicked(Event event) {apModifMatiere.toFront();
        Matiere matiereSelectionnee = (Matiere) tvListeMatAdmin.getSelectionModel().getSelectedItem();
        if (matiereSelectionnee != null) {
            tfNomMatiereAModif.setText(matiereSelectionnee.getMatiere());

        } else {
            // Gérer le cas où aucune matière n'est sélectionnée
            System.out.println("Veuillez sélectionner une matière.");
        }
    }


    @javafx.fxml.FXML
    public void btnModifNumSalleClicked(Event event) {
    }

    @javafx.fxml.FXML
    public void btnCreerSalleAdminClicked(Event event) throws SQLException { apCreerSalle.toFront();


    }

    @javafx.fxml.FXML
    public void btnModifSalleAdminClicked(Event event) throws SQLException { apModifSalle.toFront();

        Salle salle = (Salle) tvListeSalleAdmin.getSelectionModel().getSelectedItem();
        tfNumSalle.setText(salle.getCodeSalle());

        int codeSalle = Integer.parseInt(tfNouveauNumSalle.getText());
        int idSalle = salle.getId();

        servicesAdministrateur.renommerSalle(codeSalle, codeSalle);

    }

    @javafx.fxml.FXML
    public void btnRenommerSousMatClicked(Event event) throws SQLException {
        String newSousMat = tfNewSousMat.getText();
        String ancSousMat = tfSousMatAModif.getText();
        String mat = tfMatSelec2.getText();

        servicesAdministrateur.renommerSousMatiere(newSousMat,ancSousMat,mat);
    }

    @javafx.fxml.FXML
    public void btnAjouterSalleClicked(Event event) throws SQLException {
        int newSalle = Integer.parseInt(tfCreerSalle.getText());
        servicesAdministrateur.creerSalle(newSalle);
    }

    @javafx.fxml.FXML
    public void btnTraiterSoutientClicked(Event event) {
    }
}
