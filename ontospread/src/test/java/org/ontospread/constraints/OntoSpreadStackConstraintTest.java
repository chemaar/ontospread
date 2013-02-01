package org.ontospread.constraints;

import org.ontospread.state.OntoSpreadState;
import org.ontospread.state.UriDepthPair;

import junit.framework.TestCase;

public class OntoSpreadStackConstraintTest extends TestCase {

	public void testNotEmpty(){
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.getSortedList().add(new UriDepthPair("http://one.uri", 0));
		assertTrue(OntoSpreadStackConstraint.checkNotEmptyStack(ontoSpreadState));
	}
	public void testNullStack(){
		assertFalse(OntoSpreadStackConstraint.checkNotEmptyStack(null));
	}
	public void testEmpty(){
		OntoSpreadState ontoSpreadState = new OntoSpreadState();		
		assertFalse(OntoSpreadStackConstraint.checkNotEmptyStack(ontoSpreadState));
	}
	
}
