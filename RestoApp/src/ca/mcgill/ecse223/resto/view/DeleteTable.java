package ca.mcgill.ecse223.resto.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Table;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class DeleteTable extends JFrame {

	private JPanel contentPane;
	private JTable dispTable;

	/**
	 * Launch the application.
	 */
	public DeleteTable() {
		initComponents();
	}
	

	/**
	 * Create the frame
	 */
	public void initComponents() {

		// Set Panel
		setTitle("Delete Table");
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
				new String[] { "Table No.", "No. of Seats", "Location", "Size" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		DefaultTableModel model = (DefaultTableModel) dispTable.getModel();
		scrollPane.setViewportView(dispTable);

		refreshData(model);

		// Set delete button
		JButton btnDeleteTable = new JButton("Delete Table");
		// Set listener for Delete Button
		btnDeleteTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get selected row
				int selectedRow = dispTable.getSelectedRow();
				// Make sure a row was selected
				if (selectedRow != -1) {
					// Get selected table object
					Table deleteTable = RestoAppApplication.getRestoApp().getTables().get(selectedRow);
					// Delete table
					try {
						RestoAppController.deleteTable(deleteTable);
					} catch (InvalidInputException e1) {
						// Do nothing
						e1.printStackTrace();
					}
					refreshData(model);
				}
			}
		});
		btnDeleteTable.setBounds(258, 398, 119, 35);
		contentPane.add(btnDeleteTable);

		// Set back button
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(10, 404, 67, 23);
		contentPane.add(btnBack);

	}

	/**
	 * Update the display table
	 */
	private void refreshData(DefaultTableModel model) {
		// Clear table data
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		// Fill in display table with Resturaunt Tables
		Object[] newRow = { 0, 0, "", 0 };
		for (Table currTable : RestoAppApplication.getRestoApp().getTables()) {
			newRow[0] = currTable.getNumber();
			newRow[1] = currTable.getSeats().size();
			newRow[2] = currTable.getX() + ", " + currTable.getY();
			newRow[3] = currTable.getLength();
			model.addRow(newRow);
		}
	}
}
