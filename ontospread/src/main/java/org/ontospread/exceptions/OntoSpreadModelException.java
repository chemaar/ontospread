package org.ontospread.exceptions;


public class OntoSpreadModelException extends RuntimeException {

	public OntoSpreadModelException(Exception e, String message) {
		super(message,e);
	}

	
}
