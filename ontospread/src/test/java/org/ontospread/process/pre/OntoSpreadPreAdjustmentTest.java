package org.ontospread.process.pre;

import java.util.List;

import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.process.pre.OntoSpreadPreAdjustment;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentConfig;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentConfigImpl;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentImpl;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.state.UriDepthPair;
import org.ontospread.to.ScoredConceptTO;

import junit.framework.TestCase;

public class OntoSpreadPreAdjustmentTest extends TestCase {

	public void testPreAdjustment() throws ConceptNotFoundException{
		OntoSpreadPreAdjustment pre = new OntoSpreadPreAdjustmentImpl();
		OntoSpreadPreAdjustmentConfig ontoSpreadPreConfig = new OntoSpreadPreAdjustmentConfigImpl(10.0);
		pre.setOntoPreAdjustmentConfig(ontoSpreadPreConfig );
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ScoredConceptTO []initialConcepts = new ScoredConceptTO[10];
		double initialValue = 1.0;
		for(int i = 0; i<initialConcepts.length; i++){
			initialConcepts[i] = new ScoredConceptTO("http://uri",initialValue);
		}
		ontoSpreadState.setInitialConcepts(initialConcepts);
		pre.applyPreAdjustment(ontoSpreadState);
		initialConcepts = ontoSpreadState.getInitialConcepts();
		for(int i = 0; i< initialConcepts.length;i++){
			assertEquals(initialValue, initialConcepts[i].getScore());
		}
		assertEquals(ontoSpreadState.getCurrentScore(),ontoSpreadPreConfig.getInitialScore());
		List <UriDepthPair> sortedList = ontoSpreadState.getSortedList();
		assertEquals(initialConcepts.length, sortedList.size());
		for (UriDepthPair pair : sortedList) {
			assertEquals(1, pair.getDepth());
		}
	}
}
