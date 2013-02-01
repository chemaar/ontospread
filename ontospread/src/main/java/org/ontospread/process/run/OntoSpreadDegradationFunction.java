package org.ontospread.process.run;

import org.ontospread.state.OntoSpreadState;

public interface OntoSpreadDegradationFunction {

	public double applyDegradation(double []parameters, OntoSpreadState ontoSpreadState);
}
