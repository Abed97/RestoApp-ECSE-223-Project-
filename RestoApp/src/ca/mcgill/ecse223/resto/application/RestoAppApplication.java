package ca.mcgill.ecse223.resto.application;

import java.io.ObjectStreamClass;

import ca.mcgill.ecse223.resto.model.Rating;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.resto.view.MainMenu;
import ca.mcgill.ecse223.resto.view.MenuRating;

public class RestoAppApplication {
	private static RestoApp restoApp;
	private static String filename = "menu.resto";
	private static MainMenu menu;

	public static MainMenu getMenu() {
		return menu;
	}

	public static void main(String[] args) {
		getRestoApp();
		// start UI
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				MenuRating rating;
				//            	rating =  new MenuRating();
				//            	rating.setVisible(true);
				menu = new MainMenu();
				menu.setVisible(true);

			}
		});

	}

	public static RestoApp getRestoApp() {
		if (restoApp == null) {
			// load model
			restoApp = load();
		}

		return restoApp;
	}

	public static void save() {
		PersistenceObjectStream.serialize(restoApp);
		menu.validate();
		menu.repaint();
	}

	public static RestoApp load() {
		PersistenceObjectStream.setFilename(filename);
		restoApp = (RestoApp) PersistenceObjectStream.deserialize();
		// model cannot be loaded - create empty RestoApp
		if (restoApp == null) {
			restoApp = new RestoApp();
		} else {
			restoApp.reinitialize();
			restoApp.initializeRatings();
		}
		return restoApp;
	}

	public static void setFilename(String newFilename) {
		filename = newFilename;
	}

}
