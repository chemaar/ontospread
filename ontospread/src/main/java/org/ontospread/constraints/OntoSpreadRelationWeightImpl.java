package org.ontospread.constraints;

import java.util.HashSet;
import java.util.Set;

public class OntoSpreadRelationWeightImpl implements OntoSpreadRelationWeight{

	private double defaultValue = DEFAULT_VALUE;
	public double getWeight(String qualRelationUri){
		return DEFAULT_VALUE;
	}

	public Set<String> getRelationsUris() {
		return new HashSet<String>();
	}

	public void setWeight(String qualRelationUri, double value) {
				
	}

	public double getDefault() {
		return defaultValue;
	}

	public void setDefault(double value) {
		this.defaultValue = value;
	}
}
