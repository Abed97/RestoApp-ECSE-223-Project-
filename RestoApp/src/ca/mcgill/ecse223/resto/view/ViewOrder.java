package ca.mcgill.ecse223.resto.view;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.resto.model.OrderItem;


public class ViewOrder extends JFrame {

	private JPanel contentPane;
	private List<OrderItem> orders;
	private ArrayList<String> test = new ArrayList<String>();

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public ViewOrder(List<OrderItem> orders) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblOrder = new JLabel("Order");
		lblOrder.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrder.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblOrder.setBounds(110, 18, 56, 16);
		contentPane.add(lblOrder);
		

		DefaultListModel listModel = new DefaultListModel();
		JList list = new JList(listModel);
		test.add("Hello");
		test.add("world");
		/*for (OrderItem item : orders) {
			listModel.addElement(item.getPricedMenuItem().getMenuItem().getName());
		}*/
		for (String tests: test) {
			listModel.addElement(tests);
		}
		JScrollPane scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(209, 57, 202, 196);
		contentPane.add(scroll);
		
	}
}
