package org.ontospread.restrictions.common;

import org.ontospread.restrictions.OntoSpreadSimpleRestriction;
import org.ontospread.restrictions.visitor.OntoSpreadRestrictionVisitor;
import org.ontospread.state.OntoSpreadState;

public class OntoSpreadRestrictionMinConcepts extends OntoSpreadSimpleRestriction{
	
	private int minConceptsSpreaded = 0;
	
	

	public OntoSpreadRestrictionMinConcepts(int minConceptsSpreaded) {
		super();
		this.minConceptsSpreaded = minConceptsSpreaded;
	}

	public Object accept(OntoSpreadRestrictionVisitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * This method returns true if the number of concepts spreaded is greater than minConceptsSpreaded.
	 */
	public boolean eval(OntoSpreadState ontoSpreadState) {		
		return (minConceptsSpreaded > ontoSpreadState.getSpreadedConcepts().size());
	}

	public int getMinConceptsSpreaded() {
		return minConceptsSpreaded;
	}

	public void setMinConceptsSpreaded(int minConceptsSpreaded) {
		this.minConceptsSpreaded = minConceptsSpreaded;
	}

	@Override
	public String toString() {
		return super.toString()+" ("+this.minConceptsSpreaded+")";
	}
}
