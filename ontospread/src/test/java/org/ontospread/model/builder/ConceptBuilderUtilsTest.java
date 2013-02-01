package org.ontospread.model.builder;

import org.ontospread.xmlbind.Concept;

import junit.framework.TestCase;

public class ConceptBuilderUtilsTest extends TestCase {

	public void testCreateEmptyConcept(){
		Concept c = ConceptBuilderUtils.createEmptyConcept();
		assertNotNull(c);
		assertTrue(c.getRelations().getRelations().size()==0);
		assertTrue(c.getInstances().getConceptDescriptions().size()==0);
		assertTrue(c.getInstanceof().getConceptDescriptions().size()==0);
	}
}
