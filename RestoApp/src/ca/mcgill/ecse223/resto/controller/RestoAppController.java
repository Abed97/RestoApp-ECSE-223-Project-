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
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.Rating;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;
import ca.mcgill.ecse223.resto.view.MenuRating;

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
		for (int i = 0; i <= tables.size() - 2; i++) {
			if (tables.get(i).getStatus() != tables.get(i + 1).getStatus()) {
				throw new InvalidInputException("All selected tables need to be in the same state");
			}
		}

		// Show rating menu
		if (tables.get(0).getStatus() == Status.Ordered) {
			for (Table aTable : tables) {
				new MenuRating(aTable).setVisible(true);
			}
		}
		// Switch status of every table
		if (tables.get(0).getStatus() == Status.Available) {
			startOrder(tables);
		} else {
			for (Table sample : tables) {
				endOrder(sample.getOrder(sample.numberOfOrders() - 1));
			}
		}

	}

	/**
	 * Start order for table(s)
	 * 
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

	/**
	 * End order for table(s)
	 * 
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

		int nbTables = tables.size() - 1;
		for (int i = nbTables; i >= 0; i--) {
			Table table = tables.get(i);
			if (table.numberOfOrders() > 0 && table.getOrder(table.numberOfOrders() - 1).equals(order)) {
				table.endOrder(order);
			}
			System.out.println(table.numberOfOrders());
		}

		if (allTablesAvailableorDifferentCurrentOrder(tables, order)) {
			r.removeCurrentOrder(order);
		}

		RestoAppApplication.save();
	}

	// Rating an order item
	public static void rateOrderItem(OrderItem orderItem, int stars) throws InvalidInputException {
		RestoApp r = RestoAppApplication.getRestoApp();
		Rating rating = null;
		boolean ratingSet = false, wasAdded = false;

		if (r == null)
			throw new InvalidInputException("No RestoApp object");

		// Add rating to the order item
		rating = orderItem.addRating(stars);

		if (rating == null)
			throw new InvalidInputException("Rating not added");

		//RestoAppApplication.save();
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
		if ((currentdate.after(date)) || ((currentdate == date) && (currenttime.after(time)))) {
			throw new InvalidInputException("Please enter a valid date/time");
		}

		if (!contactEmailAddress.contains("@") || !contactEmailAddress.contains(".")) {
			throw new InvalidInputException("Please enter a valid e-mail address");
		}

		if (!contactPhoneNumber.matches("[0-9]+")) {
			throw new InvalidInputException("Please enter a valid phone number");
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

	public static void addMenuItem(String name, ItemCategory category, double price) throws InvalidInputException {
		if (name.isEmpty() || name == null) {
			throw new InvalidInputException("Name cannot be empty");
		}
		if (category == null) {
			throw new InvalidInputException("Category cannot be empty");
		}
		if (price <= 0) {
			throw new InvalidInputException("Price has to be greater than 0");
		}
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		Menu menu = restoApp.getMenu();
		MenuItem menuItem;
		try {
			menuItem = menu.addMenuItem(name);
			menuItem.setItemCategory(category);
			PricedMenuItem pricedMenuItem = menuItem.addPricedMenuItem(price, restoApp);
			menuItem.setCurrentPricedMenuItem(pricedMenuItem);
			RestoAppApplication.save();
		} catch (Exception e) {
			throw new InvalidInputException("Menu item with the same name already exists");
		}
	}

	public static void removeMenuItem(MenuItem menuItem) throws InvalidInputException {
		if (menuItem == null) {
			throw new InvalidInputException("Menu item cannot be empty");
		}
		boolean current = menuItem.hasCurrentPricedMenuItem();
		if (!current) {
			throw new InvalidInputException("This menu item doesn't have any priced menu items");
		}
		menuItem.setCurrentPricedMenuItem(null);
		RestoAppApplication.save();

	}

	public static List<OrderItem> getOrderItems(Table table) throws InvalidInputException {
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		List<Table> currentTables = restoApp.getCurrentTables();

		if (currentTables.isEmpty() || table == null) {
			throw new InvalidInputException("Table doesn't have seats");
		}

		Boolean current = currentTables.contains(table);
		if (current == false) {
			throw new InvalidInputException("Table is not a current table");
		}

		// status type
		Status status = table.getStatus();
		if (status == Status.Available) {
			throw new InvalidInputException("Table should be in use");
		}

		Order lastOrder = null;
		// Get last ordered object
		if (table.numberOfOrders() > 0) {
			lastOrder = table.getOrder(table.numberOfOrders() - 1);
		} else {
			throw new InvalidInputException("This table must have an order");
		}
		List<Seat> currentSeats = table.getCurrentSeats();
		List<OrderItem> result = new ArrayList<OrderItem>();
		for (Seat seat : currentSeats) {
			List<OrderItem> orderItems = seat.getOrderItems();
			for (OrderItem orderItem : orderItems) {
				// not sure abt order type
				Order order = orderItem.getOrder();
				if (lastOrder.equals(order) && !result.contains(orderItem)) {
					result.add(orderItem);
				}
			}
		}
		return result;
	}

	public static void orderMenuItem(MenuItem menuItem, int quantity, List<Seat> seats) throws InvalidInputException {
		if (menuItem == null || seats == null || seats.size() == 0 || quantity <= 0) {
			throw new InvalidInputException("please fill in all the required fields");
		}
		RestoApp r = RestoAppApplication.getRestoApp();
		Boolean current = menuItem.hasCurrentPricedMenuItem();
		if (current == false) {
			throw new InvalidInputException("the selected menu item is obsolete");
		}
		List<Table> currentTables = new ArrayList<Table>();
		currentTables = r.getCurrentTables();
		Order lastOrder = null;
		for (Seat seat : seats) {
			Table table = seat.getTable();
			current = currentTables.contains(table);
			if (current == false) {
				throw new InvalidInputException("the selected table is obsolete");
			}
			List<Seat> currentSeats = new ArrayList<Seat>();
			currentSeats = table.getCurrentSeats();
			current = currentSeats.contains(seat);
			if (current == false) {
				throw new InvalidInputException("one or more of the selected seats is obsolete");
			}
			if (lastOrder == null) {
				if (table.numberOfOrders() > 0) {
					lastOrder = table.getOrder(table.numberOfOrders() - 1);
				} else {
					throw new InvalidInputException("one or more tables/seats selected does not have an order");
				}
			} else {
				Order comparedOrder = null;
				if (table.numberOfOrders() > 0) {
					comparedOrder = table.getOrder(table.numberOfOrders() - 1);

				} else {
					throw new InvalidInputException("one more tables selected does not have an order");
				}
				if (!comparedOrder.equals(lastOrder)) {
					throw new InvalidInputException("the tables/seats selected  are not all in the same order");
				}
			}
		}
		if (lastOrder == null) {
			throw new InvalidInputException("system error. did not catch table without orders.");
		}
		PricedMenuItem pmi = menuItem.getCurrentPricedMenuItem();
		Boolean itemCreated = false;
		OrderItem newItem = null;
		for (Seat seat : seats) {
			Table table = seat.getTable();
			if (itemCreated) {
				table.addToOrderItem(newItem, seat);
			} else {
				OrderItem lastItem = null;
				if (lastOrder.numberOfOrderItems() > 0) {
					lastItem = lastOrder.getOrderItem(lastOrder.numberOfOrderItems() - 1);
				}

				table.orderItem(quantity, lastOrder, seat, pmi);
				if ((lastOrder.numberOfOrderItems() > 0)
						&& !(lastOrder.getOrderItem(lastOrder.numberOfOrderItems() - 1).equals(lastItem))) {
					itemCreated = true;
					newItem = lastOrder.getOrderItem(lastOrder.numberOfOrderItems() - 1);
				}
			}
		}
		if (itemCreated == false) {
			throw new InvalidInputException("item was not created due to input errors.");
		}
		RestoAppApplication.save();

	}

	public static void cancelOrderItem(OrderItem orderItem) throws InvalidInputException {
		if (orderItem == null) {
			throw new InvalidInputException("The orderItem doesn't exist");
		}

		List<Seat> seats = orderItem.getSeats();
		Order order = orderItem.getOrder();

		List<Table> tables = null;
		Order lastOrder = null;

		for (Seat seat : seats) {
			Table table = seat.getTable();
			if (table.minimumNumberOfOrders() > 0) {
				lastOrder = table.getOrder(table.minimumNumberOfOrders() - 1);
			} else {
				throw new InvalidInputException("Table does not have an order");
			}
			if (lastOrder.equals(order) && !tables.contains(table)) {
				tables.add(table);
			}
		}
		for (Table table : tables) {
			table.cancelOrderItem(orderItem);
		}
		RestoAppApplication.save();
	}

	public static void cancelOrder(Table table) throws InvalidInputException {
		if (table == null) {
			throw new InvalidInputException("The table doesn't exist");
		}

		RestoApp r = RestoAppApplication.getRestoApp();
		List<Table> currentTables = r.getCurrentTables();
		Boolean current = currentTables.contains(table);

		if (current == false) {
			throw new InvalidInputException("Table is not currently in use");
		} else {
			table.cancelOrder();
		}
		RestoAppApplication.save();
	}

}
