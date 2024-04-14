package sio.projetbuffteauv3.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sio.projetbuffteauv3.entities.Demande;
import sio.projetbuffteauv3.entities.Salle;
import sio.projetbuffteauv3.entities.Soutien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ServicesAdministrateur {
    private Connection uneCnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public ServicesAdministrateur()
    {
        uneCnx = ConnexionBDD.getCnx();
    }

    public void creerMatiere(String designation) throws SQLException {
        String query = "INSERT INTO matiere (designation, code, sous_matiere) VALUES (?, LAST_INSERT_ID(), NULL)";
        try {
            PreparedStatement ps = uneCnx.prepareStatement(query);
            ps.setString(1, designation);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Vous pouvez gérer cette exception selon vos besoins
        }
    }
    public void creerSousMatiere(String matiere, String sousMatiere) throws SQLException {
        String query = "UPDATE matiere SET sous_matiere = CONCAT(sous_matiere, '#', ?) WHERE designation = ?";
        try {
            PreparedStatement ps = uneCnx.prepareStatement(query);
            ps.setString(1, sousMatiere);
            ps.setString(2, matiere);
            ps.executeUpdate();
            System.out.println("Sous-matière ajoutée avec succès pour la matière " + matiere);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public void renommerSousMatiere(String nouvelleSousMatiere, String ancienneSousMatiere, String matiere) throws SQLException {
        // Mettre à jour la sous-matière dans la base de données
        String query = "SELECT sous_matiere FROM matiere WHERE designation = ?";
        String ancienneSousMatieres; // Stocker les anciennes sous-matières pour manipulation

        try {
            PreparedStatement psSelect = uneCnx.prepareStatement(query);
            psSelect.setString(1, matiere);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                ancienneSousMatieres = rs.getString("sous_matiere");

                // Remplacer l'ancienne sous-matière par la nouvelle
                ancienneSousMatieres = ancienneSousMatieres.replaceAll("#" + ancienneSousMatiere + "#", "#" + nouvelleSousMatiere + "#");

                // Mettre à jour la base de données avec les sous-matières modifiées
                String updateQuery = "UPDATE matiere SET sous_matiere = ? WHERE designation = ?";
                PreparedStatement psUpdate = uneCnx.prepareStatement(updateQuery);
                psUpdate.setString(1, ancienneSousMatieres);
                psUpdate.setString(2, matiere);

                int rowsAffected = psUpdate.executeUpdate(); // Exécuter la mise à jour

                if (rowsAffected > 0) {
                    System.out.println("La sous-matière a été renommée avec succès.");
                } else {
                    System.out.println("Aucune sous-matière n'a été renommée.");
                }
            } else {
                System.out.println("La matière spécifiée n'a pas été trouvée.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du renommage de la sous-matière : " + e.getMessage());
        }
    }

    public void renommerSalle(int idSalle, int nouveauCodeSalle) throws SQLException {
        String nouveauNomSalle = "Salle " + nouveauCodeSalle;

        String query = "UPDATE salle SET code_salle = ? WHERE id = ?";

            ps = uneCnx.prepareStatement(query);
            ps.setInt(1, nouveauCodeSalle);
            ps.setInt(2, idSalle);
            ps.executeUpdate();
        }

        public ObservableList<Salle> getAllSalles() throws SQLException {
        ObservableList<Salle> salles = FXCollections.observableArrayList();

        String query = "SELECT * FROM salle";
            ps = uneCnx.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String codeSalle = rs.getString("code_salle");
                int etage = rs.getInt("etage");

                Salle salle = new Salle(id, codeSalle, etage);
                salles.add(salle);
            }
        return salles;
    }

    public void creerSalle(int numero) throws SQLException {
        String codeSalle = "Salle " + numero;
        String query = "INSERT INTO salle (code_salle, etage) VALUES (?, 0)";
            ps = uneCnx.prepareStatement(query);
            ps.setString(1, codeSalle);
            ps.executeUpdate();
        }
    public void renommerMatiere(String matiere, String nouveauNom) throws SQLException {
        String sql = "UPDATE matiere SET designation = ? WHERE designation = ?";
        try {
            ps = uneCnx.prepareStatement(sql);
            ps.setString(1, nouveauNom);
            ps.setString(2, matiere);
            ps.executeUpdate();
            System.out.println("La matière a été renommée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors du renommage de la matière : " + e.getMessage());
        }
    }

    public ObservableList<Demande> getDemandesWithStatusOne() throws SQLException {
        ObservableList<Demande> demandes = FXCollections.observableArrayList();
        String query = "SELECT matiere.designation, demande.sous_matiere, demande.id, demande.date_updated\n" +
                "FROM demande\n" +
                "JOIN matiere ON demande.id_matiere = matiere.id\n" +
                "WHERE demande.status = 1";

        try (Connection connection = ConnexionBDD.getCnx();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String matiereDem = resultSet.getString(1);
                String sousMatiereDem = resultSet.getString(2);
                int id = resultSet.getInt(3);
                String date = resultSet.getString(4);
                Demande demande = new Demande(matiereDem, sousMatiereDem, id, date);
                demandes.add(demande);
            }
        }

        return demandes;
    }
}
