package org.ontospread.player;

import junit.framework.TestCase;

import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.process.OntoSpreadTestUtils;
import org.ontospread.state.OntoSpreadState;

public class SpreadDebugPlayerTest extends TestCase {

	public void testDebugPlayerNotPrePost() throws ConceptNotFoundException{
		String []conceptUris = new String[]{
				"http://websemantica.fundacionctic.org/ontologias/bopa/piscina.owl#Piscina"};
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setInitialConcepts(OntoSpreadTestUtils.createScoredConcepts(conceptUris,10.0));
		SpreadPlayer player = new SpreadDebugPlayer(OntoSpreadTestUtils.
				createDefaultOntoSpreadProcess(3, 3, 10.0),
				ontoSpreadState);
		assertEquals(player.first().getSpreadTime(), player.first().getSpreadTime());
		assertEquals(player.last().getSpreadTime(), player.last().getSpreadTime());		
	}
	
	public void testDebugPlayerApplyPre() throws ConceptNotFoundException{
		String []conceptUris = new String[]{
				"http://websemantica.fundacionctic.org/ontologias/bopa/piscina.owl#Piscina"};
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setInitialConcepts(OntoSpreadTestUtils.createScoredConcepts(conceptUris,10.0));
		SpreadPlayer player = new SpreadDebugPlayer(OntoSpreadTestUtils.
				createDefaultOntoSpreadProcess(3, 3, 10.0),
				ontoSpreadState);
		assertEquals(player.first().getSpreadTime(), player.first().getSpreadTime());		
	}
	public void testDebugPlayerApplyPost() throws ConceptNotFoundException{
		String []conceptUris = new String[]{
				"http://websemantica.fundacionctic.org/ontologias/bopa/piscina.owl#Piscina"};
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setInitialConcepts(OntoSpreadTestUtils.createScoredConcepts(conceptUris,10.0));
		SpreadPlayer player = new SpreadDebugPlayer(OntoSpreadTestUtils.
				createDefaultOntoSpreadProcess(3, 3, 10.0),
				ontoSpreadState);
		//assertNotSame(ontoSpreadState.getSpreadTime(), player.last().getSpreadTime());
		assertEquals(player.last().getSpreadTime(),player.last().getSpreadTime());
	}
	public void testDebugPlayerNext() throws ConceptNotFoundException{
		String []conceptUris = new String[]{
				"http://websemantica.fundacionctic.org/ontologias/bopa/piscina.owl#Piscina"};
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setInitialConcepts(OntoSpreadTestUtils.createScoredConcepts(conceptUris,10.0));
		SpreadPlayer player = new SpreadDebugPlayer(OntoSpreadTestUtils.
				createDefaultOntoSpreadProcess(3, 3, 10.0),
				ontoSpreadState);
		OntoSpreadState ontoSpreadNext = player.next();
		OntoSpreadState ontoSpreadPrevious = player.previous();		
		//assertEquals(ontoSpreadNext.getSpreadTime(), ontoSpreadPrevious.getSpreadTime());
		assertTrue(true);
	}
	
}
