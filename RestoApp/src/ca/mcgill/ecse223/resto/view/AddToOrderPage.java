package ca.mcgill.ecse223.resto.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;

public class AddToOrderPage extends JFrame {

	private JPanel contentPane;
	private JTable dispTable;
	private Table selectedTable;
	ArrayList<Order> currentOrders;

	/**
	 * Launch the application.
	 */

	public AddToOrderPage(Table selectedTable) {
		initComponents();
		this.selectedTable = selectedTable;
	}

	/**
	 * Create the frame
	 */
	public void initComponents() {
		// Get restoApp object
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		currentOrders = new ArrayList<Order>();
		for (Table table: restoApp.getCurrentTables()) {
			if (table.getStatus() == Status.Ordered || table.getStatus() == Status.NothingOrdered) {
				Order currentOrder = table.getOrder(table.getOrders().size() - 1);
				if (!currentOrders.contains(currentOrder)) {
					currentOrders.add(currentOrder);
				}
			}
		}
		// Set Panel
		setTitle("Add To Order");
		setBounds(100, 100, 649, 483);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Set scroll pane for table
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(63, 35, 489, 299);
		contentPane.add(scrollPane);

		// Set table display
		dispTable = new JTable();
		dispTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Order No.", "For tables", "Date", "Time" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		DefaultTableModel model = (DefaultTableModel) dispTable.getModel();
		scrollPane.setViewportView(dispTable);

		refreshData(model, restoApp);

		// Set delete button
		JButton addToOrderBtn = new JButton("Add");
		// Set listener for Delete Button
		addToOrderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get selected row
				int selectedRow = dispTable.getSelectedRow();
				// Make sure a row was selected
				if (selectedRow != -1) {
					// Get selected order object
					Order currentOrder = currentOrders.get(selectedRow);
					selectedTable.addToOrder(currentOrder);
					setVisible(false);
					RestoAppApplication.getMenu().repaint();
				}
			}
		});
		addToOrderBtn.setBounds(258, 398, 119, 35);
		contentPane.add(addToOrderBtn);

	}

	/**
	 * Update the display table
	 */
	private void refreshData(DefaultTableModel model, RestoApp restoApp) {
		// Clear table data
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		// Fill in display table with Resturaunt orders
		Object[] newRow = { 0, "", 0, 0 };
		StringBuilder tableNumbers;
		for (Order currentOrder :currentOrders) {
			tableNumbers = new StringBuilder();
			for(Table table: currentOrder.getTables()) {
				tableNumbers.append(table.getNumber());
				tableNumbers.append(",");
			}
			tableNumbers.deleteCharAt(tableNumbers.length() - 1);
			newRow[0] = currentOrder.getNumber();
			newRow[1] = tableNumbers.toString();
			newRow[2] = currentOrder.getDate();
			newRow[3] = currentOrder.getTime();
			model.addRow(newRow);
		}
	}
}