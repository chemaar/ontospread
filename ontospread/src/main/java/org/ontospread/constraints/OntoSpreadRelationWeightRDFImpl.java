package org.ontospread.constraints;

import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ontospread.dao.JenaOntologyDAOImpl;
import org.ontospread.model.loader.OntoSpreadModelWrapper;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class OntoSpreadRelationWeightRDFImpl implements OntoSpreadRelationWeight{

	protected static Logger logger = Logger.getLogger(JenaOntologyDAOImpl.class);

	private OntoSpreadModelWrapper model;
	private HashMap<String, Double> weightTable;

	public OntoSpreadRelationWeightRDFImpl(OntoSpreadModelWrapper model){
		this.model = model;
		createCache();

	}
	private void createCache() {
		weightTable = new HashMap<String, Double>();
		this.loadRelationsWeight();
		this.cleanModel();
	}

	private  void loadRelationsWeight() {
		Model model = getRDFModel();
		StmtIterator itr = model.listStatements();

		while(itr.hasNext()) {
			Statement stmt = itr.nextStatement();
			Double weight = new Double(stmt.getObject().toString());
			weightTable.put(stmt.getSubject().getURI(), weight);
		}
		this.weightTable.put(DEFAULT_URI,DEFAULT_VALUE);
	}

	private Model getRDFModel() {
		return (Model) this.model.getModel();
	}

	private void cleanModel() {
		Model model = getRDFModel();
		//Remove model        
		model.removeAll();
		model.close();
		model = null;
	}
	
	
	public double getWeight(String qualRelationUri) {
		//logger.debug("Get "+qualRelationUri+" "+this.weightTable.get(qualRelationUri));
		return(this.weightTable.containsKey(qualRelationUri))?
			this.weightTable.get(qualRelationUri):this.weightTable.get(DEFAULT_URI);
	}
	public Set<String> getRelationsUris() {
		return this.weightTable.keySet();
	}
	public void setWeight(String qualRelationUri, double value) {
		
		this.weightTable.put(qualRelationUri, value);
		
	}
	
	public double getDefault() {
		return this.getWeight(DEFAULT_URI);
	}

	public void setDefault(double value) {
		this.setWeight(OntoSpreadRelationWeightRDFImpl.DEFAULT_URI, value);
	}

}
