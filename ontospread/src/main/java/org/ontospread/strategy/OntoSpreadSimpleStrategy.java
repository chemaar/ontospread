package org.ontospread.strategy;

import org.apache.log4j.Logger;
import org.ontospread.restrictions.OntoSpreadRestriction;
import org.ontospread.state.OntoSpreadState;

public class OntoSpreadSimpleStrategy extends OntoSpreadStrategy{

	public OntoSpreadSimpleStrategy(OntoSpreadRestriction restrictions) {
		super(restrictions);
	}

	protected static Logger logger = Logger.getLogger(OntoSpreadSimpleStrategy.class);

	@Override
	public OntoSpreadState strategy(OntoSpreadState ontoSpreadState) {
		return ontoSpreadState;
	}



}
