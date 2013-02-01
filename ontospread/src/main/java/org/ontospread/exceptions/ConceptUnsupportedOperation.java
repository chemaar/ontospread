package org.ontospread.exceptions;

public class ConceptUnsupportedOperation extends RuntimeException {

	public ConceptUnsupportedOperation(Throwable e, String message) {
		super(message,e);
	}

	public ConceptUnsupportedOperation(String message) {
		super(message);
	}
}
