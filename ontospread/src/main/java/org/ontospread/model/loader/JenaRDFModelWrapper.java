package org.ontospread.model.loader;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.ontospread.exceptions.OntoSpreadModelException;
import org.ontospread.exceptions.ResourceNotFoundException;
import org.ontospread.model.resources.ResourceLoader;
import org.ontospread.to.KnowledgeResourceTO;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class JenaRDFModelWrapper implements OntoSpreadModelWrapper {

    private static final Logger logger = Logger.getLogger(JenaRDFModelWrapper.class);

    private ResourceLoader resourceLoader;
    private Model jenaRDFModel;
    
    public JenaRDFModelWrapper(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    public synchronized void forceReload() {
        jenaRDFModel = null; // the model will be reloaded on demand
    }
    
    /**
     * @return an instance of Model
     */
    private Model getJenaRDFModel() {   // NOTE: !synchronized
        if (loadJenaRDFModel(this.resourceLoader)) {  
        	logger.debug("Loaded Jena Rdf model");
        }            
        return jenaRDFModel;
    }
    
    
    private synchronized boolean loadJenaRDFModel(ResourceLoader rdfSource)  {
        if(this.jenaRDFModel == null){
            logger.debug("Jena model for is null: creating new Jena Model");
            //Create JenaModel               
            jenaRDFModel = createRDFModel(rdfSource);            
            return true;
        } else {
            return false;
        }        
    }
    
    private Model createRDFModel(ResourceLoader rdfSource) throws ResourceNotFoundException {
        KnowledgeResourceTO[] rdfsources = null;
        Model model =ModelFactory.createDefaultModel();
        rdfsources = rdfSource.getKnowledgeResources();
        logger.debug("Loading " + rdfsources.length  +" resources into the model");
        for ( int i = 0; i< rdfsources.length ; i++ ) {                   
            final InputStream is = rdfsources[i].getKnowledgeSourceData();
            logger.debug("Loading RDF "+rdfsources[i].getKnowledgeSourcePk());
            model.read(is, "");        
            try {
                is.close();
            } catch (IOException e) {
                throw new OntoSpreadModelException(e, "JenaRDFModelWrapper reading " + rdfsources[i].getKnowledgeSourcePk());
            }
            logger.debug("RDF stream loaded, model contains " + model.size() + " triplets");          
        }        
        return model;
    }

    /**
     * @return Returns the ontologySource.
     */
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
    
    /**
     * @param ontologySource The ontologySource to set.
     */
    public void setOntologySource(ResourceLoader ontologySource) {
        this.resourceLoader = ontologySource;
    }

    public Object getModel() {
        return getJenaRDFModel();
    }
    
    public void setModel(Model model) {
        this.jenaRDFModel = (Model) model;
    }


}
