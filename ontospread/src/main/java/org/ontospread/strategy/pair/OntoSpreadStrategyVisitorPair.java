package org.ontospread.strategy.pair;

import org.ontospread.restrictions.visitor.OntoSpreadRestrictionVisitor;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.strategy.OntoSpreadStrategy;

public class OntoSpreadStrategyVisitorPair {
	private OntoSpreadStrategy ontoSpreadStrategy;
	private OntoSpreadRestrictionVisitor ontoSpreadStrategyVisitor;
	
	public OntoSpreadStrategyVisitorPair(OntoSpreadStrategy ontoSpreadStrategy, OntoSpreadRestrictionVisitor ontoSpreadStrategyVisitor) {
		super();
		this.ontoSpreadStrategy = ontoSpreadStrategy;
		this.ontoSpreadStrategyVisitor = ontoSpreadStrategyVisitor;
	}
	
	public Object applyStrategy(OntoSpreadState ontoSpreadState){
		//Set state		
		this.ontoSpreadStrategyVisitor.setOntoSpreadState(this.ontoSpreadStrategy.strategy(ontoSpreadState));
		return this.ontoSpreadStrategyVisitor.visit(getOntoSpreadStrategy().getRestriction());
	}

	public OntoSpreadStrategy getOntoSpreadStrategy() {
		return ontoSpreadStrategy;
	}

	public void setOntoSpreadStrategy(OntoSpreadStrategy ontoSpreadStrategy) {
		this.ontoSpreadStrategy = ontoSpreadStrategy;
	}

	public OntoSpreadRestrictionVisitor getOntoSpreadStrategyVisitor() {
		return ontoSpreadStrategyVisitor;
	}

	public void setOntoSpreadStrategyVisitor(
			OntoSpreadRestrictionVisitor ontoSpreadStrategyVisitor) {
		this.ontoSpreadStrategyVisitor = ontoSpreadStrategyVisitor;
	}
	
}
