package org.ontospread.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.exceptions.ConceptUnsupportedOperation;
import org.ontospread.model.builder.JenaConceptBuilder;
import org.ontospread.model.builder.OntologyHelper;
import org.ontospread.model.loader.OntoSpreadModelWrapper;
import org.ontospread.to.ConceptTO;
import org.ontospread.to.RelationTO;
import org.ontospread.to.StatTO;
import org.ontospread.xmlbind.Concept;
import org.ontospread.xmlbind.TypeHierarchy;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.OWL;

public class JenaOntologyDAOImpl implements OntologyDAO {
	
	protected static Logger logger = Logger.getLogger(JenaOntologyDAOImpl.class);

	private OntoSpreadModelWrapper model;
	private JenaConceptBuilder conceptFactory;

	public JenaOntologyDAOImpl(OntoSpreadModelWrapper model){
		this.model = model;
		this.conceptFactory = new JenaConceptBuilder();
	}
	
	public ConceptTO[] getAllConcepts() {
		List<ConceptTO> concepts = new LinkedList<ConceptTO>();
	    //Loading classes
		concepts.addAll(createConceptsFromResources(getOntModel().listClasses()));        
	    //Loading instances
	    concepts.addAll(createConceptsFromResources(getOntModel().listIndividuals()));
		return concepts.toArray(new ConceptTO[concepts.size()]);
	}

	public Concept getConcept(String conceptUri, String contextUri)
			throws ConceptNotFoundException {
		OntResource resource = getOntModel().getOntResource(conceptUri);
		if(resource == null){
			throw new ConceptNotFoundException("Concept "+conceptUri+" can not be built.");
		}
		return conceptFactory.createConceptResource(resource);
	}

	public ConceptTO getConceptTO(String conceptUri)
			throws ConceptNotFoundException {
		OntResource resource = getOntModel().getOntResource(conceptUri);
		if(resource == null){
			throw new ConceptNotFoundException("Concept "+conceptUri+" can not be built.");
		}
		return OntologyHelper.createConceptTO(resource);
	}
	
	protected OntModel getOntModel(){
		return (OntModel) this.model.getModel();
	}
	    
    private List<ConceptTO> createConceptsFromResources(ExtendedIterator it) {
    	List<ConceptTO> concepts = new LinkedList<ConceptTO>();
        for(;it.hasNext();){
           OntResource resource = (OntResource) it.next();           
           try{
        	   //Check if the resource in anonymous created by Jena
        	   if(resource.getURI() != null){        		           	   
        		   concepts.add(OntologyHelper.createConceptTO(resource));
        	   }
              }catch(ConceptUnsupportedOperation e){
               logger.debug("Discarding create concept from non user class "+resource.getURI());
           }
        }
		return concepts;
    }

	public RelationTO[] getAllRelations() {
		List<RelationTO> relations = new LinkedList<RelationTO>();
	    List<ConceptTO> createConceptsFromResources = createConceptsFromResources(getOntModel().listObjectProperties());	    
	    //FIXME
		//Loading classes
	    for (ConceptTO conceptTO : createConceptsFromResources) {
	    	relations.add(new RelationTO(conceptTO));
		}       
		return relations.toArray(new RelationTO[relations.size()]);
	}

	public RelationTO getRelationTO(String relationUri) throws ConceptNotFoundException {
		OntResource resource = getOntModel().getOntResource(relationUri);
		if(resource == null){
			throw new ConceptNotFoundException("Concept "+relationUri+" can not be built.");
		}
		return new RelationTO(OntologyHelper.createConceptTO(resource));
	}

	public StatTO[] getStats() {
		OntModel ontModel = getOntModel();
		List<StatTO> stats = new LinkedList<StatTO>();		
		stats.add(new StatTO(OWL.AllDifferent.getURI(),
				String.valueOf(ontModel.listAllDifferent().toList().size())));
		stats.add(new StatTO(OWL.DatatypeProperty.getURI(),
				String.valueOf(ontModel.listDatatypeProperties().toList().size())));
		stats.add(new StatTO(OWL.DataRange.getURI(),
				String.valueOf(ontModel.listDataRanges().toList().size())));
		stats.add(new StatTO(OWL.AnnotationProperty.getURI(),
				String.valueOf(ontModel.listAnnotationProperties().toList().size())));
		stats.add(new StatTO(OWL.ObjectProperty.getURI(),
				String.valueOf(ontModel.listObjectProperties().toList().size())));
		stats.add(new StatTO(OWL.imports.getURI(),
				String.valueOf(ontModel.listImportedOntologyURIs().size())));
		stats.add(new StatTO(OWL.Ontology.getURI(),
				String.valueOf(ontModel.listOntologies().toList().size())));
		stats.add(new StatTO(OWL.Class.getURI(),
				String.valueOf(ontModel.listClasses().toList().size())));
		stats.add(new StatTO(ENUMERATED_CLASSES,
				String.valueOf(ontModel.listEnumeratedClasses().toList().size())));
		stats.add(new StatTO(OWL.unionOf.getURI(),
				String.valueOf(ontModel.listUnionClasses().toList().size())));
		stats.add(new StatTO(OWL.complementOf.getURI(),
				String.valueOf(ontModel.listComplementClasses().toList().size())));
		stats.add(new StatTO(OWL.intersectionOf.getURI(),
				String.valueOf(ontModel.listIntersectionClasses().toList().size())));
		stats.add(new StatTO(OWL.Restriction.getURI(),
				String.valueOf(ontModel.listRestrictions().toList().size())));
		stats.add(new StatTO(OWL.OntologyProperty.getURI(),
				String.valueOf(ontModel.listOntProperties().toList().size())));
		stats.add(new StatTO(TypeHierarchy.INSTANCE.name(),
				String.valueOf(ontModel.listIndividuals().toList().size())));
		return stats.toArray(new StatTO[stats.size()]);
	}

}
