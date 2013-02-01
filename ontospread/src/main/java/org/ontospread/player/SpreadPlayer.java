package org.ontospread.player;

import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.process.OntoSpreadProcess;
import org.ontospread.state.OntoSpreadState;

//Iterator pattern
public interface SpreadPlayer {

	public OntoSpreadState next() throws ConceptNotFoundException;
	public OntoSpreadState previous() throws ConceptNotFoundException;
	public OntoSpreadState first() throws ConceptNotFoundException;
	public OntoSpreadState last() throws ConceptNotFoundException;
	public OntoSpreadState current() throws ConceptNotFoundException;
	public boolean hasNext();	
	public OntoSpreadProcess getOntoSpreadProcess();
	
	
}
