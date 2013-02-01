package org.ontospread.restrictions;

import org.apache.log4j.Logger;
import org.ontospread.restrictions.visitor.OntoSpreadRestrictionVisitor;
import org.ontospread.state.OntoSpreadState;

public abstract class OntoSpreadSimpleRestriction implements OntoSpreadRestriction {

	protected static Logger logger = Logger.getLogger(OntoSpreadSimpleRestriction.class);

	public abstract boolean eval(OntoSpreadState ontoSpreadState) ;

	public Object accept(OntoSpreadRestrictionVisitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
