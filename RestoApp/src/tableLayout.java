import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class tableLayout {

	private JFrame frmTableLayout;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tableLayout window = new tableLayout();
					window.frmTableLayout.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public tableLayout() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTableLayout = new JFrame();
		frmTableLayout.setTitle("Table Layout");
		frmTableLayout.setBounds(100, 100, 450, 300);
		frmTableLayout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTableLayout.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Available");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(6, 6, 93, 62);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
		lblNewLabel.setBorder(border);
		frmTableLayout.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("In Use: No Orders");
		label.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.BLACK);
		label.setBackground(Color.WHITE);
		label.setBounds(122, 72, 138, 105);
		frmTableLayout.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Available");
		label_1.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		label_1.setOpaque(true);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.BLACK);
		label_1.setBackground(Color.WHITE);
		label_1.setBounds(284, 46, 81, 56);
		frmTableLayout.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("In Use: Bill");
		label_2.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		label_2.setOpaque(true);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.BLACK);
		label_2.setBackground(Color.WHITE);
		label_2.setBounds(307, 169, 114, 77);
		frmTableLayout.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("In Use: Oders");
		label_3.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		label_3.setOpaque(true);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(Color.BLACK);
		label_3.setBackground(Color.WHITE);
		label_3.setBounds(43, 207, 93, 62);
		frmTableLayout.getContentPane().add(label_3);
	}
}
