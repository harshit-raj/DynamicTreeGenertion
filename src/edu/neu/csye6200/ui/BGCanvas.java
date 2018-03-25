package edu.neu.csye6200.ui;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import edu.neu.csye6200.bg.BGGeneration;
import edu.neu.csye6200.bg.BGGenerationSet;
import edu.neu.csye6200.bg.BGLayer;
import edu.neu.csye6200.bg.BGStem;

/**
 * THe main canvas of the project on which the tree is drawn
 * @author Harshit Raj
 */
public class BGCanvas extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(BGCanvas.class.getName());
    private int lineSize = 20;
    private Color col = null;
    private long counter = 0L;
    static BGGeneration printGen = null;
    static boolean render = false;
    
    
    
	
    
	/**
     * Default constructor
     */
	public BGCanvas() {
		
	}

	/**
	 * The UI thread calls this method when the screen changes, or in response
	 * to a user initiated call to repaint();
	 */
	public void paint(Graphics g) {
		if(render) {
			drawL(g);
		}
		else {
			drawG(g);
		}
		
		
    }
	
	
	/**
	 * The method draws an empty canvas when there is no tree to be displayed 
	 */
	public void drawG(Graphics g) {
		log.info("Drawing BG " + counter++);
		Graphics2D g2d = (Graphics2D) g;
		Dimension size = getSize();
		//canvas 
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, size.width, size.height);
		//string 
		g2d.setColor(Color.RED);
		g2d.drawString("Harshit Raj", 10, 15);
		
		
	}
	
	
	
	
	
	
	
	/**
	 * The method draws the tree on the canvas
	 */
	public void drawL(Graphics g) {
		System.out.println("drawL called");
		log.info("Drawing BG " + counter++);
		Graphics2D g2d = (Graphics2D) g;
		Dimension size = getSize();
		//canvas 
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, size.width, size.height);
		//string 
		g2d.setColor(Color.RED);
		g2d.drawString("Harshit Raj", 10, 15);
		//tree
		BGGeneration bgg = printGen;
		System.out.println(printGen.getBgl().isEmpty());
		int ArSize = bgg.getBgl().size();
		boolean green = false;
		for(BGLayer bgl: bgg.getBgl()) {
		
			if(bgg.getBgl().indexOf(bgl)==ArSize-1) {
				green = true;
			}
				for(BGStem bgs: bgl.getLayer()) {
					
					if(!green) {
						paintLine(g2d,(int)bgs.getRootX()+size.width/2,(int)bgs.getRootY()+size.height,(int)bgs.getEndX()+size.width/2,(int)bgs.getEndY()+size.height,Color.RED);
					}
					else {
						paintLine(g2d,(int)bgs.getRootX()+size.width/2,(int)bgs.getRootY()+size.height,(int)bgs.getEndX()+size.width/2,(int)bgs.getEndY()+size.height,Color.GREEN);
					}
					
					/*if(!green) {
						paintLine(g2d,(int)bgs.getRootX()+size.width/4,(int)bgs.getRootY()+size.height,(int)bgs.getEndX()+size.width/4,(int)bgs.getEndY()+size.height,Color.MAGENTA);
					}
					else {
						paintLine(g2d,(int)bgs.getRootX()+size.width/4,(int)bgs.getRootY()+size.height,(int)bgs.getEndX()+size.width/4,(int)bgs.getEndY()+size.height,Color.ORANGE);
					}*/
					/*if(!green) {
						paintLine(g2d,(int)bgs.getRootX()+(size.width*3)/4,(int)bgs.getRootY()+size.height,(int)bgs.getEndX()+(size.width*3)/4,(int)bgs.getEndY()+size.height,Color.BLUE);
					}
					else {
						paintLine(g2d,(int)bgs.getRootX()+(size.width*3)/4,(int)bgs.getRootY()+size.height,(int)bgs.getEndX()+(size.width*3)/4,(int)bgs.getEndY()+size.height,Color.WHITE);
					}*/
				}
				
			}
		//paintLine(g2d,size.width/2,size.height,size.width/2,size.height-lineSize,Color.white);
		//System.out.println(size.width/2 +" "+ size.height);
	}
	
	
	
	/*
	 * A local routine to ensure that the color value is in the 0 to 255 range.
	 */
	private int validColor(int colorVal) {
		if (colorVal > 255)
			colorVal = 255;
		if (colorVal < 0)
			colorVal = 0;
		return colorVal;
	}
	

	/**
	 * A convenience routine to set the color and draw a line
	 * @param g2d the 2D Graphics context
	 * @param startx the line start position on the x-Axis
	 * @param starty the line start position on the y-Axis
	 * @param endx the line end position on the x-Axis
	 * @param endy the line end position on the y-Axis
	 * @param color the line color
	 */
	private void paintLine(Graphics2D g2d, int startx, int starty, int endx, int endy, Color color) {
		g2d.setColor(color);
		g2d.drawLine(startx, starty, endx, endy);
	}
	/**
	 * Implementation of observer. This method receives a notification when a new generation 
	 * is created by the observable class. It captures and sets the value of the new generation 
	 * and enables rendering it on canvas
	 */
	@Override
	public void update(Observable o, Object arg) {
			printGen = (BGGeneration) arg;
			render = true;
		
	}
	
}
