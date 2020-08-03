package com.api.gerenciamentonotasfiscais.exception.validator;


import java.util.*;

import com.api.gerenciamentonotasfiscais.exception.EqualsRegister;
import com.api.gerenciamentonotasfiscais.exception.NotFound;
import com.api.gerenciamentonotasfiscais.exception.RegisterExists;
import com.api.gerenciamentonotasfiscais.exception.RegisterNotNull;

public class CheckConditions {

	public static <T> T checkElementNotPresent(final Optional<T> reference, final String message) {
        if (!reference.isPresent()) {
            throw new NotFound(message);
        }
        return (T) reference.get();
    }
	
	public static <T> void checkElementNotFound(final T reference, final String message) {
        if (reference == null) {
            throw new NotFound(message);
        }
    }

    public static <T> void checkElementExists(final T reference, final String message) {
        if (reference != null) {
            throw new RegisterExists(message);
        }
    }
    
    public static <T> void checkElementEqualExist(final T reference, final T reference2, final String message) {
        if (reference.equals(reference2)) {
            throw new EqualsRegister(message);
        }
    }
    
    public static <T> void checkElementNotNull(final T reference, final String message) {
        if (reference == null) {
            throw new RegisterNotNull(message);
        }
    }
}
