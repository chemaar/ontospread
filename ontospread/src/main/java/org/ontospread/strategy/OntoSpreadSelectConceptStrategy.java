package org.ontospread.strategy;

import org.ontospread.restrictions.OntoSpreadRestriction;
import org.ontospread.state.OntoSpreadState;


public class OntoSpreadSelectConceptStrategy extends OntoSpreadStrategy {
	
	public OntoSpreadSelectConceptStrategy(OntoSpreadRestriction restrictions) {
		super(restrictions);
	}

	public  OntoSpreadState strategy(OntoSpreadState state){
		//Select concept, sort and set ConceptTO
		return state;
	}
}
