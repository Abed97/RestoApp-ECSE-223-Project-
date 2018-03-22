package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.resto.controller.RestoAppController;

public class DisplayMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public DisplayMenu() {
		String error = "Please select a category";
		JLabel errorMessage = new JLabel(error );
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(22, 200, 350, 29);
		setBounds(100, 100, 505, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
			
		JLabel lblMenu = new JLabel("Menu");
		lblMenu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMenu.setBounds(187, 13, 56, 16);
		contentPane.add(lblMenu);
		
		JLabel lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCategory.setBounds(22, 60, 80, 22);
		contentPane.add(lblCategory);
		
		 DefaultListModel listModel = new DefaultListModel();
            JList list = new JList(listModel);
			list.setBounds(202, 89, 206, 200);
			contentPane.add(list);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Select Category...");
		for (int i = 0; i < RestoAppController.getItemCategories().size(); i++) {
			comboBox.addItem(RestoAppController.getItemCategories().get(i));
		}
		ActionListener action = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listModel.removeAllElements();
				if (comboBox.getSelectedIndex() == 0) {
					contentPane.add(errorMessage);
				} else {
				for (int i =0; i < RestoAppController.getMenuItems(RestoAppController.getItemCategories().get(comboBox.getSelectedIndex()-1)).size(); i++) {
				    
					listModel.addElement(RestoAppController.getMenuItems(RestoAppController.getItemCategories().get(comboBox.getSelectedIndex()-1)).get(i).getName().toString().concat(" :  "+String.valueOf(RestoAppController.getMenuItems(RestoAppController.getItemCategories().get(comboBox.getSelectedIndex()-1)).get(i).getCurrentPricedMenuItem().getPrice())));
				}
				contentPane.remove(errorMessage);
				}
			}
			
			
		};
		
		
		comboBox.addActionListener(action);
		comboBox.setBounds(22, 89, 151, 22);
		contentPane.add(comboBox);
		comboBox.setVisible(true);
		
	}

}
