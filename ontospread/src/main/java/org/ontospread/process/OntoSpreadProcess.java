package org.ontospread.process;

import org.apache.log4j.Logger;
import org.ontospread.constraints.OntoSpreadRelationWeight;
import org.ontospread.constraints.OntoSpreadRelationWeightImpl;
import org.ontospread.dao.OntologyDAO;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.process.post.OntoSpreadPostAdjustment;
import org.ontospread.process.pre.OntoSpreadPreAdjustment;
import org.ontospread.process.run.OntoSpreadRun;
import org.ontospread.state.OntoSpreadState;

public class OntoSpreadProcess {

	protected static Logger logger = Logger.getLogger(OntoSpreadProcess.class);
	private OntoSpreadPreAdjustment ontoSpreadPreAdjustment;
	private OntoSpreadRun ontoSpreadRun;
	private OntoSpreadPostAdjustment ontoSpreadPostAdjustment;
	private OntoSpreadRelationWeight relationWeight;
	private OntologyDAO ontologyDAO;
	
	public OntoSpreadProcess(OntologyDAO ontologyDAO, 
			OntoSpreadPreAdjustment ontoSpreadPreAdjustment, 
			OntoSpreadRun ontoSpreadRun,
			OntoSpreadPostAdjustment ontoSpreadPostAdjustment,
			OntoSpreadRelationWeight relationWeight) {
		super();
		this.ontologyDAO =ontologyDAO;
		this.ontoSpreadPreAdjustment = ontoSpreadPreAdjustment;
		this.ontoSpreadRun = ontoSpreadRun;
		this.ontoSpreadPostAdjustment = ontoSpreadPostAdjustment;
		this.relationWeight = relationWeight;
		this.ontoSpreadRun.setRelationWeight(this.relationWeight);
	}
	
	public OntoSpreadProcess(OntologyDAO ontologyDAO, 
			OntoSpreadPreAdjustment ontoSpreadPreAdjustment, 
			OntoSpreadRun ontoSpreadRun,
			OntoSpreadPostAdjustment ontoSpreadPostAdjustment) {
		super();
		this.ontologyDAO =ontologyDAO;
		this.ontoSpreadPreAdjustment = ontoSpreadPreAdjustment;
		this.ontoSpreadRun = ontoSpreadRun;
		this.ontoSpreadPostAdjustment = ontoSpreadPostAdjustment;
		this.relationWeight = new OntoSpreadRelationWeightImpl();
		this.ontoSpreadRun.setRelationWeight(this.relationWeight);
	}
	
	public OntoSpreadState preAdjustment(OntoSpreadState ontoSpreadState) throws ConceptNotFoundException{
		return this.ontoSpreadPreAdjustment.applyPreAdjustment(ontoSpreadState);
	}
	public OntoSpreadState postAdjustment(OntoSpreadState ontoSpreadState) throws ConceptNotFoundException{
		return this.ontoSpreadPostAdjustment.applyPostAdjustment(ontoSpreadState);
	}
	public OntoSpreadState iterate(OntoSpreadState ontoSpreadState) throws ConceptNotFoundException{
		return this.ontoSpreadRun.applyIteration(ontoSpreadState);
	}
	public boolean hasIteration(OntoSpreadState ontoSpreadState){
		return this.ontoSpreadRun.hasIteration(ontoSpreadState);
	}

	public OntologyDAO getOntologyDAO() {
		return ontologyDAO;
	}

	public void setOntologyDAO(OntologyDAO ontologyDAO) {
		this.ontologyDAO = ontologyDAO;
	}
	
	public OntoSpreadRelationWeight getRelationWeight(){
		return this.relationWeight;
	}
	
	public void setOntoSpreadRelationWeight(OntoSpreadRelationWeight relationWeight){
		this.relationWeight = relationWeight;
	}
}
