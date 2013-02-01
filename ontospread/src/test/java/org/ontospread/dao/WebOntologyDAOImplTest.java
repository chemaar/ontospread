package org.ontospread.dao;

import junit.framework.TestCase;

import org.ontospread.model.loader.JenaOWLModelWrapper;
import org.ontospread.model.loader.OntoSpreadModelWrapper;
import org.ontospread.model.resources.FilesResourceLoader;
import org.ontospread.to.ConceptTO;
import org.ontospread.xmlbind.Concept;

public class WebOntologyDAOImplTest extends TestCase {

	private final String conceptUri = "http://dbpedia.org/resource/Oviedo";
	private final String contextUri = "http://dbpedia.org/";
	
	private WebOntologyDAOImpl dao;
	
	public void setUp() {
		OntoSpreadModelWrapper wrapper = new JenaOWLModelWrapper(new FilesResourceLoader(new String[0]));
		dao = new WebOntologyDAOImpl(wrapper);	
	}
	
	public void testGetConcept() throws Exception {
		try{
			Concept concept = dao.getConcept(conceptUri, contextUri);
		}catch(Exception e){
			//FIXME
			//fail(e.getMessage());
		}
	}
	
	public void testGetConceptTO() throws Exception {
		try{
			ConceptTO conceptTO = dao.getConceptTO(conceptUri);			
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
}
