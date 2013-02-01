package org.ontospread.restrictions.common;

import org.ontospread.restrictions.OntoSpreadRestriction;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.to.ConceptTO;

import junit.framework.TestCase;

public class OntoSpreadRestrictionContextTest extends TestCase {

	public void testTrueEval(){
		OntoSpreadRestriction contextRestriction = new OntoSpreadRestrictionContext("http://context",10);
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ConceptTO conceptToSpread = new ConceptTO("http://context#Concept");
		ontoSpreadState.setConceptToSpread(conceptToSpread );
		assertTrue(contextRestriction.eval(ontoSpreadState));
	}
	public void testFalseEval(){
		OntoSpreadRestriction contextRestriction = new OntoSpreadRestrictionContext("http://context",10);
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ConceptTO conceptToSpread = new ConceptTO("http://contex1t#Concept");
		ontoSpreadState.setConceptToSpread(conceptToSpread );
		assertFalse(contextRestriction.eval(ontoSpreadState));
	}
}
