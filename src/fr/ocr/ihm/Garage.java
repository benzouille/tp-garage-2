package fr.ocr.ihm;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fr.banane.observable.Observateur;
import fr.ocr.ihm.listener.NewVehiculeListener;
import fr.ocr.ihm.listener.ViewMenuListener;
import fr.ocr.sql.DAOTableFactory;
import fr.ocr.sql.DatabaseTable;
import fr.ocr.sql.HsqldbConnection;

/**
 * Application qui permet de gerer un garage d'ajouter des vehicules modulable couplé à une base de donnée
 * @author Ben
 *
 */
public class Garage extends JFrame implements Observateur {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4971770858535210983L;

	//-- Les logs
		private static final Logger logger = LogManager.getLogger();

	//Les différents objets de notre IHM
		
	private	JScrollPane jTableau;
		
	private JMenuBar bar = new JMenuBar();
	private JMenu menuVehicule = new JMenu("Vehicule");
	private JMenuItem menuVehiculeAjouter = new JMenuItem("Ajouter");
	private JMenuItem menuVehiculeVoir = new JMenuItem("Voir");
	
	private JMenu menuOptionVehicule = new JMenu("Option vehicule");
	private JMenuItem menuOptionVehiculeVoir = new JMenuItem("Voir");

	private JMenu menuMarque = new JMenu("Marque");
	private JMenuItem menuMarqueVoir = new JMenuItem("Voir");

	private JMenu menuMoteur = new JMenu("Moteur");
	private JMenuItem menuMoteurVoir = new JMenuItem("Voir");

	private JMenu menuOption = new JMenu("Option");
	private JMenuItem menuOptionVoir = new JMenuItem("Voir");

	private JMenu menuTypemoteur = new JMenu("Type de moteur");
	private JMenuItem menuTypemoteurVoir = new JMenuItem("Voir");

	/**
	 * Constructeur par défaut qui permet d'initialiser la fenetre et le tableau récuperé sur la base de donnée par le biais de DAOTableFactory 
	 */
	public Garage() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("JTable");
		this.setSize(800, 400);
		initTableau();
		initMenu();
		logger.debug("Le contenu de la fenêtre a été initalisée");
	}

	public void initTableau() {
		// Données de notre tableau
		jTableau = new JScrollPane(DAOTableFactory.getTable(HsqldbConnection.getInstance(), DatabaseTable.VEHICULE, this));
				this.getContentPane().add(jTableau ,BorderLayout.CENTER);
				this.setLocationRelativeTo(null);
	}
	
	
	/**
	 * Méthode qui initialise la barre de menu et les differents boutons la composant
	 */
	private void initMenu() {
		
		@SuppressWarnings("unused")
		Observateur obs = this;
		
		menuVehicule.add(menuVehiculeVoir);
		menuVehicule.add(menuVehiculeAjouter);
		menuVehiculeAjouter.addActionListener(new NewVehiculeListener(this, obs));
		menuVehiculeAjouter.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_A, KeyEvent.CTRL_MASK + KeyEvent.SHIFT_DOWN_MASK));
		menuVehiculeVoir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				KeyEvent.CTRL_MASK + KeyEvent.SHIFT_DOWN_MASK));
		menuVehiculeVoir.addActionListener(new ViewMenuListener(this,
				DatabaseTable.VEHICULE));
		menuVehicule.setMnemonic('v');

		menuOptionVehicule.add(menuOptionVehiculeVoir);
		menuOptionVehicule.setMnemonic('p');
		menuOptionVehiculeVoir.addActionListener(new ViewMenuListener(this, DatabaseTable.VEHICULE_OPTION));
		
		menuMarque.add(menuMarqueVoir);
		menuMarque.setMnemonic('a');
		menuMarqueVoir.addActionListener(new ViewMenuListener(this, DatabaseTable.MARQUE));

		menuMoteur.add(menuMoteurVoir);
		menuMoteur.setMnemonic('m');
		menuMoteurVoir.addActionListener(new ViewMenuListener(this, DatabaseTable.MOTEUR));

		menuOption.add(menuOptionVoir);
		menuOption.setMnemonic('o');
		menuOptionVoir.addActionListener(new ViewMenuListener(this, DatabaseTable.OPTION));

		menuTypemoteur.add(menuTypemoteurVoir);
		menuTypemoteur.setMnemonic('t');
		menuTypemoteurVoir.addActionListener(new ViewMenuListener(this, DatabaseTable.TYPEMOTEUR));

		bar.add(menuVehicule);
		bar.add(menuOptionVehicule);
		bar.add(menuMarque);
		bar.add(menuMoteur);
		bar.add(menuOption);
		bar.add(menuTypemoteur);

		this.setJMenuBar(bar);
	}

	public void update(String update) {
		if (update.equals("suppression")  || update.equals("ajout")) {
			//TODO freeze avec le remove() et revalidate(), le doClick() gène le clique sur les boutons voir et supprimer, l'initTableau()
			//ne fait rien
			
			//menuVehiculeVoir.doClick();
			//jTableau.removeAll();
			//initTableau();
			//jTableau.revalidate();
			logger.debug(update);
		}
	}

	/**
	 * Le main de l'application
	 * @param args
	 */
	public static void main(String[] args) {
		Garage g = new Garage();
		g.setVisible(true);
	}

}
