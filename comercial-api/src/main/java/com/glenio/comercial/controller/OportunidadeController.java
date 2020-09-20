package com.glenio.comercial.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.glenio.comercial.model.Oportunidade;
import com.glenio.comercial.repository.OportunidadeRepository;

@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {
	
	@Autowired
	private OportunidadeRepository oportunidades;
	
	@GetMapping
	public List<Oportunidade> listar() {
		return oportunidades.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Oportunidade> buscar(@PathVariable Long id) { // @PathVariable (/{id}) -> Placeholder - obrigatório passar ID
		
		Optional<Oportunidade> oportunidade = oportunidades.findById(id); // Busca pelo ID no repositorio JPA
		
		if(oportunidade.isEmpty()) { // verifica se é objeto inexistente
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oportunidade.get());
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)  // Irá retornar o HTTP 201(Created)
	public Oportunidade adicionar(@Valid @RequestBody Oportunidade oportunidade) {
/*
 * 	ATENÇÃO = O  @RequestBody desconfigura o teste de inserção no Postman
 *	se for inserir um campo vazio, o retorno é de UTF8 não suported (...)
 *	Para o teste, remover o  @RequestBody
 */
		Optional<Oportunidade> oportunidadeExistente = oportunidades.findByDescricaoAndNomeProspecto(
				oportunidade.getDescricao(), oportunidade.getNomeProspecto());
		
		if(oportunidadeExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Já existe uma oportunidade com este prospecto com a mesma descrição.");
		}
		
		return oportunidades.save(oportunidade);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {

		return oportunidades.findById(id)
				.map(record -> {
					oportunidades.deleteById(id);
					return ResponseEntity.ok().build();
				}).orElse(ResponseEntity.notFound().build());

	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Oportunidade> alterar(@PathVariable Long id, @RequestBody Oportunidade oportunidade) {
		
		return oportunidades.findById(id)
				.map(record -> {
			record.setNomeProspecto(oportunidade.getNomeProspecto());
			record.setDescricao(oportunidade.getDescricao());
			record.setValor(oportunidade.getValor());
			Oportunidade varTeste = oportunidades.save(record);
			return ResponseEntity.ok().body(varTeste);
		}).orElse(ResponseEntity.notFound().build());
	
	}
	

}
