package fr.ocr.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe driver pour la liaison à la base de donnée
 * @author Ben
 *
 */
public class HsqldbConnection {
	// URL de connexion
	private String url = "jdbc:hsqldb:file:hsqldb/database/VEHICULE";
	// Nom du user
	private String user = "SA";
	// Mot de passe de l'utilisateur
	private String passwd = "";
	// Objet Connection
	private static Connection connect;
	private static HsqldbConnection instance = new HsqldbConnection();

	/**
	 * Constructeur privé
	 */
	private HsqldbConnection() {
		try {
			connect = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode d'accès au singleton verifie si la connection est déjà effectuée.
	 * @return connect
	 */
	public static Connection getInstance() {
		if (connect == null)
			instance = new HsqldbConnection();

		return connect;
	}
}
