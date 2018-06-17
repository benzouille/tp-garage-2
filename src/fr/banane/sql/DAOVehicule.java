package fr.banane.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import voiture.Vehicule;
import voiture.option.Option;

public class DAOVehicule extends DAO<Vehicule> {

	//-- Les logs
	private static final Logger logger = LogManager.getLogger();

	public DAOVehicule(Connection conn) {
		super(conn);
	}

	public boolean create(Vehicule obj) {
		//Le véhicule 
		int idVehicule = -1;

		String queryVehicule = "INSERT INTO vehicule (marque, moteur, prix, nom) VALUES (" 
				+ obj.getMarque().getId() + ", " + obj.getMoteur().getId() + ", " + obj.getPrix() + ", " + "'" + obj.getNom() + "')";
		logger.debug(queryVehicule);
		String queryOption ="INSERT INTO vehicule_option(id_vehicule, id_option) VALUES (?, ?)";

		try {
			connect.setAutoCommit(false);
			ResultSet resultVehicule = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(queryVehicule);
			logger.debug("result = " + resultVehicule);

			// Nous allons récupérer le prochain ID
			ResultSet nextID = connect.prepareStatement("CALL NEXT VALUE FOR seq_vehicule_id").executeQuery();
			if (nextID.next()) {
				idVehicule = nextID.getInt(1)-1;
			}

			//Les options

			PreparedStatement prepSateOption = connect.prepareStatement(queryOption, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			List<Option> listeOption = obj.getOptions();
			for(Option option : listeOption) {
				prepSateOption.setInt(1, idVehicule);
				prepSateOption.setInt(2, option.getId());
				prepSateOption.executeUpdate();
			}
			connect.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Vehicule obj) {     
		try {
			connect.setAutoCommit(false);
			ResultSet result = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("DELETE FROM vehicule WHERE id = " + obj.getId());
			logger.debug("result = "+result);
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update(Vehicule obj) {
		return false;
	}

	public Vehicule find(int id) {
		Vehicule vehicule = new Vehicule();      
		DAOMarque marque = new DAOMarque(connect);
		DAOMoteur moteur = new DAOMoteur(connect);
		DAOOption option = new DAOOption(connect);
		List<Option> listOptions;
		
		try {
			//Les options du vehicule
			ResultSet resultOpt = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(
					"SELECT * FROM vehicule_option WHERE id_vehicule = " + id);
	
			listOptions = new ArrayList<>();
			//recuperation du nombre de ligne
			while(resultOpt.next()) {
				System.out.println("id_option" + resultOpt.getInt("ID_OPTION"));
				listOptions.add(option.find(resultOpt.getInt("ID_OPTION")));
			}
			//le vehicule.
			ResultSet result = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM vehicule WHERE id = " + id);
			if(result.first())
				vehicule = new Vehicule();
			
			vehicule.setNom(result.getString("NOM"));
			vehicule.setMarque(marque.find(result.getInt("MARQUE")));
			vehicule.setMoteur(moteur.find(result.getInt("MOTEUR")));
			vehicule.setPrix(result.getDouble("PRIX"));
			vehicule.setListOptions(listOptions);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vehicule;
	}

}
