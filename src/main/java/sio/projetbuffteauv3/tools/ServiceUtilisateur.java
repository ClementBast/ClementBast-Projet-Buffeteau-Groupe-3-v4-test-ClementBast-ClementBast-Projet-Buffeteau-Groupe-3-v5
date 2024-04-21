package sio.projetbuffteauv3.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceUtilisateur {
    private Connection uneCnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public ServiceUtilisateur()
    {
        uneCnx = ConnexionBDD.getCnx();
    }


    public int getNiveauUtilisateurByMail(String email) throws SQLException {
        ps = uneCnx.prepareStatement("SELECT niveau FROM user WHERE email = ?");
        ps.setString(1, email);
        rs = ps.executeQuery();
        rs.next();
        int niveau = rs.getInt(1);
        return niveau;
    }
    public int getNiveauUtilisateurByIdDemande(int idDemande) throws SQLException {
        int niveauUtilisateur = -1; // Valeur par défaut si l'utilisateur n'est pas trouvé

        // Préparation de la requête pour obtenir le niveau de l'utilisateur à partir de l'identifiant de la demande
        String query = "SELECT u.niveau " +
                "FROM demande d " +
                "JOIN user u ON d.id_user = u.id " +
                "WHERE d.id = ?";

        try (PreparedStatement ps = uneCnx.prepareStatement(query)) {
            ps.setInt(1, idDemande);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    niveauUtilisateur = rs.getInt("niveau");
                }
            }
        }

        return niveauUtilisateur;
    }
    // Méthode pour vérifier si l'utilisateur peut porter assistance


    public int getIdUtilisateurByMail(String email) throws SQLException {
        ps = uneCnx.prepareStatement("SELECT user.id FROM user WHERE email = ?");
        ps.setString(1, email);
        rs = ps.executeQuery();
        rs.next();
        int unId = rs.getInt(1);
        return unId;
    }

    public String getRoleUtilisateurByMail(String email) throws SQLException {
        ps = uneCnx.prepareStatement("SELECT user.role FROM user WHERE email = ?");
        ps.setString(1, email);
        rs = ps.executeQuery();

        if (rs.next()) {
            String role = rs.getString("role");
            return role;
        } else {
            // Gestion d'erreur si aucun utilisateur avec cet e-mail n'est trouvé
            return null;
        }
    }
}