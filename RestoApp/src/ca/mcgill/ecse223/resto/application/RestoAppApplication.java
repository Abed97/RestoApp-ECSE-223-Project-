package ca.mcgill.ecse223.resto.application;

import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.resto.view.RestoAppPage;
import ca.mcgill.ecse223.resto.model.RestoApp;

public class RestoAppApplication {
	private static RestoApp restoApp;
	private static String filename = "data.r";
	
	public static void main(String[] args) {
		
		getRestoApp();
		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RestoAppPage().setVisible(true);
            }
        });
        
	}
	
	public static RestoApp getRestoApp() {
		if (restoApp == null) {
			// load model
			restoApp = load();
			restoApp.addTable(10, 3, 4, 6, 5);
					}
	
 		return restoApp;
	}
	
	public static void save() {
		PersistenceObjectStream.serialize(restoApp);
	}	
	public static RestoApp load() {
		PersistenceObjectStream.setFilename(filename);
		restoApp = (RestoApp) PersistenceObjectStream.deserialize();
		// model cannot be loaded - create empty BTMS
		if (restoApp == null) {
			restoApp = new RestoApp();
		}
		else {
			restoApp.reinitialize();
		}
		return restoApp;
	}
	public static void setFilename(String newFilename) {
		filename = newFilename;
	}
	
	
}
