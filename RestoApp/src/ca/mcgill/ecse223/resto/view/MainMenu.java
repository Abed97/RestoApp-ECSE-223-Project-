package ca.mcgill.ecse223.resto.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;

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
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;

public class MainMenu extends JFrame {

	private final JPanel buttonsPane = new JPanel();
	private final JPanel contentPane = new JPanel();
	private TableVisualizer tableVisualizer;
	private RestoApp restoApp = RestoAppApplication.getRestoApp();
	private JLabel errorMessage;
	private String error = null;
	public static HashMap<Seat, Integer> seatsh;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */

	public MainMenu() {
		
		
		seatsh=new HashMap<Seat, Integer>();
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		int z=0;
		for(int i=0;i<restoApp.getTables().size();i++) {
			for(int k=0;k<restoApp.getTable(i).getSeats().size();k++) {
				seatsh.put(restoApp.getTable(i).getSeat(k),(Integer)z );
				z++;
			}
			
		}
		
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
		JButton btnToggle = new JButton("Toggle Table State");
		btnToggle.setBounds(188, 376, 208, 25);
		buttonsPane.add(btnToggle);
		btnToggle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					// Show rating menu
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
		btnDeleteTable.setBounds(188, 176, 208, 25);
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
				JButton btnOrderItem = new JButton("Order Item");
				btnOrderItem.setBounds(188, 536, 208, 25);
				buttonsPane.add(btnOrderItem);
				btnOrderItem.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						try {
							tableVisualizer.OrderItem();
							errorMessage.setText(null);
						} catch (InvalidInputException e) {
							error = e.getMessage();
							errorMessage.setText(e.getMessage());
						}
						
					}
				});
		// Initialize update table button
		JButton btnUpdateTableOr = new JButton("Update Table Or Seats");
		btnUpdateTableOr.setBounds(188, 216, 208, 25);
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
		// Initialize cancel order button
				JButton btnCancelOrder = new JButton("Cancel Order");
				btnCancelOrder.setBounds(188, 456, 208, 25);
				buttonsPane.add(btnCancelOrder);
				btnCancelOrder.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						try {
							tableVisualizer.cancelOrderSelection();
							errorMessage.setText(null);
						} catch (InvalidInputException e) {
							error = e.getMessage();
							errorMessage.setText(e.getMessage());
						}
						
					}
				});
		// Initialize move table button
		JButton btnMoveTable = new JButton("Move Table");
		btnMoveTable.setBounds(188, 256, 208, 25);
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
		JButton btnViewOrder = new JButton("View Order");
		btnViewOrder.setBounds(188, 416, 208, 25);
		buttonsPane.add(btnViewOrder);
		btnViewOrder.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					tableVisualizer.viewOrder();
					errorMessage.setText(null);
				} catch (InvalidInputException e) {
					error = e.getMessage();
					errorMessage.setText(e.getMessage());
				}
			}
		});

		tableVisualizer = new TableVisualizer(restoApp.getCurrentTables(), btnToggle, btnDeleteTable, btnUpdateTableOr, btnMoveTable, btnViewOrder, btnCancelOrder,seatsh, btnOrderItem);
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
		lblRestoapp.setFont(new Font("Phosphate", Font.BOLD, 45));

		lblRestoapp.setBounds(188, 28, 208, 64);
		buttonsPane.add(lblRestoapp);

		 JLabel lblGroup = new JLabel("Group 19");
		 lblGroup.setFont(new Font("Phosphate", Font.PLAIN, 13));
		 lblGroup.setBounds(258, 91, 70, 16);

//		lblRestoapp.setBounds(12, 118, 156, 47);
//		buttonsPane.add(lblRestoapp);

//		JLabel lblGroup = new JLabel("Group 19");
//		lblGroup.setBounds(59, 171, 56, 16);

		buttonsPane.add(lblGroup);

		JButton btnAddTable = new JButton("Add Table");
		btnAddTable.setBounds(188, 136, 208, 25);
		buttonsPane.add(btnAddTable);
		btnAddTable.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new CreateTablePage().setVisible(true);
			
				
			}
		});

		JButton btnMenu = new JButton("Menu");
		btnMenu.setBounds(188, 296, 208, 25);
		buttonsPane.add(btnMenu);
		btnMenu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new DisplayMenu().setVisible(true);
			}
		});

		JButton btnNewButton = new JButton("Make Reservation");
		btnNewButton.setBounds(188, 336, 208, 25);
		buttonsPane.add(btnNewButton);
		btnNewButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new MakeReservation().setVisible(true);
			}
		});
		
		JButton btnIssueBill = new JButton("Issue Bill");
		btnIssueBill.setBounds(188, 496, 208, 25);
		buttonsPane.add(btnIssueBill);
		btnIssueBill.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					new IssueBillPage(seatsh).setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	}
}
