package org.ontospread.process.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.state.OntoSpreadCommonUtils;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.to.ConceptTO;
import org.ontospread.to.PathTO;
import org.ontospread.to.SpreadedConceptTO;

public class OntoSpreadPostAdjustmentImpl implements OntoSpreadPostAdjustment{

	protected static Logger logger = Logger.getLogger(OntoSpreadPostAdjustmentImpl.class);
	boolean makePrize = false;
	
	public OntoSpreadPostAdjustmentImpl(){
		
	}
	public OntoSpreadPostAdjustmentImpl(boolean makePrize){
		this.makePrize = makePrize;
	}
	
	public OntoSpreadState applyPostAdjustment(OntoSpreadState ontoSpreadState) throws ConceptNotFoundException {
		logger.debug("Applying post adjustement");
		if(hasMakePrize()){
			makePrize(ontoSpreadState);//Premiamos
		}
		ontoSpreadState.setFinalSpreadedConcepts(getResult(ontoSpreadState));
		return ontoSpreadState;
	}

    public boolean hasMakePrize() {		
		return this.makePrize ;
	}

	public void makePrize(OntoSpreadState ontoSpreadState){        
        int hits = 1;
        HashMap<PathTO, Integer> prizeTable = ontoSpreadState.getConceptsToPrize().getPrizeTable();
        for (PathTO node : prizeTable.keySet()) {
        	 hits = ((Integer)prizeTable.get(node)).intValue();
        	  setConceptPrize(ontoSpreadState,node.getConceptUri(),hits);
       }
    }
    
    protected void setConceptPrize(OntoSpreadState ontoSpreadState, String concept,int hits){
        Double conceptScore;        
        conceptScore = (Double) ontoSpreadState.getConcepts().get(concept);
        if (hits>0) {//The concept belongs to 1 or more paths
        	ontoSpreadState.getConcepts().put(concept,
        			new Double(Math.max(conceptScore.doubleValue()*hits,1.0)));
        }
    }
    private SpreadedConceptTO[] getResult(OntoSpreadState ontoSpreadState) throws ConceptNotFoundException {
    	Map<String, Double> concepts = ontoSpreadState.getConcepts();
		List <SpreadedConceptTO>resultList = new ArrayList<SpreadedConceptTO>(concepts.size());
        Map<String, PathTO[]> spreadPathTable = ontoSpreadState.getSpreadPathTable();
        //This part composes the result, the return value
    	for (String uri : concepts.keySet()) {
    		ConceptTO concept = getConceptTO(uri);
    		  PathTO[] spreadPathTOs = OntoSpreadCommonUtils.getSpreadPath(spreadPathTable,uri);
//              ConceptTO[] spreadPath = new ConceptTO[spreadPathUris.length];
//              for ( int j = 0 ; j < spreadPath.length ; j++ ) {
//                  spreadPath[j] = getConceptTO(spreadPathUris[j].getConceptUri());
//              }
              final SpreadedConceptTO pair = new SpreadedConceptTO(concept, spreadPathTOs,
                      ((Double) concepts.get(uri)).doubleValue());
              resultList.add(pair);
		}
        return (SpreadedConceptTO[]) resultList.toArray(new SpreadedConceptTO[resultList.size()]);
    }

	private ConceptTO getConceptTO(String uri) {
		return new ConceptTO(uri);//FIXME: from ontologyDAO
	}
}
