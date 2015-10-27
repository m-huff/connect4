package connect.window;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import connect.com.ConfigLoader;

@SuppressWarnings("serial")
public class ConfigWindow extends JFrame {

	private static final int BORDER_SIDE = 8;
	private static final int BORDER_TOP = 25;
	private static final int WIDTH = 300 + (BORDER_SIDE / 2);
	private static final int HEIGHT = 120 + BORDER_TOP;

	private static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	private static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	private static final int CENTER_X = (SCREEN_WIDTH / 2) - (WIDTH / 2);
	private static final int CENTER_Y = (SCREEN_HEIGHT / 2) - (HEIGHT / 2);

	private static final Image icon = new ImageIcon(ConfigWindow.class.getResource("/connect/assets/icon.png")).getImage();
	
	private static final String HEADER = "Connect4 Configuration";
	
	private static final String NUM_PROMPT = "How many do you have to connect to win?";
	private static final String CHANGE_PROMPT = "Change";

	private JFrame frame = this;
	
	private static JLabel numPrompt;
	
	private static JTextField num;
	private static JButton changeNum;
	
	private final ConfigWindow deeznuts = this;

	public ConfigWindow() {
		setSize(WIDTH, HEIGHT);
		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setIconImage(icon);
		setTitle(HEADER);
		setVisible(true);
		setLocation(CENTER_X, CENTER_Y);
		setBackground(Color.white);

		addKeyListener(new KeyAdapter() {

			@SuppressWarnings("static-access")
			public void keyPressed(KeyEvent e) {
				if (e.VK_ESCAPE == e.getKeyCode()) {
					deeznuts.dispose();
				}

				repaint();
			}
		});
		
		numPrompt = new JLabel(NUM_PROMPT);
		numPrompt.setForeground(Color.black);
		add(numPrompt);
		numPrompt.setBounds(30, 5, 300, 30);
		
		changeNum = new JButton(CHANGE_PROMPT);
		changeNum.setBounds(75, 80, 150, 30);
		add(changeNum);
		changeNum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ConfigLoader.isInt(num.getText().trim())) {
					ConfigLoader.connectNum = Integer.parseInt(num.getText().trim());
					ConfigLoader.saveConfig();
				}
				else
					JOptionPane.showMessageDialog(frame, num.getText().trim() + " is not a valid color!",
						    "Invalid color!", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		num = new JTextField(3);
		num.setText(" " + String.valueOf(ConfigLoader.connectNum));
		num.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
		add(num, 1);
		num.setBounds(30, 35, 70, 30);
		num.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				num.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (num.getText().trim().isEmpty())
					num.setText((" " + String.valueOf(ConfigLoader.connectNum)));
			}
		});
		
		
//		ret = new JButton();
//		ret.setText(RETURN);
//		mainpanel.add(ret, 1);
//		ret.setBounds(143, 515, BUTTON_X_LARGE + 60, BUTTON_Y);
//		ret.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				((Window) frame).dispose();
//				}
//		});
		
		revalidate();
		repaint();
	}
}
