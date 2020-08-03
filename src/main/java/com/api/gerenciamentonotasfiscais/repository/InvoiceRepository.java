package com.api.gerenciamentonotasfiscais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.gerenciamentonotasfiscais.entity.InvoiceEntity;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity,Long>{

	@Query(value = "select i from InvoiceEntity i where i.empresaTomador.cnpj=?1 or i.empresaPrestador.cnpj=?1")
	List<InvoiceEntity> findAllInvoicesByCompanyCNPJ(String companyCNPJ);
	
	InvoiceEntity findByNumero(Long numero);
}
