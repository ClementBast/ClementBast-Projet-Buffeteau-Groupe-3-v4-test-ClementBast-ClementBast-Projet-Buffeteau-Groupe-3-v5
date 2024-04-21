package sio.projetbuffteauv3.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sio.projetbuffteauv3.entities.Competence;
import sio.projetbuffteauv3.entities.Demande;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicesDemandes {
    private Connection uneCnx ;
    private PreparedStatement ps;
    private ResultSet rs;

    public ServicesDemandes(){
        uneCnx = ConnexionBDD.getCnx();
    }

    public ObservableList<Demande> GetAllDemandes() throws SQLException{
        ObservableList<Demande> lesDemandes = FXCollections.observableArrayList();

        PreparedStatement ps = uneCnx.prepareStatement(
                "SELECT matiere.designation, demande.sous_matiere, demande.id, demande.date_updated\n" +
                        "FROM demande\n" +
                        "INNER JOIN matiere ON demande.id_matiere = matiere.id\n" +
                        "WHERE demande.status = 1\n" +
                        "GROUP BY matiere.designation, demande.date_updated, demande.id;\n");


        rs = ps.executeQuery();
        while(rs.next())
        {
            Demande laDemande = new Demande(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getString(4));
            lesDemandes.add(laDemande);
        }
        return lesDemandes;
    }

    public ObservableList<Demande> GetAllDemandesByIdUser(int idUser) throws SQLException{
        ObservableList<Demande> lesDemandes = FXCollections.observableArrayList();

        PreparedStatement ps = uneCnx.prepareStatement(
                "SELECT matiere.designation, demande.sous_matiere, demande.id, demande.date_updated\n" +
                        "FROM demande\n" +
                        " JOIN matiere ON demande.id_matiere = matiere.id\n" +
                        "JOIN soutien ON demande.id = soutien.id_demande\n" +
                        "JOIN competence ON soutien.id_competence = competence.id\n" +
                        "WHERE competence.id_user = ?;");

        ps.setInt(1, idUser);
        rs = ps.executeQuery();
        while(rs.next())
        {
            Demande laDemande = new Demande(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getString(4));
            lesDemandes.add(laDemande);
        }
        return lesDemandes;
    }

    public ObservableList<Demande> GetMatiereDemandes(int id_user) throws SQLException {
        ObservableList<Demande> lesMatDemandes = FXCollections.observableArrayList();
        PreparedStatement ps = uneCnx.prepareStatement("SELECT matiere.designation, demande.sous_matiere, demande.id, demande.date_updated\n" +
                "FROM demande\n" +
                "JOIN matiere ON demande.id_matiere = matiere.id\n" +
                "WHERE demande.id_user = ?;");
        ps.setInt(1, id_user);

        rs = ps.executeQuery();

        while (rs.next()) {
            Demande uneDemande = new Demande(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4));
            lesMatDemandes.add(uneDemande);
        }
        return lesMatDemandes;
    }


    public ObservableList<Demande> GetSousMatiereDemandes(int id_user, int id_demande) throws SQLException {
        ObservableList<Demande> lesSousMatDemandes = FXCollections.observableArrayList();

        // Utilisez une requête paramétrée pour éviter les problèmes de sécurité (SQL injection)
        String query = "SELECT demande.sous_matiere, demande.id, demande.date_updated\n" +
                "FROM demande\n" +
                "WHERE demande.id_user = ? AND demande.id = ?";

        PreparedStatement ps = uneCnx.prepareStatement(query);
        ps.setInt(1, id_user);
        ps.setInt(2, id_demande);

        rs = ps.executeQuery();

        while (rs.next()) {
            String sousMatiere = rs.getString("sous_matiere");
            int idDemandeResult = rs.getInt("id");
            String dateUpdated = rs.getString("date_updated");

            Demande uneMatiere = new Demande("", sousMatiere, idDemandeResult, dateUpdated);
            lesSousMatDemandes.add(uneMatiere);
        }

        return lesSousMatDemandes;
    }

    private int getIdMatiereByDesignation(String matiereDesignation) throws SQLException {
        ps = uneCnx.prepareStatement("SELECT id FROM matiere WHERE designation = ?");
        ps.setString(1, matiereDesignation);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        } else {
            throw new SQLException("Matière non trouvée pour la désignation: " + matiereDesignation);
        }
    }
    public void insererDemande(String matiere, int idUtilisateur, String sousMat, java.sql.Date dateFinDemande, java.sql.Date dateUpdated) throws SQLException {
        int idMatiere = getIdMatiereByDesignation(matiere);

        ps = uneCnx.prepareStatement("INSERT INTO demande (id_matiere, id_user, sous_matiere, status, date_updated, date_fin_demande) VALUES (?, ?, ?, 1, ?, ?)");
        ps.setInt(1, idMatiere);
        ps.setInt(2, idUtilisateur);
        ps.setString(3, sousMat);
        ps.setDate(4, dateUpdated);
        ps.setDate(5, dateFinDemande);
        ps.executeUpdate();
    }
    public void supprimerDemande(String matiere, int idUtilisateur, int idDemande) throws SQLException {
        int idMatiere = getIdMatiereByDesignation(matiere);
        System.out.println("ID de la matière pour " + matiere + " : " + idMatiere);

        ps = uneCnx.prepareStatement("DELETE FROM demande WHERE id_matiere = ? AND id_user = ? AND id = ?");
        ps.setInt(1, idMatiere);
        ps.setInt(2, idUtilisateur);
        ps.setInt(3, idDemande);
        ps.executeUpdate();
    }


    public void modifierDemande(int idDemande, String matiere, int idUtilisateur, String sousMatiere, java.sql.Date dateFinDemande, java.sql.Date dateUpdated) throws SQLException {
        int idMatiere = getIdMatiereByDesignation(matiere);

        ps = uneCnx.prepareStatement("UPDATE demande SET id_matiere = ?, sous_matiere = ?, date_updated = ?, date_fin_demande = ? WHERE id = ? AND id_user = ?");
        ps.setInt(1, idMatiere);
        ps.setString(2, sousMatiere);
        ps.setDate(3, dateUpdated);
        ps.setDate(4, dateFinDemande);
        ps.setInt(5, idDemande);
        ps.setInt(6, idUtilisateur);
        ps.executeUpdate();
    }

    public int getNombreDemandesStatutDeux() throws SQLException {
        int nombreDemandes = 0;

        // Connexion à la base de données
        System.out.println("Connexion à la base de données établie : " + !uneCnx.isClosed());

        // Préparation de la requête pour compter le nombre de demandes avec un statut de 2
        PreparedStatement ps = uneCnx.prepareStatement(
                "SELECT COUNT(*) FROM demande WHERE status = 2");

        // Exécution de la requête
        ResultSet rs = ps.executeQuery();

        // Récupération du nombre de demandes
        if (rs.next()) {
            nombreDemandes = rs.getInt(1);
        }

        // Fermeture explicite de la PreparedStatement et du ResultSet
        rs.close();
        ps.close();

        return nombreDemandes;
    }

}