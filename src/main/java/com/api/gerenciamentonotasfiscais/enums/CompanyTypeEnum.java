package com.api.gerenciamentonotasfiscais.enums;

import java.util.Arrays;

public enum CompanyTypeEnum {

	TOMADOR, PRESTADOR;

	public static CompanyTypeEnum validationValue(String value) {
		for (CompanyTypeEnum tipo : values()) {
			if (tipo.name().equalsIgnoreCase(value)) {
				return tipo;
			}
		}
		throw new IllegalArgumentException(
				"Tipo desconhecido: " + value + ", Os valores que são permitidos: " + Arrays.toString(values()));
	}
}
