package com.api.gerenciamentonotasfiscais.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.gerenciamentonotasfiscais.dto.CompanyDTO;
import com.api.gerenciamentonotasfiscais.helper.CompanyHelper;
import com.api.gerenciamentonotasfiscais.service.CompanyService;



@RestController
@RequestMapping("/companies")
public class CompanyController {

	private final CompanyService service;
	private final CompanyHelper helper;
	
	public CompanyController(CompanyService service, CompanyHelper helper) {
		this.service=service;
		this.helper=helper;
	}
	
	@PostMapping
	public ResponseEntity<CompanyDTO> addEmpresa(@Valid @RequestBody CompanyDTO companyDTO,
			BindingResult result){
		
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(companyDTO);
		}
		
		CompanyDTO company = helper.toModel(service.addEmpresa(helper.toEntity(companyDTO)));
		URI uri = getUri(company.getId());
		
		return ResponseEntity.created(uri).body(company);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CompanyDTO> updateEmpresa(@Valid @RequestBody CompanyDTO companyDTO,
			@PathVariable("id") Long id,
			BindingResult result){
		
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(companyDTO);
		}
		
		CompanyDTO company = helper.toModel(service.updateEmpresa(id,helper.toEntity(companyDTO)));
		
		return ResponseEntity.ok(company);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmpresa(@PathVariable("id") Long id){
		
		service.deleteEmpresa(id);
		
		return ResponseEntity.noContent().build();
	}
	
	private URI getUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}
}
