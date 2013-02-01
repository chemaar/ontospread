package org.ontospread.process.run;

import org.ontospread.state.OntoSpreadCommonUtils;
import org.ontospread.state.OntoSpreadState;

public class OntoSpreadDegradationFunctionImpl implements OntoSpreadDegradationFunction {

	public double applyDegradation(double[] parameters, OntoSpreadState ontoSpreadState) {
		return OntoSpreadCommonUtils.degradationFunction((int) parameters[0]);
	}

}
