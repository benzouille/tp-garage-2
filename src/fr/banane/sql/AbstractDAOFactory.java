package fr.banane.sql;



public abstract class AbstractDAOFactory {
	public static final int DAO_FACTORY = 0;
	public static final int CONSOLE_DAO_FACTORY = 1;
	
	//Retourne un objet Vehicule interagissant avec la BDD
	  public abstract DAO getVehicule();
	  
	//Retourne un objet Vehicule interagissant avec la BDD
	  public abstract DAO getMarque();
	  
	//Retourne un objet Vehicule interagissant avec la BDD
	  public abstract DAO getMoteur();
	  
	//Retourne un objet Vehicule interagissant avec la BDD
	  public abstract DAO getTypeMoteur();
	  
	//Retourne un objet Vehicule interagissant avec la BDD
	  public abstract DAO getOption();
	  
	  public static AbstractDAOFactory getFactory(int type){
		    switch(type){
		      case DAO_FACTORY:
		        return new DAOFactory();
		      case CONSOLE_DAO_FACTORY: 
		        return new ConsoleDAOFactory();
		      default:
		        return null;
		    }
		  }
}
