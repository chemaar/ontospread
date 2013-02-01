package org.ontospread.state;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ontospread.to.ConceptTO;
import org.ontospread.to.PathTO;
import org.ontospread.to.ScoredConceptTO;
import org.ontospread.to.SpreadedConceptTO;

public class OntoSpreadState implements Serializable {

	protected static Logger logger = Logger.getLogger(OntoSpreadState.class);
	
	//Initial Concepts
	private ScoredConceptTO[] initialConcepts = new ScoredConceptTO[0];
	
    //While running
	private long spreadTime = 0;
    private Set<String> spreadedConcepts = new HashSet<String>();    
    private Map<String,PathTO[]> spreadPathTable = new HashMap<String,PathTO[]>();
    
    //Final Concepts
	private SpreadedConceptTO[] finalSpreadedConcepts = new SpreadedConceptTO[0];
       
    private Map swapArea = new HashMap();//Discard to serialize
    private List<UriDepthPair> sortedList = new LinkedList<UriDepthPair>();
    //FIXME: Probably not state, to be used in strategy
    private Map<String,Double>concepts = new HashMap<String,Double>();    
    private PrizePath conceptsToPrize = new PrizePath();//Discard to serialize
    
    //To cache values
    private ConceptTO conceptToSpread = new ConceptTO();
    private boolean hasIteration = false;
    private double currentScore = 0.0;


    
	public Map<String, Double> getConcepts() {
		return concepts;
	}
	public void setConcepts(Map<String, Double> concepts) {
		this.concepts = concepts;
	}

	public PrizePath getConceptsToPrize() {
		return conceptsToPrize;
	}
	public void setConceptsToPrize(PrizePath conceptsToPrize) {
		this.conceptsToPrize = conceptsToPrize;
	}
	public List <UriDepthPair>getSortedList() {
		return sortedList;
	}
	public void setSortedList(List <UriDepthPair>sortedList) {
		this.sortedList = sortedList;
	}
	public Set<String> getSpreadedConcepts() {
		return spreadedConcepts;
	}
	public void setSpreadedConcepts(Set<String> spreadedConcepts) {
		this.spreadedConcepts = spreadedConcepts;
	}
	public Map<String, PathTO[]> getSpreadPathTable() {
		return spreadPathTable;
	}
	public void setSpreadPathTable(Map<String, PathTO[]> spreadPathTable) {
		this.spreadPathTable = spreadPathTable;
	}
	public ConceptTO getConceptToSpread() {
		return conceptToSpread;
	}
	public void setConceptToSpread(ConceptTO conceptToSpread) {
		this.conceptToSpread = conceptToSpread;
	}
	public boolean isHasIteration() {
		return hasIteration;
	}
	public void setHasIteration(boolean hasIteration) {
		this.hasIteration = hasIteration;
	}
	public long getSpreadTime() {
		return spreadTime;
	}
	public void setSpreadTime(long spreadTime) {
		this.spreadTime = spreadTime;
	}
	public Map getSwapArea() {
		return swapArea;
	}
	public void setSwapArea(Map swapArea) {
		this.swapArea = swapArea;
	}
	public double getCurrentScore() {
		return currentScore;
	}
	public void setCurrentScore(double currentScore) {
		this.currentScore = currentScore;
	}
	public ScoredConceptTO[] getInitialConcepts() {
		return initialConcepts;
	}
	public void setInitialConcepts(ScoredConceptTO[] initialConcepts) {
		this.initialConcepts = initialConcepts;
	}
	public void setFinalSpreadedConcepts(SpreadedConceptTO[] result) {
		this.finalSpreadedConcepts = result;
	}
	public SpreadedConceptTO[] getFinalSpreadedConcepts() {
		return finalSpreadedConcepts;
	}
  

}
