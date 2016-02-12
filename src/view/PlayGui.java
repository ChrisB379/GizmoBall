package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Model;
import controller.GameListener;
import controller.KeyActions;
import controller.MagicKeyListener;

/* GUI for the main game runnable. */

public class PlayGui {

	private Model model;
	private JFrame frame;
	private Board board;
	private KeyActions keyListener;
	private MagicKeyListener magicKeys;
	private GameListener listener;
	private JLabel scoreLabel;
	private int scores;
	
	public PlayGui(Model m) {
		
		model = m;
		keyListener = new KeyActions(model);
		magicKeys = new MagicKeyListener(keyListener);
		listener = new GameListener(model, this);

	}

	public void createAndShowGUI() {

		scores = model.getScore();
		frame = new JFrame("GIZMOBALL");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		board = new Board(500, 500, model);
		
				JPanel topPanel = new JPanel();
				topPanel.setLayout(new GridLayout(1,2));	    		
				{
					scoreLabel = new JLabel("Score: " + scores);
					topPanel.add(scoreLabel);
				}
			
			Container cp = frame.getContentPane();
			cp.add(topPanel, BorderLayout.NORTH);
			cp.addKeyListener(magicKeys);
			cp.add(board, BorderLayout.CENTER);
			cp.addKeyListener(listener);
			
		frame.addKeyListener(listener);
		frame.addKeyListener(magicKeys);
		frame.setFocusable(true);	
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public void updateScore(int s) {
		scores = s;
		scoreLabel.setText("Score: " + scores);
	}

	public void killWindow() {
        frame.dispose(); 

	}
	
	public void restart() {
		listener.restart();
	}
	
}