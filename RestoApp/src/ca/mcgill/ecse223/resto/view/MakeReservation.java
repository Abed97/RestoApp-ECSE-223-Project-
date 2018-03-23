package ca.mcgill.ecse223.resto.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.xml.internal.ws.api.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import com.toedter.calendar.JDayChooser;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.*;
import ca.mcgill.ecse223.resto.application.RestoAppApplication;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;

public class MakeReservation extends JFrame {

	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtPhoneNumber;
	private JTextField txtEmail;
	private JDateChooser dateChooser;
	private JTextField textField;
	private JComboBox<String> comboBox_1;
	private String error = null;
	private JLabel errorMessage;
	private SimpleDateFormat dateFormat;
	private JComboBox comboBox;
	private RestoApp restoApp = RestoAppApplication.getRestoApp();
	private List<Table> tables;
	
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

		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		 errorMessage = new JLabel("");
		 errorMessage.setForeground(Color.RED); errorMessage.setBounds(166, 224, 243,
		 16); 
		 contentPane.add(errorMessage);
		 
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

		
		  dateChooser = new JDateChooser(); dateChooser.setBounds(166, 81, 117, 22);
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

		// Name
		txtFirstName = new JTextField();
		txtFirstName.setText("Full Name");
		txtFirstName.setBounds(33, 142, 116, 22);
		contentPane.add(txtFirstName);
		txtFirstName.setColumns(10);

		// Phone Number
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setText("Phone Number");
		txtPhoneNumber.setBounds(33, 177, 116, 22);
		contentPane.add(txtPhoneNumber);
		txtPhoneNumber.setColumns(10);

		// Email
		txtEmail = new JTextField();
		txtEmail.setText("Email");
		txtEmail.setBounds(33, 212, 116, 22);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

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
		btnComplete.setBounds(312, 176, 97, 25);
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(166, 176, 117, 25);
		contentPane.add(btnNewButton);
	}
	


		// this is needed because the size of the window changes depending on whether an error message is shown or not
		//pack();
	
	public void addTableActionPerformed(java.awt.event.ActionEvent evt1) throws InvalidInputException {
		for ( Table table : restoApp.getCurrentTables()) {
			if (table.getNumber() == (Integer)comboBox.getSelectedItem()) {
				if (tables.contains(table)) {
					throw new InvalidInputException("Table already added");
				}
				tables.add(Table.getWithNumber((Integer)comboBox.getSelectedItem()));
			}
		}
		
		
	}

	public void addReservationActionPerformed(java.awt.event.ActionEvent evt) {

		// clear error message
		error = null;
		// call the controller

		try {

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 1988);
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			//Date date = (Date) cal.getTime();
			cal.set(Calendar.HOUR_OF_DAY, 12);
			cal.set(Calendar.MINUTE, 22);
			Time tim;
			java.sql.Date date =  new java.sql.Date ((dateChooser.getDate()).getTime());
			// Date date = new Date(22, 24, 22);
			Time time = null;
			try {
				time = new Time(new java.sql.Date(dateFormat.parse((comboBox_1.getSelectedItem()).toString()).getTime()).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int numberInParty = Integer.parseInt(textField.getText());
			String contactName = txtFirstName.getText();
			String contactEmailAddress = txtEmail.getText();
			String contactPhoneNumber = txtPhoneNumber.getText();
			RestoAppController.reserveTable(date, time, numberInParty, contactName, contactEmailAddress,
					contactPhoneNumber, tables);

		} catch (InvalidInputException e) {
			error = e.getMessage();
			contentPane.add(errorMessage);
		}
		catch (NumberFormatException e) {
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
	}
}