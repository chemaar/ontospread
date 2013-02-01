package org.ontospread.constraints;

import junit.framework.TestCase;

import org.ontospread.model.loader.JenaOWLModelWrapper;
import org.ontospread.model.resources.FilesResourceLoader;
import org.ontospread.model.resources.ResourceLoader;
import org.ontospread.xmlbind.TypeHierarchy;

public class OntoSpreadRelationWeightRDFImplTest extends TestCase {

	
	protected OntoSpreadRelationWeight createRelationWeight(String []filenames){
		ResourceLoader resource = new FilesResourceLoader(filenames );
		JenaOWLModelWrapper model = new JenaOWLModelWrapper(resource);
		return new OntoSpreadRelationWeightRDFImpl(model);
	}
	
	public void testGetDefaultWeight(){
		String[] filenames = new String[]{"relation-weights-test.rdf"};
		OntoSpreadRelationWeight relationWeight =createRelationWeight(filenames);
		assertEquals(OntoSpreadRelationWeight.DEFAULT_VALUE, relationWeight.getWeight(""));	
	}
	public void testGetWeight(){
		Double expectedValue = 1.0;
		String[] filenames = new String[]{"relation-weights-test.rdf"};
		OntoSpreadRelationWeight relationWeight =createRelationWeight(filenames);
		assertEquals(expectedValue, relationWeight.getWeight("http://ontospread.sf.net#testRelation"));	
	}

	public void testGetDefaultValues(){		
		Double []expectedValues = {0.5,0.2,0.0,0.0};
		String []uris = {
				OntoSpreadRelationWeight.DEFAULT_NAMESPACE+TypeHierarchy.SUPERCLASS.toString().toUpperCase(),
				OntoSpreadRelationWeight.DEFAULT_NAMESPACE+TypeHierarchy.SUBCLASS.toString().toUpperCase(),
				OntoSpreadRelationWeight.DEFAULT_NAMESPACE+TypeHierarchy.INSTANCE.toString().toUpperCase(),
				OntoSpreadRelationWeight.DEFAULT_NAMESPACE+TypeHierarchy.INSTANCE_OF.toString().toUpperCase(),
				};
		String[] filenames = new String[]{"relation-weights-test.rdf"};
		OntoSpreadRelationWeight relationWeight =createRelationWeight(filenames);
		for(int i = 0; i<expectedValues.length;i++)	{
			assertEquals(expectedValues[i], relationWeight.getWeight(uris[i]));
		}
	}

}
