package org.ontospread.model.loader;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.ontospread.exceptions.OntoSpreadModelException;
import org.ontospread.exceptions.ResourceNotFoundException;
import org.ontospread.model.resources.ResourceLoader;
import org.ontospread.to.KnowledgeResourceTO;

import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;

public class JenaOWLReasonerModelWrapper implements OntoSpreadModelWrapper {
  

    private static final Logger logger = Logger.getLogger(JenaOWLReasonerModelWrapper.class);

    private ResourceLoader resourceLoader;    
    private OntModel jenaOWLModel;
    
    
    public JenaOWLReasonerModelWrapper(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    private Model getJenaOWLModel() {   // NOTE: !synchronized
        if (loadJenaOWLModel(this.resourceLoader)) {  
        	  logger.debug("Loaded Jena Owl (with reasoning) model");
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
    
    private OntModel createJenaOWLModel(ResourceLoader owlSource) throws ResourceNotFoundException {
        KnowledgeResourceTO[] owlsources = null;
        Reasoner reasoner =  PelletReasonerFactory.theInstance().create(); 
        OntDocumentManager dm = OntDocumentManager.getInstance();
        dm.setProcessImports(false);
        OntModelSpec spec = new OntModelSpec( OntModelSpec.OWL_DL_MEM);
        spec.setDocumentManager(dm);        
        //spec.setReasoner(new OWLMicroReasoner(OWLMicroReasonerFactory.theInstance()));
        spec.setReasoner(reasoner);
        OntModel ontModel = ModelFactory.createOntologyModel( spec, null );
        owlsources = owlSource.getKnowledgeResources();
        logger.debug("Loading with reasoner " + owlsources.length  +" resources into the model");
        for ( int i = 0; i< owlsources.length ; i++ ) {                   
            final InputStream is = owlsources[i].getKnowledgeSourceData();
            logger.debug("Loading OWL Reasoner "+owlsources[i].getKnowledgeSourcePk());
            ontModel.read(is, "");   
            try {
                is.close();
            } catch (IOException e) {
                throw new OntoSpreadModelException(e, "JenaOWLReasonerModelWrapper reading " + owlsources[i].getKnowledgeSourcePk());
            }
            logger.debug("Loaded  with reasoner "+owlsources[i].getKnowledgeSourcePk());
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
