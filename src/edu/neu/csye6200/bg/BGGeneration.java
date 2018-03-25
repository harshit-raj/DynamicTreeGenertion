/**
 * 
 */
package edu.neu.csye6200.bg;

import java.util.ArrayList;

/**
 * @author harsh
 *
 */
public class BGGeneration {

	protected ArrayList<BGLayer> bgl = new ArrayList<BGLayer>();
	int genNum;
	/**
	 * 
	 */
	public BGGeneration() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<BGLayer> getBgl() {
		return bgl;
	}
	public void setBgl(ArrayList<BGLayer> bgl) {
		this.bgl = bgl;
	}
	
	public void addtoGen(BGLayer layer) {
		bgl.add(layer);
	}
	public int getGenNum() {
		return genNum;
	}
	public void setGenNum(int genNum) {
		this.genNum = genNum;
	}
	
	public BGLayer getLastLayer(){
		//System.out.println(bgl);
		if(bgl.size() == 0) {
			return null;
		}
		return bgl.get(bgl.size()-1);
	}
	
	
	

}
