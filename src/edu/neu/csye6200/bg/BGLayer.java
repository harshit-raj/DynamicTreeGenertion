/**
 * 
 */
package edu.neu.csye6200.bg;

import java.util.ArrayList;

/**BGLayer class is a collection of BGSTems
 * @author harsh
 *
 */
public class BGLayer {
	
	protected ArrayList<BGStem> layer;
	protected int layerNum;
	
	/**
	 * BGLayer Constructor
	 */
	public BGLayer() {
		layer = new ArrayList<BGStem>();
	}
	
	//Getter and setter methods

	public ArrayList<BGStem> getLayer() {
		return layer;
	}

	public void setLayer(ArrayList<BGStem> layer) {
		this.layer = layer;
	}
	
	public void addtoLayer(BGStem st) {
		layer.add(st);
	}

	public int getLayerNum() {
		return layerNum;
	}

	public void setLayerNum(int layerNum) {
		this.layerNum = layerNum;
	}
	
	

}
