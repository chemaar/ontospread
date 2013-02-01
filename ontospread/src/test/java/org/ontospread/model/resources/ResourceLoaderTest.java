package org.ontospread.model.resources;

import org.ontospread.to.KnowledgeResourceTO;

import junit.framework.TestCase;

public class ResourceLoaderTest extends TestCase {

	public void testLoadFromClassPath(){
		String[] filenames = new String[]{"relation-weights.rdf"};
		ResourceLoader resource = new FilesResourceLoader(filenames);
		assertLoad(filenames, resource);
	}
	public void testLoadFromExternal(){
		String[] filenames = new String[]{"src/main/resources/relation-weights.rdf"};
		ResourceLoader resource = new ExternalizeFilesResourceLoader(filenames);
		assertLoad(filenames, resource);
		
	}
	public void testLoadFromURL(){
		String[] filenames = new String[]{"http://www.wikier.org/foaf.rdf"};
		ResourceLoader resource = new URLFilesResourceLoader(filenames);
		assertLoad(filenames, resource);
	}
	
	protected void assertLoad(String []filenames, ResourceLoader resource){
		KnowledgeResourceTO[] knowledgeResources = resource.getKnowledgeResources();
		assertEquals(1, knowledgeResources.length);
		for(int i = 0; i<knowledgeResources.length;i++){
			assertNotNull(knowledgeResources[i].getKnowledgeSourceData());
			assertEquals(knowledgeResources[i].getKnowledgeSourcePk().getId(),filenames[i]);
		}
	}
}
