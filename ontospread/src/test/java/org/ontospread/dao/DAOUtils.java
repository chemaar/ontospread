package org.ontospread.dao;

import org.ontospread.model.loader.JenaOWLModelWrapper;
import org.ontospread.model.loader.JenaRDFModelWrapper;
import org.ontospread.model.loader.OntoSpreadModelWrapper;
import org.ontospread.model.resources.FilesResourceLoader;
import org.ontospread.model.resources.ResourceLoader;

public class DAOUtils {
	public static OntoSpreadModelWrapper createOntoModelWrapper(String []filenames){
		ResourceLoader resource = new FilesResourceLoader(filenames);
		JenaOWLModelWrapper model = new JenaOWLModelWrapper(resource);
		return model;
	}
	
	public static OntologyDAO createOntologyDAO() {
		//String[] filenames = new String[]{"OntoNodrizaFull-local.owl"};
		String[] filenames = new String[]{"full-galen.owl"};
		OntologyDAO ontoDAO = new JenaOntologyDAOImpl(createOntoModelWrapper(filenames ));
		return ontoDAO;
	}
	
	public static OntoSpreadModelWrapper createModelWrapper() {
		String[] filenames = new String[]{"relation-weights.rdf"};
		ResourceLoader resource = new FilesResourceLoader(filenames);
		return new JenaRDFModelWrapper(resource);	
		
	}
	public static OntologyDAO createOntologyGalenDAO() {
		String[] filenames = new String[]{"full-galen.owl"};
		OntologyDAO ontoDAO = new JenaOntologyDAOImpl(createOntoModelWrapper(filenames ));
		return ontoDAO;
	}

	public static OntoSpreadModelWrapper createGalenModelWrapper() {
		String[] filenames = new String[]{"relation-weights-galen.rdf"};
		ResourceLoader resource = new FilesResourceLoader(filenames);
		return new JenaRDFModelWrapper(resource);	
	}
	
	/*
	public static OntologyDAO createOntologyDAOTagFilter() {
		String[] filenames = new String[]{"VersionFinal3.0-OWLDDL.owl"};
		OntologyDAO ontoDAO = new JenaOntologyDAOImpl(createOntoModelWrapper(filenames ));
		return ontoDAO;
	}
	*/
}
