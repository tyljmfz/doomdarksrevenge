package Map;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	private final int DELAY = 10;	// milliseconds delay
	private Timer timer;
	private GameState gameState;
	
	public Board() {
		setLayout(null);
		initBoard();
		addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
//				System.out.println("X: "+e.getX()+", Y: "+e.getY());
				gameState.mouseMoved(e);	// pass the mouse event to game state
			}
		});
	}

	private void initBoard() {
		gameState = new GameState();
		setPreferredSize(new Dimension(800, 600));	// can use setSize() if component's parent has no layout manager
		setDoubleBuffered(true);	// Sets whether this component should use a buffer to paint
		timer = new Timer(DELAY, this);	// Every DELAY ms the timer will call the actionPerformed()
		timer.start();
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		gameState.initDraw(g, this);	// pass the graphics to game state to control all rendering
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gameState.update();
		gameState.draw();
		repaint();
	}
}
