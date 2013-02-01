package org.ontospread.process.run;

import org.ontospread.state.OntoSpreadState;

public class OntoSpreadDegradationFunctionIterationsImpl implements OntoSpreadDegradationFunction {

	public double applyDegradation(double[] parameters, OntoSpreadState ontoSpreadState) {
		int iterations = ontoSpreadState.getSpreadedConcepts().size();
		Double d = ontoSpreadState.getConcepts().get(ontoSpreadState.getConceptToSpread().getUri());
		double value = d/iterations;
		return (1.0+value)*Math.exp(-value);
	}

}
