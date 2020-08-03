package com.api.gerenciamentonotasfiscais;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import com.api.gerenciamentonotasfiscais.dto.*;
import com.api.gerenciamentonotasfiscais.entity.CompanyEntity;
import com.api.gerenciamentonotasfiscais.entity.InvoiceEntity;
import com.api.gerenciamentonotasfiscais.enums.CompanyTypeEnum;
import com.api.gerenciamentonotasfiscais.service.InvoiceService;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@SpringBootTest(classes = ApiGerenciamentoNotasFiscaisApplication.class, 
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiGerenciamentoNotasFiscaisApplicationTestsInvoices {

	@Autowired
	protected TestRestTemplate rest;
	
	@Autowired
	private InvoiceService service;

	private final Gson gson = new Gson();
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Test
	void gerenciamentoAddInvoiceRest() {

		// POST
		log.info("Cria nova nota fiscal e também as empresas tomadora e prestadora");
		InvoiceDTO invoice = new InvoiceDTO();
		CompanyDTO compT = new CompanyDTO();
		CompanyDTO compP = new CompanyDTO();
		

		log.info("Dados da empresa tomadora");
		compT.setCnpj("12.441.330/0001-00");
		compT.setRazaoSocial("Atacado e Varejo de Produtos R.T");
		compT.setTipo(CompanyTypeEnum.TOMADOR);
		

		log.info("Dados da empresa prestadora");
		compP.setCnpj("54.813.866/0001-40");
		compP.setRazaoSocial("Eletrônicos e Informática M.S");
		

		log.info("Dados da nota fiscal com as empresas tomadora e prestadora");
		invoice.setNumero(2345L);
		
		try {
			invoice.setDataNota(sdf.parse("03/08/2020"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		invoice.setValor(230.70);
		invoice.setEmpresaTomador(compT);
		invoice.setEmpresaPrestador(compP);
		

		
		log.info("Tenta salvar uma nota fiscal contendo só uma empresa com tipo declarado");
		ResponseEntity<InvoiceDTO> response = rest.postForEntity("/invoices", invoice, InvoiceDTO.class);
			
		log.info("Valida o status retornado");
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		

		log.info("Insere um tipo para empresa prestadora, porém atribuindo o mesmo tipo para empresa de tomadora");
		compP.setTipo(CompanyTypeEnum.TOMADOR);

		log.info("Insere novamente a empresa prestadora na nota, contendo o mesmo tipo da tomadora");
		invoice.setEmpresaPrestador(compP);
		
		
		
		log.info("Tenta salvar novamente a nota fiscal");
		 response = rest.postForEntity("/invoices", invoice, InvoiceDTO.class);
			
		 log.info("Valida o status retornado");
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		
		

		log.info("Atualiza o tipo para empresa prestadora contendo certo o valor do tipo");
		compP.setTipo(CompanyTypeEnum.PRESTADOR);

		log.info("Insere novamente a empresa prestadora na nota com tipo atualizado");
		invoice.setEmpresaPrestador(compP);

		log.info("Salva novamente a nota fiscal agora com tudo certo");
		response = rest.postForEntity("/invoices", invoice, InvoiceDTO.class);

		log.info("Valida o status de criado com as duas empresas correspondidas certas");
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		log.info("Retorno corpo: " + gson.toJson(response.getBody()));
		
		
		

		log.info("Cria outra nota fiscal e atribuindo o mesmo numero da nota anterior");
		InvoiceDTO invoice2 = new InvoiceDTO();
		invoice2.setNumero(2345L);
		
		try {
			invoice2.setDataNota(sdf.parse("04/08/2020"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		invoice2.setValor(230.70);
		invoice2.setEmpresaTomador(compT);
		invoice2.setEmpresaPrestador(compP);

		
		log.info("Tenta salvar uma nota fiscal contendo o mesmo número da nota anterior");
		ResponseEntity<InvoiceDTO> response2 = rest.postForEntity("/invoices", invoice2, InvoiceDTO.class);

		log.info("Valida o status retornado");
		assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
	}

	@Test
	void gerenciamentoAddAndGetInvoiceService() {

		// Post
		log.info("Cria mais notas");
		InvoiceEntity invoice1 = new InvoiceEntity();
		InvoiceEntity invoice2 = new InvoiceEntity();
		InvoiceEntity invoice3 = new InvoiceEntity();

		log.info("Cria mais empresas para as notas");
		CompanyEntity compT = new CompanyEntity();
		CompanyEntity compP = new CompanyEntity();
		CompanyEntity compT2 = new CompanyEntity();
		CompanyEntity compP2 = new CompanyEntity();
		CompanyEntity compT3 = new CompanyEntity();
		CompanyEntity compP3 = new CompanyEntity();

		log.info("Insere os valores para as empresas tomadoras e prestadoras");
		compT.setCnpj("85.442.126/0001-79");
		compT.setRazaoSocial("Industria Ferragem");
		compT.setTipo(CompanyTypeEnum.TOMADOR);

		compP.setCnpj("77.055.309/0001-03");
		compP.setRazaoSocial("Ferragens a aço");
		compP.setTipo(CompanyTypeEnum.PRESTADOR);

		compT2.setCnpj("09.632.947/0001-90");
		compT2.setRazaoSocial("Industria Cimento");
		compT2.setTipo(CompanyTypeEnum.TOMADOR);

		compP2.setCnpj("06.203.399/0001-94");
		compP2.setRazaoSocial("Massa e Tijolos");
		compP2.setTipo(CompanyTypeEnum.PRESTADOR);

		compT3.setCnpj("75.866.400/0001-83");
		compT3.setRazaoSocial("Informática A.C");
		compT3.setTipo(CompanyTypeEnum.TOMADOR);

		compP3.setCnpj("82.587.433/0001-50");
		compP3.setRazaoSocial("Administração Ferroviaria");
		compP3.setTipo(CompanyTypeEnum.PRESTADOR);


		log.info("Insere os valores para as notas atribuindo as empresas");
		invoice1.setNumero(11343L);
		
		try {
			invoice1.setDataNota(sdf.parse("05/07/2020"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		invoice1.setValor(290.15);
		invoice1.setEmpresaTomador(compT);
		invoice1.setEmpresaPrestador(compP);

		
		invoice2.setNumero(4432L);
		
		try {
			invoice2.setDataNota(sdf.parse("15/07/2020"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		invoice2.setValor(520.70);
		invoice2.setEmpresaTomador(compT2);
		invoice2.setEmpresaPrestador(compP2);

		
		invoice3.setNumero(111L);
		
		try {
			invoice3.setDataNota(sdf.parse("20/07/2020"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		invoice3.setValor(100.00);
		invoice3.setEmpresaTomador(compT3);
		invoice3.setEmpresaPrestador(compP3);
		

		
		
		log.info("Salva as notas ficais referentes com as empresas definidas");
		InvoiceEntity nota1 = service.addNotafiscal(invoice1);
		
		InvoiceEntity nota2 = service.addNotafiscal(invoice2);
		
		InvoiceEntity nota3 = service.addNotafiscal(invoice3);
		
		
		log.info("Valida a nota fiscal criada pelo retorno contendo o Id");
		assertNotNull(nota1.getId());
		
		assertNotNull(nota2.getId());
		
		assertNotNull(nota3.getId());

		
		
		
		
		// GET 
		log.info("Realiza a busca das notas referente a um CNPJ de uma empresa que não existe cadastrado");
		List<InvoiceEntity> list = service.findAllNotasFiscaisByEmpresaCNPJ("87.184.376/0001-81");
		
		
		log.info("Retorna a lista das notas ficais e valida se lista contém tamanho 0");
		assertEquals(0,list.size());
		
		
		
		
		log.info("Realiza a busca das notas referente a um CNPJ de uma empresa que contém cadastrado");
		list = service.findAllNotasFiscaisByEmpresaCNPJ(compP3.getCnpj());
		 
		log.info("Retorna a lista de notas ficais e valida se lista contém tamanho 1");
		assertEquals(1,list.size());
		 
		 
		
		log.info("Retorna a lista");
		log.info(gson.toJson(list));
	}
}
