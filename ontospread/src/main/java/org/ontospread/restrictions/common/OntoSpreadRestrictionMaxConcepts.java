package org.ontospread.restrictions.common;

import org.ontospread.restrictions.OntoSpreadSimpleRestriction;
import org.ontospread.restrictions.visitor.OntoSpreadRestrictionVisitor;
import org.ontospread.state.OntoSpreadState;

public class OntoSpreadRestrictionMaxConcepts extends OntoSpreadSimpleRestriction{
	
	private int maxConceptsSpreaded = 0;
	
	

	public OntoSpreadRestrictionMaxConcepts(int maxConceptsSpreaded) {
		super();
		this.maxConceptsSpreaded = maxConceptsSpreaded;
	}

	public Object accept(OntoSpreadRestrictionVisitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * This method returns true if the number of concepts spreaded is greater than maxConceptsSpreaded.
	 * The stop strategy evals: if 1 and 2 and ...n then stop else continue
	 * True is the condition to stop spreading.
	 */	
	public boolean eval(OntoSpreadState ontoSpreadState) {
		return ( ontoSpreadState.getSpreadedConcepts().size() < maxConceptsSpreaded);
	}

	public int getMaxConceptsSpreaded() {
		return maxConceptsSpreaded;
	}

	public void setMaxConceptsSpreaded(int minConceptsSpreaded) {
		this.maxConceptsSpreaded = minConceptsSpreaded;
	}

	@Override
	public String toString() {
		return super.toString()+" ("+this.maxConceptsSpreaded+")";
	}
}
