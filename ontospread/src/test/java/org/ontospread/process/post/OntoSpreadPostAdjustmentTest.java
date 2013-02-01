package org.ontospread.process.post;

import java.util.Map;

import junit.framework.TestCase;

import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.to.PathTO;
import org.ontospread.to.SpreadedConceptTO;

public class OntoSpreadPostAdjustmentTest extends TestCase {

	public void testPostAdjustment() throws ConceptNotFoundException{
		OntoSpreadPostAdjustment post = new OntoSpreadPostAdjustmentImpl();
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		Map<String, Double> concepts = ontoSpreadState.getConcepts();		
	    Map<String, PathTO[]> spreadPathTable = ontoSpreadState.getSpreadPathTable();
	    for(int i = 0; i<10;i++){
	    	concepts.put(String.valueOf(i), 1.0);
	    	spreadPathTable.put(String.valueOf(i), new PathTO[0]);
	    }
		post.applyPostAdjustment(ontoSpreadState);
		SpreadedConceptTO[] finalSpreadedConcepts = ontoSpreadState.getFinalSpreadedConcepts();
		assertEquals(10, finalSpreadedConcepts.length);
		for(int i = 0; i<finalSpreadedConcepts.length;i++){
			assertEquals(1.0, finalSpreadedConcepts[i].getScore());
			assertEquals(0, finalSpreadedConcepts[i].getSpreadPath().length);
		}
	}
}
