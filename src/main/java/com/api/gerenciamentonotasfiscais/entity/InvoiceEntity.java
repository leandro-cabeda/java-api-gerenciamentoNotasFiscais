package com.api.gerenciamentonotasfiscais.entity;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nota_fiscal")
public class InvoiceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private Long numero;
	
	@Column(name = "data_nota", nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataNota;
	
	@Column(nullable = false)
	private Double valor;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,optional = false)
	@JoinColumn(name = "empresa_tomador_id", nullable = false)
	private CompanyEntity empresaTomador;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,optional = false)
	@JoinColumn(name = "empresa_prestador_id", nullable = false)
	private CompanyEntity empresaPrestador;
}
