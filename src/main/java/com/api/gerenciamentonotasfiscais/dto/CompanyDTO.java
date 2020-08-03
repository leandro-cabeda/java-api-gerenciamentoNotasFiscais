package com.api.gerenciamentonotasfiscais.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

import com.api.gerenciamentonotasfiscais.enums.CompanyTypeEnum;
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
public class CompanyDTO {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long id;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty(value = "nome_fantasia")
	private String nomeFantasia;
	
	@NotNull(message = "Razão Social não pode ser nulo")
	@JsonProperty(value = "razao_social")
	private String razaoSocial;
	
	@NotBlank(message = "CNPJ não informado")
	@CNPJ(message = "CNPJ inválido")
	private String cnpj;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private CompanyTypeEnum tipo;
}
