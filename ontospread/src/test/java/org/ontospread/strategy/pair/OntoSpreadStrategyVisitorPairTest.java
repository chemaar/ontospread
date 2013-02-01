package org.ontospread.strategy.pair;

import org.ontospread.restrictions.OntoSpreadCompositeRestriction;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMaxConcepts;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMinActivationValue;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMinConcepts;
import org.ontospread.restrictions.visitor.OntoSpreadBooleanRestrictionVisitor;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.strategy.OntoSpreadSelectConceptStrategy;
import org.ontospread.strategy.OntoSpreadSimpleStrategy;
import org.ontospread.strategy.OntoSpreadStrategy;
import org.ontospread.to.ConceptTO;

import junit.framework.TestCase;

public class OntoSpreadStrategyVisitorPairTest extends TestCase {

	public void testApplyStopStrategy() {
		//Restrictions
		OntoSpreadCompositeRestriction restrictions = new OntoSpreadCompositeRestriction();
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMinConcepts(2));
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMaxConcepts(10));
		//State		
		OntoSpreadState state = new OntoSpreadState();
		
		OntoSpreadStrategy strategy = new OntoSpreadSimpleStrategy(restrictions);		
		OntoSpreadStrategyVisitorPair pair = new OntoSpreadStrategyVisitorPair(strategy,new OntoSpreadBooleanRestrictionVisitor());
		
		Object continues = pair.applyStrategy(state) ;
		assertEquals(continues.getClass(),Boolean.class);
		assertTrue(((Boolean)continues).booleanValue());
		for(int i = 0; i<2;i++){
			state.getSpreadedConcepts().add(String.valueOf(i));
		}
		assertTrue(((Boolean)continues).booleanValue());
		
		for(int i = 2; i<10;i++){
			state.getSpreadedConcepts().add(String.valueOf(i));
		}
		continues = pair.applyStrategy(state) ;
		assertFalse(((Boolean)continues).booleanValue());
		
	}
	
	public void testApplySelectConceptStrategy() {
		//Restrictions
		OntoSpreadCompositeRestriction restrictions = new OntoSpreadCompositeRestriction();
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMinActivationValue(10.0));
		//State		
		OntoSpreadState state = new OntoSpreadState();
		
		
		ConceptTO conceptTO = new ConceptTO("http://uri.to.test");
		
		state.setConceptToSpread(conceptTO);

		
		OntoSpreadStrategy strategy = new OntoSpreadSelectConceptStrategy(restrictions);		
		OntoSpreadStrategyVisitorPair pair = new OntoSpreadStrategyVisitorPair(strategy,new OntoSpreadBooleanRestrictionVisitor());
		
		state.setCurrentScore(11.0);		
		Object select = pair.applyStrategy(state) ;
		assertEquals(select.getClass(),Boolean.class);
		assertTrue(((Boolean)select).booleanValue());
		state.setCurrentScore(9.0);
		select = pair.applyStrategy(state) ;
		assertFalse(((Boolean)select).booleanValue());
		
	}
}
