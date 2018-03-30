package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.resto.controller.RestoAppController;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class DisplayMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public DisplayMenu() {
		String error = "Please select a category";
		JLabel errorMessage = new JLabel(error);
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
		comboBox.setBounds(22, 90, 151, 22);
		comboBox.addItem("Select Category...");
		for (int i = 0; i < RestoAppController.getItemCategories().size(); i++) {
			comboBox.addItem(RestoAppController.getItemCategories().get(i));
		}

		JButton deleteButton = new JButton("Delete selected Item");
		deleteButton.setBounds(22, 220, 151, 22);
		contentPane.add(deleteButton);

		JTextField newItemName = new JTextField("New item name");

		newItemName.setForeground(Color.gray);
		newItemName.setBounds(22, 250, 151, 22);
		contentPane.add(newItemName);

		newItemName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				newItemName.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				newItemName.setText("New item name");
			}
		});

		JButton addButton = new JButton("Add Menu Item");
		addButton.setBounds(22, 280, 151, 22);
		contentPane.add(addButton);

		ActionListener action = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listModel.removeAllElements();
				if (comboBox.getSelectedIndex() == 0) {
					contentPane.add(errorMessage);
				} else {
					for (int i = 0; i < RestoAppController
							.getMenuItems(RestoAppController.getItemCategories().get(comboBox.getSelectedIndex() - 1))
							.size(); i++) {

						listModel
								.addElement(
										RestoAppController
												.getMenuItems(RestoAppController.getItemCategories()
														.get(comboBox.getSelectedIndex() - 1))
												.get(i).getName().toString().concat(
														" :  " + String
																.valueOf(RestoAppController
																		.getMenuItems(
																				RestoAppController.getItemCategories()
																						.get(comboBox.getSelectedIndex()
																								- 1))
																		.get(i).getCurrentPricedMenuItem()
																		.getPrice())));
					}
					contentPane.remove(errorMessage);
				}
			}

		};

		comboBox.addActionListener(action);
		contentPane.add(comboBox);
		comboBox.setVisible(true);

	}

}
