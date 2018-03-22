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

import javax.swing.JPanel;

import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Table;

public class TableVisualizer extends JPanel {

	private static final long serialVersionUID = 5765666411683246454L;

	// UI elements
	private List<Rectangle2D> tableRectangles = new ArrayList<Rectangle2D>();
	private List<Table> currentTables = new ArrayList<Table>();
	private static final int SCALEFACTOR = 8;
	int lineHeight;

	// data elements
	private HashMap<Rectangle2D, Table> tables;
	Table selectedTable;

	public TableVisualizer(List<Table> currentTables) {
		super();
		init();
		this.currentTables = currentTables;
	}

	private void init() {
		tables = new HashMap<Rectangle2D, Table>();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				for (Rectangle2D rectangle : tableRectangles) {
					if (rectangle.contains(x, y)) {
						selectedTable = tables.get(rectangle);
						RestoAppController.toggleUse(selectedTable);
						break;
					}
				}
				repaint();
			}
		});
	}

	public void setCurrentTables(List<Table> currentTables) {
		this.currentTables = currentTables;
		tables = new HashMap<Rectangle2D, Table>();
		repaint();
	}

//	public void moveUp() {
//		if (firstVisibleBusStop > 0) {
//			firstVisibleBusStop--;			
//			repaint();
//		}
//	}
//
//	public void moveDown() {
//		if (route != null && firstVisibleBusStop < route.getBusStops().size() - MAXNUMBEROFBUSSTOPSSHOWN) {
//			firstVisibleBusStop++;
//			repaint();
//		}
//	}

	private void doDrawing(Graphics g) {
		int nbTables = currentTables.size();
		if (nbTables != 0) {
			//			if (number > MAXNUMBEROFBUSSTOPSSHOWN) {
			//				number = MAXNUMBEROFBUSSTOPSSHOWN;
			//				if (firstVisibleBusStop < route.getBusStops().size() - MAXNUMBEROFBUSSTOPSSHOWN)
			//					number++;
			//			}

			Graphics2D g2d = (Graphics2D) g.create();
			BasicStroke thickStroke = new BasicStroke(4);
			g2d.setStroke(thickStroke);
			g2d.setColor(Color.BLACK);
			g2d.setBackground(Color.WHITE);
			for(Table table: currentTables) {
				Rectangle2D rectangle = new Rectangle2D.Float(SCALEFACTOR * table.getX(), SCALEFACTOR * table.getY(), SCALEFACTOR * table.getWidth(), SCALEFACTOR * table.getLength());
				g2d.setColor(Color.WHITE);
				g2d.fill(rectangle);
				g2d.setColor(Color.BLACK);
				g2d.draw(rectangle);
				g2d.drawString(new Integer(table.getNumber()).toString(), (int) rectangle.getCenterX(), (int) rectangle.getCenterY());
				g2d.drawString(table.getStatusFullName(), (int) (rectangle.getCenterX() - 0.25 * rectangle.getWidth()), (int) (rectangle.getCenterY() + 0.25 * rectangle.getHeight()));
				tableRectangles.add(rectangle);
				tables.put(rectangle, table);
			}
//			BasicStroke thinStroke = new BasicStroke(2);
//			g2d.setStroke(thinStroke);
//			rectangles.clear();
//			busStops.clear();
//			int index = 0;
//			int visibleIndex = 0;
//			for (BusStop busStop : route.getBusStops()) {
//				if (index >= firstVisibleBusStop && visibleIndex < MAXNUMBEROFBUSSTOPSSHOWN) {
//					Rectangle2D rectangle = new Rectangle2D.Float(LINEX - RECTWIDTH / 2, LINETOPY - RECTHEIGHT / 2 + visibleIndex * (RECTHEIGHT + SPACING), RECTWIDTH, RECTHEIGHT);
//					rectangles.add(rectangle);
//					busStops.put(rectangle, busStop);
//
//					g2d.setColor(Color.WHITE);
//					g2d.fill(rectangle);
//					g2d.setColor(Color.BLACK);
//					g2d.draw(rectangle);
//					g2d.drawString(new Integer(busStop.getNumber()).toString(), LINEX - RECTWIDTH / 4, LINETOPY + RECTHEIGHT / 4 + visibleIndex * (RECTHEIGHT + SPACING));
//
//					if (selectedBusStop != null && selectedBusStop.equals(busStop)) {
//						busStopDetails = BtmsController.getBusStopMinutesFromStart(selectedBusStop) + "min from first stop";
//						g2d.drawString(busStopDetails, LINEX + RECTWIDTH * 3 / 4, LINETOPY + RECTHEIGHT / 4 + visibleIndex * (RECTHEIGHT + SPACING));
//					}
//
//					visibleIndex++;
//				}
//				index++;
			}
		}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

}	