package org.ontospread.process.utils;

import junit.framework.TestCase;

import org.ontospread.dao.DAOUtils;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.xmlbind.Concept;

public class ConceptOperationsTest extends TestCase {

	public void testConceptOperations() throws ConceptNotFoundException{
		String conceptUri = "http://websemantica.fundacionctic.org/ontologias/bopa/piscina.owl#Piscina";
		Concept c = DAOUtils.createOntologyDAO().getConcept(conceptUri, "");
		ConceptOperations cop = new ConceptOperations(c);
		assertEquals(18, cop.getRelatedConcepts().size());
		assertEquals(1, cop.getSubClasses().size());
		assertEquals(3, cop.getSuperClasses().size());
	}
}
