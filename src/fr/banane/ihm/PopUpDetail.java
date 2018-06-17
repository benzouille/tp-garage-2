package fr.banane.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.banane.sql.DAOOption;
import fr.banane.sql.DAOVehicule;
import fr.ocr.sql.HsqldbConnection;
import voiture.Vehicule;
import voiture.option.Option;

public class PopUpDetail extends JDialog {
	
	//-- Les logs
			private static final Logger logger = LogManager.getLogger();
	//-- Connection
	protected static final Connection conn = HsqldbConnection.getInstance();
	
	private JLabel nomLabel, nom, marqueLabel, typeLabel, marque, typeMoteur, prixLabel, prix, prixTot, optionLabel, option, prixTotLabel;
	private PopUpDetail pan = this;
	private int id;
	private Vehicule v;
	
	public PopUpDetail(JFrame parent, String title, boolean modal, int id){
	    //On appelle le construteur de JDialog correspondant
	    super(parent, title, modal);
	    setSize(650, 420);
	    setLocationRelativeTo(null);
	    setResizable(false);
	    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    this.id = id;
	    reciveData();
	    initComponent();
	    setVisible(true);
	  }
	
	public void initComponent() {

	    //Le nom
	    JPanel panNom = new JPanel();
	    panNom.setBackground(Color.white);
	    panNom.setPreferredSize(new Dimension(270, 60));
	    nom = new JLabel(v.getNom());
	    nom.setPreferredSize(new Dimension(100, 25));
	    panNom.setBorder(BorderFactory.createTitledBorder("Nom du vehicule"));
	    nomLabel = new JLabel("Saisir un nom :");
	    panNom.add(nomLabel);
	    panNom.add(nom);

	    //La marque
	    JPanel panMarque = new JPanel();
	    panMarque.setBackground(Color.white);
	    panMarque.setPreferredSize(new Dimension(270, 60));
	    panMarque.setBorder(BorderFactory.createTitledBorder("Marque du vehicule"));
	    marqueLabel = new JLabel("Marque : ");
	    marque = new JLabel(v.getMarque().toString());
	    panMarque.add(marqueLabel);
	    panMarque.add(marque);

	  //Le type moteur
	    JPanel panMot = new JPanel();
	    panMot.setBackground(Color.white);
	    panMot.setPreferredSize(new Dimension(540, 60));
	    panMot.setBorder(BorderFactory.createTitledBorder("Type de moteur du vehicule"));
	    typeLabel = new JLabel("Type de moteur : ");
	    typeMoteur = new JLabel(v.getMoteur().toString());
	    panMot.add(typeLabel);
	    panMot.add(typeMoteur);
	    
	    //Le prix 
	    JPanel panPrix = new JPanel();
	    panPrix.setBackground(Color.white);
	    panPrix.setPreferredSize(new Dimension(540, 60));
	    panPrix.setBorder(BorderFactory.createTitledBorder("prix du vehicule"));
	    prix = new JLabel(v.getPrix().toString());
	    prixLabel = new JLabel("Prix : ");
	    prix.setPreferredSize(new Dimension(100, 25));
	    panPrix.add(prixLabel);
	    panPrix.add(prix);

	    

	    //Les options
	    JPanel panOpt = new JPanel();
	    panOpt.setBackground(Color.white);
	    panOpt.setBorder(BorderFactory.createTitledBorder("Options disponibles"));
	    panOpt.setPreferredSize(new Dimension(540, 60));
	    option = new JLabel(v.getOptions().toString());
	    optionLabel = new JLabel("Option selectionnée : ");
	    

	    panOpt.add(optionLabel);
	    panOpt.add(option);

	  //Prix total
	    prixTot = new JLabel(v.getPrixTotal().toString());
	    JPanel panPrixTot = new JPanel();
	    panPrixTot.setBackground(Color.GREEN);
	    panPrixTot.setBorder(BorderFactory.createTitledBorder("Prix total du véhicule"));
	    prixTotLabel = new JLabel("Prix total : ");
	    panPrixTot.setPreferredSize(new Dimension(540, 60));
	    panPrixTot.add(prixTotLabel);
	    panPrixTot.add(prixTot);
	    
	    JPanel content = new JPanel();
	    content.setBackground(Color.white);
	    content.add(panNom);
	    content.add(panMarque);
	    content.add(panMot);
	    content.add(panPrix);
	    content.add(panOpt);
	    content.add(panPrixTot);

	    
	    JPanel control = new JPanel();
	    JButton okBouton = new JButton("OK");
	    
	    okBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {        
	        setVisible(false);
	        pan.dispose();
	      }
	    });

	    control.add(okBouton);
	    
	    this.getContentPane().add(content, BorderLayout.CENTER);
	    this.getContentPane().add(control, BorderLayout.SOUTH);
	  } 
	
	public void reciveData() {
		DAOVehicule daoV = new DAOVehicule(conn);
		v = daoV.find(id);
		
		System.out.println(v.getNom() + " marque " + v.getMarque());
		logger.debug("id : " + id + " / " + v.toString());
	}
}