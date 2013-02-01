package org.ontospread.player;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.exceptions.OntoSpreadModelException;
import org.ontospread.process.OntoSpreadProcess;
import org.ontospread.state.OntoSpreadState;

public class SpreadDebugPlayer implements SpreadPlayer {
	protected static Logger logger = Logger.getLogger(SpreadDebugPlayer.class);
	List <ByteArrayOutputStream> iterations = new LinkedList<ByteArrayOutputStream>();
	int current;
	OntoSpreadProcess ontoSpreadProcess;
	
	
	public SpreadDebugPlayer(OntoSpreadProcess ontoSpreadProcess, OntoSpreadState ontoSpreadState) throws ConceptNotFoundException {
		super();
		this.ontoSpreadProcess = ontoSpreadProcess;
		this.ontoSpreadProcess.preAdjustment(ontoSpreadState);
		current = 0;
		addOntoSpreadState(ontoSpreadState);				
	}

	private boolean addOntoSpreadState(OntoSpreadState ontoSpreadState) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(output);
			oos.writeObject(ontoSpreadState);
			oos.close();
			output.close();
			return iterations.add(output);
		} catch (IOException e) {
			throw new OntoSpreadModelException(e,"Writing iteration "+current);
		}
//		OntoSpreadXMLState xmlSerializer = OntoSpreadStateXMLSerializer.asXML(ontoSpreadState);
//		Document document = OntoSpreadXMLStateXMLBind.getInstance().serializeOntoSpreadXMLState(xmlSerializer);		
//		return iterations.add(DOMToString.print(document));
	}

	private OntoSpreadState getOntoSpreadState(int current) {
//		Document document;
//		try {
//			document = DocumentBuilderHelper.getDocumentFromString(iterations.get(current));
//			OntoSpreadXMLState xmlSerializer = OntoSpreadXMLStateXMLBind.getInstance().restoreOntoSpreadXMLState(document);	
//			return OntoSpreadStateXMLSerializer.asState(xmlSerializer);		
//		} catch (DocumentBuilderException e) {
//			throw new OntoSpreadModelException(e,"Getting iteration "+current);
//		}	
		ByteArrayInputStream inputStream = new ByteArrayInputStream(iterations.get(current).toByteArray());
		try {
			ObjectInputStream input = new ObjectInputStream(inputStream);
			OntoSpreadState ontoSpreadState = (OntoSpreadState) input.readObject();
			input.close();
			inputStream.close();
			return ontoSpreadState; 
		} catch (IOException e) {
			throw new OntoSpreadModelException(e,"Getting iteration "+current);
		} catch (ClassNotFoundException e) {
			throw new OntoSpreadModelException(e,"Getting iteration "+current);
		}
		
	}
	
	public OntoSpreadState first() throws ConceptNotFoundException {
		current = 0;
		logger.debug("Getting first "+current);
		OntoSpreadState ontoSpreadState = (iterations.size()==0?null:getOntoSpreadState(current));
		return ontoSpreadState;
	}

	public boolean hasNext() {
		return getOntoSpreadProcess().hasIteration(getOntoSpreadState(current));
	}

	public OntoSpreadState last() throws ConceptNotFoundException {
		while(hasNext()){
			next();
		}
		return getOntoSpreadState(current);
	}

	public OntoSpreadState next() throws ConceptNotFoundException {
		if(hasNext()){
			if(current+1>=iterations.size()){
				OntoSpreadState ontoSpreadState = 
					getOntoSpreadProcess().iterate(getOntoSpreadState(current));			
				addOntoSpreadState(ontoSpreadState);
			}
			current++;
			if(!hasNext()){				
				this.ontoSpreadProcess.postAdjustment(getOntoSpreadState(current));					
			}
		}
		logger.debug("Getting next "+current);
		return  (getOntoSpreadState(current));
	}

	public OntoSpreadState previous() {
		if(current>0){
			current--;
		}
		logger.debug("Getting previous "+current);
		return (getOntoSpreadState(current));
	}

	public OntoSpreadProcess getOntoSpreadProcess() {
		return ontoSpreadProcess;
	}

	public OntoSpreadState current() throws ConceptNotFoundException {
		return getOntoSpreadState(current);
	}

}
