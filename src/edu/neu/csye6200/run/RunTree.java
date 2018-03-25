/**
 * 
 */
package edu.neu.csye6200.run;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import edu.neu.csye6200.bg.BGGenerationSet;
import edu.neu.csye6200.ui.BGApp;
import edu.neu.csye6200.ui.BGCanvas;

/**
 * @author harsh
 *
 */
public class RunTree extends BGApp implements Observer{
	
	
	private static Logger log = Logger.getLogger(RunTree.class.getName());
	private int genCount = 8;
	//panels
	protected static JPanel mainPanel = null;
	protected static JPanel northPanel = null;
	protected static JPanel treeControlPanel = null;
	
	//controls for northPanel
	protected static JButton startBtn = null;
	protected static JButton stopBtn = null;
	protected static JButton pauseBtn = null;
	protected static JRadioButton rule1 = null;
    protected static JRadioButton rule2= null;
    protected static JRadioButton rule3= null;
    protected static JRadioButton ruleCustom= null;
    protected static ButtonGroup ruleSelect = new ButtonGroup();
    protected static JLabel playSpeedLabel = new JLabel("Speed");
    protected static JSlider playSpeed = null;
    protected static JLabel rewindSliderLabel = new JLabel("Rewind");
	protected static JSlider rewindSlider = null;
	public static Hashtable<Integer, JLabel> rewindSliderLabels = new Hashtable<Integer, JLabel>();   
	protected static JCheckBox dynamicRule = null;
	//canvas object
	private static BGCanvas bgPanel = null;
	
	//controls for tree control panel
	protected static JLabel childCountLabel = new JLabel("Branches");
	protected static JSpinner childCountSpinner = null;  
	protected static SpinnerModel childCountModel = new SpinnerNumberModel(3,2,7,1);//(default val, min, max, step)
	protected static JLabel genCountLabel = new JLabel("Generations");
	protected static JSpinner genCountSpinner = null;  
	protected static SpinnerModel genCountModel = new SpinnerNumberModel(8,1,10,1);//(default val, min, max, step)
	protected static JRadioButton angle180 = null;
    protected static JRadioButton angle360 = null;
    protected static ButtonGroup angleSelect = new ButtonGroup();
    protected static JLabel growFactLabel = new JLabel("Growth Factor");
    protected static JSlider growFactSlider = null;
    protected static JLabel lengthSpinnerLabel = new JLabel("Stem Length");
	protected static JSpinner lengthSpinner = null;  //////////////////////////////////////////////////////
	protected static SpinnerModel lengthSpinnerModel = new SpinnerNumberModel(5,4,20,1);//(default val, min, max, step)
    
	int interval = 500; //thread sleep duration
	
	//init prameters for Stem generation in BGGenerationset
	public static int frameHeightRT = 1000;  
	public static int frameWidthRT = 1200;
	public static int branchNumRT = 3;
	public static int genCountRT = 8;
	public static boolean fullCircleRT = true;
	public static int statusRT = 0;  //0 - stop, 1 run, 2 pause
    public static double growthFactorRT = 1.7;
    public static double stemLengthRT = 5;
   
    //frame dimensions
    private int frameHeight = 1000;
	private int frameWidth = 1200;
	
	public static boolean rewindRT = false;
	public static boolean dynamicRuleRT = true;
	//public static int rewindGenRT;
	
	static BGGenerationSet bgs = null;
	Thread bgsTd = null; 
	
	static int maxGen = 0;
	/**
	 * 
	 */
	public RunTree() {
		log.info("Run Tree instance created");
		frame.setSize(frameWidth, frameHeight); // initial Frame size
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setTitle("Tree Generation");
		
		
    	showUI(); // Cause the Swing Dispatch thread to display the JFrame
    	
    	
	}
	/*
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		log.info("We received an ActionEvent " + ae);
		if(angle180.isSelected() == true) {
			fullCircleRT = false;
		}
		
		if(angle360.isSelected() == true) {
			fullCircleRT = true;
		}
		
		if(rule1.isSelected() == true) {
			configRule1();
		}
		
		if(rule2.isSelected() == true) {
			configRule2();
		}
		
		if(rule3.isSelected() == true) {
			configRule3();
		}
		
		if(ruleCustom.isSelected() == true) {
			configCustomRule();
		}
		
		if(dynamicRule.isSelected() == true) {
			dynamicRuleRT = true;
		}
		else {
			dynamicRuleRT = false;
		}
		
		
		if (ae.getSource() == startBtn) {
			//System.out.println("Start pressed");
			statusRT = 1;
			//bgs.status= 1;
			bgs.setStatus(1);
			//System.out.println(statusRT);
			rewindSliderLabel.setVisible(false);
			rewindSlider.setVisible(false);
			
			//bgsTd.start();
			maxGen = 0;
		}
			
		else if (ae.getSource() == stopBtn) {
			
			statusRT = 0;
			rewindSlider.setValue(maxGen);
			rewindSlider.setMaximum(maxGen);
			enableRewind();
		}
		else if (ae.getSource() == pauseBtn) {
			statusRT = 2;
			
		}
		
			
			

	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		log.info("Window activated");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		log.info("Window closed");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {	
		log.info("Window closing");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {	
		log.info("Window deactivated");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {	
		log.info("Window deiconified");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) {
		log.info("Window iconified");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		log.info("Window opened");
	}
	/* (non-Javadoc)
	 * @see edu.neu.csye6200.ui.BGApp#getMainPanel()
	 */
	@Override
	public JPanel getMainPanel() {
		mainPanel = new JPanel();
    	mainPanel.setLayout(new BorderLayout());
    	mainPanel.add(BorderLayout.NORTH, getNorthPanel());
    	mainPanel.add(BorderLayout.SOUTH, getTreeControlPanel());
    	bgPanel = new BGCanvas();
    	mainPanel.add(BorderLayout.CENTER, bgPanel);
    	treeControlPanel.setVisible(false);
		
    	
    	return mainPanel;
		
	}
	
	/**
	 * Generates the panel that has the controls to create custom rules for the tree.
	 * @return JPanel with cortols to create custom rules for tree generation
	 */
	public JPanel getTreeControlPanel() {
		treeControlPanel = new JPanel();
		treeControlPanel.setLayout(new FlowLayout( FlowLayout.CENTER,5,5));
		
		
		treeControlPanel.add(childCountLabel);
    	childCountSpinner = new JSpinner(childCountModel);
    	childCountSpinner.addChangeListener(this);
    	treeControlPanel.add(childCountSpinner);
    	
    	treeControlPanel.add(genCountLabel);
    	genCountSpinner = new JSpinner(genCountModel);
    	genCountSpinner.addChangeListener(this);
    	treeControlPanel.add(genCountSpinner);
    	
    	angle180 = new JRadioButton("180°",true);
    	angle360 = new JRadioButton("360°");
    	angle180.addActionListener(this);
    	angle360.addActionListener(this);
    	angleSelect.add(angle180);
    	angleSelect.add(angle360);
    	treeControlPanel.add(angle180);
    	treeControlPanel.add(angle360);
    	
    	treeControlPanel.add(growFactLabel);
    	Hashtable growFactLabels = new Hashtable();
       	growFactLabels.put(11, new JLabel("Less"));
       	growFactLabels.put(19, new JLabel("More"));
    	growFactSlider = new JSlider(JSlider.HORIZONTAL,10,20,15); 
    	growFactSlider.addChangeListener(this);
    	growFactSlider.setLabelTable(growFactLabels);
    	growFactSlider.setPaintLabels(true);
    	treeControlPanel.add(growFactSlider);
    	
    	treeControlPanel.add(lengthSpinnerLabel);
    	lengthSpinner = new JSpinner(lengthSpinnerModel);
    	lengthSpinner.addChangeListener(this);
    	treeControlPanel.add(lengthSpinner);
    	
		
		
		return treeControlPanel;
		
	}
	/**
	 * Creates JPanel with primary controls for generating tree
	 * @return 
	 */
    public JPanel getNorthPanel() {
    	northPanel = new JPanel();
    	//northPanel.setLayout(new FlowLayout());
    	
    	northPanel.setLayout(new FlowLayout( FlowLayout.LEFT,5,5));
    	
    	startBtn = new JButton("Start");
    	startBtn.addActionListener(this); // Allow the app to hear about button pushes
    	northPanel.add(startBtn);
    	
    	stopBtn = new JButton("Stop"); // Allow the app to hear about button pushes
    	stopBtn.addActionListener(this);
    	northPanel.add(stopBtn);
    	pauseBtn = new JButton("Pause"); // Allow the app to hear about button pushes
    	pauseBtn.addActionListener(this);
    	northPanel.add(pauseBtn);
    	
    	
        northPanel.add(playSpeedLabel);
    	Hashtable playSpeedLabels = new Hashtable();
    	playSpeedLabels.put(130, new JLabel("slow"));
    	playSpeedLabels.put(970, new JLabel("fast"));
        playSpeed = new JSlider(JSlider.HORIZONTAL,100,1000,500);
    	playSpeed.addChangeListener(this);
    	playSpeed.setLabelTable(playSpeedLabels);
    	playSpeed.setPaintLabels(true);
    	northPanel.add(playSpeed);
       	
    	
    	rule1 = new JRadioButton("Rule 1",true);
    	rule2 = new JRadioButton("Rule 2");
    	rule3 = new JRadioButton("Rule 3");
    	ruleCustom = new JRadioButton("Custom Rule");
    	rule1.addActionListener(this);
    	rule2.addActionListener(this);
    	rule3.addActionListener(this);
    	ruleCustom.addActionListener(this);
    	ruleSelect.add(rule1);
    	ruleSelect.add(rule2);
    	ruleSelect.add(rule3);
    	ruleSelect.add(ruleCustom);
    	northPanel.add(rule1);
    	northPanel.add(rule2);
    	northPanel.add(rule3);
    	northPanel.add(ruleCustom);
    	  	
    	dynamicRule = new JCheckBox("Dynamic Rule",true);
    	dynamicRule.addActionListener(this);
    	northPanel.add(dynamicRule);
    	
    	northPanel.add(rewindSliderLabel);
    	rewindSliderLabel.setVisible(false);
    	rewindSliderLabels.put(1, new JLabel("1"));
    	rewindSlider = new JSlider(JSlider.HORIZONTAL,0,genCountRT,genCountRT);
    	rewindSlider.setMajorTickSpacing(1);
    	rewindSlider.setPaintTicks(true);
    	rewindSlider.setSnapToTicks(true);
    	rewindSlider.setLabelTable(playSpeedLabels);
    	rewindSlider.setPaintLabels(true);
    	rewindSlider.addChangeListener(this);
    	rewindSlider.setVisible(false);
    	northPanel.add(rewindSlider);
    	
    	
    	
    	return northPanel;
    	
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RunTree runObj = new RunTree();
		bgs = BGGenerationSet.getInstance();
		bgs.setFrameHeight(frameHeightRT);
		bgs.setFrameWidth(frameWidthRT);
		bgs.setBranchNum(branchNumRT);
		bgs.setGenCount(genCountRT);
		bgs.setFullCircle(fullCircleRT);
		runObj.run();

	}

	private void run() {
		bgs = BGGenerationSet.getInstance();
		bgs.setFrameHeight(frameHeightRT);
		bgs.setFrameWidth(frameWidthRT);
		bgs.setBranchNum(branchNumRT);
		bgs.setGenCount(genCountRT);
		bgs.setFullCircle(fullCircleRT);
		bgsTd = new Thread(bgs);
		System.out.println("Starting Thread");
		bgsTd.start();
		
		
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Stuff observed in Run Tree");
		if(rewindRT == false) {
			maxGen++;
			try {
				bgsTd.sleep(interval);
			} catch (InterruptedException e) {
				 //TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			rewindRT = false;
		}
		
		bgPanel.repaint();
		
	}
	@Override
	public void stateChanged(ChangeEvent ce) {
		log.info("We received an ActionEvent " + ce);
		int val = 0;
		if(ce.getSource() == childCountSpinner) {
			val = (int) ((JSpinner)ce.getSource()).getValue();
			this.branchNumRT = val;
			System.out.println(val);
		}
		if(ce.getSource() == genCountSpinner) {
			val = (int) ((JSpinner)ce.getSource()).getValue();
			this.genCountRT = val;
			System.out.println(val);
		}
		if(ce.getSource()== playSpeed) {
			val = (int) ((JSlider)ce.getSource()).getValue();
			this.interval = 1100-val;
		}
		if(ce.getSource()== growFactSlider) {
			val = (int) ((JSlider)ce.getSource()).getValue();
			this.growthFactorRT = (double)val/10.0;
		}
		if(ce.getSource() == lengthSpinner) {
			val = (int) ((JSpinner)ce.getSource()).getValue();
			this.stemLengthRT = val;
		}
		if(ce.getSource()== rewindSlider) {
			val = (int) ((JSlider)ce.getSource()).getValue();
			rewindRT = true;
			bgs.rewindGen(val);
		}
		
	}
	
	public void enableRewind(){
		rewindSliderLabel.setVisible(true);
		rewindSlider.setVisible(true);
	}
	
	public void configRule1() {
		treeControlPanel.setVisible(false);
		branchNumRT = 3;
		genCountRT = 8;
		fullCircleRT = true;
		growthFactorRT = 1.7;
	    stemLengthRT = 6;
		
	    
	}
	public void configRule2() {
		treeControlPanel.setVisible(false);
		
		branchNumRT = 3;
		genCountRT = 8;
		fullCircleRT = false;
		growthFactorRT = 1.5;
	    stemLengthRT = 15;
		
		
	}
	public void configRule3() {
		treeControlPanel.setVisible(false);
		
		branchNumRT = 4;
		genCountRT = 8;
		fullCircleRT = false;
		growthFactorRT = 1.7;
	    stemLengthRT = 10;
	}
	public void configCustomRule() {
		treeControlPanel.setVisible(true);
	}
	

}
