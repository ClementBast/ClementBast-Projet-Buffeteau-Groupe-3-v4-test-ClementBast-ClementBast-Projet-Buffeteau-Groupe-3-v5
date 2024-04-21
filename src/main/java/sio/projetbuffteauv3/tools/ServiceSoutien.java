package sio.projetbuffteauv3.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sio.projetbuffteauv3.entities.Demande;
import sio.projetbuffteauv3.entities.Soutien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceSoutien {
    private Connection uneCnx ;
    private PreparedStatement ps;
    private ResultSet rs;

    public ServiceSoutien(){
        uneCnx = ConnexionBDD.getCnx();
    }
    public ObservableList<Soutien> GetAllSoutientsByIdUser(int idUser) throws SQLException {
        ObservableList<Soutien> lesSoutiens = FXCollections.observableArrayList();

        try (PreparedStatement ps = uneCnx.prepareStatement(
                "SELECT\n" +
                        "    soutien.id,\n" +
                        "    soutien.date_du_soutien,\n" +
                        "    soutien.date_updated,\n" +
                        "    soutien.description,\n" +
                        "    soutien.status ,\n" +
                        "    demande.id ,\n" +
                        "    demande.date_fin_demande,\n" +
                        "    demande.sous_matiere ,\n" +
                        "    matiere.designation,\n" +
                        "    competence.sous_matiere,\n" +
                        "    competence.statut\n" +
                        "FROM\n" +
                        "    soutien\n" +
                        "    JOIN demande ON soutien.id_demande = demande.id\n" +
                        "    JOIN matiere ON demande.id_matiere = matiere.id\n" +
                        "    JOIN competence ON soutien.id_competence = competence.id\n" +
                        "WHERE\n" +
                        "    competence.id_user = ?")) {

            ps.setInt(1, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Soutien leSoutien = new Soutien(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), 0);
                    lesSoutiens.add(leSoutien);
                }
            }
        }

        return lesSoutiens;
    }

    public void insererSoutien(int idDemande, int idComp, java.sql.Date dateSout, java.sql.Date dateUpt, String descri, int statut) throws SQLException {

        System.out.println("Connexion à la base de données établie : " + !uneCnx.isClosed());

        // Déclaration de la PreparedStatement avant utilisation
        PreparedStatement ps = uneCnx.prepareStatement(
                "INSERT INTO soutien (id_demande, id_competence, date_du_soutien, date_updated, description, status) VALUES (?, ?, ?, ?, ?, ?)");

        // Paramétrage de la PreparedStatement
        ps.setInt(1, idDemande);
        ps.setInt(2, idComp);
        ps.setDate(3, dateSout);
        ps.setDate(4, dateUpt);
        ps.setString(5, descri);
        ps.setInt(6, statut);

        // Exécution de la requête
        ps.executeUpdate();

        // Fermeture explicite de la PreparedStatement
        ps.close();

        PreparedStatement psUpdateDemande = uneCnx.prepareStatement(
                "UPDATE demande SET status = ? WHERE id = ?");

        // Paramétrage de la PreparedStatement pour la mise à jour du statut de la demande
        psUpdateDemande.setInt(1, 2); // Statut 2 pour la demande traitée
        psUpdateDemande.setInt(2, idDemande);

        // Exécution de la requête de mise à jour du statut de la demande
        psUpdateDemande.executeUpdate();

        // Fermeture explicite de la PreparedStatement de mise à jour du statut de la demande
        psUpdateDemande.close();
    }
}