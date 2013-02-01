package org.ontospread.process.run;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ontospread.constraints.OntoSpreadRelationWeight;
import org.ontospread.constraints.OntoSpreadRelationWeightImpl;
import org.ontospread.constraints.OntoSpreadStackConstraint;
import org.ontospread.dao.OntologyDAO;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.process.utils.ConceptOperations;
import org.ontospread.process.utils.ConceptRelationPair;
import org.ontospread.state.OntoSpreadCommonUtils;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.state.UriDepthPair;
import org.ontospread.strategy.pair.OntoSpreadStrategyVisitorPair;
import org.ontospread.to.ConceptTO;
import org.ontospread.to.PathTO;
import org.ontospread.utils.TimeMonitor;
import org.ontospread.xmlbind.Concept;

public class OntoSpreadRunImpl implements OntoSpreadRun {

	protected static Logger logger = Logger.getLogger(OntoSpreadRunImpl.class);
	private OntologyDAO ontologyDAO;
	OntoSpreadStrategyVisitorPair managerStopStrategy;
	OntoSpreadStrategyVisitorPair managerSelectConceptStrategy;
	/**These properties can be null */
	OntoSpreadStrategyVisitorPair managerBeforeSpreadStrategy;
	OntoSpreadStrategyVisitorPair managerAfterSpreadStrategy;
	OntoSpreadStrategyVisitorPair managerBeforeConceptActivationStrategy;
	OntoSpreadStrategyVisitorPair managerAfterConceptActivationStrategy;

	
	//FIXME: extract, Default
	OntoSpreadStackConstraint stackConstraint = new OntoSpreadStackConstraint();
	OntoSpreadRelationWeight relationWeight = new OntoSpreadRelationWeightImpl();
	OntoSpreadDegradationFunction degradationFunction = new OntoSpreadDegradationFunctionImpl();
	
	TimeMonitor timer = new TimeMonitor();

	
	public OntoSpreadRunImpl(OntologyDAO ontologyDAO, 
			OntoSpreadStrategyVisitorPair managerStopStrategy, 
			OntoSpreadStrategyVisitorPair managerSelectConceptStrategy, 
			OntoSpreadStrategyVisitorPair managerBeforeSpreadConceptStrategy, 
			OntoSpreadStrategyVisitorPair managerAfterSpreadConceptStrategy, 
			OntoSpreadStrategyVisitorPair managerBeforeConceptSpreadConceptStrategy, 
			OntoSpreadStrategyVisitorPair managerAfterConceptSpreadConceptStrategy) {
		super();
		this.ontologyDAO = ontologyDAO;
		this.managerStopStrategy = managerStopStrategy;
		this.managerSelectConceptStrategy = managerSelectConceptStrategy;
		this.managerBeforeSpreadStrategy = managerBeforeSpreadConceptStrategy;
		this.managerAfterSpreadStrategy = managerAfterSpreadConceptStrategy;
		this.managerBeforeConceptActivationStrategy = managerBeforeConceptSpreadConceptStrategy;
		this.managerAfterConceptActivationStrategy = managerAfterConceptSpreadConceptStrategy;
	}

	public OntoSpreadRunImpl(OntologyDAO ontologyDAO, 
			OntoSpreadStrategyVisitorPair managerStopStrategy, 
			OntoSpreadStrategyVisitorPair managerSelectConceptStrategy, 
			OntoSpreadStrategyVisitorPair managerBeforeSpreadConceptStrategy, 
			OntoSpreadStrategyVisitorPair managerAfterSpreadConceptStrategy, 
			OntoSpreadStrategyVisitorPair managerBeforeConceptSpreadConceptStrategy, 
			OntoSpreadStrategyVisitorPair managerAfterConceptSpreadConceptStrategy,
			OntoSpreadRelationWeight relationWeight,
			OntoSpreadDegradationFunction ontoSpreadDegradationFunction) {
		super();
		this.ontologyDAO = ontologyDAO;
		this.managerStopStrategy = managerStopStrategy;
		this.managerSelectConceptStrategy = managerSelectConceptStrategy;
		this.managerBeforeSpreadStrategy = managerBeforeSpreadConceptStrategy;
		this.managerAfterSpreadStrategy = managerAfterSpreadConceptStrategy;
		this.managerBeforeConceptActivationStrategy = managerBeforeConceptSpreadConceptStrategy;
		this.managerAfterConceptActivationStrategy = managerAfterConceptSpreadConceptStrategy;
		this.relationWeight = relationWeight;
		this.degradationFunction = ontoSpreadDegradationFunction;
	}
	
	public OntoSpreadRunImpl(OntologyDAO ontologyDAO, 
			OntoSpreadStrategyVisitorPair managerStopStrategy, 
			OntoSpreadStrategyVisitorPair managerSelectConceptStrategy,
			OntoSpreadRelationWeight relationWeight) {
		super();
		this.ontologyDAO = ontologyDAO;
		this.managerStopStrategy = managerStopStrategy;
		this.managerSelectConceptStrategy = managerSelectConceptStrategy;
		this.relationWeight = relationWeight;
	}
	public OntoSpreadRunImpl(OntologyDAO ontologyDAO, 
			OntoSpreadStrategyVisitorPair managerStopStrategy, 
			OntoSpreadStrategyVisitorPair managerSelectConceptStrategy,
			OntoSpreadRelationWeight relationWeight,
			OntoSpreadDegradationFunction ontoSpreadDegradationFunction) {
		super();
		this.ontologyDAO = ontologyDAO;
		this.managerStopStrategy = managerStopStrategy;
		this.managerSelectConceptStrategy = managerSelectConceptStrategy;
		this.relationWeight = relationWeight;
		this.degradationFunction = ontoSpreadDegradationFunction;
	}
	
	public OntoSpreadRunImpl(OntologyDAO ontologyDAO, 
			OntoSpreadStrategyVisitorPair managerStopStrategy, 
			OntoSpreadStrategyVisitorPair managerSelectConceptStrategy) {
		super();
		this.ontologyDAO = ontologyDAO;
		this.managerStopStrategy = managerStopStrategy;
		this.managerSelectConceptStrategy = managerSelectConceptStrategy;		
	}
	
	public OntologyDAO getOntologyDAO() {
		return ontologyDAO;
	}

	public void setOntologyDAO(OntologyDAO ontologyDAO) {
		this.ontologyDAO = ontologyDAO;
	}

	public OntoSpreadState applyIteration(OntoSpreadState ontoSpreadState) throws ConceptNotFoundException {
		if(hasIteration(ontoSpreadState) && OntoSpreadStackConstraint.checkNotEmptyStack(ontoSpreadState)){
			managerSelectConceptStrategy.applyStrategy(ontoSpreadState);
			//ConceptTO conceptTO = ontoSpreadState.getConceptToSpread();
			if(managerBeforeSpreadStrategy != null) managerBeforeSpreadStrategy.applyStrategy(ontoSpreadState);
			spreadConcept(null, ontoSpreadState);
			if(managerAfterSpreadStrategy != null) managerAfterSpreadStrategy.applyStrategy(ontoSpreadState);
		}
		return ontoSpreadState;
	}
	
	
	public void spreadConcept(ConceptTO conceptTO, OntoSpreadState ontoSpreadState) throws ConceptNotFoundException{
	     //Begin the algorithm with each concept
	    final UriDepthPair uriDepth = extractFromConceptStack(ontoSpreadState);	    
		final String currentUri = uriDepth.getUri();
		ontoSpreadState.setConceptToSpread(new ConceptTO(uriDepth.getUri()));//FIXME
        final PathTO[] currentSpreadPath = getSpreadPath(ontoSpreadState.getSpreadPathTable(),currentUri);        
        final double currentScore = getScore(ontoSpreadState, currentUri);
        ontoSpreadState.setCurrentScore(currentScore);//FIXME
        final int depth = uriDepth.getDepth();
        final double []parameters = new double[]{depth};
        double degradation = 1.0;
		Concept concept = getOntologyDAO().getConcept(currentUri, null);
		ConceptOperations conceptOperations = new ConceptOperations(concept);
        //We take all the concepts (and the relation wich join with the  // ''concept'' variable)
        //Iterate over them, and score its spread value
		//System.out.println("Spreading "+uriDepth);
		logger.debug("SPREADING "+uriDepth.getUri()+" DEPTH "+uriDepth.getDepth()+" SCORE "+currentScore+
				" related "+conceptOperations.getRelatedConcepts().size());
		timer.start();
		for (ConceptRelationPair conceptRelation : conceptOperations.getRelatedConcepts()) {
	    	final String qualRelationUri = conceptRelation.getOnproperty();
            final String relatedUri =conceptRelation.getConceptUri(); 
            logger.debug("Activation for "+relatedUri+" from "+uriDepth.getUri()+" on "+qualRelationUri);            
            if (conceptOperations.isSon(conceptRelation.getConceptUri()))
                degradation = getDegradationFunction().applyDegradation(parameters, ontoSpreadState);            
            /**
            * Concept activation
            */
        	if(managerBeforeConceptActivationStrategy!= null) {
        		managerBeforeConceptActivationStrategy.applyStrategy(ontoSpreadState);
        	}
    
            //FIXME:USE as key relatedUri and qualRelationUri
			registerSpreadPath(ontoSpreadState, relatedUri, uriDepth.getUri(), qualRelationUri,currentSpreadPath);          
            /*
             * The puntuation of the concept of this iteration, is
             * related with the score of the one it's been spreaded, the
             * weight of the relation (a "gobernadoPor" should has "more
             * weight" than a "itDoesn'tMatter" one) and the degradation
             * score (if this concept is a really abstract one, the
             * degradation should be really big
             */
            final double newScore = currentScore * getWeight(qualRelationUri) * degradation; 
            addScore(ontoSpreadState, newScore, relatedUri);
            int newDepth = 0;
            //The new node increments its depth if it's a parent
            if(conceptOperations.isSon(conceptRelation.getConceptUri())){
            	newDepth = depth; 
            }else{
            	newDepth = depth + 1;
            }
            //If the uri in spreading is the related Uri no
            
            addToConceptStack(ontoSpreadState,relatedUri, uriDepth.getUri(), newDepth);
            logger.debug("Adding to stack "+relatedUri);
        	if(managerAfterConceptActivationStrategy != null){ 
        		managerAfterConceptActivationStrategy.applyStrategy(ontoSpreadState);
        	}
        	
		}
		ontoSpreadState.setSpreadTime(ontoSpreadState.getSpreadTime()+timer.stop());		
		markAsVisited(ontoSpreadState,currentUri);         
	
	}

    public void markAsVisited(OntoSpreadState ontoSpreadState, String currentUri) {
    	ontoSpreadState.getSpreadedConcepts().add(currentUri);
	}

	public void addToConceptStack(OntoSpreadState ontoSpreadState, String relatedUri, String fromUri, int depth) {		
		if(!ontoSpreadState.getSpreadedConcepts().contains(relatedUri) && !relatedUri.equals(fromUri)){
			UriDepthPair uriDepthPair = new UriDepthPair(relatedUri, depth);
			//FIXME: Linealizar conceptos con relaciones
			if(!ontoSpreadState.getSortedList().contains(uriDepthPair))
				ontoSpreadState.getSortedList().add(uriDepthPair);
		}
					
	}

	public void addScore( OntoSpreadState ontoSpreadState,double score, String uri) {
		 OntoSpreadCommonUtils.addScore(ontoSpreadState, score, uri);
	}

	public double getWeight(String qualRelationUri) {
		return getRelationWeight().getWeight(qualRelationUri);
	}

	public void registerSpreadPath(OntoSpreadState ontoSpreadState,
			String conceptRelatedUri, 
			String fromUri,
			String fromRelationUri, 
			PathTO[] spreadPath) {
		  Map<String,PathTO[]> spreadPathTable = ontoSpreadState.getSpreadPathTable();
		    if (!spreadPathTable.containsKey(conceptRelatedUri)) {
	            List<String> relations = null;
	            PathTO[] newSpreadPath = null;
	         	relations = new LinkedList<String>();
	            relations.add(fromRelationUri);	
	            if(spreadPath[spreadPath.length-1].getConceptUri().equals(fromUri)){
	            	//Keep path, add relations
	            	relations.addAll(Arrays.asList(spreadPath[spreadPath.length-1].getRelationsUri()));
	            	newSpreadPath = new PathTO[spreadPath.length];
	            }else{
	            	newSpreadPath = new PathTO[spreadPath.length+1];
	            }
            	System.arraycopy(spreadPath, 0, newSpreadPath, 0, spreadPath.length);
            	newSpreadPath[newSpreadPath.length - 1] = new PathTO(fromUri,relations.toArray(new String[relations.size()]));
	            spreadPathTable.put(conceptRelatedUri, newSpreadPath);
	        }else{
	        	  /************** Prize relations ********************/            
	            PathTO []  pathOld = spreadPath;
	            PathTO [] pathNew = (PathTO[]) spreadPathTable.get(conceptRelatedUri);
	            if(!isSameStart(pathOld[0].getConceptUri(),pathNew[0].getConceptUri())){
	                  //Only prize concepts if all concepts in "path new" have been propagated.
	                 //This constraint let us avoid prize concepts have not been propagated.
	                   if(ontoSpreadState.getSpreadedConcepts().contains(pathNew[pathNew.length-1])){      
	                     //prizeRelations(path_old,path_new); //V2 Prize concepts at the moment                                
	                	   ontoSpreadState.getConceptsToPrize().prizePaths(pathOld, pathNew);//V3 Prize concepts when algorithm finishes
	                    //showPath(path_old);
	                    //showPath(path_new); 
	                }
	            }             
	        }
	}

    protected boolean isSameStart(String start1,String start2){
        return start1.equals(start2);
    }   
      
    
	public UriDepthPair extractFromConceptStack(OntoSpreadState ontoSpreadState) {
		    Comparator<UriDepthPair> scoreComparator = new ScoreComparator(ontoSpreadState);
			Collections.sort(ontoSpreadState.getSortedList(), scoreComparator);
	        //We take the first element of the list (the one with the best // score)
			UriDepthPair toReturn = (UriDepthPair) ontoSpreadState.getSortedList().remove(0);
			return toReturn;//FIXME
	}

	public double getScore(OntoSpreadState ontoSpreadState,String uri) {
	     Double score = ((Double) ontoSpreadState.getConcepts().get(uri));
	        if (score == null){
	            return 0;
	        } else {
	            return score.doubleValue();
	        }
	}

	public PathTO[] getSpreadPath(Map<String, PathTO[]> spreadPathTable,String uri) {	
		return  OntoSpreadCommonUtils.getSpreadPath(spreadPathTable,uri);		
	}
	
	public boolean hasIteration(OntoSpreadState ontoSpreadState) {
		Boolean continues = (Boolean) managerStopStrategy.applyStrategy(ontoSpreadState);
		return continues  && OntoSpreadStackConstraint.checkNotEmptyStack(ontoSpreadState);
	}

    class ScoreComparator implements Comparator<UriDepthPair> {
    	 private  Map<String,Double>concepts;
    	public ScoreComparator(OntoSpreadState ontoSpreadState){
    		 this.concepts = ontoSpreadState.getConcepts();
    	 }
    	public int compare(UriDepthPair o1, UriDepthPair o2) {
		   Double score1 = (Double) concepts.get(o1.getUri());
	      Double score2 = (Double) concepts.get(o2.getUri());
	       return -score1.compareTo(score2);
		}
    }

	public OntoSpreadStackConstraint getStackConstraint() {
		return stackConstraint;
	}

	public OntoSpreadRelationWeight getRelationWeight(){ 
		return this.relationWeight;
	}


	public OntoSpreadDegradationFunction getDegradationFunction() {
		return degradationFunction;
	}

	public void setRelationWeight(OntoSpreadRelationWeight ontoSpreadRelationWeight) {
		
		this.relationWeight = ontoSpreadRelationWeight;
	}




}
