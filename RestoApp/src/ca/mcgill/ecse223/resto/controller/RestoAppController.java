package ca.mcgill.ecse223.resto.controller;

import ca.mcgill.ecse223.resto.application.*;
import ca.mcgill.ecse223.resto.model.*;
import java.util.*;

public class RestoAppController {

	public RestoAppController() {
	}
	
	public  static  void  createTable(int  number,   int  x,  int  y,  int  width,  int  length,  int  numberOfSeats)  throws  InvalidInputException {
		
		if(x < 0 || y < 0 || number <= 0 || width <= 0 || length <= 0 || numberOfSeats <= 0) {
			throw new InvalidInputException("Invalid negative input");
		}
		
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		ArrayList<Table> currentTables = (ArrayList<Table>) restoApp.getCurrentTables();
		
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new InvalidInputException(e.getMessage());
		}		
	}

}