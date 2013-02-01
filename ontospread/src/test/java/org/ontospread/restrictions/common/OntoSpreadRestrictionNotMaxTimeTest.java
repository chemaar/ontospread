package org.ontospread.restrictions.common;

import org.ontospread.restrictions.OntoSpreadRestriction;
import org.ontospread.state.OntoSpreadState;

import junit.framework.TestCase;

public class OntoSpreadRestrictionNotMaxTimeTest extends TestCase {

	public void testMaxTime(){
		OntoSpreadRestriction restriction = new OntoSpreadRestrictionNotMaxTime(2000);
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setSpreadTime(0);
		assertTrue(restriction.eval(ontoSpreadState));
	}
	
	public void testMaxTime1(){
		OntoSpreadRestriction restriction = new OntoSpreadRestrictionNotMaxTime(2000);
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setSpreadTime(1999);
		assertTrue(restriction.eval(ontoSpreadState));
	}
	
	public void testMaxTime2(){
		OntoSpreadRestriction restriction = new OntoSpreadRestrictionNotMaxTime(2000);
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setSpreadTime(2001);
		assertFalse(restriction.eval(ontoSpreadState));
	}
}
