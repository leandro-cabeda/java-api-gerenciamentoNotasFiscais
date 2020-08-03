package com.api.gerenciamentonotasfiscais.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.api.gerenciamentonotasfiscais.enums.CompanyTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empresa")
public class CompanyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome_fantasia")
	private String nomeFantasia;
	
	@Column(nullable = false, name = "razao_social")
	private String razaoSocial;
	
	@Column(nullable = false,unique = true)
	private String cnpj;
	
	@Enumerated(EnumType.STRING)
	private CompanyTypeEnum tipo;
}
