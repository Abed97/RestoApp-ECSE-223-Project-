package ca.mcgill.ecse223.resto.view;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.application.*;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.event.ActionEvent;

public class UpdateTablePage extends JFrame {

	private final JPanel contentPane = new JPanel();
	private JLabel errorMessage;
	private JTextField textField_1;
	private JTextField textField;
	private JComboBox comboBox;
	private JRadioButton rdbtnSameNumberOf;
	private String error = null;


	/**
	 * Create the dialog.
	 */

	public UpdateTablePage() {
		initComponents();
		refreshData();

	}


	public void initComponents() {



		// elements for error message
		errorMessage = new JLabel(error);
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(10, 200, 350, 29);

		setTitle("Update Table or Seats");
		setBounds(100, 100, 511, 307);
		getContentPane().setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPane, BorderLayout.CENTER);
		contentPane.setLayout(null);


		JLabel lblOldTableNumber = new JLabel("Old table number");
		lblOldTableNumber.setBounds(12, 78, 121, 16);
		contentPane.add(lblOldTableNumber);

		JLabel lblNewTableNumber = new JLabel("New table number");
		lblNewTableNumber.setBounds(12, 120, 105, 16);
		contentPane.add(lblNewTableNumber);

		comboBox = new JComboBox();
		comboBox.setBounds(145, 75, 52, 22);
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		List<Table> currentTables = restoApp.getCurrentTables();
		ArrayList<Integer> numTable = new ArrayList<Integer>();
		for (Table currentTable : currentTables) {
			numTable.add(currentTable.getNumber());
			Collections.sort(numTable);
		}
		comboBox.setModel(new DefaultComboBoxModel(numTable.toArray()));
		contentPane.add(comboBox);

		textField = new JTextField();
		textField.setBounds(145, 117, 52, 22);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(416, 117, 43, 22);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		rdbtnSameNumberOf = new JRadioButton("Same number of seats");
		rdbtnSameNumberOf.setBounds(276, 74, 170, 25);
		rdbtnSameNumberOf.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(rdbtnSameNumberOf.isSelected()) {
					textField_1.setEditable(false);
				}
				if(!rdbtnSameNumberOf.isSelected()) {
					textField_1.setEditable(true);
				}
			}

		});
		contentPane.add(rdbtnSameNumberOf);

		JLabel lblNewNumberOf = new JLabel("New number of seats");
		lblNewNumberOf.setBounds(276, 120, 128, 16);
		contentPane.add(lblNewNumberOf);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(253, 180, 97, 25);
		contentPane.add(btnUpdate);
		btnUpdate.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnUpdateTableActionPerformed(evt);
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

	}
	private void refreshData() {// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			// populate page with data
			textField.setText("");
			textField_1.setText("");
			RestoApp restoApp = RestoAppApplication.getRestoApp();
			List<Table> currentTables = restoApp.getCurrentTables();
			ArrayList<Integer> numTable = new ArrayList<Integer>();
			for (Table currentTable : currentTables) {
				numTable.add(currentTable.getNumber());
			}
			Collections.sort(numTable);
			comboBox.setModel(new DefaultComboBoxModel(numTable.toArray()));
		}

	}
	private void btnUpdateTableActionPerformed(java.awt.event.ActionEvent evt) {
		//clear error message
				error = null;
				// call the controller

				try {
					int tableNumber = (Integer)(comboBox.getSelectedItem());
					int newTableNumber = Integer.parseInt(textField.getText());
					boolean hasSameSeats = rdbtnSameNumberOf.isSelected();
					int newSeatsNum;
					if (hasSameSeats) {
						newSeatsNum = 1;
					}else {
						newSeatsNum = Integer.parseInt(textField_1.getText());
					}
					
					RestoAppController.updateTableOrSeats(tableNumber, newTableNumber, newSeatsNum, hasSameSeats);

				}
				catch (InvalidInputException e) {
					error = e.getMessage();
					contentPane.add(errorMessage);
				}
				catch (NumberFormatException e) {
					error = "One or more input is either empty or is not a number";
					contentPane.add(errorMessage);
				}
				catch (NullPointerException e) {
					error = "Please select a table";
					contentPane.add(errorMessage);
				}

				// update visuals
				refreshData();
	}
}
