package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Table;


public class ViewOrder extends JFrame {
	private String error = null;
	private JPanel contentPane;
	private List<OrderItem> orders;
	private ArrayList<String> test = new ArrayList<String>();
	private JLabel errorMessage;
    private Table table;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public ViewOrder(List<OrderItem> orders, Table table) {
		this.table = table;
		errorMessage = new JLabel(error);
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(22, 310, 350, 29);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblOrder = new JLabel("Order");
		lblOrder.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrder.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblOrder.setBounds(90, 18, 56, 16);
		contentPane.add(lblOrder);
		

		DefaultListModel listModel = new DefaultListModel();
		JList list = new JList(listModel);
		for (OrderItem item : orders) {
			listModel.addElement(item.getPricedMenuItem().getMenuItem().getName());
		}

		
		JScrollPane scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(209, 57, 202, 196);
		contentPane.add(scroll);
		
		JButton btnDeleteItem = new JButton("Delete selected item");
		btnDeleteItem.setBounds(31, 55, 160, 25);
		contentPane.add(btnDeleteItem);
		btnDeleteItem.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				// call the controller

				try {
					Table t = RestoAppApplication.getRestoApp().getCurrentTable(0);
					int index = list.getSelectedIndex();
					if (index < 0) {
						throw new InvalidInputException("Please select an item");
					}

					RestoAppController.cancelOrderItem(RestoAppController.getOrderItems(table).get(index));
					listModel.remove(index);
				}
				catch (InvalidInputException e) {
					error = e.getMessage();
				}
				errorMessage.setText(error);
				contentPane.add(errorMessage);
				setContentPane(contentPane);
				// update visuals
				//refreshData();
			}

		});
		
	}
}
