package org.ontospread.process.pre;


public class OntoSpreadPreAdjustmentConfigImpl implements OntoSpreadPreAdjustmentConfig{

	private double initialScore = 0.0;
	
	public OntoSpreadPreAdjustmentConfigImpl(double initialScore){
		this.initialScore = initialScore;
	}
	public double getInitialScore(){
		return this.initialScore;
	}
}
