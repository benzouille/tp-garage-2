package fr.banane.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import voiture.Marque;
import voiture.moteur.TypeMoteur;

public class DAOTypeMoteur extends DAO<TypeMoteur>{

	public DAOTypeMoteur(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(TypeMoteur obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(TypeMoteur obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(TypeMoteur obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TypeMoteur find(int id) {
		TypeMoteur typeMoteur = null;
		try {
			ResultSet result = (this.connect).createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM TYPE_MOTEUR WHERE id = " + id);
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
