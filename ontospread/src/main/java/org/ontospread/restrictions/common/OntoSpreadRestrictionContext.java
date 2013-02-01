package org.ontospread.restrictions.common;

import org.ontospread.restrictions.OntoSpreadSimpleRestriction;
import org.ontospread.restrictions.visitor.OntoSpreadRestrictionVisitor;
import org.ontospread.state.OntoSpreadState;

public class OntoSpreadRestrictionContext extends OntoSpreadSimpleRestriction{	
	
	private String context = "";
	private int maxRetries;
	private int currentRetries;
	
	

	public OntoSpreadRestrictionContext(String context, int maxRetries) {
		super();
		this.context = context;
		this.maxRetries = maxRetries;
		currentRetries = 0;
	}

	public Object accept(OntoSpreadRestrictionVisitor visitor) {
		return visitor.visit(this);
	}

	public boolean eval(OntoSpreadState ontoSpreadState) {
		if(ontoSpreadState.getConceptToSpread().getUri() == null) return true;
		return (ontoSpreadState.getConceptToSpread().getUri().startsWith(context) && this.currentRetries < this.maxRetries);
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	public String toString() {
		return super.toString()+" ("+this.context+", maxRetries="+this.maxRetries+", currentRetries="+this.currentRetries+")";
	}

	public int getCurrentRetries() {
		return currentRetries;
	}

	public void setCurrentRetries(int currentRetries) {
		this.currentRetries = currentRetries;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	
}
