package fr.banane.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import voiture.moteur.Moteur;

public class DAOMoteur extends DAO<Moteur> {

	public DAOMoteur(Connection conn) {
		super(conn);
	}

	public boolean create(Moteur obj) {
		return false;
	}

	public boolean delete(Moteur obj) {
		return false;
	}

	public boolean update(Moteur obj) {
		return false;
	}

	public Moteur find(int id) {
		Moteur moteur = null;
		DAOTypeMoteur typeMot = new DAOTypeMoteur(connect);
			int idTypeMot = -1;
			if (id == 0 || id == 1)
				idTypeMot = 0;
			else if (id == 2 || id == 3 || id == 4)
				idTypeMot = 1;
			else if (id == 5 || id == 6)
				idTypeMot = 2;
			else
				idTypeMot = 3;
		
		try {
			ResultSet result = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM MOTEUR WHERE id = " + id);
			if(result.first())
				moteur = new Moteur();
			moteur.setId(id);
			moteur.setCylindre(result.getString("CYLINDRE"));
			System.out.println("l'id du type moteur : " + idTypeMot);
			moteur.setType(typeMot.find(idTypeMot));
			moteur.setPrix(result.getDouble("PRIX"));
		} catch (SQLException e) {
			e.printStackTrace();	
		}
		return moteur;
	}
}
