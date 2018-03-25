/**
 * 
 */
package edu.neu.csye6200.bg;

/**
 * @author harsh
 *This is the Rule class which processes all the input parameters such as number of generations, number of children stem per stem, pattern and angles of stem and generates new generation
 */
public class BGRule {
	
	double length =  20;	//basic length of stem default balue 20											
	boolean fullCircle = true;	//flag to decide if the stems should be split out in 180 degree of 360 degree true = 360 false = 180 

	/**
	 * This method generates a new generation based on an existing generation
	 * @param gen existing generation used as an input to create new generation
	 * @param childCount number of children per stem in new generation
	 * @param fc flag to decide if the stem should be split in 180 degree or 360 degree
	 * @param stemLength length of stems in new generation
	 * @return new generation
	 */
	public BGGeneration newGen(BGGeneration gen, int childCount, boolean fc, double stemLength, double growthFactor) {
		length = stemLength;
		fullCircle = fc;
		BGGeneration genNext = new BGGeneration();
		boolean first = true;
		for(BGLayer bgl: gen.getBgl()) {
			BGLayer newLayer = new BGLayer();
			
			for(BGStem bgs:bgl.getLayer()) {
				BGStem newStem = new BGStem(bgs, growthFactor);
				////////////////
				if(!first) {
					newStem.rootX = genNext.getLastLayer().layer.get(bgs.parentLayerIndex).endX;
					newStem.rootY = genNext.getLastLayer().layer.get(bgs.parentLayerIndex).endY;
					newStem.setEndX();
					newStem.setEndY();
				}
				first = false;
				
				newLayer.addtoLayer(newStem);
				
			}
			genNext.addtoGen(newLayer);
		}
		genNext.addtoGen(generateNewLayer(genNext.getLastLayer(),childCount));
		
		
		return genNext;
	}
	/**
	 * This method generates a new layer of stems on top of a pre exixting layer 
	 * @param layer BGLayer instance on which a new layer will be built
	 * @param childCount number of child stems to be generated per parent stem 
	 * @return new BGLayer instance
	 */
	public BGLayer generateNewLayer(BGLayer layer, int childCount) {
		BGLayer newLayer = new BGLayer();
		for(BGStem stem: layer.layer) {
			double endsx = stem.getEndX();
			double endsy = stem.getEndY();
			double angleDiff=0;
			if(fullCircle) {
				angleDiff = 360/(childCount); //divides 360 degree equally among children
			}
			else {
				angleDiff = 180/(childCount + 1); //divides 180 degree equally among children
			}
			
			double oldAngle = stem.angle;
			if(childCount%2 ==0) {																						//if number of children stem is even
				for(int i = 1; i<= childCount/2; i++) {																	//add half the stem clockwise
					BGStem s = null;
						s = new BGStem(endsx,endsy,length,oldAngle + (angleDiff*i),(stem.getId()*10)+i);
					newLayer.layer.add(s);
					s.myLayerIndex = newLayer.layer.size()-1;//
					s.parentLayerIndex = stem.myLayerIndex;
				}
				
				for(int i = 1; i<= childCount/2; i++) {																	//add other half anti-clockwise
					BGStem s = null;
					s = new BGStem(endsx,endsy,length,oldAngle - (angleDiff*i),(stem.getId()*10)+i);
					newLayer.layer.add(s);
					s.myLayerIndex = newLayer.layer.size()-1;//
					s.parentLayerIndex = stem.myLayerIndex;
				}
			}
			else {																										//if number of stem is odd
				for(int i = 1; i<= childCount/2; i++) {																	//add half the stem clockwise
					BGStem s = null;
						s = new BGStem(endsx,endsy,length,oldAngle + (angleDiff*i),(stem.getId()*10)+i);
					newLayer.layer.add(s);
					s.myLayerIndex = newLayer.layer.size()-1;//
					s.parentLayerIndex = stem.myLayerIndex;
				}
				{
				BGStem s = null;
					s = new BGStem(endsx,endsy,length,oldAngle ,(stem.getId()*10)+((childCount/2)+1));
				newLayer.layer.add(s);
				s.myLayerIndex = newLayer.layer.size()-1;//
				s.parentLayerIndex = stem.myLayerIndex;
				}
				for(int i = 1; i<= childCount/2; i++) {
					//double oldAngle = stem.angle;
					BGStem s = null;
					s = new BGStem(endsx,endsy,length,oldAngle - (angleDiff*i),(stem.getId()*10)+i);
					newLayer.layer.add(s);
					s.myLayerIndex = newLayer.layer.size()-1;//
					s.parentLayerIndex = stem.myLayerIndex;
				}
			}
			
			
		}
		
		return newLayer;
		
	}
	/**
	 * This method Generates the first stem at root position
	 * @param stemLength the length of the stems to be generated
	 * @return first Generation and instance of BGGeneration
	 */
	public BGGeneration firstGen(double stemLength) {
		length = stemLength;
		BGStem stem = new BGStem(0,0,length,90,1);//create stem at root 
		BGLayer layer = new BGLayer();// add stem to first generation
		layer.layer.add(stem);
		stem.myLayerIndex = layer.layer.size()-1;//
		BGGeneration genOne = new BGGeneration();
		genOne.addtoGen(layer);
		return genOne;
	}

}
