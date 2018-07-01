package fr.banane.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.banane.observable.Observable;
import fr.banane.observable.Observateur;
import fr.banane.sql.DAOMarque;
import fr.banane.sql.DAOMoteur;
import fr.banane.sql.DAOOption;
import fr.banane.sql.DAOVehicule;
import fr.ocr.sql.HsqldbConnection;
import voiture.Marque;
import voiture.Vehicule;
import voiture.moteur.Moteur;
import voiture.option.Option;

/**
 * Pop up d'ajout de vehicule avec les parametres suivant : nom, marque, type de moteur, prix, options.
 * @author Ben
 *
 */
public class PopUpAjout extends JDialog implements Observable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8277123847429828323L;
	protected static final Connection conn = HsqldbConnection.getInstance();
	
	//-- Les logs
	private static final Logger logger = LogManager.getLogger();
	//-- L'observateur
	protected ArrayList<Observateur> listObservateur = new ArrayList<Observateur>();

	private JLabel icon, nomLabel, marqueLabel, typeLabel, prixLabel;
	private JTextField nomField;
	private JComboBox<String> comboMarque, comboTypeMoteur;
	private JFormattedTextField prixField;
	private JCheckBox bDeToit, clim, gps, sieChauff, toitOuv;

	private String nom;
	private double prix;
	private int typeMoteur, marque;
	private boolean isOkData;

	/**
	 * Constructeur 
	 * @param parent
	 * @param title
	 * @param modal
	 */
	public PopUpAjout(JFrame parent, String title, boolean modal, Observateur obs){ //- OFA : Il faut initialiser l'observateur dans le constructeur au plus tôt...
		//On appelle le construteur de JDialog correspondant
		super(parent, title, modal);
		
		this.addObservateur(obs);
		
		this.setSize(850, 370);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
		this.setVisible(true);
	}

	/**
	 * Methode d'initialisation de la JDialog et des differents JPanel 
	 */
	public void initComponent() {
		//Icône
		icon = new JLabel(new ImageIcon("resources/images/initVehicule.png"));
		JPanel panIcon = new JPanel();
		panIcon.setBackground(Color.white);
		panIcon.setLayout(new BorderLayout());
		panIcon.add(icon);

		//Le nom
		JPanel panNom = new JPanel();
		panNom.setBackground(Color.white);
		panNom.setPreferredSize(new Dimension(270, 60));
		nomField = new JTextField();
		nomField.setPreferredSize(new Dimension(100, 25));
		panNom.setBorder(BorderFactory.createTitledBorder("Nom du vehicule"));
		nomLabel = new JLabel("Saisir un nom :");
		panNom.add(nomLabel);
		panNom.add(nomField);

		//La marque
		JPanel panMarque = new JPanel();
		panMarque.setBackground(Color.white);
		panMarque.setPreferredSize(new Dimension(270, 60));
		panMarque.setBorder(BorderFactory.createTitledBorder("Marque du vehicule"));
		comboMarque = new JComboBox();
		comboMarque.addItem("Pigeot");
		comboMarque.addItem("Reno");
		comboMarque.addItem("Troën");
		marqueLabel = new JLabel("Marque : ");
		panMarque.add(marqueLabel);
		panMarque.add(comboMarque);

		//Le type moteur
		JPanel panMot = new JPanel();
		panMot.setBackground(Color.white);
		panMot.setPreferredSize(new Dimension(540, 60));
		panMot.setBorder(BorderFactory.createTitledBorder("Type de moteur du vehicule"));
		comboTypeMoteur = new JComboBox();
		comboTypeMoteur.addItem("Essence 150 Chevaux");
		comboTypeMoteur.addItem("Essence 200 Chevaux");
		comboTypeMoteur.addItem("Diesel 150 Hdi");
		comboTypeMoteur.addItem("Diesel 180 Hdi");
		comboTypeMoteur.addItem("Diesel 250 Hdi");
		comboTypeMoteur.addItem("Hybride Essence/Nucléaire");
		comboTypeMoteur.addItem("Hybride Essence/Eolienne");
		comboTypeMoteur.addItem("Electrique 100 Kw");
		comboTypeMoteur.addItem("Electrique 1000 Kw");
		typeLabel = new JLabel("Type de moteur : ");
		panMot.add(typeLabel);
		panMot.add(comboTypeMoteur);

		//Le prix 
		JPanel panPrix = new JPanel();
		panPrix.setBackground(Color.white);
		panPrix.setPreferredSize(new Dimension(540, 60));
		panPrix.setBorder(BorderFactory.createTitledBorder("prix du vehicule"));
		prixField = new JFormattedTextField(NumberFormat.getNumberInstance());
		prixLabel = new JLabel("Prix : ");
		prixField.setPreferredSize(new Dimension(100, 25));
		panPrix.add(prixLabel);
		panPrix.add(prixField);



		//Les options
		JPanel panOpt = new JPanel();
		panOpt.setBackground(Color.white);
		panOpt.setBorder(BorderFactory.createTitledBorder("Options disponibles"));
		panOpt.setPreferredSize(new Dimension(540, 60));
		bDeToit = new JCheckBox("Barre de toit");
		clim = new JCheckBox("Climatisation");
		gps = new JCheckBox("GPS");
		sieChauff = new JCheckBox("Siège chauffant");
		toitOuv = new JCheckBox("Toit ouvrant");

		panOpt.add(bDeToit);
		panOpt.add(clim);
		panOpt.add(gps);
		panOpt.add(sieChauff);
		panOpt.add(toitOuv);

		JPanel content = new JPanel();
		content.setBackground(Color.white);
		content.add(panNom);
		content.add(panMarque);
		content.add(panMot);
		content.add(panPrix);
		content.add(panOpt);

		JPanel control = new JPanel();
		JButton okBouton = new JButton("OK");

		okBouton.addActionListener(new Run());

		JButton cancelBouton = new JButton("Annuler");
		cancelBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}      
		});

		control.add(okBouton);
		control.add(cancelBouton);

		this.getContentPane().add(panIcon, BorderLayout.EAST);
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
	} 

	/**
	 * Verifie l'integrité des données et les formate pour le pojo Vehicule.
	 * Si les données ne sont pas bonnes empêche la fermeture de la fenêtre et ouvre un popup d'explication
	 */
	private void acurateData() {

		isOkData = true;
		nom = nomField.getText();
		if(nom.equals("")) {
			logger.debug("champ nom : vide");
			JOptionPane.showMessageDialog(null, "Erreur ! \n Veuillez entrer un nom.", "ERREUR", JOptionPane.ERROR_MESSAGE);
			isOkData = false;
		}

		marque = comboMarque.getSelectedIndex();
		typeMoteur = comboTypeMoteur.getSelectedIndex();

		try {
			prix = ((Number) prixField.getValue()).doubleValue(); 
		}catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur ! \n Veuillez entrer un prix.", "ERREUR", JOptionPane.ERROR_MESSAGE);
			isOkData = false;
			logger.debug("champ prix : vide");
		}
	}

	/**
	 * Envoie les données à la BDD
	 */
	public void sendSQLData() {
		//TODO utiliser DAOVehicule pour envoyer les données

		/*
		 * Par le DAO recuperer le typeMoteur par le parametre Id pour le transmetre au Moteur avec le parametre Id
		 * les options : si une option selectionnée recuperer le nom le mettre dans un arrayList<option>
		 * créer l'objet véhicule vide dans la bdd avec :
		 * On démarre notre transction
		 * connect.setAutoCommit(false);
		 * Nous allons récupérer le prochain ID
		 * ResultSet nextID = connect.prepareStatement("CALL NEXT VALUE FOR seq_vehicule_id").executeQuery();
		 * if (nextID.next()) {
		 * int ID = nextID.getInt(1);
		 * }
		 * 
		 * Puis on ajoute toute les variables string nom, Marque marque, Moteur moteur, Double prix, ArrayList<Option> options
		 * pour finir on l'envoie dans la bdd par le DAOVehicule create
		 */

		//création des objets pour le vehicule
		//le moteur
		DAOMoteur daoM = new DAOMoteur(conn);
		Moteur moteur = daoM.find(typeMoteur);
		//la marque
		DAOMarque daoMa = new DAOMarque(conn);
		Marque marqueVeh = daoMa.find(marque);
		//les options
		DAOOption daoOpt = new DAOOption(conn);
		List<Option> listOptions = new ArrayList<>();
		if(getToitOuv().isSelected())
			listOptions.add(daoOpt.find(0));
		if(getClim().isSelected())
			listOptions.add(daoOpt.find(1));
		if(getGps().isSelected())
			listOptions.add(daoOpt.find(2));
		if(getSieChauff().isSelected())
			listOptions.add(daoOpt.find(3));
		if(getbDeToit().isSelected())
			listOptions.add(daoOpt.find(4));
		
		Vehicule v = new Vehicule(nom, marqueVeh, moteur, getPrix(),listOptions );
		logger.debug("le vehicule crée : " +v.toString());
		
		
		DAOVehicule daoV = new DAOVehicule(conn);
		daoV.create(v);
		logger.debug("nom : ." + nom + ". \n" +" les options : " + getbDeToit().isSelected() + ", "+ getClim().isSelected() + ", " + getGps().isSelected() + ", " + getSieChauff().isSelected() + ", " + getToitOuv().isSelected() + ".");
	}

	public String getNom() {return nom;}
	public void setNom(String nom) {this.nom = nom;}

	public int getMarque() {return marque;}
	public void setMarque(int marque) {this.marque = marque;}

	public int getTypeMoteur() {return typeMoteur;}
	public void setTypeMoteur(int typeMoteur) {this.typeMoteur = typeMoteur;}

	public Double getPrix() {return prix;}
	public void setPrix(Double prix) {this.prix = prix;}

	public JCheckBox getbDeToit() {return bDeToit;}
	public void setbDeToit(JCheckBox bDeToit) {this.bDeToit = bDeToit;}

	public JCheckBox getClim() {return clim;}
	public void setClim(JCheckBox clim) {this.clim = clim;}

	public JCheckBox getGps() {return gps;}
	public void setGps(JCheckBox gps) {this.gps = gps;}

	public JCheckBox getSieChauff() {return sieChauff;}
	public void setSieChauff(JCheckBox sieChauff) {this.sieChauff = sieChauff;}

	public JCheckBox getToitOuv() {return toitOuv;}
	public void setToitOuv(JCheckBox toitOuv) {this.toitOuv = toitOuv;}


	/**
	 * Listener du bouton ok 
	 * @author Ben
	 *
	 */
	class Run implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			logger.info("On passe ici !");
			acurateData(); //-- OFA Rq : C'est cette méthode qui permet de fixer "isOKData"
			sendSQLData();
			if (isOkData) //- Les données ont déjà été envoyé à la base de données et on teste encore si les données sont bonnes ...ce n'est pas un peu tard...c'est inutile ce test non ? 
				setVisible(false);
			
			updateObservateur(); //-- OFA : Le mieux est tout de même de rafraichir l'écran une fois que la base de données à été renseignée... Tu le faisais avant !
		}
	}

	public void addObservateur(Observateur obs) {
		listObservateur.add(obs);
		
		logger.info("L'objet " + obs.getClass().getName() + " s'est abonné au bouton d'ajout : " + this.getClass().getName() + ".");
	}

	public void updateObservateur() {
		logger.info("listObservateur - Taille = " + listObservateur.size() ); // OFA
		for(Observateur obs : listObservateur)
			obs.update("ajout");
	}

	public void delObservateur() {
		listObservateur = new ArrayList<Observateur>();
	}
}