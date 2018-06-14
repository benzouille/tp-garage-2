package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import fr.banane.observable.Observable;
import fr.banane.observable.Observateur;
import fr.banane.sql.DAOOption;
import fr.banane.sql.DAOVehicule;
import fr.ocr.sql.HsqldbConnection;
import voiture.Vehicule;
import voiture.option.Option;



//Notre listener pour le bouton
public class ButtonListener implements ActionListener, Observable {
	
	protected static final Connection conn = HsqldbConnection.getInstance();
	
	protected int column, row, id;
	protected JTable table;

	protected ArrayList<Observateur> listObservateur = new ArrayList<Observateur>();

	public void setColumn(int col) {
		this.column = col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public void actionPerformed(ActionEvent event) {
		/*
		Ici, il vous faut définir comment supprimer un véhicule
		et n'oubliez pas de supprimer toutes les options de ceui-ci..
		voici les requetes :
		DELETE FROM VEHICULE_OPTION WHERE VEHICULE_OPTION.ID_VEHICULE = "id vehicule de la ligne selectionnée";
		DELETE FROM VEHICULE WHERE VEHICULE.ID = "id vehicule de la ligne selectionnée";
		 */

		JOptionPane jop = new JOptionPane();
		int select = jop.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ce véhicule ? ", "Supression d'un véhicule", JOptionPane.YES_NO_CANCEL_OPTION);

		if (select == 0) {
			System.out.println("0");
			this.id = Integer.valueOf((String) table.getValueAt(row, this.column-2));
			System.out.println(this.id);
			
			//supression des options du véhicule par le DAOOption
			DAOOption daoOption = new DAOOption(conn);
			Option option = new Option();
			option.setId(this.id);
			daoOption.delete(option);
			//supression du véhicule par le DAOVehicule
			DAOVehicule daoVehicule = new DAOVehicule(conn);
			Vehicule vehicule = new Vehicule();
			vehicule.setId(this.id);
			//daoVehicule.delete(vehicule);
			updateObservateur();
		}
		else 
			System.out.println("annulé");
	}

	public boolean ObservateurExists(Observateur obs){
		return this.listObservateur.contains(obs);
	}
	
	public void addObservateur(Observateur obs) {
		if (!ObservateurExists(obs)) {	
			listObservateur.add(obs);
			this.updateObservateur();
		}
	}

	@Override
	public void updateObservateur() {
		// TODO Auto-generated method stub
		for(Observateur obs : listObservateur)
			obs.update("suppression");
		System.out.println("updateObs ButtonListener");
	}

	@Override
	public void delObservateur() {
		// TODO Auto-generated method stub
		
	}
}