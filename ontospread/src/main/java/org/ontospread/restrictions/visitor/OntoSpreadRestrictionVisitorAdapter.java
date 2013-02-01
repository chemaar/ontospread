package org.ontospread.restrictions.visitor;

import org.apache.log4j.Logger;
import org.ontospread.restrictions.OntoSpreadCompositeRestriction;
import org.ontospread.restrictions.OntoSpreadRestriction;
import org.ontospread.restrictions.OntoSpreadSimpleRestriction;
import org.ontospread.state.OntoSpreadState;

public class OntoSpreadRestrictionVisitorAdapter extends OntoSpreadRestrictionVisitor{

	protected static Logger logger = Logger.getLogger(OntoSpreadRestrictionVisitorAdapter.class);
	private OntoSpreadState ontoSpreadState;
	
	public OntoSpreadRestrictionVisitorAdapter(OntoSpreadState ontoSpreadState) {
		super();
		this.ontoSpreadState = ontoSpreadState;
	}
	public OntoSpreadRestrictionVisitorAdapter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public OntoSpreadState getOntoSpreadState() {
		return this.ontoSpreadState;
	}
	@Override
	public void setOntoSpreadState(OntoSpreadState ontoSpreadState) {
		this.ontoSpreadState = ontoSpreadState;
	}
	@Override
	public Object visit(OntoSpreadCompositeRestriction compositeRestrictions) {
		for (OntoSpreadRestriction restriction : compositeRestrictions.getRestrictions()) {
			restriction.accept(this);
		}
		return null;
	}
	@Override
	public Object visit(OntoSpreadSimpleRestriction restriction) {
		return restriction.eval(getOntoSpreadState());
	}
	@Override
	public Object visit(OntoSpreadRestriction ontoSpreadRestriction) {
		if(ontoSpreadRestriction instanceof OntoSpreadCompositeRestriction){
			return this.visit((OntoSpreadCompositeRestriction) ontoSpreadRestriction);
		}else  if(ontoSpreadRestriction instanceof OntoSpreadSimpleRestriction){
			return this.visit((OntoSpreadSimpleRestriction)ontoSpreadRestriction);
		}
		return null;
	};
}
