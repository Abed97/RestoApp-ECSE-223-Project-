package ca.mcgill.ecse223.resto.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.application.*;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class IssueBillPage extends JFrame {

	private final JPanel contentPane = new JPanel();
	private JLabel errorMessage;
	private JComboBox comboBox;
	private String error = null;
	private Order order;
	private DefaultListModel listModel;
	private HashMap<Seat, Integer> seatsh;
	List<Seat> seatsList;
	private JList list;

	/**
	 * Create the dialog.
	 */

	public IssueBillPage(HashMap<Seat, Integer> seatsh) {
		this.seatsh = seatsh;
		initComponents();
		refreshData();
	}

	public void initComponents() {
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		// elements for error message
		errorMessage = new JLabel(error);
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(10, 200, 350, 29);

		setTitle("Issue Bill");
		setBounds(100, 100, 511, 307);
		getContentPane().setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPane, BorderLayout.CENTER);
		contentPane.setLayout(null);

		JLabel lblOrderNumber = new JLabel("Order number:");
		lblOrderNumber.setBounds(34, 13, 86, 16);
		contentPane.add(lblOrderNumber);

		JButton btnIssueBill = new JButton("Issue Bill");
		btnIssueBill.setBounds(34, 212, 86, 25);
		contentPane.add(btnIssueBill);
		btnIssueBill.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnIssueBillActionPerformed(evt);
			}

		});
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		JComboBox comboBox = new JComboBox(model);
		comboBox.setBounds(34, 42, 86, 22);
		for (Order aOrder : restoApp.getCurrentOrders()) {
			if (model.getIndexOf(aOrder.getNumber()) == -1) {
				model.addElement(aOrder.getNumber());
			}
		}
		contentPane.add(comboBox);

		JLabel lblSeatsNumber = new JLabel("Seats number:");
		lblSeatsNumber.setBounds(326, 13, 86, 16);
		contentPane.add(lblSeatsNumber);

		listModel = new DefaultListModel();
		list = new JList(listModel);
		JScrollPane scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(296, 37, 156, 159);
		contentPane.add(scroll);

		for (Order aOrder : restoApp.getCurrentOrders()) {
			if ((int) comboBox.getSelectedItem() == aOrder.getNumber()) {
				order = aOrder;
			}
		}
		
		for (OrderItem item : order.getOrderItems()) {
			for (Seat seat : item.getSeats()) {
				if (!listModel.contains(seat)) {
					listModel.addElement(1);
				}
			}
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

	}

	private void refreshData() {// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			// populate page with data
			// textField.setText("");
			// textField_1.setText("");
			RestoApp restoApp = RestoAppApplication.getRestoApp();
			List<Table> currentTables = restoApp.getCurrentTables();
			ArrayList<Integer> numTable = new ArrayList<Integer>();
			for (Table currentTable : currentTables) {
				numTable.add(currentTable.getNumber());
			}
			Collections.sort(numTable);
		}

	}

	private void btnIssueBillActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		error = null;
		// call the controller
		try {
			List<Seat> seats = null;
			int[] selectedIx = list.getSelectedIndices();
			for (int i = 0; i < selectedIx.length; i++) {
				for (Entry<Seat, Integer> entry : seatsh.entrySet()) {
					if (entry.getValue().equals(listModel.get(selectedIx[i]))) {
						seats.add(entry.getKey());
					}
				}
			}
			RestoAppController.issueBill(seats);

		} catch (InvalidInputException e) {
			error = e.getMessage();
			contentPane.add(errorMessage);
		} catch (NumberFormatException e) {
			error = "One or more input is either empty or is not a number";
			contentPane.add(errorMessage);
		}

		catch (NullPointerException e) {
			error = "Please select a table";
			contentPane.add(errorMessage);
		}

		// update visuals
		refreshData();
	}

}
