package ca.mcgill.ecse223.resto.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.RestoApp;

public class MainMenu extends JFrame {

	private final JPanel buttonsPane = new JPanel();
	private final JPanel contentPane = new JPanel();
	private TableVisualizer tableVisualizer;
	private RestoApp restoApp = RestoAppApplication.getRestoApp();
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
		errorMessage.setBounds(361, 500, 350, 29);
		buttonsPane.add(errorMessage);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("RestoApp");
		setBounds(0, 0, 1200, 900);
		getContentPane().setLayout(new BorderLayout());
		buttonsPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Initialize toggle button
		JButton btnToggle = new JButton("Toggle table state");
		btnToggle.setBounds(361, 310, 208, 25);
		buttonsPane.add(btnToggle);
		btnToggle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					tableVisualizer.confirmSelection();
					errorMessage.setText(null);
				} catch (InvalidInputException e) {
					error = e.getMessage();
					errorMessage.setText(e.getMessage());
				}
			}
		});
		btnToggle.setVisible(false);

		
		// Initialize delete table button
		JButton btnDeleteTable = new JButton("Delete Table");
		btnDeleteTable.setBounds(361, 110, 208, 25);
		buttonsPane.add(btnDeleteTable);
		btnDeleteTable.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					tableVisualizer.removeSelection();
					errorMessage.setText(null);
				} catch (InvalidInputException e) {
					error = e.getMessage();
					errorMessage.setText(e.getMessage());
				}
				// new DeleteTable().setVisible(true);
			}
		});

		// Initialize update table button
		JButton btnUpdateTableOr = new JButton("Update Table or Seats");
		btnUpdateTableOr.setBounds(361, 150, 208, 25);
		buttonsPane.add(btnUpdateTableOr);
		btnUpdateTableOr.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					tableVisualizer.updateSelection();
					errorMessage.setText(null);
				} catch (InvalidInputException e) {
					error = e.getMessage();
					errorMessage.setText(e.getMessage());
				}
				// new UpdateTablePage().setVisible(true);
			}
		});

		// Initialize move table button
		JButton btnMoveTable = new JButton("Move Table");
		btnMoveTable.setBounds(361, 190, 208, 25);
		buttonsPane.add(btnMoveTable);
		btnMoveTable.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					tableVisualizer.moveSelection();
					errorMessage.setText(null);
				} catch (InvalidInputException e) {
					error = e.getMessage();
					errorMessage.setText(e.getMessage());
				}
				// new RestoAppPage().setVisible(true);
			}
		});

		tableVisualizer = new TableVisualizer(restoApp.getCurrentTables(), btnToggle, btnDeleteTable, btnUpdateTableOr, btnMoveTable);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		contentPane.add(tableVisualizer);
		contentPane.add(buttonsPane);
		getContentPane().add(contentPane, BorderLayout.CENTER);
		buttonsPane.setLayout(null);
		JScrollPane scroll = new JScrollPane(contentPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(0, 0, 1920, 1980);
		getContentPane().add(scroll, BorderLayout.CENTER);

		JLabel lblRestoapp = new JLabel("RestoApp");
		lblRestoapp.setFont(new Font("Serif", Font.BOLD, 36));

		lblRestoapp.setBounds(120, 118, 156, 47);
		buttonsPane.add(lblRestoapp);

		// JLabel lblGroup = new JLabel("Group 19");
		// lblGroup.setBounds(120, 171, 70, 16);

		lblRestoapp.setBounds(12, 118, 156, 47);
		buttonsPane.add(lblRestoapp);

		JLabel lblGroup = new JLabel("Group 19");
		lblGroup.setBounds(59, 171, 56, 16);

		buttonsPane.add(lblGroup);

		JButton btnAddTable = new JButton("Add Table");
		btnAddTable.setBounds(361, 70, 208, 25);
		buttonsPane.add(btnAddTable);
		btnAddTable.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new CreateTablePage().setVisible(true);
			}
		});

		JButton btnMenu = new JButton("Menu");
		btnMenu.setBounds(361, 230, 208, 25);
		buttonsPane.add(btnMenu);
		btnMenu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new DisplayMenu().setVisible(true);
			}
		});

		JLabel lblFeatures = new JLabel("Features");
		lblFeatures.setBounds(440, 27, 56, 16);
		lblFeatures.setFont(new Font("Serif", Font.BOLD, 14));
		buttonsPane.add(lblFeatures);

		JButton btnNewButton = new JButton("Make Reservation");
		btnNewButton.setBounds(361, 270, 208, 25);
		buttonsPane.add(btnNewButton);
		btnNewButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new MakeReservation().setVisible(true);
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	}
}
