package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

@RestController /*Define como Controller*/
@RequestMapping("/postagens") /*Endpoint*/
@CrossOrigin(origins = "*", allowedHeaders = "*") /*Libera as routes para serem acessadas por outras plataformas*/
public class PostagemController {
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){ /*Buscar postagens*/
		return ResponseEntity.ok(postagemRepository.findAll()); /*Traz os métodos*/
	}
	
	/*Comunicação com SQL*/
	
	/*Método GET - Buscando postagens pelo ID*/
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id){ /*Procurando a postagem por ID*/
		return postagemRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta)) /*Trazendo a postagem pelo ID*/
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		/*Lambda -> método dentro de outro método que não exige uma estrutura ou parâmetro, é um método mais resumido*/	
	} 
	
	/*Método Get - buscando postagens pelo título*/
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	/*O método de postagem é unico*/
	/*Método POST - Publicar novas postagens*/
	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){
		if(temaRepository.existsById(postagem.getTema().getId()))
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	/*Método PUT - editar postagens*/
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem){
		if (postagemRepository.existsById(postagem.getId())) {
			
			if (temaRepository.existsById(postagem.getTema().getId()))
				return ResponseEntity.status(HttpStatus.OK)
						.body(postagemRepository.save(postagem));
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	
	}
	
	/*Método Delete - Deleta as postagens*/
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Postagem> postagem = postagemRepository.findById(id);
		
		if(postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		postagemRepository.deleteById(id);
	}
		
}
