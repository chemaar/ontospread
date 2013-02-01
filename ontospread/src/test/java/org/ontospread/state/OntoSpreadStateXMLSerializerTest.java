package org.ontospread.state;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.xml.serialize.OutputFormat;
import org.ontospread.exceptions.DocumentBuilderException;
import org.ontospread.exceptions.OntoSpreadModelException;
import org.ontospread.process.OntoSpreadTestUtils;
import org.ontospread.to.ConceptTO;
import org.ontospread.to.PathTO;
import org.ontospread.to.SpreadedConceptTO;
import org.ontospread.utils.DOMToString;
import org.ontospread.utils.DocumentBuilderHelper;
import org.ontospread.xmlbind.OntoSpreadXMLState;
import org.ontospread.xmlbind.utils.OntoSpreadXMLStateXMLBind;
import org.w3c.dom.Document;

import junit.framework.TestCase;

public class OntoSpreadStateXMLSerializerTest extends TestCase {

	public static OntoSpreadState createDemoState(){
		String []conceptUris = new String[]{"http://concept.uri.1","http://concept.uri.2"};
		String []spreaded = new String[]{"http://concept.spread.1","http://concept.spread.2"};
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setInitialConcepts(OntoSpreadTestUtils.createScoredConcepts(conceptUris,1.0));
		List<UriDepthPair> sortedList = new LinkedList<UriDepthPair>();
		for(int i = 0; i<conceptUris.length;i++){
			sortedList.add(new UriDepthPair(conceptUris[i],1));
		}
		ontoSpreadState.setSortedList(sortedList);
		Set <String>spreadedConcepts = new HashSet();
		for(int i = 0; i<spreaded.length;i++){
			spreadedConcepts.add(spreaded[i]);
		}
		ontoSpreadState.setSpreadedConcepts(spreadedConcepts);
		List <SpreadedConceptTO>finalSpreadedConcepts = new LinkedList<SpreadedConceptTO>();
		for(int i = 0; i<spreaded.length;i++){
			finalSpreadedConcepts.add(
					new SpreadedConceptTO(new ConceptTO(spreaded[i]),new PathTO[]{},i));
		}
		ontoSpreadState.setFinalSpreadedConcepts((SpreadedConceptTO[]) 
				finalSpreadedConcepts.toArray(new SpreadedConceptTO[finalSpreadedConcepts.size()]));
		Map <String,PathTO[]>spreadPathTable = new HashMap<String,PathTO[]>();
		for(int i = 0;i<spreaded.length;i++){
			PathTO []value = new PathTO[3];
			for(int j=0; j<value.length;j++){
				value[j]=new PathTO(spreaded[i]+"."+j, new String[0]);
			}
			spreadPathTable.put(spreaded[i], value);
		}
		ontoSpreadState.setSpreadPathTable(spreadPathTable);
		ontoSpreadState.setConceptToSpread(new ConceptTO("http://uri.concept.spread"));
		return ontoSpreadState;
	}

	public void testXMLSerializer(){	
		OntoSpreadXMLState xmlSerializer = OntoSpreadStateXMLSerializer.asXML(createDemoState());	
		assertNotNull(xmlSerializer);
	}

	public void testDocument() throws DocumentBuilderException{
		OntoSpreadXMLState xmlSerializer = OntoSpreadStateXMLSerializer.asXML(createDemoState());
		Document document = OntoSpreadXMLStateXMLBind.getInstance().serializeOntoSpreadXMLState(xmlSerializer);
		assertNotNull(document);
	}
	public Document createDocument(OntoSpreadState ontoSpreadState){
		OntoSpreadXMLState xmlSerializer = OntoSpreadStateXMLSerializer.asXML(createDemoState());
		return OntoSpreadXMLStateXMLBind.getInstance().serializeOntoSpreadXMLState(xmlSerializer);		
	}

	public void testDocumentContent() throws DocumentBuilderException{
		OutputFormat outFormat = new OutputFormat();
		outFormat.setOmitXMLDeclaration(true);		

		OntoSpreadState ontoSpreadState1 = createDemoState();	

		OntoSpreadXMLState xmlSerializer = OntoSpreadStateXMLSerializer.asXML(ontoSpreadState1);
		Document document1 = createDocument(ontoSpreadState1);
		String domString =DOMToString.printXML(document1,outFormat); 
		assertNotNull(domString);		

		OntoSpreadState ontoSpreadState2 = OntoSpreadStateXMLSerializer.asState(xmlSerializer);
		assertNotNull(ontoSpreadState2);
		Document document2 = createDocument(ontoSpreadState2);
		String domString2 =DOMToString.printXML(document2,outFormat);
		assertNotNull(domString2);

		assertEquals(domString, domString2);


		document1 = DocumentBuilderHelper.getDocumentFromString(domString);
		xmlSerializer = OntoSpreadXMLStateXMLBind.getInstance().restoreOntoSpreadXMLState(document1);	
		assertNotNull(OntoSpreadStateXMLSerializer.asState(xmlSerializer));		

		//assertEquals(domString, xmlString);		
	}
}
