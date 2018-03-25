/**
 * 
 */
package edu.neu.csye6200.bg;

/**
 * @author harsh
 *
 */
public class BGStem {
	//start co-ordinates of the stem
	protected double rootX; 
	protected double rootY;
	//end co-ordinates of the stem
	protected double endX;
	protected double endY;
	//length of the stem, used only when generating a new stem
	protected double length;
	//angle of the stem
	protected double angle;
	//unique id of the stem
	protected int id;
	//color of the stem(unused)
	protected int color;
	//id of the parent of the stem
	protected int parentid;
	//index of the stem in the layer 
	protected int myLayerIndex;
	//index of stems parent in the layer
	protected int parentLayerIndex;
	
	
	
	
	//getter and setter method

	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public int getMyIndex() {
		return myLayerIndex;
	}
	public void setMyIndex(int myIndex) {
		this.myLayerIndex = myIndex;
	}
	public int getParentIndex() {
		return parentLayerIndex;
	}
	public void setParentIndex(int parentIndex) {
		this.parentLayerIndex = parentIndex;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getRootX() {
		return rootX;
	}
	public void setRootX(double rootX) {
		this.rootX = rootX;
	}
	public double getRootY() {
		return rootY;
	}
	public void setRootY(double rootY) {
		this.rootY = rootY;
	}
	public double getEndX() {
		return endX;
	}
	/**
	 * Sets the X co-0rdinate of the end of stem based on its start co-ordinates and angle
	 */
	public void setEndX() {
		double cos = (Math.cos(Math.toRadians(angle)));
		if (angle==90) {
			cos = 0;
		}
		endX = rootX + (length * cos);
	}
	public double getEndY() {
		return endY;
	}
	/**
	 * Sets the Y co-0rdinate of the end of stem based on its start co-ordinates and angle
	 */
	public void setEndY() {
		double sin = (Math.sin(Math.toRadians(angle)));
		if (angle==90) {
			sin = 1;
		}
		endY = rootY - (length * sin);
	}

	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	/**
	 * Constructor of BGStem 
	 * @param rootX X coordiate of the start of stem
	 * @param rootY Y coordiate of the start of stem
	 * @param length length of stem
	 * @param angle angle of stem
	 * @param id id of stem
	 */
	public BGStem(double rootX, double rootY, double length, double angle, int id) {
		this.rootX = rootX;
		this.rootY = rootY;
		this.length = length;
		this.angle = angle;
		this.id = id;
		setEndX();
		setEndY();
	}
	/**
	 * Constructor os BGStem, meant to copy a stem and grow it according to the growthFactor
	 * @param bgs instance of BGStem to be copied
	 * @param growthFactor value deciding the growth of the stem
	 */
	public BGStem(BGStem bgs, double growthFactor) {
		super();
		this.rootX = bgs.rootX;
		this.rootY = bgs.rootY;
		this.length = bgs.length * growthFactor;
		this.angle = bgs.angle;
		this.id = bgs.id;
		this.myLayerIndex = bgs.myLayerIndex;
		this.parentLayerIndex = bgs.parentLayerIndex;
		this.setEndX();
		this.setEndY();
		
	}
	
	/**
	 * toString method to print the stem, soecially useful when debussing without a GUI
	 */
	@Override
	public String toString() {
		String s = "Root x "+rootX+" RootY " + rootY+" length "+ length+ " angle "+ angle +" EndX " + endX +" End Y " +endY+" ID "+id+" Index "+myLayerIndex+" Parent index "+parentLayerIndex;
		return s;
	}

}
