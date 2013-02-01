package org.ontospread.process.run;

import java.util.Map;

import org.ontospread.constraints.OntoSpreadRelationWeight;
import org.ontospread.constraints.OntoSpreadStackConstraint;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.state.UriDepthPair;
import org.ontospread.to.ConceptTO;
import org.ontospread.to.PathTO;

public interface OntoSpreadRun {

	public OntoSpreadState applyIteration(OntoSpreadState ontoSpreadState) throws ConceptNotFoundException;

	public boolean hasIteration(OntoSpreadState ontoSpreadState);

	public void spreadConcept(ConceptTO conceptTO, OntoSpreadState ontoSpreadState) throws ConceptNotFoundException;

	public OntoSpreadStackConstraint getStackConstraint();	

	void markAsVisited(OntoSpreadState ontoSpreadState, String currentUri);

	public void addToConceptStack(OntoSpreadState ontoSpreadState, String relatedUri,String fromUri, int depth);

	public void addScore(OntoSpreadState ontoSpreadState, double score, String uri);

	public double getWeight(String qualRelationUri);

	public void registerSpreadPath(OntoSpreadState ontoSpreadState, String conceptRelatedUri, String fromUri, String fromRelationUri, PathTO[] spreadPath);

	public UriDepthPair extractFromConceptStack(OntoSpreadState ontoSpreadState);

	public double getScore(OntoSpreadState ontoSpreadState, String uri);

	public PathTO[] getSpreadPath(Map<String, PathTO[]> spreadPathTable, String uri);
	
	public OntoSpreadRelationWeight getRelationWeight();
	
	public void setRelationWeight(OntoSpreadRelationWeight ontoSpreadRelationWeight);
	
	public OntoSpreadDegradationFunction getDegradationFunction();
}
