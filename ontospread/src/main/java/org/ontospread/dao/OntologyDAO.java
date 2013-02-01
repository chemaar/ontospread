package org.ontospread.dao;

import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.to.ConceptTO;
import org.ontospread.to.RelationTO;
import org.ontospread.to.StatTO;
import org.ontospread.xmlbind.Concept;

public interface OntologyDAO {
	 public static final String ENUMERATED_CLASSES = "enumeratedClasses";
	/**
	 * Get the list of all concepts in the ontology
	 * 
	 * @return An array of concept transfer objects
	 */
	public abstract RelationTO[] getAllRelations();
	public abstract ConceptTO[] getAllConcepts();
	public abstract Concept getConcept(String conceptUri, String contextUri)
		throws ConceptNotFoundException;
    public abstract ConceptTO getConceptTO(String conceptUri)
        throws ConceptNotFoundException;    
    public abstract RelationTO getRelationTO(String relationUri)
    throws ConceptNotFoundException;  
    public abstract StatTO[]getStats();
    
}
