package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import fr.banane.ihm.PopUpAjout;

public class NewVehiculeListener implements ActionListener {

	private JFrame frame;

	public NewVehiculeListener(JFrame f) {
		frame = f;
	}

	public void actionPerformed(ActionEvent e) {
		PopUpAjout ajout = new PopUpAjout(null,"Ajout véhicule",true);
		//System.out.println("bouton ajouter vehicule");
	}
}
