package org.ontospread.process.pre;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.state.OntoSpreadCommonUtils;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.state.UriDepthPair;
import org.ontospread.to.PathTO;
import org.ontospread.to.ScoredConceptTO;

public class OntoSpreadPreAdjustmentImpl implements OntoSpreadPreAdjustment{
	private static final int INIT_DEPTH = 1;
	protected static Logger logger = Logger.getLogger(OntoSpreadPreAdjustmentImpl.class);
	private OntoSpreadPreAdjustmentConfig ontoSpreadPreAdjustmentConfig;
	
	public OntoSpreadState applyPreAdjustment(OntoSpreadState ontoSpreadState) throws ConceptNotFoundException {
		logger.debug("Applying pre adjustement");
		double initialScore = setupInitialConcepts(ontoSpreadState);
		ontoSpreadState.setCurrentScore(initialScore);
		return ontoSpreadState;	
	}

    private double setupInitialConcepts(OntoSpreadState ontoSpreadState) throws ConceptNotFoundException {
        double initialScore = getOntoPreAdjustmentConfig().getInitialScore();
        ScoredConceptTO[] initialConcepts = ontoSpreadState.getInitialConcepts(); 
    	List<UriDepthPair> sortedList = new LinkedList<UriDepthPair>();
        for ( int i=0; i < initialConcepts.length ; i++ ) {
            // the initial concept is activated by itself
            String initialConcept = initialConcepts[i].getConceptUri();
            OntoSpreadCommonUtils.registerSpreadPath(ontoSpreadState,initialConcept, null, new PathTO[0]);                
            //Initialitate the list with the uri concept that will spreaded
            OntoSpreadCommonUtils.addToConceptStack(ontoSpreadState,initialConcept, INIT_DEPTH);         
            OntoSpreadCommonUtils.addScore(ontoSpreadState,initialConcepts[i].getScore(), initialConcept);
            initialScore = Math.max(initialScore, initialConcepts[i].getScore());
    		sortedList.add(new UriDepthPair(initialConcepts[i].getConceptUri(),INIT_DEPTH));
           }        
        ontoSpreadState.setSortedList(sortedList);
        return initialScore;
    }
    

	public OntoSpreadPreAdjustmentConfig getOntoPreAdjustmentConfig() {
		return ontoSpreadPreAdjustmentConfig;
	}

	public void setOntoPreAdjustmentConfig(OntoSpreadPreAdjustmentConfig ontoSpreadPreConfig) {
		this.ontoSpreadPreAdjustmentConfig = ontoSpreadPreConfig;
	}
    


}
