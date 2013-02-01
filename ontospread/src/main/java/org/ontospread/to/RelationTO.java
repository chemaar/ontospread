package org.ontospread.to;

import java.io.Serializable;

public class RelationTO implements Serializable{

	private ConceptTO conceptTO;
	
	public RelationTO(ConceptTO conceptTO){
		this.conceptTO = conceptTO;
	}
	public String getDescription() {
		return this.conceptTO.getDescription();
	}
	
	public void setDescription(String description) {
		this.conceptTO.setDescription(description);
	}
	public String getName() {
		return this.conceptTO.getName();
	}
	public void setName(String name) {
	 this.conceptTO.setName(name);
	}
	public String getUri() {
		return this.conceptTO.getUri();
	}
	public void setUri(String uri) {
		this.conceptTO.setUri(uri);
	}
	
	public String toString(){
		return this.getClass().getSimpleName()+"("+getUri()+", "+getName()+", "+getDescription()+")";
	}
}
