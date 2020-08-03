package com.api.gerenciamentonotasfiscais.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.gerenciamentonotasfiscais.dto.InvoiceDTO;
import com.api.gerenciamentonotasfiscais.helper.InvoiceHelper;
import com.api.gerenciamentonotasfiscais.service.InvoiceService;


@RestController
@RequestMapping("/invoices")
public class InvoiceController {

	private final InvoiceService service;
	private final InvoiceHelper helper;
	
	public InvoiceController(InvoiceService service, InvoiceHelper helper) {
		this.service=service;
		this.helper=helper;
	}
	
	@GetMapping("/{empresaCNPJ}")
	public ResponseEntity<List<InvoiceDTO>> findAllNotasFiscaisByEmpresaCNPJ(@PathVariable("empresaCNPJ") String empresaCNPJ){
		
		List<InvoiceDTO> invoices = helper.toModel(service.findAllNotasFiscaisByEmpresaCNPJ(empresaCNPJ));
		
		return ResponseEntity.ok(invoices);
	}
	
	@PostMapping
	public ResponseEntity<InvoiceDTO> addNotafiscal(@Valid @RequestBody InvoiceDTO invoiceDTO,
			BindingResult result){
		
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(invoiceDTO);
		}
		
		InvoiceDTO invoice = helper.toModel(service.addNotafiscal(helper.toEntity(invoiceDTO)));
		URI uri = getUri(invoice.getId());
		
		return ResponseEntity.created(uri).body(invoice);
	}
	
	private URI getUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}
}
