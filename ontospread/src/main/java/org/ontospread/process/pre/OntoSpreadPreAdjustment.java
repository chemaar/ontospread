package org.ontospread.process.pre;

import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.state.OntoSpreadState;

public interface OntoSpreadPreAdjustment {

	public OntoSpreadState applyPreAdjustment(OntoSpreadState ontoSpreadState) throws ConceptNotFoundException;
	public OntoSpreadPreAdjustmentConfig getOntoPreAdjustmentConfig();
	public void setOntoPreAdjustmentConfig(OntoSpreadPreAdjustmentConfig ontoSpreadPreConfig);
}
