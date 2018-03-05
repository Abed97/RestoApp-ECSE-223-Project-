package ca.mcgill.ecse223.resto.view;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

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
import java.awt.Dimension;

import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame {

	private final JPanel contentPane = new JPanel();
	private JLabel errorMessage;
	private String error = null;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the dialog.
	 */

	public MainMenu() {
		// elements for error message
		errorMessage = new JLabel(error);
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(10, 200, 350, 29);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("RestoApp");
		setBounds(100, 100, 634, 367);
		getContentPane().setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPane, BorderLayout.CENTER);
		contentPane.setLayout(null);
		
		JLabel lblRestoapp = new JLabel("RestoApp");
		lblRestoapp.setFont(new Font("Serif", Font.BOLD, 36));
		lblRestoapp.setBounds(120, 118, 156, 47);
		contentPane.add(lblRestoapp);
		
		JLabel lblGroup = new JLabel("Group 19");
		lblGroup.setBounds(120, 171, 70, 16);
		contentPane.add(lblGroup);
		
		JButton btnAddTable = new JButton("Add Table");
		btnAddTable.setBounds(361, 70, 208, 25);
		contentPane.add(btnAddTable);
		btnAddTable.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new CreateTablePage().setVisible(true);
			}
		});
		
		JButton btnDeleteTable = new JButton("Delete Table");
		btnDeleteTable.setBounds(361, 110, 208, 25);
		contentPane.add(btnDeleteTable);
		btnDeleteTable.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new DeleteTable().setVisible(true);
			}
		});
		
		JButton btnUpdateTableOr = new JButton("Update Table or Seats");
		btnUpdateTableOr.setBounds(361, 150, 208, 25);
		contentPane.add(btnUpdateTableOr);
		btnUpdateTableOr.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new UpdateTablePage().setVisible(true);
			}
		});
		
		JButton btnMoveTable = new JButton("Move Table");
		btnMoveTable.setBounds(361, 190, 208, 25);
		contentPane.add(btnMoveTable);
		btnMoveTable.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new RestoAppPage().setVisible(true);
			}
		});
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.setBounds(361, 230, 208, 25);
		contentPane.add(btnMenu);
		btnMenu.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new DisplayMenu().setVisible(true);
			}
		});
		
		JLabel lblFeatures = new JLabel("Features");
		lblFeatures.setBounds(440, 27, 56, 16);
		lblFeatures.setFont(new Font("Serif", Font.BOLD, 14));
		contentPane.add(lblFeatures);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	}
}
