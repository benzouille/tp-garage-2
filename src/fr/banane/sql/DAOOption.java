package fr.banane.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import voiture.option.Option;

public class DAOOption extends DAO<Option> {

	//-- Les logs
	private static final Logger logger = LogManager.getLogger();

	public DAOOption(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Option obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Option obj) {
		try {
			ResultSet result = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("DELETE FROM VEHICULE_OPTION WHERE VEHICULE_OPTION.ID_VEHICULE = " + obj.getId());
			logger.info("id à supprimer = "+ obj.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Option obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Option find(int id) {
		
		Option option = null;
		try {
			ResultSet result = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM option WHERE id = " + id);
			if(result.first())
				option = new Option();
			option.setId(id);
			option.setNom(result.getString("DESCRIPTION"));
			option.setPrix(result.getDouble("PRIX"));
		} catch (SQLException e) {
			e.printStackTrace();	
		}
		return option;
	}
}
