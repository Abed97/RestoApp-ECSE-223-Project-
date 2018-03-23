package ca.mcgill.ecse223.resto.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;

public class RestoAppController {

	public RestoAppController() {
	}

	public static void createTable(int number, int x, int y, int width, int length, int numberOfSeats)
			throws InvalidInputException {
		if (x < 0 || y < 0 || number <= 0 || width <= 0 || length <= 0 || numberOfSeats <= 0) {
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
			// Do nothing
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void removeTable(Table currTable) throws InvalidInputException {
		// Make sure table is not null
		if (currTable == null)
			throw new InvalidInputException("Table is null");
		// Get restoApp object
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		// Check if table is reserved, if so return an error
		Boolean reserved = currTable.hasReservations();
		if (reserved)
			throw new InvalidInputException("Table is reserved");

		List<Order> currentOrders = currTable.getOrders();
		for (Order tempOrder : currentOrders) {
			// Get list of tables and orders of table
			List<Table> tables = tempOrder.getTables();
			if (tables.contains(currTable)) {
				throw new InvalidInputException("Table has orders");
			}
		}
		restoApp.removeCurrentTable(currTable);
		RestoAppApplication.save();
	}

	public static void moveTable(int number, int x, int y) throws InvalidInputException {
		String error = "";

		if (x < 0 || y < 0) {
			throw new InvalidInputException("Invalid negative input");
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		Table table = Table.getWithNumber(number);
		// check if null
		if (table == null) {
			error = error + "A table must be specified ";
		}
		int width = table.getWidth();
		int length = table.getLength();
		RestoApp restoApp = RestoAppApplication.getRestoApp();

		for (Table currentTable : restoApp.getCurrentTables()) {
			if (currentTable.doesOverlap(x, y, width, length)) {
				if (currentTable == table)
					continue;
				else
					throw new InvalidInputException("A table already exists at this location");

			}

		}

		table.setX(x);
		table.setY(y);
		RestoAppApplication.save();

	}

	public static List<ItemCategory> getItemCategories() {
		// List<ItemCategory> ics = new ArrayList<ItemCategory>();
		// for (int i=0 ; i<5; i++) {
		// ics.add(ItemCategory.)
		List<ItemCategory> CategoryList = Arrays.asList(ItemCategory.values());
		return CategoryList;
	}

	public static List<MenuItem> getMenuItems(ItemCategory itemCategory) {
		List<MenuItem> mis = new ArrayList<MenuItem>();
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		Menu menu = restoApp.getMenu();
		List<MenuItem> menuItems = menu.getMenuItems();
		for (MenuItem m : menuItems) {
			Boolean current = m.hasCurrentPricedMenuItem();
			ItemCategory c = m.getItemCategory();
			if (current && c.equals(itemCategory)) {
				mis.add(m);
			}
		}

		return mis;
	}

	public static void updateTableOrSeats(int oldNumber, int newNumber, int newNumSeats, boolean hasSameSeats)
			throws InvalidInputException {
		if (newNumber <= 0 || newNumSeats <= 0) {
			throw new InvalidInputException("Invalid negative input");
		}

		RestoApp restoApp = RestoAppApplication.getRestoApp();
		List<Table> currentTables = restoApp.getTables();
		if (oldNumber != newNumber) {
			for (Table currentTable : currentTables) {
				if (currentTable.getNumber() == newNumber) {
					throw new InvalidInputException("A table already has this number");
				}
			}
		}
		Table table = Table.getWithNumber(oldNumber);
		table.setNumber(newNumber);
		if (hasSameSeats == false) {
			int seats = table.numberOfCurrentSeats();
			int difSeats = Math.abs(seats - newNumSeats);

			if (seats < newNumSeats) {
				for (int i = 0; i < difSeats; i++) {
					table.addCurrentSeat(table.addSeat());
				}
			}
			if (seats > newNumSeats) {
				List<Seat> seatsList = table.getCurrentSeats();
				for (int i = difSeats; i > 0; i--) {
					table.removeCurrentSeat(seatsList.get(i));
				}
			}
		}
		RestoAppApplication.save();

	}

	// returns how many seats are avaliable(already been billed)
	public static int numberOfBilledSeatsOfTable(Table t) {
		// boolean billed= true;
		int count = 0;
		Order o = t.getOrder(t.numberOfOrders() - 1);
		List<Seat> seats = t.getCurrentSeats();
		List<Bill> bills = o.getBills(); // bills that belong to current order
		for (Seat s : seats) { // for each seat
			// boolean seatBilled=false;
			List<Bill> seatbills = s.getBills();
			for (Bill sb : seatbills) { // for each bill in seat bills
				if (bills.contains(sb)) { // if orderbills contains this bill
					// seatBilled= true; // then the seat is billed
					count++;
					break;
				} // break out of this seatbills

			}

		}
		return count;
	}

	/**
	 * Toggle wether table is in use or not
	 * 
	 * @param aTable
	 * @throws InvalidInputException
	 */
	public static void toggleUse(List<Table> tables) throws InvalidInputException {
		// Check all tables are all available or in use (in same state)
		for (int i = 0; i < tables.size() - 2; i++) {
			if (tables.get(i).getStatus() != tables.get(i + 1).getStatus()) {
				throw new InvalidInputException("All selected tables need to be available/in use");
			}
		}
		
		// Switch status of every table
		if (tables.get(0).getStatus() == Status.Available) {
			startOrder(tables);
		} else {
			//endOrder(tempOrder);
		}
	}

	/**
	 * Start order for table(s)
	 * @param tables
	 * @throws InvalidInputException
	 */
	public static void startOrder(List<Table> tables) throws InvalidInputException {
		if (tables == null)
			throw new InvalidInputException("No tables entered");
		// Get RestoApp and list of current tables
		RestoApp r = RestoAppApplication.getRestoApp();
		List<Table> currentTables = r.getCurrentTables();

		// Check if ordered tables are current tables
		for (Table table : tables) {
			Boolean current = false;
			current = currentTables.contains(table);

			// If table is not in currentTables, throw an exception
			if (!current)
				throw new InvalidInputException("Table is not a current table");
		}

		Boolean orderCreated = false;
		Order newOrder = null;

		for (Table table : tables) {
			if (orderCreated) {
				// If an order has already been created, add to it
				table.addToOrder(newOrder);
			} else {
				Order lastOrder = null;
				// Get last ordered object
				if (table.numberOfOrders() > 0) {
					lastOrder = table.getOrder(table.numberOfOrders() - 1);
				}

				// Initialize start of orders
				table.startOrder();

				if (table.numberOfOrders() > 0 && !table.getOrder(table.numberOfOrders() - 1).equals(lastOrder)) {
					orderCreated = true;
					newOrder = table.getOrder(table.numberOfOrders() - 1);
				}
			}
		}

		if (!orderCreated)
			throw new InvalidInputException("No order created");

		r.addCurrentOrder(newOrder);
		RestoAppApplication.save();
	}

	
	
	/** End order for table(s)
	 * @param order
	 * @throws InvalidInputException
	 */
	public static void endOrder(Order order) throws InvalidInputException {
		if (order == null)
			throw new InvalidInputException("No orders for table");
		// Get RestoApp and list of current orders
		RestoApp r = RestoAppApplication.getRestoApp();
		List<Order> currentOrders = r.getCurrentOrders();

		// Check if current orders contains our order
		Boolean current = false;
		current = currentOrders.contains(order);

		if (!current)
			throw new InvalidInputException("No such current order");

		List<Table> tables = order.getTables();

		for (Table table : tables) {
			if (table.numberOfOrders() < 0 && table.getOrder(table.numberOfOrders() - 1).equals(order)) {
				table.endOrder(order);
			}
		}

		if (allTablesAvailableorDifferentCurrentOrder(tables, order)) {
			r.removeCurrentOrder(order);
		}
		
		RestoAppApplication.save();
	}

	private static boolean allTablesAvailableorDifferentCurrentOrder(List<Table> tables, Order order) {
		boolean tableAvailable = true, differentCurrentOrder = false;
		for (Table aTable : tables) {
			if (aTable.getStatus() != Status.Available) {
				tableAvailable = false;
			}

			if (!aTable.getOrder(Table.minimumNumberOfOrders() - 1).equals(order)) {
				differentCurrentOrder = true;
			}
		}

		if (tableAvailable || differentCurrentOrder) {
			return true;
		} else {
			return false;
		}
	}

	public static void reserveTable(Date date, Time time, int numberInParty, String contactName,
			String contactEmailAddress, String contactPhoneNumber, List<Table> tables) throws InvalidInputException {
		Date currentdate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		Time currenttime = new java.sql.Time(Calendar.getInstance().getTime().getTime());

		if (date == null || time == null || contactName == null || numberInParty < 0 || contactEmailAddress == null
				|| contactPhoneNumber == null || tables == null || contactName.trim().length() == 0
				|| contactEmailAddress.trim().length() == 0 || contactPhoneNumber.trim().length() == 0) {
			throw new InvalidInputException("Please Enter all fields");

		}
		if (currentdate.after(date) || currenttime.after(time)) {
			throw new InvalidInputException("Please enter a valid date/time");
		}

		RestoApp restoApp = RestoAppApplication.getRestoApp();
		List<Table> currentTables = restoApp.getCurrentTables();

		int seatCapacity = 0;
		for (Table table : tables) {
			if (!currentTables.contains(table)) {
				throw new InvalidInputException("Table does not currently exist");
			}
			seatCapacity += table.numberOfCurrentSeats();
			List<Reservation> reservations = table.getReservations();
			for (Reservation r : reservations) {
				if (r.doesOverlap(date, time)) {
					throw new InvalidInputException("Table is already reserved at that time");
				}

			}
		}

		if (seatCapacity < numberInParty) {
			throw new InvalidInputException("Not Enough Seats");
		}
		// check if its correct (gunther)
		Table[] tablearray = tables.toArray(new Table[tables.size()]);
		Reservation res = new Reservation(date, time, numberInParty, contactName, contactEmailAddress,
				contactPhoneNumber, restoApp, tablearray);
		RestoAppApplication.save();

	}

}
