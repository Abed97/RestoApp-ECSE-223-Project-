package ca.mcgill.ecse223.resto.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;

public class TableVisualizer extends JPanel {

	private static final long serialVersionUID = 5765666411683246454L;

	// UI elements
	private List<Rectangle2D> tableRectangles = new ArrayList<Rectangle2D>();
	private JButton btnConfirm;

	private static final int SCALEFACTOR = 8;
	int lineHeight;

	// data elements
	private HashMap<Rectangle2D, Table> tables;
	Table selectedTable;
	private Boolean confirm = false;
	private List<Table> currentTables = new ArrayList<Table>();
	private List<Table> selectedTables = new ArrayList<Table>();



	public TableVisualizer(List<Table> currentTables, JButton btnConfirm) {
		super();
		init();
		this.currentTables = currentTables;
		this.btnConfirm = btnConfirm;
	}

	private void init() {
		tables = new HashMap<Rectangle2D, Table>();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Boolean alreadySelected = false;
				int x = e.getX();
				int y = e.getY();
				for (Rectangle2D rectangle : tableRectangles) {
					if (rectangle.contains(x, y)) {
						selectedTable = tables.get(rectangle);
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
						}
						else if (SwingUtilities.isRightMouseButton(e)) {
							if (selectedTable.getStatus() == Status.Available) {
								AddToOrderPage addToOrderPage = new AddToOrderPage(selectedTable);
								addToOrderPage.setVisible(true);
								repaint();
								break;
							}
						}
					}
				}
				repaint();
			}
		});
	}

	public void confirmSelection() throws InvalidInputException {
		if (selectedTables.isEmpty())
			throw new InvalidInputException("No tables selected");

		RestoAppController.toggleUse(selectedTables);

		selectedTables = new ArrayList<Table>();
		repaint();
	}

	public void setCurrentTables(List<Table> currentTables) {
		this.currentTables = currentTables;
		tables = new HashMap<Rectangle2D, Table>();
		repaint();
	}

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
				g2d.drawString(new Integer(table.getNumber()).toString(), (int) rectangle.getCenterX(),
						(int) rectangle.getCenterY());
				g2d.drawString(table.getStatusFullName(), (int) (rectangle.getCenterX() - 0.25 * rectangle.getWidth()),
						(int) (rectangle.getCenterY() + 0.25 * rectangle.getHeight()));
				tableRectangles.add(rectangle);
				tables.put(rectangle, table);
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
		} else {
			btnConfirm.setVisible(false);
		}
		confirm = false;
	}

}