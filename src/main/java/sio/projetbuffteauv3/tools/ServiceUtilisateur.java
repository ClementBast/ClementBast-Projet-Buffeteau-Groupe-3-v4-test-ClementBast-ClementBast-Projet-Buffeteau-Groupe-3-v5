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