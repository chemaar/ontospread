package org.ontospread.to;

import java.io.Serializable;

public class ConceptTO implements Serializable{
	private String uri;
	private String name;
	private String description;
	
	public ConceptTO(String uri) {
		super();
		this.uri = uri;
	}
	
	public ConceptTO(String uri, String name, String description) {
		super();
		this.uri = uri;
		this.name = name;
		this.description = description;
	}

	public ConceptTO() {
		super();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String toString(){
		return this.getClass().getSimpleName()+"("+getUri()+", "+getName()+", "+getDescription()+")";
	}
	

}
