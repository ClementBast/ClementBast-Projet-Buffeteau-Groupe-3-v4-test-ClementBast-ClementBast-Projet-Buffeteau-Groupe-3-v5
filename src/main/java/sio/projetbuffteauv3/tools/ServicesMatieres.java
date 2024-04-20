package sio.projetbuffteauv3.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sio.projetbuffteauv3.entities.Demande;
import sio.projetbuffteauv3.entities.Matiere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


public class ServicesMatieres {
    private Connection uneCnx ;
    private PreparedStatement ps;
    private ResultSet rs;

    public ServicesMatieres(){
        uneCnx = ConnexionBDD.getCnx();
    }
    public ObservableList<Matiere> getAllMatieres() throws SQLException{
        ObservableList<Matiere> lesMatieres = FXCollections.observableArrayList();

        PreparedStatement ps = uneCnx.prepareStatement("SELECT designation, sous_matiere FROM matiere ");
        rs = ps.executeQuery();

        while(rs.next())
        {
            Matiere laMatiere = new Matiere(rs.getString(1),rs.getString(2));
            lesMatieres.add(laMatiere);
        }
        return lesMatieres;
    }

    public ObservableList<Matiere> getAllSousMatieresByMatieres(String matiere) throws SQLException {
        ObservableList<Matiere> lesSousMatieres = FXCollections.observableArrayList();

        PreparedStatement ps = uneCnx.prepareStatement("SELECT designation, sous_matiere FROM matiere WHERE designation = ?");
        ps.setString(1, matiere);
        rs = ps.executeQuery();

        while (rs.next()) {
            String sousMatieresStr = rs.getString(2);
            if (sousMatieresStr != null && !sousMatieresStr.isEmpty()) {
                String[] lesSousMatSans = sousMatieresStr.split("#");
                for (String sousMatSans : lesSousMatSans) {
                    if (!sousMatSans.isEmpty()) {
                        Matiere sousMatMatiere = new Matiere(matiere, sousMatSans);
                        lesSousMatieres.add(sousMatMatiere);
                    }
                }
            }
        }
        return lesSousMatieres;
    }


}