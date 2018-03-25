package edu.neu.csye6200.bg;

import java.util.ArrayList;
import java.util.Observable;

import edu.neu.csye6200.run.RunTree;
import edu.neu.csye6200.ui.BGCanvas;
/**
 * BGGenerationSet class holds a collection of BGGenerations.
 * Sends notification to {@BGCanvas} and {@RunTree} when a new generation is created
 * @author Harshit Raj
 *
 */
public class BGGenerationSet extends Observable implements Runnable{//extends BGApp 
	BGGeneration newGen; 
	//stem properties 
	//length of stem
	public double stemLength = 10;
	//number of branches children branches from split
	public int branchNum = 3;
	//number of generations to generate
	private int genCount = 8;
	protected boolean fullCircle = true;
	public double growthFactor;
	//initial frame size
	private int frameHeight = 1000;
	private int frameWidth = 1200;
	//Static instance for singleton
	private static BGGenerationSet genSetObj = null;
	//ArrayList of generations
	private ArrayList<BGGeneration> genSet = new ArrayList<BGGeneration>();
	//for observer
    static BGCanvas bc = new BGCanvas();
    static RunTree rt = new RunTree();
    //rule instance
	private BGRule rule = new BGRule();
	
	public static int status = 0;  //status flag, 0 - not started, 1 run, 2 pause, 3 stop
 	boolean pauseUsed = false;// pause use flag
	boolean rewind = false; //rewind flag 
	int rewindGen = 0;
	static boolean dynamicRule;
	
	//getter and setter methods 
	///////////////////////////////////////////////////////////////////////////////////
	

	public boolean isFullCircle() {
		return fullCircle;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public void setFullCircle(boolean fullCircle) {
		this.fullCircle = fullCircle;
	}


	

	

	

	public int getBranchNum() {
		return branchNum;
	}


	public void setBranchNum(int branchNum) {
		this.branchNum = branchNum;
	}


	public int getGenCount() {
		return genCount;
	}


	public void setGenCount(int genCount) {
		this.genCount = genCount;
	}


	public BGRule getRule() {
		return rule;
	}


	public void setRule(BGRule rule) {
		this.rule = rule;
	}


	public int getFrameHeight() {
		return frameHeight;
	}


	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}


	public int getFrameWidth() {
		return frameWidth;
	}


	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}


	public ArrayList<BGGeneration> getGenSet() {
		return genSet;
	}


	public void setGenSet(ArrayList<BGGeneration> genSet) {
		this.genSet = genSet;
	}


	//private constructor for singleton 	
	private BGGenerationSet() {
		
		
	}
	/**
	 * Singleton pattern object 
	 * @return object of BGGenerationSet class
	 */
	public static BGGenerationSet getInstance() {
		if(genSetObj == null) {
			genSetObj = new BGGenerationSet();
			genSetObj.addObserver(bc);
			genSetObj.addObserver(rt);
			//rt.showUI();
		}
		return genSetObj;
	}
	
	/**
	 * Gets values from GUI and updates instance
	 */
	public void getTreeProperties() {
		
		this.branchNum = RunTree.branchNumRT;
		this.genCount = RunTree.genCountRT;
		this.fullCircle =  RunTree.fullCircleRT;
		this.growthFactor = RunTree.growthFactorRT;
		this.stemLength = RunTree.stemLengthRT;
		rewind = RunTree.rewindRT;
		this.dynamicRule = RunTree.dynamicRuleRT;
		
	}
	/**
	 * Gets the status of the dynamic rule change condition
	 */
	public void getDynamicFlag() {
		this.dynamicRule = RunTree.dynamicRuleRT;
	}
	
	/**
	 * gets the current state of exceution from GUI and updates 
	 */
	public void getControlStatus() {
		this.status = RunTree.statusRT;
		dynamicRule = RunTree.dynamicRuleRT;
	}
	
	/**
	 * Runnable for thread implementation
	 * Generates new generations
	 * Sends notification to observing classes when a new generation is created  
	 */
	@Override
	public void run() {
		int gen = 0;
		while(1>0) {
			getDynamicFlag();
			getControlStatus();
			System.out.println(status);	
			if(status == 0){        //stop condition 
				getControlStatus();
				getDynamicFlag();
				getTreeProperties();
				System.out.println(status);
				//genSet.clear();
				newGen = null;
				gen = 0;
				rt.enableRewind();
				continue;
			}
			if(status == 2) { //pause condition
				getControlStatus();
				getDynamicFlag();
				System.out.println(dynamicRule);
				if(dynamicRule) {
					getTreeProperties();
				}
				pauseUsed = true;
				continue;
				//System.out.println(status);
			}
			if(!pauseUsed) {
				gen = 0;
				pauseUsed = false;
			}
			
			while((gen<genCount)&& (status == 1)) {
				getDynamicFlag();
				if(dynamicRule) {
					getTreeProperties();
				}
				
				
				if(newGen == null) {
					genSet.clear();
					//generate first stem at root index
					newGen = rule.firstGen(stemLength);
					genSet.add(newGen);
				}
				else {
					//generate new steams
					//pass the last generation  and the number of stems required for to rule.newGen
					//store gen before adding and send the stored gen///////////////////////////////////////////////////////////////////////////
					newGen = rule.newGen(newGen, branchNum, fullCircle, stemLength, growthFactor);
					genSet.add(newGen);
					}
					//notify subscribers that a new generation has been created 
					setChanged();
					notifyObservers(newGen);
					gen++;
					getControlStatus();
				}
			if (!(gen < genCount)) {
				gen = 0;
				RunTree.statusRT = 0;
				rt.enableRewind();
				newGen = null;
			}
			
		}
	}


	public void rewindGen(int val) {
		setChanged();
		notifyObservers(genSet.get(val));
		
	}
	
}
