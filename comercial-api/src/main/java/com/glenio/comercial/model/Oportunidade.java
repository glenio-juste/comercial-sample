package com.glenio.comercial.model;

/*
 * Aqui pode ser usado o Lombok para o uso do Getters e Setters	
 * 
 * O Lombok é um framework para Java que permite escrever código eliminando a verbosidade. 
 * Seu uso permite gerar em tempo de compilação os métodos getters e setters, 
 * métodos construtores, padrão builder e muito mais.
 * 
 */

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor     //cria automaticamente um construtor com todas os atributos da classe como argumento.
@NoArgsConstructor     // cria automaticamente um construtor vazio (sem argumentos).
@Data                 //cria automaticamente os métodos toString, equals, hashCode, getters e setters.
@Entity
public class Oportunidade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(max = 80)
	@Column(name = "nome_prospecto")
	private String nomeProspecto;
	
	@NotEmpty
	@Size(max = 200)
	private String descricao;
	
	@Min(0)
	private BigDecimal valor;

}
