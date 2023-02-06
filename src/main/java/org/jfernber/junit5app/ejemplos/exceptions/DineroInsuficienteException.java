package org.jfernber.junit5app.ejemplos.exceptions;

public class DineroInsuficienteException extends RuntimeException{

	private static final long serialVersionUID = 7324501898236691832L;

	public DineroInsuficienteException(String message) {
		super(message);
	}


}
