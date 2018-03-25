package edu.neu.csye6200.ui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;

/**
 * A sample Biological Growth Abstract application class
 * @author Harshit Raj
 *
 */
public abstract class BGApp implements ActionListener, WindowListener, ChangeListener {
	static protected JFrame frame = null;
	static boolean uishow = false; 

	/**
	 * The Biological growth constructor
	 */
	public BGApp() {
		System.out.println("In BGApp constructor");
		initGUI();
	}
	
	/**
	 * Initialize the Graphical User Interface
	 */
    public void initGUI() {
    	frame = new JFrame();
		frame.setTitle("BGApp");

		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //JFrame.DISPOSE_ON_CLOSE)
		
		// Permit the app to hear about the window opening
		frame.addWindowListener(this); 
				
		
		
		frame.setLayout(new BorderLayout());
		frame.add(getMainPanel(), BorderLayout.CENTER);
    }
    
    /**
     * Override this method to provide the main content panel.
     * @return a JPanel, which contains the main content of of your application
     */
    public abstract JPanel getMainPanel() ;

    
    /**
     * A convenience method that uses the Swing dispatch threat to show the UI.
     * This prevents concurrency problems during component initialization.
     */
    public void showUI() {
    	System.out.println("Show UI called");
    	if(uishow) {
    		System.out.println("Show UI executed");
    		SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                	frame.setVisible(true); // The UI is built, so display it;
                }
            });
    	}
    	uishow = true;
        
        
        
    	
    }
    
    
    
    /**
     * Shut down the application
     */
    public void exit() {
    	frame.dispose();
    	System.exit(0);
    }

    /**
     * Override this method to show a About Dialog
     */
    public void showHelp() {
    }
	
}
