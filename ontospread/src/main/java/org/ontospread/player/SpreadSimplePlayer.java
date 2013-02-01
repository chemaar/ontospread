package org.ontospread.player;

import org.apache.log4j.Logger;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.process.OntoSpreadProcess;
import org.ontospread.state.OntoSpreadState;

//Iterator pattern
public class SpreadSimplePlayer implements SpreadPlayer{
	protected static Logger logger = Logger.getLogger(SpreadSimplePlayer.class);
	OntoSpreadProcess ontoSpreadProces;
	private OntoSpreadState ontoCurrentSpreadState;	
	
	public SpreadSimplePlayer(OntoSpreadProcess ontoSpreadProces, OntoSpreadState ontoSpreadState) throws ConceptNotFoundException {
		super();
		this.ontoSpreadProces = ontoSpreadProces;
		this.ontoCurrentSpreadState = ontoSpreadState;
		this.ontoSpreadProces.preAdjustment(this.ontoCurrentSpreadState);
	}

	public OntoSpreadState first() throws ConceptNotFoundException {
		throw new UnsupportedOperationException();
	}

	public boolean hasNext() {
		return getOntoSpreadProcess().hasIteration(ontoCurrentSpreadState);
	}

	public OntoSpreadState last() throws ConceptNotFoundException {
		 throw new UnsupportedOperationException();
	}

	public OntoSpreadState next() throws ConceptNotFoundException {
		OntoSpreadState ontoSpreadState = 
			getOntoSpreadProcess().iterate(ontoCurrentSpreadState);
		if(!hasNext()){
			this.ontoSpreadProces.postAdjustment(ontoSpreadState);
		}
		ontoCurrentSpreadState = ontoSpreadState;
		return ontoCurrentSpreadState;
	}

	public OntoSpreadState previous() {
		throw new UnsupportedOperationException();
	}

	public OntoSpreadProcess getOntoSpreadProcess() {		
		return ontoSpreadProces;
	}

	public OntoSpreadState current() throws ConceptNotFoundException {
		return ontoCurrentSpreadState;
	}
	
}
