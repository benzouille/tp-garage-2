package fr.banane.sql;

import java.sql.Connection;

import fr.ocr.sql.HsqldbConnection;

public class DAOFactory extends AbstractDAOFactory {
protected static final Connection conn = HsqldbConnection.getInstance();

	public DAOVehicule getVehicule() {
		return new DAOVehicule(conn);
	}

	public DAO getMarque() {
		return null;
	}

	public DAO getMoteur() {
		return null;
	}

	public DAO getTypeMoteur() {
		return null;
	}

	public DAO getOption() {
		return null;
	}

}
