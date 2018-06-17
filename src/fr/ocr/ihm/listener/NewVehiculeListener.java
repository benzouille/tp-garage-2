package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import fr.banane.ihm.PopUpAjout;
import fr.banane.observable.Observateur;

public class NewVehiculeListener implements ActionListener {

	private JFrame frame;
	private Observateur obs;

	public NewVehiculeListener(JFrame f, Observateur obs) {
		frame = f;
		this.obs = obs;
	}

	public void actionPerformed(ActionEvent e) {
		PopUpAjout ajout = new PopUpAjout(null,"Ajout véhicule",true, obs);
		ajout.addObservateur(obs);
	}
}
