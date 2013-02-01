package org.ontospread.restrictions.common;

import java.util.HashSet;
import java.util.Set;

import org.ontospread.restrictions.OntoSpreadRestriction;
import org.ontospread.state.OntoSpreadState;

import junit.framework.TestCase;

public class OntoSpreadRestrictionMaxConceptsTest extends TestCase {

	public void testEval() {
		OntoSpreadRestriction maxRestriction = new OntoSpreadRestrictionMaxConcepts(10);
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		Set<String> spreadedConcepts= new HashSet<String>();
		ontoSpreadState.setSpreadedConcepts(spreadedConcepts);
		assertTrue(maxRestriction.eval(ontoSpreadState));
		for(int i = 0; i<=10;i++){
			ontoSpreadState.getSpreadedConcepts().add(String.valueOf(i));
		}
		assertFalse(maxRestriction.eval(ontoSpreadState));
	}

	public void testGetMaxConceptsSpreaded() {
		OntoSpreadRestrictionMaxConcepts maxRestriction = new OntoSpreadRestrictionMaxConcepts(10);
		assertEquals(10, maxRestriction.getMaxConceptsSpreaded());	
	}

	public void testSetMaxConceptsSpreaded() {
		OntoSpreadRestrictionMaxConcepts maxRestriction = new OntoSpreadRestrictionMaxConcepts(10);
		assertEquals(10, maxRestriction.getMaxConceptsSpreaded());
		maxRestriction.setMaxConceptsSpreaded(15);
		assertEquals(15, maxRestriction.getMaxConceptsSpreaded());
	}

}
