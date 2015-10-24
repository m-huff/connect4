package connect.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class MainMenuWindow extends JFrame {
	
	private static final int BORDER_SIDE = 8;
	private static final int BORDER_TOP = 25;
	private static final int WIDTH = 495 + (BORDER_SIDE / 2);
	private static final int HEIGHT = 525 + BORDER_TOP;

	private static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	private static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	private static final int CENTER_X = (SCREEN_WIDTH / 2) - (WIDTH / 2);
	private static final int CENTER_Y = (SCREEN_HEIGHT / 2) - (HEIGHT / 2);

	private static final Image WINDOW_ICON = new ImageIcon(MainMenuWindow.class.getResource("/connect/assets/icon.png")).getImage();
	private static final ImageIcon BIG_ICON = new ImageIcon(MainMenuWindow.class.getResource("/connect/assets/connect4.png"));
//	private static final ImageIcon CONFIG_ICON = new ImageIcon(MainMenuWindow.class.getResource("/connect/assets/gear.png"));
	
	private static final String HEADER = "Connect 4";
	
	private static JButton play;
	private static JLabel displayIcon;
	
	final MainMenuWindow mmw = this;
	
	public MainMenuWindow() {
		setSize(WIDTH, HEIGHT);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setIconImage(WINDOW_ICON);
		setTitle(HEADER);
		setVisible(true);
		setLocation(CENTER_X, CENTER_Y);
		setBackground(Color.white);
		
		play = new JButton("Play Connect 4");
		play.setPreferredSize(new Dimension(200, 30));
		add(play, BorderLayout.PAGE_END);
		play.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				final Connect4Window c = new Connect4Window();
				mmw.dispose();
			}
		});
	}

}
