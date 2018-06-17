package fr.banane.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import voiture.moteur.TypeMoteur;

public class DAOTypeMoteur extends DAO<TypeMoteur>{

	public DAOTypeMoteur(Connection conn) {
		super(conn);
	}

	public boolean create(TypeMoteur obj) {
		return false;
	}

	public boolean delete(TypeMoteur obj) {
		return false;
	}

	public boolean update(TypeMoteur obj) {
		return false;
	}

	public TypeMoteur find(int id) {
		TypeMoteur typeMoteur = null;
		try {
			ResultSet result = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM TYPE_MOTEUR WHERE id = " + id);
			if(result.first())
				typeMoteur = new TypeMoteur();
			typeMoteur.setId(id);
			typeMoteur.setNom(result.getString("DESCRIPTION"));
		} catch (SQLException e) {
			e.printStackTrace();	
		}
		return typeMoteur;
	}

}
