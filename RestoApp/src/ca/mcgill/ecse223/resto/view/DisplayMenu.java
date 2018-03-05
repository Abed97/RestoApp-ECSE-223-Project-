package ca.mcgill.ecse223.resto.view;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;

public class DisplayMenu {

	private JFrame frmMenu;
	private JLabel lblFoods;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DisplayMenu window = new DisplayMenu();
					window.frmMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DisplayMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMenu = new JFrame();
		frmMenu.getContentPane().setForeground(SystemColor.activeCaption);
		frmMenu.setForeground(Color.ORANGE);
		frmMenu.getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		frmMenu.getContentPane().setLayout(null);
		
		lblFoods = new JLabel("Category");
		lblFoods.setBounds(15, 16, 173, 39);
		lblFoods.setVerticalAlignment(SwingConstants.TOP);
		lblFoods.setForeground(new Color(0, 0, 0));
		lblFoods.setFont(new Font("Dubai", Font.BOLD, 23));
		lblFoods.setBackground(SystemColor.activeCaption);
		frmMenu.getContentPane().add(lblFoods);
		
		comboBox = new JComboBox();
		comboBox.addItem("Select Category...");
		comboBox.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//btnMoveTableActionPerformed(evt);
			}

		});
		//comboBox.setModel(new DefaultComboBoxModel(new String[] {"Appetizers"}));
		//comboBox.setToolTipText("choose category...");
		comboBox.setBounds(15, 71, 188, 26);
		frmMenu.getContentPane().add(comboBox);
		frmMenu.setBackground(SystemColor.desktop);
		frmMenu.setTitle("Menu");
		frmMenu.setBounds(100, 100, 450, 300);
		frmMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/*private void displayCategory(java.awt.event.ActionEvent evt) {

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
			contentPane.add(errorMessage);
		}

		catch (NumberFormatException e) {
			error = "Invalid Input";
			contentPane.add(errorMessage);
		}
		catch (NullPointerException e) {
			error = "Invalid Input";
			contentPane.add(errorMessage);
		}

		// update visuals
		refreshData();
	}*/
}

	

