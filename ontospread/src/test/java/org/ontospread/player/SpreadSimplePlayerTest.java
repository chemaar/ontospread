package org.ontospread.player;

import junit.framework.TestCase;

import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.process.OntoSpreadTestUtils;
import org.ontospread.state.OntoSpreadState;

public class SpreadSimplePlayerTest extends TestCase {

	public void testSimplePlayerNull() throws ConceptNotFoundException{
		String []conceptUris = new String[]{
				"http://websemantica.fundacionctic.org/ontologias/bopa/piscina.owl#Piscina"};
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setInitialConcepts(OntoSpreadTestUtils.createScoredConcepts(conceptUris,1.0));
		SpreadPlayer player = new SpreadSimplePlayer(OntoSpreadTestUtils.
				createDefaultOntoSpreadProcess(3, 3, 10.0),
				ontoSpreadState);	
		try{
			player.first();
			player.last();		
			player.previous();
			fail();
		}catch(Exception e){
			
		}
	}
	
	public void testSimplePlayerApplyPre() throws ConceptNotFoundException{
		String []conceptUris = new String[]{
				"http://websemantica.fundacionctic.org/ontologias/bopa/piscina.owl#Piscina"};
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setInitialConcepts(OntoSpreadTestUtils.createScoredConcepts(conceptUris,1.0));
		SpreadPlayer player = new SpreadSimplePlayer(OntoSpreadTestUtils.
				createDefaultOntoSpreadProcess(3, 3, 10.0),
				ontoSpreadState);
		try{
			player.first();
			fail();
		}catch(Exception e){
			
		}
	}
	public void testSimplePlayerApplyPost() throws ConceptNotFoundException{
		String []conceptUris = new String[]{
				"http://websemantica.fundacionctic.org/ontologias/bopa/piscina.owl#Piscina"};
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setInitialConcepts(OntoSpreadTestUtils.createScoredConcepts(conceptUris,1.0));
		SpreadPlayer player = new SpreadSimplePlayer(OntoSpreadTestUtils.
				createDefaultOntoSpreadProcess(3, 3, 10.0),
				ontoSpreadState);
		try{
			player.last();
			fail();
		}catch(Exception e){
			
		}
	}
	public void testSimplePlayerNext() throws ConceptNotFoundException{
		String []conceptUris = new String[]{
				"http://websemantica.fundacionctic.org/ontologias/bopa/piscina.owl#Piscina"};
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setInitialConcepts(OntoSpreadTestUtils.createScoredConcepts(conceptUris,10.0));
		SpreadPlayer player = new SpreadSimplePlayer(OntoSpreadTestUtils.
				createDefaultOntoSpreadProcess(3, 3, 10.0),
				ontoSpreadState);		
		assertTrue(player.hasNext());
		OntoSpreadState ontoSpreadNext = player.next();
		assertTrue(player.hasNext());
		try{
			player.previous();
			fail();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
