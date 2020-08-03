package com.api.gerenciamentonotasfiscais.service;

import org.springframework.stereotype.Service;

import com.api.gerenciamentonotasfiscais.entity.CompanyEntity;
import com.api.gerenciamentonotasfiscais.enums.CompanyTypeEnum;
import com.api.gerenciamentonotasfiscais.exception.validator.CheckConditions;
import com.api.gerenciamentonotasfiscais.repository.CompanyRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CompanyService {

	private final CompanyRepository repository;
	
	public CompanyService(CompanyRepository repository) {
		this.repository=repository;
	}
	
	public CompanyEntity addEmpresa(CompanyEntity empresa) {
		log.info("Verifica se a empresa de CNPJ: " + empresa.getCnpj() + " caso existe no banco registrado!");
		CheckConditions.checkElementExists(repository.findByCnpj(empresa.getCnpj()), 
				"Esta empresa de CNPJ: "+empresa.getCnpj()+" já existe cadastrado!");
		
		log.info("Valida o tipo da empresa caso foi informado!");
		if(empresa.getTipo() != null) CompanyTypeEnum.validationValue(empresa.getTipo().name());
		
		log.info("Salva a empresa no banco de dados!");
		return repository.save(empresa);
	}
	
	public CompanyEntity updateEmpresa(Long id, CompanyEntity empresa) {
		log.info("Verifica se a empresa com id: " + id + " existe registrado!");
		CompanyEntity entity=CheckConditions.checkElementNotPresent(repository.findById(id), "Empresa de Id: "+id+" não foi encontrado");
		
		log.info("Valida o tipo da empresa caso foi informado!");
		if(empresa.getTipo() != null) CompanyTypeEnum.validationValue(empresa.getTipo().name());
		
		empresa.setId(entity.getId());
		
		log.info("Atualiza a empresa no banco de dados!");
		return repository.save(empresa);
	}
	
	public void deleteEmpresa(Long id) {
		log.info("Verifica se a empresa com id: " + id + " existe registrado!");
		CompanyEntity entity= CheckConditions.checkElementNotPresent(repository.findById(id), "Empresa de Id: "+id+" não foi encontrado");
		
		log.info("Deletando uma empresa no banco com id: " + id + "!");
		repository.deleteById(entity.getId());
	}
}
