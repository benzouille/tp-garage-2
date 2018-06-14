package fr.banane.sql;

import java.sql.Connection;

import fr.ocr.sql.HsqldbConnection;

public class DAOFactory extends AbstractDAOFactory {
protected static final Connection conn = HsqldbConnection.getInstance();
	@Override
	public DAOVehicule getVehicule() {
		// TODO Auto-generated method stub
		return new DAOVehicule(conn);
	}

	@Override
	public DAO getMarque() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DAO getMoteur() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DAO getTypeMoteur() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DAO getOption() {
		// TODO Auto-generated method stub
		return null;
	}

}
