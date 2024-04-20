package sio.projetbuffteauv3;

import sio.projetbuffteauv3.tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphiqueController {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public GraphiqueController() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<String> GetAllDemande() {
        ArrayList<String> lesNomsDesActions = new ArrayList<>();
        try {
            ps = cnx.prepareStatement("SELECT id FROM demande");
            rs = ps.executeQuery();
            while(rs.next()) {
                lesNomsDesActions.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return lesNomsDesActions;
    }

    public HashMap<String, Integer> GetDatasGraphique2() {
        HashMap<String, Integer> datas = new HashMap<>();
        try {
            ps = cnx.prepareStatement("SELECT matiere.designation, COUNT(*) AS nb FROM soutien " +
                    "INNER JOIN demande ON soutien.id_demande = demande.id " +
                    "INNER JOIN matiere ON demande.id_matiere = matiere.id " +
                    "WHERE soutien.status = 3 " +
                    "GROUP BY matiere.designation");
            rs = ps.executeQuery();
            while(rs.next()) {
                datas.put(rs.getString(1), rs.getInt(2));
            }
            rs.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return datas;
    }

}
