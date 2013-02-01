package org.ontospread.restrictions.common;

import java.util.HashSet;
import java.util.Set;

import org.ontospread.restrictions.OntoSpreadRestriction;
import org.ontospread.state.OntoSpreadState;

import junit.framework.TestCase;

public class OntoSpreadRestrictionMinConceptsTest extends TestCase {

	public void testEval() {
		OntoSpreadRestriction minRestriction = new OntoSpreadRestrictionMinConcepts(10);
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		Set<String> spreadedConcepts= new HashSet<String>();
		ontoSpreadState.setSpreadedConcepts(spreadedConcepts);
		assertTrue(minRestriction.eval(ontoSpreadState));
		for(int i = 0; i<=10;i++){
			ontoSpreadState.getSpreadedConcepts().add(String.valueOf(i));
		}
		assertFalse(minRestriction.eval(ontoSpreadState));
	}

	public void testGetMinConceptsSpreaded() {
		OntoSpreadRestrictionMinConcepts minRestriction = new OntoSpreadRestrictionMinConcepts(10);
		assertEquals(10, minRestriction.getMinConceptsSpreaded());	
	}

	public void testSetMaxConceptsSpreaded() {
		OntoSpreadRestrictionMinConcepts minRestriction = new OntoSpreadRestrictionMinConcepts(10);
		assertEquals(10, minRestriction.getMinConceptsSpreaded());
		minRestriction.setMinConceptsSpreaded(15);
		assertEquals(15, minRestriction.getMinConceptsSpreaded());
	}

}
