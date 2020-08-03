package com.api.gerenciamentonotasfiscais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.gerenciamentonotasfiscais.entity.CompanyEntity;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity,Long>{

	CompanyEntity findByCnpj(String cnpj);
}
