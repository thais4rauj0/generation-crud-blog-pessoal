package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController /*Define como Controller*/
@RequestMapping("/postagens") /*Endpoint*/
@CrossOrigin(origins = "*", allowedHeaders = "*") /*Libera as routes para serem acessadas por outras plataformas*/
public class PostagemController {
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){ /*Buscar postagens*/
		return ResponseEntity.ok(postagemRepository.findAll()); /*Traz os métodos*/
	}
	
	/*Comunicação com SQL*/
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id){ /*Procurando a postagem por ID*/
		return postagemRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta)) /*Trazendo a postagem pelo ID*/
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		/*Lambda -> método dentro de outro método que não exige uma estrutura ou parâmetro, é um método mais resumido*/	
	} 
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
}
