package org.ontospread.to;

import java.io.Serializable;

import org.ontospread.utils.ToStringHelper;

public class PathTO implements Serializable{

	private String conceptUri;
	private String[] relationsUri = new String[0];
	public String getConceptUri() {
		return conceptUri;
	}
	public void setConceptUri(String conceptUri) {
		this.conceptUri = conceptUri;
	}
	public String[] getRelationsUri() {
		return this.relationsUri;
	}
	public void setRelationsUri(String[] relationsUri) {
		this.relationsUri = relationsUri;
	}
	public PathTO(){
		
	}
	
	public PathTO(String conceptUri, String[] relationsUri) {
		super();
		this.conceptUri = conceptUri;
		this.relationsUri = relationsUri;
	}
	
	public String toString(){
		return this.getClass().getSimpleName()+"("+conceptUri+", {"+ToStringHelper.arrayToString(relationsUri)+"})";
	}
	
}
