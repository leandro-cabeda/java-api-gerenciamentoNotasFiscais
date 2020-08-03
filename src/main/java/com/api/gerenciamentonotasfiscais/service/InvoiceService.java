package com.api.gerenciamentonotasfiscais.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.gerenciamentonotasfiscais.entity.InvoiceEntity;
import com.api.gerenciamentonotasfiscais.exception.validator.CheckConditions;
import com.api.gerenciamentonotasfiscais.repository.InvoiceRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InvoiceService {

	private final InvoiceRepository repository;
	
	public InvoiceService(InvoiceRepository repository) {
		this.repository=repository;
	}
	
	public InvoiceEntity addNotafiscal(InvoiceEntity notaFiscal) {
		log.info("Verifica se a nota fiscal do Numero: " + notaFiscal.getNumero() + " caso existe no banco registrado!");
		CheckConditions.checkElementExists(repository.findByNumero(notaFiscal.getNumero()), 
				"Esta nota fiscal do Numero: "+notaFiscal.getNumero()+" já existe cadastrado!");
		
		log.info("Verifica se a empresa tomadora contém cnpj e tipo registrado para cadastrar a nota!");
		CheckConditions.checkElementNotNull(notaFiscal.getEmpresaTomador().getTipo(), 
				"O tipo referente a empresa é nulo e não pode ser registrado a nota se o tipo da empresa não contém valor!");
		
		log.info("Verifica se a empresa prestadora contém cnpj e tipo registrado para cadastrar a nota!");
		CheckConditions.checkElementNotNull(notaFiscal.getEmpresaPrestador().getTipo(),
				"O tipo referente a empresa é nulo e não pode ser registrado a nota se o tipo da empresa não contém valor!");
		
		log.info("Realiza a verificação se a empresa tomadora contém mesmo tipo da empresa prestadora ou vice-versa durante o cadastro!");
		CheckConditions.checkElementEqualExist(notaFiscal.getEmpresaTomador().getTipo(), 
				notaFiscal.getEmpresaPrestador().getTipo(), 
				"A empresa tomadora e a empresa prestadora são ambas do mesmo tipo e não pode cadastrar valores iguais perante a nota!");
		
		
		log.info("Salva a nota fiscal no banco de dados!");
		return repository.save(notaFiscal);
	}
	
	public List<InvoiceEntity> findAllNotasFiscaisByEmpresaCNPJ(String empresaCNPJ){
		log.info("Busca as notais fiscais pela empresa referente!");
		return repository.findAllInvoicesByCompanyCNPJ(empresaCNPJ);
	}
}
