package org.ontospread.restrictions.visitor;

import junit.framework.TestCase;

import org.ontospread.restrictions.OntoSpreadCompositeRestriction;
import org.ontospread.restrictions.OntoSpreadRestriction;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMaxConcepts;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMinConcepts;
import org.ontospread.state.OntoSpreadState;

public class OntoSpreadBooleanRestrictionVisitorTest extends TestCase {

	public void testVisitOntoSpreadCompositeRestriction() {
		OntoSpreadCompositeRestriction restrictions = new OntoSpreadCompositeRestriction();
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMinConcepts(2));
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMaxConcepts(10));		
		OntoSpreadState state = new OntoSpreadState();
		OntoSpreadBooleanRestrictionVisitor visitor = new OntoSpreadBooleanRestrictionVisitor();
		visitor.setOntoSpreadState(state);
		Object stop = visitor.visit(restrictions); 
		assertEquals(stop.getClass(),Boolean.class);
		assertTrue(((Boolean)stop).booleanValue());
		for(int i = 0; i<1;i++){
			state.getSpreadedConcepts().add(String.valueOf(i));
		}
		stop = visitor.visit(restrictions); 
		assertTrue(((Boolean)stop).booleanValue());
		for(int i = 2; i<10;i++){
			state.getSpreadedConcepts().add(String.valueOf(i));
		}
		stop = visitor.visit(restrictions); 
		assertFalse(((Boolean)stop).booleanValue());
		
	}

	public void testVisitOntoSpreadSimpleRestriction() {
		OntoSpreadRestriction restriction = new OntoSpreadRestrictionMinConcepts(2);
		OntoSpreadState state = new OntoSpreadState();
		OntoSpreadBooleanRestrictionVisitor visitor = new OntoSpreadBooleanRestrictionVisitor();
		visitor.setOntoSpreadState(state);
		Object stop = visitor.visit(restriction); 
		assertEquals(stop.getClass(),Boolean.class);
		assertTrue(((Boolean)stop).booleanValue());
		for(int i = 0; i<10;i++){
			state.getSpreadedConcepts().add(String.valueOf(i));
		}
		stop = visitor.visit(restriction); 
		assertFalse(((Boolean)stop).booleanValue());		
	}

}
