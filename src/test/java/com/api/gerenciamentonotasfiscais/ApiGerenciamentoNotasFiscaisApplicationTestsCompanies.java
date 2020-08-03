package com.api.gerenciamentonotasfiscais;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import com.api.gerenciamentonotasfiscais.dto.CompanyDTO;
import com.api.gerenciamentonotasfiscais.enums.CompanyTypeEnum;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest(classes = ApiGerenciamentoNotasFiscaisApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiGerenciamentoNotasFiscaisApplicationTestsCompanies {

	@Autowired
	protected TestRestTemplate rest;
	
	private final Gson gson = new Gson();
	
	@Test
	void gerenciamentoEmpresaRest() {
		
		// POST
		log.info("Cria nova empresa");
		CompanyDTO company = new CompanyDTO();
		company.setRazaoSocial("Industria Ferragem");
		
		log.info("Tenta salvar uma empresa sem cnpj");
		ResponseEntity<CompanyDTO> response = rest.postForEntity("/companies", company,CompanyDTO.class);
		
		log.info("Valida o status se não é válido como esperado sem cnpj");
		assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
		
		
		
		log.info("Insere um CNPJ inválido para cadastrar a empresa");
		company.setCnpj("832783278");
		
		response = rest.postForEntity("/companies", company,CompanyDTO.class);
		
		log.info("Valida o status se não é válido como esperado o CNPJ inserido estar inválido");
		assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
		
		
		
		log.info("Insere um CNPJ válido");
		company.setCnpj("12.441.330/0001-00");
		
		log.info("Salva uma empresa com CNPJ válido");
		response = rest.postForEntity("/companies", company,CompanyDTO.class);
		
		log.info("Valida o status de criado com CNPJ válido");
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
		
		
		
		log.info("Cria outra empresa com mesmo CNPJ da anterior");
		CompanyDTO company2 = new CompanyDTO();
		company2.setRazaoSocial("Comércio Eletrônico S.A");
		company2.setCnpj("12.441.330/0001-00");
		
		log.info("Tenta salvar outra empresa com memso CNPJ");
		ResponseEntity<CompanyDTO> response2 = rest.postForEntity("/companies", company,CompanyDTO.class);
		
		log.info("Valida o status se não é válido como esperado tendo a segunda empresa com"
				+ " memso CNPJ sendo cadastrado");
		assertEquals(HttpStatus.BAD_REQUEST,response2.getStatusCode());
		
		
		
		
		log.info("Recebe o objeto da primeira empresa cadastrada a partir do corpo "
				+ "da resposta e verifica se não é nulo!");
		CompanyDTO comp = response.getBody();
		assertNotNull(comp);
		
		log.info("Retorno corpo: "+gson.toJson(response.getBody()));
		
		
		log.info("Recebe o location retornado do cabeçalho depois de criado o objeto!");
		String location = response.getHeaders().get("location").get(0);
		
		log.info("Valida o location caso existe e depois envia para atualizar a referente"
				+ " empresa que foi criada primeiro!");
		assertNotNull(location);
		
		
		
		// PUT
		log.info("Atualiza a Razao Social da empresa e insere o tipo criado");
		company.setRazaoSocial("Industrial de aço para ferragens!");
		company.setTipo(CompanyTypeEnum.TOMADOR);
		
		log.info("Salva a modificação para atualizar");
		ResponseEntity<CompanyDTO> response3 = rest.exchange(location, HttpMethod.PUT,
				new HttpEntity<>(company), CompanyDTO.class);
		
		log.info("Valida o status com o tipo válido da empresa ao atualizar");
		assertEquals(HttpStatus.OK,response3.getStatusCode());
		
		
		log.info("Recebe o objeto da empresa atualizada a partir do corpo "
				+ "da resposta e verifica se não é nulo!");
		CompanyDTO comp2 = response3.getBody();
		assertNotNull(comp2);
		
		
		
		
		// Delete
		log.info("Envia o location para deletar o objeto da empresa que foi atualizado");
		log.info("Deleta a empresa referente pelo id contido no location da url");
		ResponseEntity<Void> response4 = rest.exchange(location, HttpMethod.DELETE,
				null, Void.class);
		
		
		log.info("Valida se deletou a empresa se o status retornou 204, ou seja, sem contéudo e se deu certo!");
		assertEquals(HttpStatus.NO_CONTENT,response4.getStatusCode());
		
	}

}
