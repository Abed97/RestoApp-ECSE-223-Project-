package ca.mcgill.ecse223.resto.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

public class OrderItemPage extends JFrame {

	private JPanel contentPane;
	private JTextField txtquantity;
	private int tableNumber = -1;
	List<Integer>tableNumbers=null;
	List<Table>selectedTables=null;
	private JComboBox comboBox;
	private JComboBox comboBox1;
	private JComboBox comboBox2;
	private JButton btnOrder;
	List<Seat>seats = new ArrayList<Seat>();
	/**
	 * 
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public OrderItemPage(List<Table>selectedTables) {
		//this.tableNumbers=tableNumbers;
		this.selectedTables=selectedTables;
		this.tableNumber = tableNumber;
		initComponents();
		refreshData();
		}
	public void initComponents() {
		setTitle(" Order Item");
		setBounds(100, 100, 492, 329);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		 
		
		txtquantity = new JTextField();
		txtquantity.setText("quantity");
		txtquantity.setBounds(200, 25, 100, 30);
		contentPane.add(txtquantity);
		txtquantity.setColumns(10);
		
	
	
		
		
		
		comboBox = new JComboBox();
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		for (int i = 0; i < restoApp.getMenu().getMenuItems().size(); i++) {
			comboBox.addItem( restoApp.getMenu().getMenuItem(i).getName());
					//.toString());
		}
		comboBox.setBounds(20, 35, 100, 26);
		contentPane.add(comboBox);

		JLabel lblChooseTable = new JLabel("Choose Menu Item");
		lblChooseTable.setBounds(20, 2, 200, 50);
		contentPane.add(lblChooseTable);
		
		btnOrder = new JButton("Order");
		btnOrder.setBounds(110, 200, 100, 29);
		contentPane.add(btnOrder);
		btnOrder.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCreateOrderItemActionPerformed(evt); 
		}

		});
		contentPane.add(btnOrder);
		
		comboBox1 = new JComboBox();
		//for (int i = 0; i < restoApp.getCurrentMenuItems().size(); i++) {
			//comboBox.addItem(restoApp.getCurrentTable(i).getNumber());
		for (int i = 0; i < selectedTables.size(); i++) {
		
			for(int k=0; k<(selectedTables.get(i).getSeats().size());k++){
				comboBox1.addItem( "Table ".concat(String.valueOf(selectedTables.get(i).getNumber())).concat(
						", seat:").concat(String.valueOf(k)));
								//selectedTables.get(i).getSeat(k).toString()) );
			}	
		
		
		}
		comboBox1.setBounds(20, 85, 200, 30);
		contentPane.add(comboBox1);

		JLabel lblChooseTable1 = new JLabel("Choose seats");
		lblChooseTable1.setBounds(20, 55, 200, 45);
		contentPane.add(lblChooseTable1);

		JButton btnNewButton = new JButton("Add Seat");
	
		btnNewButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt1) {
				try {
					addSeatActionPerformed(evt1);
				} catch (InvalidInputException e) {

					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(20, 120, 117, 25);
		contentPane.add(btnNewButton);}
	
		public void addSeatActionPerformed(java.awt.event.ActionEvent evt1) throws InvalidInputException {
			
				int l=0;
			    int found=0;
			    int q=0;
			    int w=0;
				for (int i = 0; i < selectedTables.size(); i++) {
					
					for(int k=0; k<(selectedTables.get(i).getSeats().size());k++){
						if(comboBox1.getSelectedIndex()==l) {
							found=1;
							w=k;
							break;}
						 l++;}
					if(found==1) { q=i;
					              
						break;}
					
				}
				seats.add(selectedTables.get(q).getCurrentSeat(w));
			}

		
		private void refreshData() {// error
			
				// populate page with data
				txtquantity.setText("quantity");
				
				RestoApp restoApp = RestoAppApplication.getRestoApp();
			
			}

		
		
		public void btnCreateOrderItemActionPerformed(java.awt.event.ActionEvent evt) {
			RestoApp restoApp = RestoAppApplication.getRestoApp();
			// clear error message
		
			// call the controller

		

			MenuItem m= null;
			for (int i = 0; i < restoApp.getMenu().getMenuItems().size(); i++) {
				if(comboBox.getSelectedIndex()==i) {m=restoApp.getMenu().getMenuItems().get(i);
				break;}
			}
				int quantity = Integer.parseInt(txtquantity.getText());
				
				try {
					RestoAppController.orderMenuItem(m,quantity,seats);
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			// update visuals
		refreshData();	
		}

		
		
	}
	//}
	
	
	


