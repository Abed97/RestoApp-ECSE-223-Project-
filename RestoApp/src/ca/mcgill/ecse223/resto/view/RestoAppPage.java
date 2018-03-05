package ca.mcgill.ecse223.resto.view;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.application.*;
import ca.mcgill.ecse223.resto.model.Table;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RestoAppPage extends JFrame {

	private final JPanel contentPane = new JPanel();
	private JLabel errorMessage;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField;
	private String error = null;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the dialog.
	 */

	public RestoAppPage() {
		initComponents();
		refreshData();

	}


	public void initComponents() {
		// elements for error message
		errorMessage = new JLabel(error);
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(10, 200, 350, 29);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Move Table");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPane, BorderLayout.CENTER);
		contentPane.setLayout(null);
		{

			JLabel lblTableNumber = new JLabel("Table Number");
			lblTableNumber.setBounds(27, 40, 88, 16);
			contentPane.add(lblTableNumber);


			JLabel lblNewXPosition = new JLabel("New X Position");
			lblNewXPosition.setBounds(27, 105, 105, 16);
			contentPane.add(lblNewXPosition);

			JLabel lblNewYPosition = new JLabel("New Y Position");
			lblNewYPosition.setBounds(27, 173, 105, 16);
			contentPane.add(lblNewYPosition);



			JButton btnMoveTable = new JButton("Move Table");
			btnMoveTable.setBounds(313, 233, 117, 29);
			contentPane.add(btnMoveTable);
			btnMoveTable.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					btnMoveTableActionPerformed(evt);
				}

			});

			textField = new JTextField();
			textField.setColumns(10);
			textField.setBounds(140, 35, 81, 26);
			contentPane.add(textField);


			textField_1 = new JTextField();
			textField_1.setBounds(140, 100, 81, 26);
			contentPane.add(textField_1);
			textField_1.setColumns(10);

			textField_2 = new JTextField();
			textField_2.setColumns(10);
			textField_2.setBounds(140, 168, 81, 26);
			contentPane.add(textField_2);



			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}
	private void refreshData() {// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			// populate page with data

			textField.setText("");
			textField_1.setText("");
			textField_2.setText("");



		}


		// this is needed because the size of the window changes depending on whether an error message is shown or not
		//pack();
	}
	private void btnMoveTableActionPerformed(java.awt.event.ActionEvent evt) {

		//clear error message
		error = null;
		// call the controller

		try {

			int number = Integer.parseInt(textField.getText());
			int x = Integer.parseInt(textField_1.getText());
			int y = Integer.parseInt(textField_2.getText());

			RestoAppController.moveTable(number, x,y);

		}
		catch (InvalidInputException e) {
			error = e.getMessage();
			contentPane.add(errorMessage);
		}

		catch (NumberFormatException e) {
			error = "Invalid Input";
			contentPane.add(errorMessage);
		}
		catch (NullPointerException e) {
			error = "Invalid Input";
			contentPane.add(errorMessage);
		}

		// update visuals
		refreshData();
	}
}
