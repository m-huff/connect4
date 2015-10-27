package connect.window;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

import connect.com.Piece;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements Runnable {

	static final int XBORDER = 20;
	static final int YBORDER = 20;
	static final int YTITLE = 30;
	static final int WINDOW_BORDER = 8;
	static final int WINDOW_WIDTH = 2 * (WINDOW_BORDER + XBORDER) + 495;
	static final int WINDOW_HEIGHT = YTITLE + WINDOW_BORDER + 2 * YBORDER + 525;
	boolean animateFirstTime = true;
	int xsize = -1;
	int ysize = -1;
	Image image;
	Graphics2D g;

	private static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	private static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

	private static final int CENTER_X = (SCREEN_WIDTH / 2) - (WINDOW_WIDTH / 2);
	private static final int CENTER_Y = (SCREEN_HEIGHT / 2) - (WINDOW_HEIGHT / 2);

	final int numRows = 8;
	final int numColumns = 8;
	Piece board[][];
	boolean playerOnesTurn;
	boolean moveHappened;
	int currentRow;
	int currentColumn;

	int delay = 0;
	int time = 0;

	int score1;
	int score2;

	static int CONNECT_NUM = 4;

	Piece fallingPiece;
	int falling;

	public static Random rand = new Random();
	public static ImageIcon icon = new ImageIcon(MainWindow.class.getResource("/connect/assets/icon.png"));

	enum WinState {
		None, PlayerOne, PlayerTwo, Tie
	}

	WinState winState;
	int winRow;
	int winColumn;

	enum WinDirection {
		Horizontal, Vertical, DiagonalUp, DiagonalDown
	}

	WinDirection winDirection;
	int piecesOnBoard;

	private final MainWindow deeznuts = this;

	public MainWindow() {

		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setIconImage(icon.getImage());
		setTitle("Connect 4");
		setLocation(CENTER_X, CENTER_Y);

		addKeyListener(new KeyAdapter() {

			@SuppressWarnings("static-access")
			public void keyPressed(KeyEvent e) {
				if (e.VK_X == e.getKeyCode()) {
					new Connect4Window();
					deeznuts.dispose();
				}
				if (e.VK_Q == e.getKeyCode()) {
					new ConfigWindow();
				}

				repaint();
			}
		});

		init();
		start();
	}

	Thread relaxer;

	public void init() {
		requestFocus();
	}

	public void paint(Graphics gOld) {
		if (image == null || xsize != getSize().width || ysize != getSize().height) {
			xsize = getSize().width;
			ysize = getSize().height;
			image = createImage(xsize, ysize);
			g = (Graphics2D) image.getGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		g.setColor(Color.black);
		g.fillRect(0, 0, xsize, ysize);

		if (animateFirstTime) {
			gOld.drawImage(image, 0, 0, null);
			return;
		}

		for (int zrow = 0; zrow < numRows; zrow++) {
			for (int zcolumn = 0; zcolumn < numColumns; zcolumn++) {

				if (board[zrow][zcolumn] != null) {
					g.setColor(board[zrow][zcolumn].getColor());
					g.fillOval(getX(0) + zcolumn * getWidth2() / numColumns,
							(board[zrow][zcolumn] == fallingPiece ? getY(0) + zrow * getHeight2() / numRows - falling
									: getY(0) + zrow * getHeight2() / numRows),
							getWidth2() / numColumns, getHeight2() / numRows);
				}

			}
		}

		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 42));
		g.drawString("Connect 4", 30, 80);

		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("Press X to play", 30, 105);
		g.drawString("Press Q to configure", 30, 120);

		gOld.drawImage(image, 0, 0, null);
	}

	public void run() {
		while (true) {
			animate();
			repaint();
			double seconds = 0.03;
			int miliseconds = (int) (1000.0 * seconds);
			try {
				Thread.sleep(miliseconds);
			} catch (InterruptedException e) {
			}
		}
	}

	public void reset() {
		board = new Piece[numRows][numColumns];

		playerOnesTurn = true;
		moveHappened = false;
		winState = WinState.None;
		piecesOnBoard = 0;

		falling = 0;
		fallingPiece = null;
	}

	public void animate() {

		if (animateFirstTime) {
			animateFirstTime = false;
			if (xsize != getSize().width || ysize != getSize().height) {
				xsize = getSize().width;
				ysize = getSize().height;
			}

			reset();
		}

		if (moveHappened) {
			moveHappened = false;
		}

		int fallSpeed = 25;

		if (fallingPiece != null && falling > fallSpeed - 1) {
			falling -= fallSpeed;
		}

		if (falling < fallSpeed - 1) {
			fallingPiece = null;
			falling = 0;
		}

		if (fallingPiece == null && falling == 0 && delay == 5) {
			int col = rand.nextInt(numRows);
			boolean addPiece = false;
			for (int i = 0; addPiece == false && i < numRows; i++) {
				if (board[numColumns - 1 - i][col] == null) {
					addPiece = true;
					board[numColumns - 1 - i][col] = new Piece((playerOnesTurn ? Color.red : Color.darkGray));
					falling = getHeight2() / numColumns * currentRow;
					fallingPiece = board[numColumns - 1 - i][col];
					playerOnesTurn = !playerOnesTurn;
					moveHappened = true;
					piecesOnBoard++;
				}
			}
			delay = 0;
		} else {
			delay++;
		}

		if (time > 200) {
			for (int zrow = 0; zrow < numRows; zrow++) {
				for (int zcolumn = 0; zcolumn < numColumns; zcolumn++) {
					board[zrow][zcolumn] = null;
					piecesOnBoard = 0;
				}
			}
			time = 0;
		}

		time++;
	}

	public void start() {
		if (relaxer == null) {
			relaxer = new Thread(this);
			relaxer.start();
		}
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		if (relaxer.isAlive()) {
			relaxer.stop();
		}
		relaxer = null;
	}

	public int getX(int x) {
		return (x + XBORDER + WINDOW_BORDER);
	}

	public int getY(int y) {
		return (y + YBORDER + YTITLE);
	}

	public int getYNormal(int y) {
		return (-y + YBORDER + YTITLE + getHeight2());
	}

	public int getWidth2() {
		return (xsize - 2 * (XBORDER + WINDOW_BORDER));
	}

	public int getHeight2() {
		return (ysize - 2 * YBORDER - WINDOW_BORDER - YTITLE);
	}
}
