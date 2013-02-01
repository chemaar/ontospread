package org.ontospread.xmlbind.utils;

import org.ontospread.xmlbind.Concept;
import org.ontospread.xmlbind.ObjectFactory;

import junit.framework.TestCase;

public class ConceptXMLBindTest extends TestCase {

	public void testRestoreConcept(){
		ObjectFactory factory = new ObjectFactory();
		Concept concept  = factory.createConcept();
		assertNotNull(concept);
		assertNotNull(ConceptXMLBind.getInstance().serializeConcept(concept));
	}
}
