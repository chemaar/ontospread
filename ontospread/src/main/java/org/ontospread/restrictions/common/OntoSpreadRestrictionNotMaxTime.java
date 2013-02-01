package org.ontospread.restrictions.common;

import org.ontospread.restrictions.OntoSpreadSimpleRestriction;
import org.ontospread.restrictions.visitor.OntoSpreadRestrictionVisitor;
import org.ontospread.state.OntoSpreadState;

public class OntoSpreadRestrictionNotMaxTime extends OntoSpreadSimpleRestriction{
	
	private long maxTime = 0;
	
	

	public OntoSpreadRestrictionNotMaxTime(long maxTime) {
		super();
		this.maxTime = maxTime;
	}

	public Object accept(OntoSpreadRestrictionVisitor visitor) {
		return visitor.visit(this);
	}

	public boolean eval(OntoSpreadState ontoSpreadState) {
		return (ontoSpreadState.getSpreadTime()<this.maxTime);
	}

	public long getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}
	@Override
	public String toString() {
		return super.toString()+" ("+this.maxTime+")";
	}
	
}
