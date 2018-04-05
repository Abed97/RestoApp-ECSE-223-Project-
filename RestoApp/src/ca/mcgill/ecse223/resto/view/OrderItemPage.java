package ca.mcgill.ecse223.resto.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class OrderItemPage extends JFrame {

	private JPanel contentPane;
	private JTextField txtquantity;
	private int tableNumber = -1;
	private JComboBox comboBox;
	private JComboBox comboBox1;
	private JComboBox comboBox2;
	private JButton btnOrder;
	/**
	 * 
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public OrderItemPage(int tableNumber) {
		this.tableNumber = tableNumber;
		initComponents();
		//refreshData();
		}
	public void initComponents() {
		setTitle(" Order Item");
		setBounds(100, 100, 492, 329);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		 
		
		txtquantity = new JTextField();
		txtquantity.setText("quantity");
		txtquantity.setBounds(20, 15, 100, 30);
		contentPane.add(txtquantity);
		txtquantity.setColumns(10);
		
		comboBox2 = new JComboBox();
		//for (int i = 0; i < restoApp.getCurrentMenuItems().size(); i++) {
			//comboBox.addItem(restoApp.getCurrentTable(i).getNumber());
		
		comboBox2.setBounds(20, 60, 100, 30);
		contentPane.add(comboBox2);

		JLabel lblChooseOrder = new JLabel("Choose Order");
		lblChooseOrder.setBounds(20, 35, 100, 30);
		contentPane.add(lblChooseOrder);
		
		
		
		comboBox = new JComboBox();
		//for (int i = 0; i < restoApp.getCurrentMenuItems().size(); i++) {
			//comboBox.addItem(restoApp.getCurrentTable(i).getNumber());
		
		comboBox.setBounds(20, 160, 100, 26);
		contentPane.add(comboBox);

		JLabel lblChooseTable = new JLabel("Choose Menu Item");
		lblChooseTable.setBounds(20, 120, 200, 50);
		contentPane.add(lblChooseTable);
		
		comboBox1 = new JComboBox();
		//for (int i = 0; i < restoApp.getCurrentMenuItems().size(); i++) {
			//comboBox.addItem(restoApp.getCurrentTable(i).getNumber());
		
		comboBox1.setBounds(200, 160, 100, 26);
		contentPane.add(comboBox1);

		JLabel lblChooseTable1 = new JLabel("Choose seats");
		lblChooseTable1.setBounds(200, 120, 200, 45);
		contentPane.add(lblChooseTable1);

		btnOrder = new JButton("Order");
		btnOrder.setBounds(60, 200, 100, 29);
		contentPane.add(btnOrder);
		//btnAddTable.addActionListener(new java.awt.event.ActionListener(){
			//public void actionPerformed(java.awt.event.ActionEvent evt) {
				//btnCreateTableActionPerformed(evt); check add table
		//	}

		//});
		//contentPane.add(btnOrder);
	}
	//}
	
	
	

}
