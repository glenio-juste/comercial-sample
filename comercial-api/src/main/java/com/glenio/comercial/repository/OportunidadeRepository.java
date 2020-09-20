package com.glenio.comercial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glenio.comercial.model.Oportunidade;

public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long>{
	
	Optional<Oportunidade> findByDescricaoAndNomeProspecto(String nome, String nomeProspecto);

}
