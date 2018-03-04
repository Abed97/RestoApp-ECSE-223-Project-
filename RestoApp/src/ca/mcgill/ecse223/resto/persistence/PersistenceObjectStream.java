package ca.mcgill.ecse223.resto.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;

public class PersistenceObjectStream {

	private static String filename;

	public static void serialize(Object object) {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(object);
			out.close();
			fileOut.close();
		} catch (Exception e) {
			throw new RuntimeException("Could not save data to file '" + filename + "'.");
		}

	}

	public static Object deserialize() {
		RestoApp o = null;
		ObjectInputStream in;
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			in = new ObjectInputStream(fileIn);
			o = (RestoApp) in.readObject();
			in.close();
			fileIn.close();
			for(Table table : o.getTables()) {
				 System.out.println("Table Nb: " + table.getNumber() + " (x, y) = " + "(" + table.getX() + ", " + table.getY() + ")" + " Width: " + table.getWidth() + " Length: " + table.getLength() + " Number of Seats: " + table.getSeats().size());
			  }
		} catch (Exception e) {
			o = null;
		}
		return o;
	}
	
	public static void setFilename(String newFilename) {
		filename = newFilename;
	}

}