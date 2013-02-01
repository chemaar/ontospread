package org.ontospread.restrictions;

import org.ontospread.restrictions.visitor.OntoSpreadRestrictionVisitor;
import org.ontospread.state.OntoSpreadState;

public interface OntoSpreadRestriction {

	public boolean eval(OntoSpreadState ontoSpreadState);
	public Object accept(OntoSpreadRestrictionVisitor visitor);
}
