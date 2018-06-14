package fr.banane.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Vehicule obj) {
		//Le véhicule 
		int idVehicule = -1;

		String queryVehicule = "INSERT INTO vehicule (marque, moteur, prix, nom) VALUES (" 
				+ obj.getMarque() + ", " + obj.getMoteur() + ", " + obj.getPrix() + ", " + "'" + obj.getNom() + "')";

		String queryOption ="INSERT INTO vehicule_option(vehicule_id, option_id) VALUES (?, ?)";

		try {
			this.connect.setAutoCommit(false);
			ResultSet resultVehicule = ((Connection) this.connect).createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(queryVehicule);
			logger.debug("result = " + resultVehicule);

			// Nous allons récupérer le prochain ID
			ResultSet nextID = connect.prepareStatement("CALL NEXT VALUE FOR seq_vehicule_id").executeQuery();
			if (nextID.next()) {
				idVehicule = nextID.getInt(1);
			}
			
			//Les options
			
			PreparedStatement prepSateOption = connect.prepareStatement(queryOption, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			List<Option> listeOption = obj.getOptions();
			for(Option option : listeOption) {
				prepSateOption.setInt(1, idVehicule);
				prepSateOption.setInt(2, option.getId());
				prepSateOption.executeUpdate();
			}
			this.connect.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Vehicule obj) {
		// TODO créer le DELETE
		Vehicule vehicule = new Vehicule();      

		try {
			ResultSet result = ((Connection) this.connect).createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("DELETE FROM vehicule WHERE id = " + obj.getId());
			logger.debug("result = "+result);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Vehicule obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vehicule find(int id) {
		Vehicule vehicule = new Vehicule();      
		DAOMarque marque = new DAOMarque(connect);
		DAOMoteur moteur = new DAOMoteur(connect);
		try {
			ResultSet result = (this.connect).createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM vehicule WHERE id = " + id);
			if(result.first())
				vehicule = new Vehicule();
			vehicule.setNom(result.getString("NOM"));
			System.out.println("id marque" + result.getInt("MARQUE"));
			vehicule.setMarque(marque.find(result.getInt("MARQUE")));
			System.out.println("id moteur" + result.getInt("MOTEUR"));
			vehicule.setMoteur(moteur.find(result.getInt("MOTEUR")));
			vehicule.setPrix(result.getDouble("PRIX"));
			//vehicule.setListOptions(listOptions);result.getString("option du vehicule");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vehicule;
	}

}
