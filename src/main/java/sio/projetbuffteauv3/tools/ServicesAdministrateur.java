package sio.projetbuffteauv3.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sio.projetbuffteauv3.entities.Demande;
import sio.projetbuffteauv3.entities.Matiere;
import sio.projetbuffteauv3.entities.Salle;
import sio.projetbuffteauv3.entities.Soutien;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServicesAdministrateur {
    private Connection uneCnx;
    private PreparedStatement ps;
    private ResultSet rs;
    LocalDate DateActuelle = LocalDate.now();
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
        // Sélectionnez les sous-matières de la matière concernée
        String query = "SELECT sous_matiere FROM matiere WHERE designation = ?";
        try {
            PreparedStatement psSelect = uneCnx.prepareStatement(query);
            psSelect.setString(1, matiere);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                String sousMatieres = rs.getString("sous_matiere");
                if (sousMatieres != null) {
                    // Divisez les sous-matières en utilisant '#' comme délimiteur
                    List<String> items = new ArrayList<>(Arrays.asList(sousMatieres.split("#")));

                    // Remplacez l'ancienne sous-matière par la nouvelle
                    int index = items.indexOf(ancienneSousMatiere);
                    if (index != -1) {
                        items.set(index, nouvelleSousMatiere);
                    }

                    // Réassemblez la chaîne avec le délimiteur
                    String updatedSousMatieres = "#" + String.join("#", items);

                    // Mettez à jour la base de données
                    String updateQuery = "UPDATE matiere SET sous_matiere = ? WHERE designation = ?";
                    PreparedStatement psUpdate = uneCnx.prepareStatement(updateQuery);
                    psUpdate.setString(1, updatedSousMatieres);
                    psUpdate.setString(2, matiere);
                    int rowsAffected = psUpdate.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("La sous-matière a été renommée avec succès.");
                    } else {
                        System.out.println("Aucune sous-matière n'a été renommée.");
                    }
                } else {
                    System.out.println("Aucune sous-matière existante pour la matière.");
                }
            } else {
                System.out.println("La matière spécifiée n'a pas été trouvée.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du renommage de la sous-matière : " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void renommerSalle(int idSalle, int nouveauCodeSalle) throws SQLException {
        String nouveauNomSalle = "Salle " + nouveauCodeSalle;

        String query = "UPDATE salle SET code_salle = ? WHERE id = ?";

        ps = uneCnx.prepareStatement(query);
        ps.setString(1, nouveauNomSalle);
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
    public ObservableList<Matiere> getSousMatiereFromCompetence(int idCompetence) throws SQLException {
        ObservableList<Matiere> sousMatieres = FXCollections.observableArrayList();
        String query = "SELECT sous_matiere FROM competence WHERE id = ?";

        try (Connection connection = ConnexionBDD.getCnx();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCompetence);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String sousMatiere = resultSet.getString("sous_matiere");
                // Créer un objet Matiere avec la matiere correspondante
                Matiere matiere = new Matiere("", sousMatiere); // Vous devez fournir la matière correspondante
                sousMatieres.add(matiere);
            }
        } catch (SQLException e) {
            // Gérer l'exception SQLException
            e.printStackTrace();
        }

        return sousMatieres;
    }


    public int GetIdMatiere(String nomMatiere) throws SQLException {
        ps = uneCnx.prepareStatement("SELECT matiere.id FROM matiere WHERE matiere.designation=?");
        ps.setString(1,nomMatiere);
        rs = ps.executeQuery();

        // Vérifier si l'ensemble de résultats contient des données
        if (rs.next()) {
            int idMatiere = rs.getInt(1);
            return idMatiere;
        } else {
            // Gérer le cas où aucun résultat n'est trouvé
            throw new SQLException("Aucune matière trouvée pour la désignation : " + nomMatiere);
        }
    }


    public ObservableList<Demande>getDemandesWithStatusDeux() throws SQLException {
        ObservableList<Demande> demandes = FXCollections.observableArrayList();
        PreparedStatement ps = uneCnx.prepareStatement("SELECT demande.id, demande.sous_matiere " +
                "FROM demande " +
                "WHERE demande.status = 2");

        ResultSet resultSet = ps.executeQuery(); // Exécuter la requête SELECT

        // Parcourir les résultats du ResultSet
        while (resultSet.next()) {
            int idDemande = resultSet.getInt("id");
            String sousMatiere = resultSet.getString("sous_matiere");
            demandes.add(new Demande("", sousMatiere, idDemande, ""));
        }

        // Fermeture explicite du ResultSet et de la PreparedStatement
        resultSet.close();
        ps.close();

        return demandes;
    }
    public void ajouterSalle(int idDemande, int idSalle) throws SQLException {
        // Connexion à la base de données
        System.out.println("Connexion à la base de données établie : " + !uneCnx.isClosed());

        // Préparation de la requête pour insérer la salle dans la table soutien
        PreparedStatement ps = uneCnx.prepareStatement(
                "UPDATE soutien SET id_salle = ?, status = 2 WHERE id_demande = ?");


        ps.setInt(1, idSalle);
        ps.setInt(2, idDemande);


        ps.executeUpdate();
        ps.close();

        // Préparation de la requête pour mettre à jour le statut de la demande dans la table demande
        PreparedStatement psDemande = uneCnx.prepareStatement(
                "UPDATE demande SET status = 3 WHERE id = ?");

        // Paramétrage de la PreparedStatement pour la mise à jour du statut de la demande dans la table demande
        psDemande.setInt(1, idDemande);

        // Exécution de la requête
        psDemande.executeUpdate();

        // Fermeture explicite de la PreparedStatement
        psDemande.close();
    }

}
