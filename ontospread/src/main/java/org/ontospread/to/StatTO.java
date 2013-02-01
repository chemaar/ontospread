package org.ontospread.to;

import java.io.Serializable;

public class StatTO implements Serializable {

	private String type;
	private String value;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public StatTO(String type, String value) {
		super();
		this.type = type;
		this.value = value;
	}
	

}
