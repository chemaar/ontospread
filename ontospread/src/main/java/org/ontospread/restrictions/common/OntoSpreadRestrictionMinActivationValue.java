package org.ontospread.restrictions.common;

import org.ontospread.restrictions.OntoSpreadSimpleRestriction;
import org.ontospread.restrictions.visitor.OntoSpreadRestrictionVisitor;
import org.ontospread.state.OntoSpreadState;

public class OntoSpreadRestrictionMinActivationValue extends OntoSpreadSimpleRestriction{
	
	private double minActivationValue = 0;
	
	

	public OntoSpreadRestrictionMinActivationValue(double minConceptsSpreaded) {
		super();
		this.minActivationValue = minConceptsSpreaded;
	}

	public Object accept(OntoSpreadRestrictionVisitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * This method returns true if the number of concepts spreaded is greater than minConceptsSpreaded.
	 */
	public boolean eval(OntoSpreadState ontoSpreadState) {
		return minActivationValue <= ontoSpreadState.getCurrentScore();
	}

	public double getMinActivationValue() {
		return minActivationValue;
	}

	public void setMinActivationValue(double minActivationValue) {
		this.minActivationValue = minActivationValue;
	}

	@Override
	public String toString() {
		return super.toString()+" ("+this.minActivationValue+")";
	}

}
