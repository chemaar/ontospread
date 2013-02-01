package org.ontospread.model.resources;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ontospread.exceptions.OntoSpreadModelException;
import org.ontospread.exceptions.ResourceNotFoundException;
import org.ontospread.pk.KnowledgeSourcePK;
import org.ontospread.to.KnowledgeResourceTO;
import org.ontospread.utils.DocumentBuilderHelper;
import org.w3c.dom.Document;


public class FilesResourceLoader  implements ResourceLoader {
    
    private static final Logger logger = Logger.getLogger(FilesResourceLoader.class);

    private String []resourceNames;
    
    public FilesResourceLoader(String[] filenames) {
        this.resourceNames = filenames;
    }
    public FilesResourceLoader(List <String>filenames) {
        this.resourceNames = filenames.toArray(new String[filenames.size()]);
    }

    protected InputStream openInputStream(String filename) throws FileNotFoundException {
        logger.debug("Opening resource input stream for filename: " + filename);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream in = classLoader.getResourceAsStream(filename);
        if (in == null) {
            logger.error("Resource file not found: " + filename);
            throw new FileNotFoundException(filename);
        } else {
            return in;
        }
    }
    
    

    public Document getKnowledgeResourceAsDocument(String filename) throws ResourceNotFoundException {
        try {
        	logger.debug("Parsing resource filename: " + filename);
            InputStream in = openInputStream(filename);
            Document document = DocumentBuilderHelper.getDocumentFromInputStream(in);
            logger.debug("Finished parsing of resource filename: " + filename);
            in.close();
            return document;
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage()); // propagate
        } catch (Exception e) {
            throw new OntoSpreadModelException(e, "OntoSpread files resource loaded: parsing Resource file " + filename);
        }
    }
    

  
    public KnowledgeResourceTO[] getKnowledgeResources() {
        Collection<KnowledgeResourceTO> ontologies = new LinkedList<KnowledgeResourceTO>();        
        String file = "";
        try {
            for (int i = 0 ;i< resourceNames.length;i++) {
                file =  resourceNames[i]; 
                KnowledgeSourcePK ontologyPK = new KnowledgeSourcePK(file);
                KnowledgeResourceTO resource = new KnowledgeResourceTO(
                		(this.openInputStream(file)),ontologyPK);
                ontologies.add(resource);
            }
        } catch (FileNotFoundException e) {
            throw new OntoSpreadModelException(e, "Resource Files getting resource file:  "+file);
        }
        return  ontologies.toArray(new KnowledgeResourceTO[ontologies.size()]);
    }


	public String[] getKnowledgeResourcesNames() throws ResourceNotFoundException {
		return this.resourceNames;
	}



}
