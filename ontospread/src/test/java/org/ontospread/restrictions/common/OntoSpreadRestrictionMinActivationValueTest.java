package org.ontospread.restrictions.common;

import junit.framework.TestCase;

import org.ontospread.restrictions.OntoSpreadRestriction;
import org.ontospread.state.OntoSpreadState;

public class OntoSpreadRestrictionMinActivationValueTest extends TestCase {


	public void testEval() {
		OntoSpreadRestriction valueRestriction = new OntoSpreadRestrictionMinActivationValue(10.0);
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setCurrentScore(11.0);
		assertTrue(valueRestriction.eval(ontoSpreadState));
		ontoSpreadState.setCurrentScore(9.0);		
		assertFalse(valueRestriction.eval(ontoSpreadState));		
	}
}
