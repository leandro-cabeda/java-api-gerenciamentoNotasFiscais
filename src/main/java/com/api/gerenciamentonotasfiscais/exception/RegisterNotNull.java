package com.api.gerenciamentonotasfiscais.exception;

import java.io.Serializable;
import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegisterNotNull extends RuntimeException {
	private static final long serialVersionUID = 3035109919406038268L;
	
	public RegisterNotNull(String msg) {
		super(msg);
	}
	
	public static <T extends Serializable> Supplier<EqualsRegister> registerNotNull(final T register) {
	    return () -> new EqualsRegister("Error: "+ register);
	}
}
