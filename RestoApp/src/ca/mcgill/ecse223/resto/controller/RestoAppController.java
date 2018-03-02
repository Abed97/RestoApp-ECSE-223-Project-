package ca.mcgill.ecse223.resto.controller;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import java.util.*;

public class RestoAppController {

	public RestoAppController() {			
	}
	public  static  void  createTable(int  number,   int  x,  int  y,  int  width,  int  length,  int  numberOfSeats)  throws  InvalidInputException {
		if(x < 0 || y < 0 || number <= 0 || width <= 0 || length <= 0 || numberOfSeats <= 0) {
			throw new InvalidInputException("Invalid negative input");
		}


		RestoApp restoApp = RestoAppApplication.getRestoApp();
		List<Table> currentTables = restoApp.getCurrentTables();

		for (Table currentTable : currentTables) {
			if (currentTable.doesOverlap(x, y, width, length)) {
				throw new InvalidInputException("Table would overlap with existing table");
			}
		}
		try {
			Table table = new Table(number, x, y, width, length, restoApp);
			restoApp.addCurrentTable(table);
			for (int i = 0; i < numberOfSeats; i++) {
				Seat seat = new Seat(table);
				table.addCurrentSeat(seat);
			}
			RestoAppApplication.save();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new InvalidInputException(e.getMessage());
		}	
	}

	public static void moveTable(int number, int x, int y) throws InvalidInputException {
		String error = "";

		if ( x< 0 ) {
			error =  error +"The x coordinate of the table must be greater than zero. ";
		}
		if ( y< 0 ) {
			error =error+ "The y coordinate of the table must be greater than zero. ";
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		Table table = Table.getWithNumber(number);
		int width = table.getWidth();
		int length = table.getLength();
		RestoApp restoApp = RestoAppApplication.getRestoApp();

		for (Table currentTable : restoApp.getCurrentTables()) {
			if (currentTable.doesOverlap(x, y,width, length)) {

				throw new InvalidInputException("A table already exists at this location");
			}

		}

		table.setX(x);
		table.setY(y);
		RestoAppApplication.save();

	}
	public static void updateTableOrSeats(int oldNumber, int newNumber, int newNumSeats, boolean hasSameSeats) throws InvalidInputException {
		if (newNumber <= 0 || newNumSeats <= 0) {
			throw new InvalidInputException("Invalid negative input");
		}

		RestoApp restoApp = RestoAppApplication.getRestoApp();
		List<Table> currentTables = restoApp.getCurrentTables();

		for (Table currentTable : currentTables) {
			if(currentTable.getNumber() == newNumber) {
				throw new InvalidInputException("A table already has this number");
			}
		}
		Table table = restoApp.getCurrentTable(oldNumber);
		table.setNumber(newNumber);
		if (hasSameSeats == false) {
			int seats = table.numberOfCurrentSeats();
			int difSeats = Math.abs(seats - newNumSeats);

			if(seats < newNumSeats) {
				for (int i = 0; i < difSeats; i++ ) {
					table.addCurrentSeat(table.addSeat());
				}
			}
			if(seats > newNumSeats) {
				List<Seat> seatsList = table.getCurrentSeats();
				for (int i = 0; i < difSeats; i++ ) {
					table.removeCurrentSeat(seatsList.get(i));
				}
			}
		}
		RestoAppApplication.save();


	}
}
