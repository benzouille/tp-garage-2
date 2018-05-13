package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class NewVehiculeListener implements ActionListener {

	private JFrame frame;

	public NewVehiculeListener(JFrame f) {
		frame = f;
	}

	public void actionPerformed(ActionEvent e) {
		/*
		 
		 Vous devez d�finir cette m�thode afin d'afficher
		 une popup personnalis�e pour ainsi pouvoir cr�er un nouveau v�hicule
		 
		 */
		System.out.println("bouton ajouter vehicule");
	}
}
