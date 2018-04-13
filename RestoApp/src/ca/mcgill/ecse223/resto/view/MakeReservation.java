package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Font;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MakeReservation extends JFrame {

	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtPhoneNumber;
	private JTextField txtEmail;
	private JDateChooser dateChooser;
	private JTextField textField;
	private JComboBox<String> comboBox_1;
	private String error = null;
	private String error1 = null;
	private JLabel errorMessage;
	private JLabel errorMessage1;
	private SimpleDateFormat dateFormat;
	private JComboBox comboBox;
	private RestoApp restoApp = RestoAppApplication.getRestoApp();
	private List<Table> tables;
	DefaultListModel listModel;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public MakeReservation() {
		initComponents();
		refreshData();
	}

	public void initComponents() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 0);

		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 22);
		end.set(Calendar.MINUTE, 59);
		dateFormat = new SimpleDateFormat("hh:mm a");
		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd");

		setBounds(100, 100, 729, 534);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		errorMessage = new JLabel("");
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(33, 305, 243, 16);
		contentPane.add(errorMessage);

		errorMessage1 = new JLabel("");
		errorMessage1.setForeground(Color.RED);
		errorMessage1.setBounds(33, 211, 106, 16);
		contentPane.add(errorMessage1);

		// Number of people
		JLabel lblPeople = new JLabel("People");
		lblPeople.setHorizontalAlignment(SwingConstants.CENTER);
		lblPeople.setBounds(62, 52, 56, 16);
		contentPane.add(lblPeople);

		textField = new JTextField();
		textField.setBounds(33, 81, 116, 22);
		contentPane.add(textField);
		textField.setColumns(10);

		// Date
		JLabel lblDate = new JLabel("Date");
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setBounds(191, 52, 56, 16);
		contentPane.add(lblDate);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(166, 81, 117, 22);
		contentPane.add(dateChooser);

		// Time
		JLabel lblTime = new JLabel("Time");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setBounds(327, 52, 56, 16);
		contentPane.add(lblTime);

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		while (calendar.getTime().before(end.getTime())) {
			model.addElement(dateFormat.format(calendar.getTime()));
			calendar.add(Calendar.MINUTE, 30);
		}

		comboBox_1 = new JComboBox<>(model);
		comboBox_1.setBounds(305, 81, 104, 22);
		contentPane.add(comboBox_1);

		// First name
		txtFirstName = new JTextField();
		txtFirstName.setText("Full Name");
		txtFirstName.setBounds(33, 142, 116, 22);
		contentPane.add(txtFirstName);
		txtFirstName.setColumns(10);

		// Listeners for first name text box
		txtFirstName.addFocusListener(new FocusAdapter() {
			// When focused gained
			@Override
			public void focusGained(FocusEvent arg0) {
				txtFirstName.setText("");
			}

			// When focused lost
			@Override
			public void focusLost(FocusEvent e) {
				if (txtFirstName.getText() == "") {
					txtFirstName.setText("Full Name");
				}
			}
		});

		// Phone Number
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setText("Phone Number");
		txtPhoneNumber.setBounds(33, 177, 116, 22);
		contentPane.add(txtPhoneNumber);
		txtPhoneNumber.setColumns(10);

		txtPhoneNumber.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtPhoneNumber.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtPhoneNumber.getText() == "") {
				txtPhoneNumber.setText("Phone Number");
				}
			}
		});

		// Email
		txtEmail = new JTextField();

		txtEmail.setText("Email");
		txtEmail.setBounds(33, 240, 116, 22);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		txtEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtEmail.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtEmail.getText() == "") {
				txtEmail.setText("Email");
				}
			}
		});

		// Reserve
		JLabel lblReserveTable = new JLabel("Reservation");
		lblReserveTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblReserveTable.setHorizontalAlignment(SwingConstants.CENTER);
		lblReserveTable.setBounds(171, 13, 95, 16);
		contentPane.add(lblReserveTable);

		JButton btnComplete = new JButton("Reserve ");
		btnComplete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addReservationActionPerformed(evt);

			}
		});
		btnComplete.setBounds(166, 239, 117, 25);
		contentPane.add(btnComplete);

		comboBox = new JComboBox();
		for (int i = 0; i < restoApp.getCurrentTables().size(); i++) {
			comboBox.addItem(restoApp.getCurrentTable(i).getNumber());
		}
		comboBox.setBounds(166, 142, 117, 22);
		contentPane.add(comboBox);

		JLabel lblChooseTable = new JLabel("Choose Table");
		lblChooseTable.setBounds(179, 115, 104, 16);
		contentPane.add(lblChooseTable);

		JButton btnNewButton = new JButton("Add Table");
		tables = new ArrayList<Table>();
		btnNewButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt1) {
				try {
					addTableActionPerformed(evt1);
				} catch (InvalidInputException e) {

					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(166, 176, 117, 25);
		contentPane.add(btnNewButton);
	}

	public void addTableActionPerformed(java.awt.event.ActionEvent evt1) throws InvalidInputException {
		for (Table table : restoApp.getCurrentTables()) {
			if (table.getNumber() == (Integer) comboBox.getSelectedItem()) {
				if (tables.contains(table)) {
					throw new InvalidInputException("Table already added");
				}
				tables.add(Table.getWithNumber((Integer) comboBox.getSelectedItem()));
			}
		}

	}

	public void addReservationActionPerformed(java.awt.event.ActionEvent evt) {

		// clear error message
		error = null;
		// call the controller

		try {

			java.sql.Date date = new java.sql.Date((dateChooser.getDate()).getTime());
			Time time = null;
			try {
				time = new Time(new java.sql.Date(dateFormat.parse((comboBox_1.getSelectedItem()).toString()).getTime())
						.getTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			int numberInParty = Integer.parseInt(textField.getText());
			String contactName = txtFirstName.getText();
			String contactEmailAddress = txtEmail.getText();
			String contactPhoneNumber = txtPhoneNumber.getText();
			if (Pattern.matches("[a-zA-Z]+", txtPhoneNumber.getText()) == true
					|| txtPhoneNumber.getText().length() != 10) {
				error1 = "Invalid Number";
				contentPane.add(errorMessage1);
			}
			RestoAppController.reserveTable(date, time, numberInParty, contactName, contactEmailAddress,
					contactPhoneNumber, tables);

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

	private void refreshData() {// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			// populate page with data
			textField.setText("");
			txtFirstName.setText("Full Name");
			txtEmail.setText("Email");
			txtPhoneNumber.setText("Phone Number");
			// dateChooser.cleanup();
			comboBox_1.setSelectedIndex(-1);
		}
		listModel = new DefaultListModel();
		restoApp = RestoAppApplication.getRestoApp();
		for (Reservation reservation : restoApp.getReservations()) {
			listModel.addElement("Client Name: " + reservation.getContactName() + ", Date: " + reservation.getDate()
					+ ", Time: " + reservation.getTime());
			listModel.addElement("Tables: ");
			for (int i = 0; i < reservation.getTables().size(); i++) {

				listModel.addElement(reservation.getTable(i).getNumber());

			}
		}
		JList list = new JList(listModel);
		JScrollPane scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(305, 144, 379, 296);
		contentPane.add(scroll);

		JLabel lblReservations = new JLabel("Reservations");
		lblReservations.setBounds(457, 115, 93, 16);
		contentPane.add(lblReservations);
	}
}