package org.ontospread.model.loader;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.ontospread.exceptions.OntoSpreadModelException;
import org.ontospread.exceptions.ResourceNotFoundException;
import org.ontospread.model.resources.ResourceLoader;
import org.ontospread.to.KnowledgeResourceTO;

import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;


public class JenaOWLModelWrapper implements OntoSpreadModelWrapper{
  

    private static final Logger logger = Logger.getLogger(JenaOWLModelWrapper.class);

    private ResourceLoader resourceLoader;
    private OntModel jenaOWLModel;
    
    public JenaOWLModelWrapper(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private Model getJenaOWLModel() {   // NOTE: !synchronized
        if (loadJenaOWLModel(this.resourceLoader)) {  
            logger.debug("Loaded Jena Owl model");
        }            
        return jenaOWLModel;
    }
    
    
    private synchronized boolean loadJenaOWLModel(ResourceLoader rdfSource)  {
        if(this.jenaOWLModel == null){
            logger.debug("Jena model for is null: creating new Jena Model");
            //Create JenaModel               
            jenaOWLModel = createJenaOWLModel(rdfSource);
            return true;
        } else {
            return false;
        }        
    }
    
    /**
     * Helper Method
     * @return
     * @throws ResourceNotFoundException
     */
    private OntModel createJenaOWLModel(ResourceLoader owlSource) throws ResourceNotFoundException {
        KnowledgeResourceTO[] owlsources = null;
        //Do not use reasoner to load ontologies
        OntDocumentManager dm = OntDocumentManager.getInstance();
        dm.setProcessImports(false);
        OntModelSpec spec = new OntModelSpec( OntModelSpec.OWL_MEM);
        spec.setDocumentManager(dm);
        OntModel ontModel = ModelFactory.createOntologyModel(spec, null );
        owlsources = owlSource.getKnowledgeResources();
        logger.debug("Loading " + owlsources.length  +" resources into the model");
        for ( int i = 0; i< owlsources.length ; i++ ) {
            final InputStream is = owlsources[i].getKnowledgeSourceData();        
            logger.debug("Loading OWL "+owlsources[i].getKnowledgeSourcePk());
            ontModel.read(is, "");   
            try {
                is.close();
            } catch (IOException e) {
                throw new OntoSpreadModelException(e, "JenaOWLModelWrapper reading " + owlsources[i].getKnowledgeSourcePk());
            }
            logger.debug("Loaded "+owlsources[i].getKnowledgeSourcePk());          
        }
        return ontModel;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
    
    public void setOntologySource(ResourceLoader ontologySource) {
        this.resourceLoader = ontologySource;
    }
    
    public Object getModel() {
        return getJenaOWLModel();
    }
    
    public void setModel(Model model) {
        this.jenaOWLModel = (OntModel) model;
    }
    
}
