package org.ontospread.restrictions.visitor;

import org.ontospread.restrictions.OntoSpreadCompositeRestriction;
import org.ontospread.restrictions.OntoSpreadRestriction;
import org.ontospread.restrictions.OntoSpreadSimpleRestriction;

public class OntoSpreadBooleanRestrictionVisitor extends
		OntoSpreadRestrictionVisitorAdapter {

	@Override
	public Object visit(OntoSpreadCompositeRestriction compositeRestrictions) {
		boolean storedValue = true;
		for (OntoSpreadRestriction restriction : compositeRestrictions.getRestrictions()) {
			Boolean value = (Boolean)restriction.accept(this);
			storedValue = storedValue &&  value.booleanValue();			
		}
		return storedValue?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public Object visit(OntoSpreadSimpleRestriction restriction) {	
		return restriction.eval(getOntoSpreadState())?Boolean.TRUE:Boolean.FALSE;
	}


}
