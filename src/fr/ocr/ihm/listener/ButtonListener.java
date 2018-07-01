package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.banane.observable.Observable;
import fr.banane.observable.Observateur;
import fr.banane.sql.DAOOption;
import fr.banane.sql.DAOVehicule;
import fr.ocr.sql.HsqldbConnection;
import voiture.Vehicule;
import voiture.option.Option;



/**
 * Le listener pour le bouton SUPPRIMER dans la table VEHICULE
 * @author Benzouille
 *
 */
public class ButtonListener implements ActionListener, Observable {

	//-- Les logs
	private static final Logger logger = LogManager.getLogger();

	protected static final Connection conn = HsqldbConnection.getInstance(); // Pourquoi y a t'il une connexion ici ?

	protected int column, row, id;
	protected JTable table;
	protected Observateur obs; // Ajout OFA	
	private ArrayList<Observateur> listObservateur = new ArrayList<Observateur>();

	public void setColumn(int col) {
		this.column = col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	/**
	 * Fixer l'observateur après avoir initialisé la vaiable d'instance.
	 * @param table
	 */
	public void setObservateur(Observateur obs) {
		this.obs = obs;
		this.addObservateur(obs);
	}

	/**
	 * Supprimer un véhicule lors de l'appui sur le bouton [Supprimer]
	 */
	public void actionPerformed(ActionEvent event) {

		JOptionPane jop = new JOptionPane();
		int select = jop.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ce véhicule ? ", "Supression d'un véhicule", JOptionPane.YES_NO_CANCEL_OPTION);

		if (select == 0) {
			this.id = Integer.valueOf((String) table.getValueAt(row, this.column-2));
			logger.info("id dans la table à supprimer = " + this.id);

			//supression des options du véhicule par le DAOOption
			DAOOption daoOption = new DAOOption(conn);
			Option option = new Option();
			option.setId(this.id);
			daoOption.delete(option);
			//supression du véhicule par le DAOVehicule
			DAOVehicule daoVehicule = new DAOVehicule(conn);
			Vehicule vehicule = new Vehicule();
			vehicule.setId(this.id);
			daoVehicule.delete(vehicule);
			updateObservateur();
		}
		else 
			logger.info("Suppression d'un vhicule annulée"); //- OFA : Le System.out.println(... ou le logger, il faut choisir...
	}

	/**
	 * Elle sert à quoi ???
	 * @param obs
	 * @return
	 */
	public boolean ObservateurExists(Observateur obs){
		return this.listObservateur.contains(obs);
	}

	//---------------------------------------------------------------
	//-- Informer les abonnés qu'un nouveau véhicule à été ajouté
	//---------------------------------------------------------------
	@Override
	public void addObservateur(Observateur obs) {
		listObservateur.add(obs);
		
		// Trace OFA : Il n'y a pas d'observateur pour la vue de détail => cela explique le "if". Comme ViewDetailVehiculeListener dépend de ButtonListener, on passe
		if (obs != null)
			logger.info("L'objet " + obs.getClass().getName() + " s'est abonné au bouton de suppression.");
	}

	@Override
	public void updateObservateur() {
		for(Observateur unObservateur : listObservateur)
			unObservateur.update("suppression");
	}

	@Override
	public void delObservateur() {
		this.listObservateur = new ArrayList<Observateur>();
	}
}