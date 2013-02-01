package org.ontospread.restrictions.visitor;

import org.ontospread.restrictions.OntoSpreadCompositeRestriction;
import org.ontospread.restrictions.OntoSpreadRestriction;
import org.ontospread.restrictions.OntoSpreadSimpleRestriction;
import org.ontospread.state.OntoSpreadState;

public abstract class OntoSpreadRestrictionVisitor {

	public abstract Object visit(OntoSpreadCompositeRestriction compositeRestrictions);

	public abstract Object visit(OntoSpreadSimpleRestriction restriction);

	public abstract Object visit(OntoSpreadRestriction ontoSpreadRestriction);//This methods delegate in other ones
	
	public abstract OntoSpreadState getOntoSpreadState();
	
	public abstract void setOntoSpreadState(OntoSpreadState ontoSpreadState);
	
	
}
