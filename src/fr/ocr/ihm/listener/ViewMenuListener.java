package fr.ocr.ihm.listener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import fr.ocr.sql.DAOTableFactory;
import fr.ocr.sql.DatabaseTable;
import fr.ocr.sql.HsqldbConnection;

/**
 * Classe permettant de g�rer l'affichage des diff�rentes tables de la BDD en
 * fonction du menu cliqu�
 * 
 * @author cysboy
 */
public class ViewMenuListener implements ActionListener {
	DatabaseTable table;
	JFrame frame;

	public ViewMenuListener(JFrame f, DatabaseTable tab) {
		table = tab;
		frame = f;
	}

	public void actionPerformed(ActionEvent e) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new JScrollPane(DAOTableFactory.getTable(HsqldbConnection.getInstance(), table, null)),
				BorderLayout.CENTER);
		frame.getContentPane().revalidate();

	}
}
