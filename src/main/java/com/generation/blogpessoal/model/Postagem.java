package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity /*Transforma em entidade*/
@Table(name= "tb_postagens") /*Transforma em tabela e define o nome da tabela*/
public class Postagem {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY) /*Auto_Increment*/
private Long id;

@NotBlank(message = "O Atributo titulo é Obrigatório!") /*Not null*/
@Size(min=5, max=100, message= "O atributo texto deve conter no mínimo 05 e no maximo 100 caracteres") /*Controla o tamanho da variavel*/
private String titulo;

@NotBlank(message="O atributo texto é obrigatório!")
@Size(min=10, max=1000, message ="O texto deve conter no mínimo 10 e no máximo 1000 caracteres")
private String texto;

@UpdateTimestamp /*Mantém a data e o tempo atualizado*/
private LocalDateTime data;

@ManyToOne /*Um para muitos*/
@JsonIgnoreProperties("postagem") /*indica que uma parte do arquivo Json será ignorado - coloca o tema como subclasse de Postagem*/
private Tema tema;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getTitulo() {
	return titulo;
}

public void setTitulo(String titulo) {
	this.titulo = titulo;
}

public String getTexto() {
	return texto;
}

public void setTexto(String texto) {
	this.texto = texto;
}

public LocalDateTime getData() {
	return data;
}

public void setData(LocalDateTime data) {
	this.data = data;
}

public Tema getTema() {
	return tema;
}

public void setTema(Tema tema) {
	this.tema = tema;
}




}
