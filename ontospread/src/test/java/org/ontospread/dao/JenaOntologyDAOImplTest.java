package org.ontospread.dao;

import junit.framework.TestCase;

import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.to.ConceptTO;
import org.ontospread.to.RelationTO;
import org.ontospread.utils.DOMToString;
import org.ontospread.xmlbind.Concept;
import org.ontospread.xmlbind.utils.ConceptXMLBind;

public class JenaOntologyDAOImplTest extends TestCase {

	public void testConceptFound() throws ConceptNotFoundException{
		String conceptUri = "http://websemantica.fundacionctic.org/ontologias/bopa/piscina.owl#Piscina";
		Concept c = DAOUtils.createOntologyDAO().getConcept(conceptUri, "");
		assertNotNull(c);
	}

	public void testConceptNotFound() {
		String conceptUri = "http://not.exists";
		try {
			assertNotNull(DAOUtils.createOntologyDAO().getConcept(conceptUri, ""));
			fail("Exception has not been thrown, expecting ConceptNotFoundException");
		} catch (ConceptNotFoundException e) {
			//
		}
	}

	public void testConceptNull() {
		String conceptUri = null;
		try {
			assertNotNull(DAOUtils.createOntologyDAO().getConcept(conceptUri, ""));
			fail("Exception not thrown, expecting ConceptNotFoundException");
		} catch (ConceptNotFoundException e) {
			//Test, ok
		}
	}
	/*
	public void testConceptTagFilter() throws ConceptNotFoundException{
		String conceptUri = "http://www.victordiazm.com/PFC0708#playa";
		Concept c = DAOUtils.createOntologyDAOTagFilter().getConcept(conceptUri, "");
		System.out.println(DOMToString.printXML(ConceptXMLBind.getInstance().serializeConcept(c)));
		assertNotNull(c);
	}
	*/

	public void testGetConcepTO() throws ConceptNotFoundException {
		String conceptUri = "http://websemantica.fundacionctic.org/ontologias/bopa/piscina.owl#Piscina";
		assertNotNull(DAOUtils.createOntologyDAO().getConceptTO(conceptUri));			
	}

	public void testNotGetConcepTO() {
		String conceptUri = null;
		try {
			assertNotNull(DAOUtils.createOntologyDAO().getConceptTO(conceptUri));
			fail("Exception not thrown, expecting ConceptNotFoundException");
		} catch (ConceptNotFoundException e) {
			//Test, ok
		}			
	}
	public void testGetConceptNull() {
		String conceptUri = null;
		try {
			assertNotNull(DAOUtils.createOntologyDAO().getConceptTO(conceptUri));
		} catch (ConceptNotFoundException e) {
			//Test, ok
		}			
	}

	public void testDescribeAllConcepts() throws ConceptNotFoundException{
		OntologyDAO ontoDAO = DAOUtils.createOntologyDAO();
		ConceptTO [] allConcepts = ontoDAO.getAllConcepts();
		assertEquals(2779, allConcepts.length);
		for(int i = 0; i<allConcepts.length; i++){
			assertNotNull(allConcepts[i].getUri());
			assertNotNull(ontoDAO.getConcept(allConcepts[i].getUri(),null));
		}
	}

	public void testGetRelationTO() throws ConceptNotFoundException {
		String relationUri = "http://websemantica.fundacionctic.org/ontologias/bopa#esCompetenciaDe";
		assertNotNull(DAOUtils.createOntologyDAO().getRelationTO(relationUri));			
	}

	public void testNotGetRelationTO() {
		String relationUri = null;
		try {
			assertNotNull(DAOUtils.createOntologyDAO().getRelationTO(relationUri));
			fail("Exception not thrown, expecting ConceptNotFoundException");
		} catch (ConceptNotFoundException e) {
			//Test, ok
		}			
	}
	public void testGetRelationNull() {
		String relationUri = null;
		try {
			assertNotNull(DAOUtils.createOntologyDAO().getRelationTO(relationUri));
		} catch (ConceptNotFoundException e) {
			//Test, ok
		}			
	}

	public void testDescribeAllRelations() throws ConceptNotFoundException{
		OntologyDAO ontoDAO = DAOUtils.createOntologyDAO();
		RelationTO [] allRelations = ontoDAO.getAllRelations();
		assertEquals(42, allRelations.length);
		for(int i = 0; i<allRelations.length; i++){		
			assertNotNull(allRelations[i].getUri());		
		}
	}




}
