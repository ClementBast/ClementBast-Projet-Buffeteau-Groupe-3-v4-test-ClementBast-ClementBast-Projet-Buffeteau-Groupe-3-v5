package sio.projetbuffteauv3.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import sio.projetbuffteauv3.entities.Competence;
import sio.projetbuffteauv3.entities.Demande;
import sio.projetbuffteauv3.entities.Matiere;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ServicesCompetences implements Initializable {
    private Connection uneCnx ;
    private PreparedStatement ps;
    private ResultSet rs;

    public ServicesCompetences(){uneCnx = ConnexionBDD.getCnx();}

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.uneCnx = ConnexionBDD.getCnx();
    }

    public ObservableList<Competence> GetMatiereCompetences(int id_user) throws SQLException {
        ObservableList<Competence> lesMatCompetences = FXCollections.observableArrayList();
        PreparedStatement ps = uneCnx.prepareStatement("SELECT competence.id, matiere.designation, competence.sous_matiere FROM competence JOIN matiere ON matiere.id = competence.id_matiere WHERE id_user = ?");
        ps.setInt(1, id_user);

        rs = ps.executeQuery();

        while(rs.next()) {
            Competence uneCompetence = new Competence(rs.getInt(1),rs.getString(2), rs.getString(3), id_user);
            lesMatCompetences.add(uneCompetence);
        }
        return lesMatCompetences;
    }

    public ObservableList<Competence> GetSousMatiereCompetences(int id_user, String matiere) throws SQLException {
        ObservableList<Competence> lesSousMatCompetences = FXCollections.observableArrayList();
        PreparedStatement ps = uneCnx.prepareStatement("SELECT competence.id ,competence.sous_matiere FROM competence JOIN matiere ON matiere.id = competence.id_matiere \n" +
                "WHERE matiere.designation = ? AND id_user = ?;");
        ps.setString(1, matiere);
        ps.setInt(2, id_user);
        rs = ps.executeQuery();

        while(rs.next()) {
            ObservableList<Competence> lesCompetences = FXCollections.observableArrayList();
            String[] compSousMat = rs.getString(2).split("#");

            for (String compSousMats : compSousMat) {
                if (!compSousMats.isEmpty()) {
                    Competence uneCompetence = new Competence(rs.getInt(1), matiere, compSousMats, id_user);
                    lesSousMatCompetences.add(uneCompetence);
                }
            }
        }
        return lesSousMatCompetences;
    }

    public void insererCompetence(String matiere, int idUtilisateur, String sousMat) throws SQLException {
        // Vérifier si la compétence existe déjà pour cet utilisateur et cette matière
        boolean competenceExistante = checkCompetenceExistante(matiere, idUtilisateur);

        if (!competenceExistante) {
            // La compétence n'existe pas encore, on peut l'insérer
            ps = uneCnx.prepareStatement("INSERT INTO competence (id_matiere, id_user, sous_matiere, statut) VALUES ((SELECT id FROM matiere WHERE designation = ?), ?, ?, 1)");
            ps.setString(1, matiere);
            ps.setInt(2, idUtilisateur);
            ps.setString(3, sousMat);
            ps.executeUpdate();
        } else {
            // La compétence existe déjà, récupérons d'abord les sous-matières existantes
            String sousMatExistantes = getCompetenceSousMatieres(matiere, idUtilisateur);

            // Ajoutons uniquement les nouvelles sous-matières qui n'existent pas déjà
            String[] nouvellesSousMatieres = sousMat.split("#");
            for (String nouvelleSousMatiere : nouvellesSousMatieres) {
                if (!sousMatExistantes.contains("#" + nouvelleSousMatiere)) {
                    sousMatExistantes += "#" + nouvelleSousMatiere;
                }
            }

            // Mettons à jour les sous-matières dans la base de données
            ps = uneCnx.prepareStatement("UPDATE competence SET sous_matiere = ? WHERE id_matiere = (SELECT id FROM matiere WHERE designation = ?) AND id_user = ?");
            ps.setString(1, sousMatExistantes);
            ps.setString(2, matiere);
            ps.setInt(3, idUtilisateur);
            ps.executeUpdate();
        }
    }


    // Méthode pour vérifier si une compétence existe déjà pour cet utilisateur et cette matière
    private boolean checkCompetenceExistante(String matiere, int idUtilisateur) throws SQLException {
        ps = uneCnx.prepareStatement("SELECT COUNT(*) FROM competence WHERE id_matiere = (SELECT id FROM matiere WHERE designation = ?) AND id_user = ?");
        ps.setString(1, matiere);
        ps.setInt(2, idUtilisateur);

        rs = ps.executeQuery();
        rs.next();

        return rs.getInt(1) > 0;
    }

    private String getCompetenceSousMatieres(String matiere, int idUtilisateur) throws SQLException {
        PreparedStatement selectPs = uneCnx.prepareStatement("SELECT sous_matiere FROM competence WHERE id_matiere = (SELECT id FROM matiere WHERE designation = ?) AND id_user = ?");
        selectPs.setString(1, matiere);
        selectPs.setInt(2, idUtilisateur);

        ResultSet selectRs = selectPs.executeQuery();

        if (selectRs.next()) {
            return selectRs.getString(1);
        } else {
            return "";
        }
    }


    public void supprimerCompetence(String matiere, int idUtilisateur) throws SQLException {
        ps = uneCnx.prepareStatement("DELETE FROM competence WHERE id_matiere = (SELECT id FROM matiere WHERE designation = ?) AND id_user = ?");
        ps.setString(1, matiere);
        ps.setInt(2, idUtilisateur);
        ps.executeUpdate();
    }
    public int getIdCompetenceParDemande(int idDemande) throws SQLException {
        System.out.println("Méthode getIdCompetenceParDemande exécutée avec l'id de demande : " + idDemande);
        int idCompetence = 0;
        try (Connection uneCnx = ConnexionBDD.getCnx();
             PreparedStatement ps = uneCnx.prepareStatement(
                     "SELECT c.id " +
                             "FROM competence c " +
                             "INNER JOIN demande d ON c.id_matiere = d.id_matiere " +
                             "WHERE d.id = ?")) {
            ps.setInt(1, idDemande);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    idCompetence = rs.getInt("id");
                }
            }
        }

        return idCompetence;
    }

    public int getIdCompetenceFromDemande(int idDemande) throws SQLException {
        ps = uneCnx.prepareStatement( "SELECT c.id " +
                "FROM competence c " +
                "JOIN demande d ON c.sous_matiere = d.sous_matiere " +
                "WHERE d.id = ?");
        ps.setInt(1, idDemande);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        } else {
            throw new SQLException("echec");
        }
    }
}