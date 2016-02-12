package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import model.Model;
import controller.PauseListener;

/* GUI for the pause menu */

public class PauseGui implements IgameBoard {
	
	private PauseListener listener;
	private JFrame mainFrame;
	private JPanel panel;
	private Model model;
	private PlayGui gui;
	
	public PauseGui(Model m, PlayGui g) {
		
		gui = g;
		model = m;
		listener = new PauseListener(this, model, gui);
	}

	public void createAndShowGUI() {
				
		mainFrame = new JFrame("PAUSED");
		mainFrame.setBounds(100, 100, 100, 100);
		mainFrame.setPreferredSize(new Dimension(300, 240));
	    mainFrame.addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent evt) {
	    		exitWindow();
	    	}


	    });
	    {
	    	panel = new JPanel();
	    	mainFrame.getContentPane().add(panel, BorderLayout.NORTH);
	    	panel.setLayout(new GridLayout(7,1));
    		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	    	
	    	{
		    		JButton button1 = new JButton("Resume");
		    		button1.addActionListener(listener);
		    		button1.setSize(150, 40);
		    		panel.add(button1);
		    		
		    		JButton button8 = new JButton("Restart");
		    		button8.addActionListener(listener);
		    		button8.setSize(150, 40);
		    		panel.add(button8);
		
		    		
		    		JButton button3 = new JButton("Save");
		    		button3.addActionListener(listener);
		    		button3.setSize(150, 40);
		    		panel.add(button3);
		    		
		    		JButton button4 = new JButton("Load");
		    		button4.addActionListener(listener);
		    		button4.setSize(150, 40);
		    		panel.add(button4);
		    		
		    		JButton button6 = new JButton("Build Mode");
		    		button6.addActionListener(listener);
		    		button6.setSize(150, 40);
		    		panel.add(button6);
		    		
		    		JButton button7 = new JButton("Main Menu");
		    		button7.addActionListener(listener);
		    		button7.setSize(150, 40);
		    		panel.add(button7);
		    		
		    		JButton button5 = new JButton("Quit");
		    		button5.addActionListener(listener);
		    		button5.setSize(150, 40);
		    		panel.add(button5);
		    		
		    		
	    	}
	    }
		
	    mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);

	}
	
	private void exitWindow() {
		gui.restart();
	}
	
	public void killWindow() {
        mainFrame.dispose(); 

	}

}