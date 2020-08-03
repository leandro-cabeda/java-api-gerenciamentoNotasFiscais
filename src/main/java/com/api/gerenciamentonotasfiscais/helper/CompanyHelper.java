package com.api.gerenciamentonotasfiscais.helper;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.api.gerenciamentonotasfiscais.dto.CompanyDTO;
import com.api.gerenciamentonotasfiscais.entity.CompanyEntity;

@Component
public class CompanyHelper {

	private final ModelMapper modelMapper;
	
	public CompanyHelper(ModelMapper modelMapper) {
		this.modelMapper=modelMapper;
	}
	
	public CompanyEntity toEntity(CompanyDTO company) {
		return modelMapper.map(company, CompanyEntity.class);
	}
	
	public CompanyDTO toModel(CompanyEntity company) {
		return modelMapper.map(company, CompanyDTO.class);
	}
	
	public List<CompanyDTO> toModel(List<CompanyEntity> companies){
		return companies.stream().map(this::toModel).collect(toList());
	}
}
