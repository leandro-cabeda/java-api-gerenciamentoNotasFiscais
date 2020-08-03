package com.api.gerenciamentonotasfiscais.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long id;
	
	@NotNull(message = "Numero não pode ser nulo")
	@Min(value = 0, message = "O numero informado não pode ser abaixo de {min}")
	private Long numero;
	
	@JsonProperty(value = "data_nota")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Data da nota não pode ser nulo")
	private Date dataNota;
	
	@NotNull(message = "Valor não pode ser nulo")
	@Min(value = 0, message = "O valor informado não pode ser abaixo de {min}")
	private Double valor;
	
	@NotNull(message = "Empresa tomadora não pode ser nula")
	@JsonProperty(value = "empresa_tomador")
	private CompanyDTO empresaTomador;
	
	@NotNull(message = "Empresa prestadora não pode ser nula")
	@JsonProperty(value = "empresa_prestador")
	private CompanyDTO empresaPrestador;
}
