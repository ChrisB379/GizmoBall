package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.PlayNowRunListener;

/* GUI for the game start selection screen */

public class PlayNowGui implements IgameBoard{
	
	private PlayNowRunListener listener;
	private JFrame mainFrame;
	private JPanel panel;
	
	public PlayNowGui() {
		// RunListener catches all GUI events. In reality might have many listeners.
		listener = new PlayNowRunListener(this);
	}

	public void createAndShowGUI() {
				
		mainFrame = new JFrame("GIZMOBALL");
		mainFrame.setBounds(100, 100, 100, 100);
		mainFrame.setPreferredSize(new Dimension(300, 150));
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    {
	    	panel = new JPanel();
	    	mainFrame.getContentPane().add(panel, BorderLayout.NORTH);
	    	panel.setLayout(new GridLayout(3,1));
    		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	    	
	    	{
		    		JButton button1 = new JButton("New Game");
		    		button1.addActionListener(listener);
		    		button1.setSize(150, 40);
		    		panel.add(button1);

	
		    		JButton button2 = new JButton("Load Game");
		    		button2.addActionListener(listener);
		    		button2.setSize(150, 40);
		    		panel.add(button2);
		    		
		    		JButton button3 = new JButton("Quit");
		    		button3.addActionListener(listener);
		    		button3.setSize(150, 40);
		    		panel.add(button3);
	    	}
	    }
		
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);

	}
	
	public void killWindow() {
        mainFrame.dispose(); 

	}

}