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

    public void insererSoutien(Soutien soutien) throws SQLException {
        try (Connection uneCnx = ConnexionBDD.getCnx();
             PreparedStatement ps = uneCnx.prepareStatement(
                     "INSERT INTO soutien (id_demande, id_competence, date_du_soutien, date_updated, description, status, id_salle) VALUES (?, ?, ?, ?, ?, ?, 101)")) {

            ps.setInt(1, soutien.getIdDemandeSoutient());
            ps.setInt(2, soutien.getIdCompSoutient());
            ps.setString(3, soutien.getDate());
            ps.setString(4, soutien.getDate());  // Utilisez la date actuelle pour date_updated
            ps.setString(5, "Description du soutien");  // Remplacez cela par la description réelle si nécessaire
            ps.setInt(6, soutien.getStatut());

            ps.executeUpdate();
        }
    }


}
