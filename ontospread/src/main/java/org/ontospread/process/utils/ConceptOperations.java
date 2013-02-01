package org.ontospread.process.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ontospread.constraints.OntoSpreadRelationWeight;
import org.ontospread.xmlbind.Concept;
import org.ontospread.xmlbind.ConceptDescription;
import org.ontospread.xmlbind.Relation;
import org.ontospread.xmlbind.TypeHierarchy;

public class ConceptOperations {
	    private static final Logger logger = Logger.getLogger(ConceptOperations.class);

	    private List<String> superClassesUris;
	    private List<String> subClassesUris;
	    private List<String> relationsUris;
	    //This properties are list of ConceptPairRelations
	    private List <ConceptRelationPair>relationConcept;	    
	    
	    public ConceptOperations(Concept c) {
	        logger.debug("Processing concept " + c.getConceptDescription().getUri());
	        relationConcept = new ArrayList<ConceptRelationPair>();
	        superClassesUris = new ArrayList<String>();
	        subClassesUris = new ArrayList<String>();
	        relationsUris = new ArrayList<String>();	  
	        createLists(c.getRelations().getRelations());
	        addInstanceReferences(c.getInstances().getConceptDescriptions(),TypeHierarchy.INSTANCE);
	        addInstanceReferences(c.getInstanceof().getConceptDescriptions(),TypeHierarchy.INSTANCE_OF);        
	    }

	    private void addInstanceReferences(List <ConceptDescription>instances, TypeHierarchy hierarchy) {
	    	for (ConceptDescription description : instances) {
	    		final String objectConceptUri = description.getUri();
	    		if(objectConceptUri!=null){
	    			relationConcept.add(new ConceptRelationPair(hierarchy, objectConceptUri, OntoSpreadRelationWeight.DEFAULT_NAMESPACE+hierarchy.toString().toUpperCase()));
	    		}
	    	}
	    }
	    
	    private void createLists(List <Relation>relations) {
	    	for (Relation r : relations) {
	            final String relationUri = r.getUri();
	            //For each conceptDescription in relation
	            for (ConceptDescription conceptDescription : r.getConceptDescriptions()) {
	            	final String objectConceptUri = conceptDescription.getUri();
	                logger.debug("Adding "+relationUri+" object "+objectConceptUri);
	                if(objectConceptUri!=null ){
	                    TypeHierarchy hierarchy = r.getHierarchy();
						if (r.getOnproperty() != null) {
	                        logger.debug("Registering relation " + relationUri + "(" + r.getOnproperty() + ") with concept " + objectConceptUri);                
	                        relationsUris.add(objectConceptUri);	                        
	                        relationConcept.add(new ConceptRelationPair(hierarchy  ,  objectConceptUri, r.getOnproperty()));
	                    } else {                
	                        // Caution! this may seem counter-intuitive, but it is right
	                        if (isSuperclassOfRelation(r)) {
	                            // this _is superclass of_ object, so...
	                        	logger.debug("Adding subclass "+objectConceptUri);
	                            subClassesUris.add(objectConceptUri);
	                        } else {
	                            // otherwise, the object _is superclass of_ this, so...
	                        	logger.debug("Adding superclass "+objectConceptUri);
	                            superClassesUris.add(objectConceptUri);
	                        }
	                        relationConcept.add(
	                        		new ConceptRelationPair(hierarchy,
	                        				objectConceptUri, 
	                        				OntoSpreadRelationWeight.DEFAULT_NAMESPACE+hierarchy.toString()));
	                    }
	                } else{
	                }    
	            }//End for each concept description in relation            
	        }
	    }

	    private boolean isSuperclassOfRelation(Relation r) {
	        return r.getHierarchy().equals (TypeHierarchy.SUPERCLASS);
	    }

	    public boolean isSon(String uri) {
	        return superClassesUris.contains(uri);
	    }

	    public List<String> getSuperClasses() {
	        return (superClassesUris);
	    }

	    public List<String> getSubClasses() {
	        return (subClassesUris);
	    }

	    public List <ConceptRelationPair> getRelatedConcepts() {
	        return relationConcept;
	    }

	    
}
