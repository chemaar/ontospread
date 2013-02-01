package org.ontospread.state;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ontospread.to.PathTO;

//FIXME: Extract to strategies
public class OntoSpreadCommonUtils {

	protected static Logger logger = Logger.getLogger(OntoSpreadCommonUtils.class);
	
    public static void registerSpreadPath(OntoSpreadState ontoSpreadState,String concept, String relation, PathTO[] spreadPath) {
        if (!ontoSpreadState.getSpreadPathTable().containsKey(concept)) {
            PathTO[] newSpreadPath = new PathTO[spreadPath.length + 1];
            System.arraycopy(spreadPath, 0, newSpreadPath, 0, spreadPath.length);
            List<String> relations = new LinkedList<String>();
            if(relation != null) {
            	relations.add(relation);            
            }
            newSpreadPath[newSpreadPath.length - 1] = new PathTO(concept,
            		relations.toArray(new String[relations.size()]));
            ontoSpreadState.getSpreadPathTable().put(concept, newSpreadPath);            
        }
    }
    

    public  static double degradationFunction(int depth) {
        if (depth == 0) {
            throw new RuntimeException("Depth = 0");
        }
        //FIXME: only for debug
        //return 1.0;
        //return prof * DEGRADATION_CONSTANT;
        return 1.0 / ((double)depth * 1.0);
    }
    
    public static PathTO[] getSpreadPath(Map<String, PathTO[]> spreadPathTable, String uri) {
	    PathTO[] spreadPath = spreadPathTable.get(uri);
	    return (spreadPath == null)?new PathTO[0]:spreadPath;        
	}


	public static void addToConceptStack(OntoSpreadState ontoSpreadState,String uri, int depth) {
       ontoSpreadState.getSortedList().add(new UriDepthPair(uri, depth));
    }
    
    public static void markAsVisited(OntoSpreadState ontoSpreadState,String uri) {
        ontoSpreadState.getSpreadedConcepts().add(uri);
    }
    
    public static void addScore(OntoSpreadState ontoSpreadState,double scoreIncrement, String uri) {
        Double score =  ontoSpreadState.getConcepts().get(uri);        
        if (score == null) {
            logger.debug("Initializing score of concept " + uri + " to value " + scoreIncrement);
            score = new Double(scoreIncrement);
        } else {
            logger.debug("Updating score of concept " + uri + " from value " + score.doubleValue() + " to " + (scoreIncrement + score.doubleValue()));
            score = new Double(scoreIncrement + score.doubleValue());
        }
        ontoSpreadState.getConcepts().put(uri, score);
    }
}
