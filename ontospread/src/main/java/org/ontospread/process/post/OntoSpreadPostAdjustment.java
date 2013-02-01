package org.ontospread.process.post;

import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.state.OntoSpreadState;

public interface OntoSpreadPostAdjustment {

	public OntoSpreadState applyPostAdjustment(OntoSpreadState ontoSpreadState) throws ConceptNotFoundException;

	public boolean hasMakePrize();

	public void makePrize(OntoSpreadState ontoSpreadState);

}
