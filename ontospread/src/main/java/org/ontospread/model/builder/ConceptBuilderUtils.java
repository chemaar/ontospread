package org.ontospread.model.builder;

import org.ontospread.xmlbind.Concept;
import org.ontospread.xmlbind.Concepts;
import org.ontospread.xmlbind.Relations;

public class ConceptBuilderUtils {


    public static Concept createEmptyConcept() {
        Concept concept = new Concept();
        concept.setRelations(new Relations());
        concept.setContext(false);        
        concept.setInstances(new Concepts());
        concept.setInstanceof(new Concepts());
        return concept;
    }
    
    
}
