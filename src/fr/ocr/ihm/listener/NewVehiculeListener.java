package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.banane.ihm.PopUpAjout;
import fr.banane.observable.Observateur;

/**
 * Classe de création d'un nouveau véhicule.
 * @author vifie
 *
 */
public class NewVehiculeListener implements ActionListener {

	private Observateur obs;

	public NewVehiculeListener(Observateur obs) {
		this.obs = obs;
	}

	public void actionPerformed(ActionEvent e) {
		PopUpAjout ajout = new PopUpAjout(null,"Ajout véhicule",true, obs);
		//-- OFA : initialiser l'observateur après avoir créé lla boite de dialogue est trop tard :
		//         On a déjà eu le temps d'ajouter un vehicule et de refermer la boite de dialogue.
	}
}
