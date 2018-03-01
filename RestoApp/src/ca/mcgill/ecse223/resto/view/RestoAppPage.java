package ca.mcgill.ecse223.resto.view;


import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;

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
import java.awt.event.ActionEvent;

public class RestoAppPage extends JFrame {

	private final JPanel contentPanel = new JPanel();
	private JLabel errorMessage;
	private JTextField txtTableNumber;
	private JTextField txtNewXPosition;
	private JTextField txtNewYPosition;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField;
	private String error = null;

	/**
	 * Launch the application.
	 */
//		public static void main(String[] args) {
//			try {
//				RestoAppPage dialog = new RestoAppPage();
//				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//				dialog.setVisible(true);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

	/**
	 * Create the dialog.
	 */

	public RestoAppPage() {
		initComponents();
		refreshData();
	}


	public void initComponents() {
		// AJouter les truc en photooooooooo
		
		
		// elements for error message
		errorMessage = new JLabel(error);
		errorMessage.setForeground(Color.RED);

		setTitle("Move Table");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			txtTableNumber = new JTextField();
			txtTableNumber.setBackground(null);
			txtTableNumber.setText("Table Number");
			txtTableNumber.setBounds(18, 44, 130, 26);
			contentPanel.add(txtTableNumber);
			txtTableNumber.setColumns(10);
			txtTableNumber.setBorder(null);
		}

		txtNewXPosition = new JTextField();
		txtNewXPosition.setText("New X Position");
		txtNewXPosition.setBounds(236, 44, 130, 26);
		contentPanel.add(txtNewXPosition);
		txtNewXPosition.setColumns(10);
		txtNewXPosition.setBorder(null);
		txtNewXPosition.setBackground(null);

		txtNewYPosition = new JTextField();
		txtNewYPosition.setText("New Y Position");
		txtNewYPosition.setBounds(236, 110, 130, 26);
		contentPanel.add(txtNewYPosition);
		txtNewYPosition.setColumns(10);
		txtNewYPosition.setBorder(null);
		txtNewYPosition.setBackground(null);

		JButton btnMoveTable = new JButton("Move Table");
		btnMoveTable.setBounds(313, 233, 117, 29);
		contentPanel.add(btnMoveTable);
		btnMoveTable.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnMoveTableActionPerformed(evt);
			}

		});

		textField_1 = new JTextField();
		textField_1.setBounds(365, 44, 65, 26);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(365, 110, 65, 26);
		contentPanel.add(textField_2);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(148, 44, 65, 26);
		contentPanel.add(textField);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}
	private void refreshData() {// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			// populate page with data
			textField.setText("");
			textField_1.setText("");
			textField_2.setText("");
		}
		// this is needed because the size of the window changes depending on whether an error message is shown or not
	}
	private void btnMoveTableActionPerformed(java.awt.event.ActionEvent evt) {

		//clear error message
		error = null;
		// call the controller

		try {

			int number = Integer.parseInt(textField.getText());
			int x = Integer.parseInt(textField_1.getText());
			int y = Integer.parseInt(textField_2.getText());

			RestoAppController.moveTable(number, x,y);

		}
		catch (InvalidInputException e) {
			error = e.getMessage();
			contentPanel.add(errorMessage);
			System.out.print(error);
		}

		// update visuals
		refreshData();
	}
}
