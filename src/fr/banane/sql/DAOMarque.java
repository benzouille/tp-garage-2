package fr.banane.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import voiture.Marque;

public class DAOMarque extends DAO<Marque> {

	public DAOMarque(Connection conn) {
		super(conn);
	}

	public boolean create(Marque obj) {
		return false;
	}

	public boolean delete(Marque obj) {
		return false;
	}

	public boolean update(Marque obj) {
		return false;
	}

	public Marque find(int id) {
		Marque marque = null;
		try {
			ResultSet result = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM marque WHERE id = " + id);
			if(result.first())
				marque = new Marque();
			marque.setId(id);
			marque.setNom(result.getString("NOM"));
		} catch (SQLException e) {
			e.printStackTrace();	
		}
		return marque;
	}

}
