package org.ontospread.state;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ontospread.to.ConceptTO;
import org.ontospread.to.PathTO;
import org.ontospread.to.ScoredConceptTO;
import org.ontospread.to.SpreadedConceptTO;
import org.ontospread.xmlbind.EntryConceptsType;
import org.ontospread.xmlbind.EntryPathTableType;
import org.ontospread.xmlbind.EntryPrizeTableTOType;
import org.ontospread.xmlbind.ObjectFactory;
import org.ontospread.xmlbind.OntoSpreadXMLState;
import org.ontospread.xmlbind.PathTOType;
import org.ontospread.xmlbind.PathTOTypeList;
import org.ontospread.xmlbind.ScoredConceptTOType;
import org.ontospread.xmlbind.SpreadedConceptTOType;
import org.ontospread.xmlbind.UriDepthPairType;

public class OntoSpreadStateXMLSerializer {
	
	public static OntoSpreadXMLState asXML(OntoSpreadState ontoSpreadState){
		ObjectFactory factory = new ObjectFactory();
		OntoSpreadXMLState ontoSpreadXMLState = factory.createOntoSpreadXMLState();
		ontoSpreadXMLState.setScoredConcepts(factory.createScoredConceptTOListType());
		List<ScoredConceptTOType> scoredConceptTOs = ontoSpreadXMLState.getScoredConcepts().getScoredConceptTOs();		
		for (ScoredConceptTO scoredConceptTO : ontoSpreadState.getInitialConcepts()) {
			ScoredConceptTOType scoredXML = new ScoredConceptTOType();
			scoredXML.setConceptUri(scoredConceptTO.getConceptUri());
			scoredXML.setScore(scoredConceptTO.getScore());
			scoredConceptTOs.add(scoredXML);
		}
		ontoSpreadXMLState.setSpreadTime(ontoSpreadState.getSpreadTime());
		
		ontoSpreadXMLState.setSpreadedConcepts(factory.createSpreadConceptListType());
		List<String> spreadedConcepts = ontoSpreadXMLState.getSpreadedConcepts().getSpreadedConcepts();
		for (String spreadedUri : ontoSpreadState.getSpreadedConcepts()) {			
			spreadedConcepts.add(spreadedUri);
		}
		
		ontoSpreadXMLState.setSpreadPathTable(factory.createEntryPathTableListType());
		Map<String, PathTO[]> spreadPathTable = ontoSpreadState.getSpreadPathTable();
		for (String key : spreadPathTable.keySet()) {
			PathTO[] pathTOs = spreadPathTable.get(key);
			EntryPathTableType entry = new EntryPathTableType();
			entry.setUri(key);
			for (PathTO pathTO : pathTOs) {
				PathTOType pathToAdd = new PathTOType();
				pathToAdd.setUri(pathTO.getConceptUri());
				pathToAdd.setRelations(factory.createListUrisType());
				if(pathTO.getRelationsUri()!=null){
					pathToAdd.getRelations().getRelations().addAll(Arrays.asList(pathTO.getRelationsUri()));
				}
				entry.setPathTOs(factory.createPathTOTypeList());
				entry.getPathTOs().getPathTOs().add(pathToAdd );				
			}
			ontoSpreadXMLState.getSpreadPathTable().getSpreadTableTOs().add(entry );
		}
		
		ontoSpreadXMLState.setFinalSpreadedConcepts(factory.createSpreadedConceptTOListType());
		for (SpreadedConceptTO spreadedConcept : ontoSpreadState.getFinalSpreadedConcepts()) {
			SpreadedConceptTOType spreadedXML = new SpreadedConceptTOType();
			spreadedXML.setConceptUri(spreadedConcept.getConcept().getUri());
			PathTO[] pathTOs = spreadedConcept.getSpreadPath();
			for (PathTO pathTO : pathTOs) {
				PathTOType pathToAdd = new PathTOType();
				pathToAdd.setUri(pathTO.getConceptUri());
				pathToAdd.setRelations(factory.createListUrisType());
				if(pathTO.getRelationsUri() != null){
					pathToAdd.getRelations().getRelations().addAll(Arrays.asList(pathTO.getRelationsUri()));
				}
				spreadedXML.setPathTOs(factory.createPathTOTypeList());
				spreadedXML.getPathTOs().getPathTOs().add(pathToAdd);
			}
			spreadedXML.setScore(spreadedConcept.getScore());
			ontoSpreadXMLState.getFinalSpreadedConcepts().getSpreadedConceptTOs().add(spreadedXML);
		}
		
		ontoSpreadXMLState.setUriDepthPair(factory.createUriDepthListType());
		List<UriDepthPairType> uriDepthPairs = ontoSpreadXMLState.getUriDepthPair().getUriDepthPairs();
		for (UriDepthPair uriPair : ontoSpreadState.getSortedList()) {
			UriDepthPairType uriXMLPair = new UriDepthPairType();
			uriXMLPair.setConceptUri(uriPair.getUri());
			uriXMLPair.setDepth(uriPair.getDepth());			
			uriDepthPairs.add(uriXMLPair );
		}
		ontoSpreadXMLState.setConcepts(factory.createEntryConceptsListType());
		List<EntryConceptsType> entryConceptTOs = ontoSpreadXMLState.getConcepts().getEntryConceptTOs();
		for (String key : ontoSpreadState.getConcepts().keySet()) {			
			Double value = ontoSpreadState.getConcepts().get(key);
			EntryConceptsType entryXML = new EntryConceptsType();
			entryXML.setUri(key);
			entryXML.setValue(value);
			entryConceptTOs.add(entryXML );			
		}
		HashMap<PathTO,Integer> prizeTable = ontoSpreadState.getConceptsToPrize().getPrizeTable();
		ontoSpreadXMLState.setPrizeTable(factory.createEntryPrizeTableListType());
		for (PathTO pathTO : prizeTable.keySet()) {
			EntryPrizeTableTOType entry = new EntryPrizeTableTOType();
			PathTOType pathToAdd = new PathTOType();
			pathToAdd.setUri(pathTO.getConceptUri());
			pathToAdd.setRelations(factory.createListUrisType());
			if(pathTO.getRelationsUri() != null){
				pathToAdd.getRelations().getRelations().addAll(Arrays.asList((pathTO.getRelationsUri())));
			}
			entry.setHits(prizeTable.get(pathTO));
			entry.setPathTO(pathToAdd);
			ontoSpreadXMLState.getPrizeTable().getEntryPrizeTableTOs().add(entry);
		}
		
		ConceptTO conceptToSpread = ontoSpreadState.getConceptToSpread();
		ontoSpreadXMLState.setConceptTOSpread(conceptToSpread.getUri());
		ontoSpreadXMLState.setHasIteration(ontoSpreadState.isHasIteration());
		ontoSpreadXMLState.setCurrentScore(ontoSpreadState.getCurrentScore());
		return ontoSpreadXMLState;
	}
	
	public static OntoSpreadState asState(OntoSpreadXMLState ontoSpreadXMLState){
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ScoredConceptTO []initialConcepts = new ScoredConceptTO[ontoSpreadXMLState.getScoredConcepts().getScoredConceptTOs().size()];
		int k = 0;
		for (ScoredConceptTOType scoredXML : ontoSpreadXMLState.getScoredConcepts().getScoredConceptTOs()) {
			ScoredConceptTO scoredConceptTO = new ScoredConceptTO();
			scoredConceptTO.setConceptUri(scoredXML.getConceptUri());
			scoredConceptTO.setScore(scoredXML.getScore());	
			initialConcepts[k++] = scoredConceptTO;	
		}
		ontoSpreadState.setInitialConcepts(initialConcepts);
		ontoSpreadState.setSpreadTime(ontoSpreadXMLState.getSpreadTime());
		
		Set<String> spreadedConcepts = new HashSet<String>();
		for (String uri : ontoSpreadXMLState.getSpreadedConcepts().getSpreadedConcepts()) {
			spreadedConcepts.add(uri);
		}
		ontoSpreadState.setSpreadedConcepts(spreadedConcepts );
		
		
		
		Map<String, PathTO[]> spreadPathTable = new HashMap<String, PathTO[]>();
		for (EntryPathTableType entry : ontoSpreadXMLState.getSpreadPathTable().getSpreadTableTOs()) {
			String key = entry.getUri();
			PathTOTypeList pathTOs = entry.getPathTOs();
			PathTO []path = new PathTO[pathTOs==null?0:pathTOs.getPathTOs().size()];
			if(pathTOs != null){
				int i = 0;
				for (PathTOType pathTo : pathTOs.getPathTOs()) {
					path[i++] = new PathTO(pathTo.getUri(),pathTo.getRelations().getRelations().toArray(new String[pathTo.getRelations().getRelations().size()]));
				}	
			}			
			spreadPathTable.put(key, path);			
		}
		ontoSpreadState.setSpreadPathTable(spreadPathTable);
		
		SpreadedConceptTO[] result = new SpreadedConceptTO[ontoSpreadXMLState.getFinalSpreadedConcepts().getSpreadedConceptTOs().size()];
		int i = 0;
		for (SpreadedConceptTOType spreadedXML : ontoSpreadXMLState.getFinalSpreadedConcepts().getSpreadedConceptTOs()){
			PathTOTypeList pathTOs = spreadedXML.getPathTOs();
			PathTO []path = new PathTO[pathTOs==null?0:pathTOs.getPathTOs().size()];
			if(pathTOs != null){
				int j = 0;
				for (PathTOType pathTo : pathTOs.getPathTOs()) {
					path[j++] = new PathTO(pathTo.getUri(),pathTo.getRelations().getRelations().toArray(new String[pathTo.getRelations().getRelations().size()]));
				}	
			}			
			double score = spreadedXML.getScore();
			result[i++] = new SpreadedConceptTO(new ConceptTO(spreadedXML.getConceptUri()),path,score );
			
		}
		ontoSpreadState.setFinalSpreadedConcepts(result);
		
		List<UriDepthPair> sortedList = new LinkedList<UriDepthPair>();
		for (UriDepthPairType uriXMLPair : ontoSpreadXMLState.getUriDepthPair().getUriDepthPairs()) {
			sortedList.add(new UriDepthPair(uriXMLPair.getConceptUri(),uriXMLPair.getDepth()));
		}
		ontoSpreadState.setSortedList(sortedList);

		Map<String, Double> concepts = new HashMap<String, Double>();
		for (EntryConceptsType entryXML : ontoSpreadXMLState.getConcepts().getEntryConceptTOs()) {
			concepts.put(entryXML.getUri(), entryXML.getValue());
		}
		ontoSpreadState.setConcepts(concepts );
		
		HashMap<PathTO, Integer> prizeTable = ontoSpreadState.getConceptsToPrize().getPrizeTable();		
		for (EntryPrizeTableTOType entry : ontoSpreadXMLState.getPrizeTable().getEntryPrizeTableTOs()) {
			int value = entry.getHits();
			PathTO pathTO = new PathTO(entry.getPathTO().getUri(),entry.getPathTO().getRelations().getRelations().toArray(new String[entry.getPathTO().getRelations().getRelations().size()]));
			prizeTable.put(pathTO , value);
		}
		ontoSpreadState.setConceptToSpread(new ConceptTO(ontoSpreadXMLState.getConceptTOSpread()));
		ontoSpreadState.setHasIteration( ontoSpreadXMLState.isHasIteration());
		ontoSpreadState.setCurrentScore(ontoSpreadXMLState.getCurrentScore());
		
		return ontoSpreadState;
	}
	
//	public static Document asDocument(OntoSpreadState ontoSpreadState) throws DocumentBuilderException{
//		String xmlString = asXML(ontoSpreadState);
//		return DocumentBuilderHelper.getDocumentFromString(xmlString);
//	}
}
