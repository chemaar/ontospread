package org.ontospread.model.resources;

import org.ontospread.exceptions.ResourceNotFoundException;
import org.ontospread.to.KnowledgeResourceTO;
import org.w3c.dom.Document;


public interface ResourceLoader {
    
    public KnowledgeResourceTO [] getKnowledgeResources() throws ResourceNotFoundException;
    public String []getKnowledgeResourcesNames() throws ResourceNotFoundException;
    public Document getKnowledgeResourceAsDocument(String filename) throws ResourceNotFoundException;
    
}
