package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;

public class DisplayMenu extends JFrame {

	private JPanel contentPane;
	private String error = null;
	private JLabel errorMessage;
	
	/**
	 * Create the frame.
	 */
	public DisplayMenu() {
		//error = "Please select a category";
		errorMessage = new JLabel(error);
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(22, 310, 350, 29);
		setBounds(100, 100, 500, 400);
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
		//list.setBounds(250, 89, 206, 200);
		//contentPane.add(list);
		JScrollPane scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(250, 89, 206, 200);
		contentPane.add(scroll);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(22, 90, 180, 22);
		comboBox.addItem("Select Category...");
		for (int i = 0; i < RestoAppController.getItemCategories().size(); i++) {
			comboBox.addItem(RestoAppController.getItemCategories().get(i));
		}

		JButton deleteButton = new JButton("Delete selected Item");
		deleteButton.setBounds(22, 220, 180, 22);
		contentPane.add(deleteButton);
		deleteButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//clear error message
				error = null;
				// call the controller

				try {
					int index = list.getSelectedIndex();
					if (index < 0) {
						throw new InvalidInputException("Please select an item");
					}
					MenuItem toRemove = RestoAppController
					.getMenuItems(RestoAppController.getItemCategories()
							.get(comboBox.getSelectedIndex() - 1))
					.get(index);

					RestoAppController.removeMenuItem(toRemove);
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

		JTextField newItemName = new JTextField("New item name");

		newItemName.setForeground(Color.BLACK);
		newItemName.setBounds(22, 250, 135, 22);
		contentPane.add(newItemName);

		JTextField newItemPrice = new JTextField("Price");


		newItemPrice.setForeground(Color.BLACK);
		newItemPrice.setBounds(155, 250, 50, 22);
		contentPane.add(newItemPrice);

		newItemName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				newItemName.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(newItemName.getText().isEmpty()) {
					newItemName.setText("New item name");
				}			}
		});

		newItemPrice.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				newItemPrice.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(newItemPrice.getText().isEmpty()) {
					newItemPrice.setText("Price");
				}
			}
		});

		JButton addButton = new JButton("Add Menu Item");
		addButton.setBounds(22, 280, 180, 22);
		contentPane.add(addButton);
		addButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//clear error message
				error = null;
				// call the controller

				try {
					String name = newItemName.getText();
					double price = Double.parseDouble(newItemPrice.getText());
					if (comboBox.getSelectedIndex() == 0) {
						throw new InvalidInputException("Please select a category");
					}
					ItemCategory category = RestoAppController.getItemCategories().get(comboBox.getSelectedIndex() - 1);

					RestoAppController.addMenuItem(name, category, price);
					newItemName.setText("New item name");
					newItemPrice.setText("Price");
					listModel.addElement(name + " :  " + price);
				}
				catch (InvalidInputException e) {
					error = e.getMessage();

				}
				catch (NumberFormatException e) {
					error = "Price is either empty or is not a number";

				}
				errorMessage.setText(error);
				contentPane.add(errorMessage);
				setContentPane(contentPane);
			}

		});

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
						MenuItem menuItem = RestoAppController
								.getMenuItems(RestoAppController.getItemCategories()
										.get(comboBox.getSelectedIndex() - 1))
								.get(i);
						listModel
						.addElement(
								menuItem.getName().toString().concat(
										" :  " + String
										.valueOf(menuItem.getCurrentPricedMenuItem()
												.getPrice())) + "  " + menuItem.getRating() + " stars");
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
