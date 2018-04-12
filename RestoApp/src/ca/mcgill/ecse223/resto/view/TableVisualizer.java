package ca.mcgill.ecse223.resto.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;

public class TableVisualizer extends JPanel {

	private static final long serialVersionUID = 5765666411683246454L;

	// UI elements
	private List<Rectangle2D> tableRectangles = new ArrayList<Rectangle2D>();
	private JButton btnConfirm;
	private JButton btnDelete;
	private JButton btnUpdate;
	private JButton btnMove;
	private JButton btnViewOrder;
	private JButton btnCancelOrder;
	public static HashMap<Seat, Integer> seatsh;
	private static final int SCALEFACTOR = 8;
	int lineHeight;

	// data elements
	private HashMap<Rectangle2D, Table> tables;
	Table selectedTable;
	private Boolean confirm = false;
	private List<Table> currentTables = new ArrayList<Table>();
	private static List<Table> selectedTables = new ArrayList<Table>();

	public TableVisualizer(List<Table> currentTables, JButton btnConfirm, JButton btnDeleteTable,
			JButton btnUpdateTableOr, JButton btnMoveTable, JButton btnViewOrder, JButton btnCancelOrder,HashMap<Seat, Integer> seatsh) {
		super();
		init();
		this.seatsh=seatsh;
		this.currentTables = currentTables;
		this.btnConfirm = btnConfirm;
		this.btnDelete = btnDeleteTable;
		this.btnUpdate = btnUpdateTableOr;
		this.btnMove = btnMoveTable;
		this.btnViewOrder = btnViewOrder;
		this.btnCancelOrder = btnCancelOrder;
		/**seatsh=new HashMap<Seat, Integer>();
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		int z=0;
		for(int i=0;i<restoApp.getTables().size();i++) {
			for(int k=0;k<restoApp.getTable(i).getSeats().size();k++) {
				seatsh.put(restoApp.getTable(i).getSeat(k),(Integer)z );
				z++;
			}

		}**/
	}

	/**
	 * Mouse listeners for mouse clicks (right or left) and mouse movement
	 * 
	 */
	private void init() {



		/**seatsh=new HashMap<Seat, Integer>();
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		int z=0;
		for(int i=0;i<restoApp.getTables().size();i++) {
			for(int k=0;k<restoApp.getTable(i).getSeats().size();k++) {
				seatsh.put(restoApp.getTable(i).getSeat(k),(Integer)z );
				z++;
			}

		}
		 **/
		tables = new HashMap<Rectangle2D, Table>();

		// When mouse clicked (right or left) on rectangle
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Boolean alreadySelected = false;
				int x = e.getX();
				int y = e.getY();
				// Check if mouse is clicked on rectangle
				for (Rectangle2D rectangle : tableRectangles) {
					if (rectangle.contains(x, y)) {
						// get table from clicked rectanlge from HashMap
						selectedTable = tables.get(rectangle);

						// If left click, select table
						if (SwingUtilities.isLeftMouseButton(e)) {
							for (Table aTable : selectedTables) {
								// If it was, set alreadySelected to true
								if (selectedTable == aTable) {
									alreadySelected = true;
								}
							}
							// If alreadySelected is true, remove from selectedTables, else add it
							if (alreadySelected) {
								selectedTables.remove(selectedTable);
							} else {
								selectedTables.add(selectedTable);
							}
							break;

							// If right click, open order page
						} else if (SwingUtilities.isRightMouseButton(e)) {
							if (selectedTable.getStatus() == Status.Available) {
								AddToOrderPage addToOrderPage = new AddToOrderPage(selectedTable);
								addToOrderPage.setVisible(true);
								repaint();
								break;
							}
						}
					}
				}
				// Repaint table visualizer
				repaint();
			}
		});

		// When mouse moved onto a rectangle
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				Cursor cursor;
				Reservation r = null;
				String s = "No reservation";
				// Check if mouse is moving on a rectangle
				for (Rectangle2D rectangle : tableRectangles) {
					if (rectangle.contains(x, y)) {
						selectedTable = tables.get(rectangle);

						// Get reservations of table the mouse is hovering over
						ArrayList<Reservation> tableReservations = new ArrayList<Reservation>();
						for (Reservation reservation : RestoAppApplication.getRestoApp().getReservations()) {
							if (reservation.getTables().contains(selectedTable)) {
								tableReservations.add(reservation);
							}
						}

						// If there are reservations
						if (tableReservations.size() > 0) {
							// Get first reservation
							r = tableReservations.get(0);
							// For every reservation
							for (Reservation res : tableReservations) {
								// Set reservation to r, if its date is before our currently selected
								// reservation's date
								if (res.getDate().before(r.getDate())) {
									r = res;
								}

								// Set reservation to r, if its time is before our currently selected
								// reservation's time
								else if (res.getDate().equals(r.getDate())) {
									if (res.getTime().before(r.getTime())) {
										r = res;
									}
								}
							}
							s = "Next Reservation: " + r.getDate().toString() + " ," + r.getTime().toString();

							// Otherwise there are no reservations
						} else {
							s = "No reservations";
						}
						cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
						setCursor(cursor);
						ToolTipManager.sharedInstance().setInitialDelay(0);
						setToolTipText(s);
						ToolTipManager.sharedInstance().setEnabled(true);
						break;
					}
					cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
					setCursor(cursor);
					ToolTipManager.sharedInstance().setEnabled(false);
				}
			}

			@Override
			public void mouseDragged(MouseEvent arg0) {

			}
		});

	}

	/**
	 * Run toggle method on selected tables
	 * 
	 * @throws InvalidInputException
	 */
	public void removeSelection() throws InvalidInputException {
		if (selectedTables.isEmpty())
			throw new InvalidInputException("No tables selected");

		for (Table aTable : selectedTables) {
			RestoAppController.removeTable(aTable);
		}

		selectedTables = new ArrayList<Table>();
		repaint();
	}

	/**
	 * Update selected table
	 * 
	 * @throws InvalidInputException
	 */
	public void updateSelection() throws InvalidInputException {
		if (selectedTables.isEmpty()) {
			throw new InvalidInputException("No tables selected");
		} else if (selectedTables.size() > 1) {
			throw new InvalidInputException("Only one table must be selected");
		}

		new UpdateTablePage(selectedTables.get(0).getNumber()).setVisible(true);

		// Clear selected tables
		selectedTables = new ArrayList<Table>();
		repaint();
	}


	public void OrderItem() throws InvalidInputException {
		if (selectedTables.isEmpty()) {
			throw new InvalidInputException("No tables selected");
		} 
		for (Table table: selectedTables) {
			if (table.getStatus().equals(Table.Status.Available)){
				throw new InvalidInputException("Table " + table.getNumber() + " does not have an order");
			}
		}
		List<Integer>tableNumbers=new ArrayList<Integer>();
		/*for(int i=0; i<selectedTables.size();i++) {
	tableNumbers.add(selectedTables.get(i).getNumber());
}*/
		new OrderItemPage(selectedTables,seatsh).setVisible(true);

		// Clear selected tables
		selectedTables = new ArrayList<Table>();
		repaint();
	}

	/**
	 * Cancel Order
	 * 
	 * @throws InvalidInputException
	 */
	public void cancelOrderSelection() throws InvalidInputException {
		if (selectedTables.isEmpty()) {
			throw new InvalidInputException("No tables selected");
		} else if (selectedTables.size() > 1) {
			throw new InvalidInputException("Only one table must be selected");
		}

		if (selectedTables.get(0).hasOrders()) {
			RestoAppController.cancelOrder(selectedTables.get(0));

		} else {
			throw new InvalidInputException("This table does not have an order");
		}

		// Clear selected tables
		selectedTables = new ArrayList<Table>();
		repaint();
	}


	/**
	 * Move selected table
	 * 
	 * @throws InvalidInputException
	 */
	public void moveSelection() throws InvalidInputException {
		if (selectedTables.isEmpty()) {
			throw new InvalidInputException("No tables selected");
		} else if (selectedTables.size() > 1) {
			throw new InvalidInputException("Only one table must be selected");
		}

		new RestoAppPage(selectedTables.get(0).getNumber()).setVisible(true);

		// Clear selected tables
		selectedTables = new ArrayList<Table>();
		repaint();
	}

	/**
	 * Run toggle method on selected tables
	 * 
	 * @throws InvalidInputException
	 */
	public void confirmSelection() throws InvalidInputException {
		if (selectedTables.isEmpty())
			throw new InvalidInputException("No tables selected");

		// Show rating menu
		if (selectedTables.get(0).getStatus() == Status.Ordered) {
			for (Table aTable : selectedTables) {
				new MenuRating(aTable).setVisible(true);
			}

		}
		else {
			RestoAppController.toggleUse(selectedTables);
		}
		

		selectedTables = new ArrayList<Table>();
		repaint();
	}

	/**
	 * Set the currentTables equal to our currentTables
	 * 
	 * @param currentTables
	 */
	public void setCurrentTables(List<Table> currentTables) {
		this.currentTables = currentTables;
		tables = new HashMap<Rectangle2D, Table>();
		repaint();
	}
	public  void viewOrder() throws InvalidInputException {
		List<OrderItem> orders = new ArrayList<>();
		if (selectedTables.isEmpty()) {
			throw new InvalidInputException("No tables selected");
		} else if (selectedTables.size() > 1) {
			throw new InvalidInputException("Only one table must be selected");
		}
		orders = RestoAppController.getOrderItems(selectedTables.get(0));

		new ViewOrder (orders, selectedTables.get(0)).setVisible(true);


	}

	/**
	 * Create rectangles on main menu
	 * 
	 * @param g
	 */
	private void doDrawing(Graphics g) {
		int nbTables = currentTables.size();
		if (nbTables != 0) {

			Graphics2D g2d = (Graphics2D) g.create();
			BasicStroke thickStroke = new BasicStroke(4);
			g2d.setStroke(thickStroke);
			g2d.setColor(Color.BLACK);
			g2d.setBackground(Color.WHITE);
			for (Table table : currentTables) {
				Rectangle2D rectangle = new Rectangle2D.Float(SCALEFACTOR * table.getX(), SCALEFACTOR * table.getY(),
						SCALEFACTOR * table.getWidth(), SCALEFACTOR * table.getLength());
				g2d.setColor(Color.WHITE);
				g2d.fill(rectangle);

				g2d.setColor(Color.BLACK);

				if (table.getStatus() != Status.Available) {
					g2d.setColor(Color.RED);
				}
				for (Table aTable : selectedTables) {
					if (aTable == table) {
						g2d.setColor(Color.BLUE);
						confirm = true;
					}
				}
				g2d.draw(rectangle);
				g2d.drawString("Table " + new Integer(table.getNumber()).toString(), (int) rectangle.getCenterX()- 14,
						(int) rectangle.getCenterY());

				g2d.drawString(new Integer(table.getSeats().size()).toString() + " seats",
						(int) rectangle.getCenterX() - 14, (int) rectangle.getCenterY() + 13);
				// g2d.drawString(table.getStatusFullName(), (int) (rectangle.getCenterX() -
				// 0.25 * rectangle.getWidth()),
				// (int) (rectangle.getCenterY() + 0.25 * rectangle.getHeight()));
				// ArrayList<Reservation> tableReservations = new ArrayList<Reservation>();
				// for (Reservation reservation :
				// RestoAppApplication.getRestoApp().getReservations()) {
				// if (reservation.getTables().contains(table)) {
				// tableReservations.add(reservation);
				// }
				// }
				// if (tableReservations.size() > 0) {
				// Reservation r = tableReservations.get(0);
				// for (int k = 0; k < tableReservations.size(); k++) {
				//
				// if (tableReservations.get(k).getDate().before(r.getDate())) {
				// r = tableReservations.get(k);
				//
				// }
				//
				// else if (tableReservations.get(k).getDate().equals(r.getDate())) {
				// if (tableReservations.get(k).getTime().before(r.getTime())) {
				// r = tableReservations.get(k);
				// }
				// }
				//
				// String s = "Next Reservation: " + r.getDate().toString() + " ," +
				// r.getTime().toString();
				// g2d.clearRect((int) (rectangle.getCenterX() - 0.5 * rectangle.getWidth()),
				// (int) (rectangle.getCenterY() - 15 - 0.25 * rectangle.getHeight()), 270, 15);
				// g2d.drawString(s, (int) (rectangle.getCenterX() - 0.5 *
				// rectangle.getWidth()),
				// (int) (rectangle.getCenterY() - 0.25 * rectangle.getHeight()));
				// }
				// }
				tableRectangles.add(rectangle);
				tables.put(rectangle, table);
				g2d.draw(rectangle);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
		confirmButton();
	}

	public void confirmButton() {
		if (confirm) {
			btnConfirm.setVisible(true);
			btnDelete.setVisible(true);
			btnUpdate.setVisible(true);
			btnMove.setVisible(true);
			btnViewOrder.setVisible(true);
			btnCancelOrder.setVisible(true);
		} else {
			btnConfirm.setVisible(false);
			btnDelete.setVisible(false);
			btnUpdate.setVisible(false);
			btnMove.setVisible(false);
			btnViewOrder.setVisible(false);
			btnCancelOrder.setVisible(false);
		}
		confirm = false;
	}

}