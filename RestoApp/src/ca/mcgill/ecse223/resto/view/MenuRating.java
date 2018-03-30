package ca.mcgill.ecse223.resto.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class MenuRating extends JFrame {

	private static final long serialVersionUID = 5765666411683246454L;

	private JFrame frame;
	private JPanel contentPane;

	private Shape[] stars = new Shape[5];
	private Boolean[] selectedStars = { false, false, false, false, false };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuRating window = new MenuRating();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenuRating() {
		setTitle("Menu Rating");
		getContentPane().setLayout(null);

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 660, 476);
		setBounds(100, 100, 649, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Menu items
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(230, 11, 151, 30);
		getContentPane().add(comboBox);
		comboBox.setBackground(Color.WHITE);

		comboBox.addItem("Select a menu item:");

		JButton btnConfirmRating = new JButton("Confirm rating");
		btnConfirmRating.setBounds(230, 304, 151, 23);
		getContentPane().add(btnConfirmRating);

		repaint();

		// When mouse clicked on star
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Boolean alreadySelected = false;
				int x = e.getX();
				int y = e.getY();

				// Check if mouse is clicked on star
				for (int i = 0; i < 5; i++) {
					if (stars[i].contains(x, y)) {
						selectedStars[i] = true;
						for (int j = 0; j < i; j++) {
							selectedStars[j] = true;
						}
						repaint();
					} else {
						selectedStars[i] = false;
					}
				}
			}
		});

	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g.create();
		BasicStroke thickStroke = new BasicStroke(4);
		g2d.setStroke(thickStroke);
		g2d.setColor(Color.BLACK);
		g2d.setBackground(Color.WHITE);

		for (int i = 0; i < 5; i++) {
			if (selectedStars[i]) {
				g2d.setColor(Color.decode("#E1E100"));

			} else {
				g2d.setColor(Color.GRAY);
			}
			stars[i] = createDefaultStar(10, 180 + (70) * i, 200);
			g2d.draw(stars[i]);
		}
	}

	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		doDrawing(g);
	}

	private static Shape createDefaultStar(double radius, double centerX, double centerY) {
		return createStar(centerX, centerY, radius, radius * 2.63, 5, Math.toRadians(-18));
	}

	private static Shape createStar(double centerX, double centerY, double innerRadius, double outerRadius, int numRays,
			double startAngleRad) {
		Path2D path = new Path2D.Double();
		double deltaAngleRad = Math.PI / numRays;
		for (int i = 0; i < numRays * 2; i++) {
			double angleRad = startAngleRad + i * deltaAngleRad;
			double ca = Math.cos(angleRad);
			double sa = Math.sin(angleRad);
			double relX = ca;
			double relY = sa;
			if ((i & 1) == 0) {
				relX *= outerRadius;
				relY *= outerRadius;
			} else {
				relX *= innerRadius;
				relY *= innerRadius;
			}
			if (i == 0) {
				path.moveTo(centerX + relX, centerY + relY);
			} else {
				path.lineTo(centerX + relX, centerY + relY);
			}
		}
		path.closePath();
		return path;
	}
}
