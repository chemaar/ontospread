package org.ontospread.restrictions;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ontospread.restrictions.visitor.OntoSpreadRestrictionVisitor;
import org.ontospread.state.OntoSpreadState;

public class OntoSpreadCompositeRestriction implements OntoSpreadRestriction{
	
	protected static Logger logger = Logger.getLogger(OntoSpreadCompositeRestriction.class);
	List<OntoSpreadRestriction> restrictions = new LinkedList<OntoSpreadRestriction>();

	
	public OntoSpreadCompositeRestriction(){

	}

	public Object accept(OntoSpreadRestrictionVisitor visitor) {
		return visitor.visit(this);
	}

	public boolean eval(OntoSpreadState ontoSpreadState) {
		//The restrictions can not be evaluated in this method because the result value is not known.
		return false;
	}


	public List<OntoSpreadRestriction> getRestrictions() {
		return restrictions;
	}


	public void setRestrictions(List<OntoSpreadRestriction> restrictions) {
		this.restrictions = restrictions;
	}


	
	

}
