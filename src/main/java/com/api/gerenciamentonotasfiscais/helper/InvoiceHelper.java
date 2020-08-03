package com.api.gerenciamentonotasfiscais.helper;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.api.gerenciamentonotasfiscais.dto.InvoiceDTO;
import com.api.gerenciamentonotasfiscais.entity.InvoiceEntity;

@Component
public class InvoiceHelper {

	private final ModelMapper modelMapper;
	
	public InvoiceHelper(ModelMapper modelMapper) {
		this.modelMapper=modelMapper;
	}
	
	public InvoiceEntity toEntity(InvoiceDTO invoice) {
		return modelMapper.map(invoice, InvoiceEntity.class);
	}
	
	public InvoiceDTO toModel(InvoiceEntity invoice) {
		return modelMapper.map(invoice, InvoiceDTO.class);
	}
	
	public List<InvoiceDTO> toModel(List<InvoiceEntity> invoices){
		return invoices.stream().map(this::toModel).collect(toList());
	}
}
