package ca.mcgill.ecse223.resto.application;

import ca.mcgill.ecse223.resto.model.RestoApp;
//import ca.mcgill.ecse.btms.view.BtmsPage;

public class RestoAppApplication {
	
	private static RestoApp restoApp;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new BtmsPage().setVisible(true);
            }
        });
        
	}

	public static RestoApp getRestoApp() {
		if (restoApp == null) {
			// load model
			// TODO
			// for now, we are just creating an empty BTMS
			restoApp = new RestoApp();
		}
 		return restoApp;
	}
	
}
