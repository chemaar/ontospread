package org.ontospread.constraints;

import org.ontospread.state.OntoSpreadState;

public final class OntoSpreadStackConstraint {

	public static final boolean checkNotEmptyStack(OntoSpreadState ontoSpreadState){
		return (ontoSpreadState!= null && ontoSpreadState.getSortedList().size()!=0);		
	}
}
