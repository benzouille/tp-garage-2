package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;

import fr.banane.ihm.PopUpDetail;

/**
 * Classe d'affichage du détail d'un véhicule.
 * @author vifie
 *
 */
public class ViewDetailVehiculeListener extends ButtonListener {
	private Integer id;

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//-- Récupération de l'ID du vehicule sur la ligne du bouton
		this.id = Integer.valueOf((String) table.getValueAt(row, this.column-1));
		
		PopUpDetail detail = new PopUpDetail(null,"Détail",true, id);
		detail.setVisible(true);
	}

	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}
	
	
}
