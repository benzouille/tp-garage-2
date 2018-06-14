package fr.banane.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import voiture.Marque;
import voiture.option.Option;

public class DAOMarque extends DAO<Marque> {

	public DAOMarque(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Marque obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Marque obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Marque obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Marque find(int id) {
		Marque marque = null;
		try {
			ResultSet result = (this.connect).createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM marque WHERE id = " + id);
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
