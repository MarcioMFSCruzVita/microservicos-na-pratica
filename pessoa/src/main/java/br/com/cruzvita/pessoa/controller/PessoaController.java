package br.com.cruzvita.pessoa.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cruzvita.pessoa.dto.PessoaDto;
import br.com.cruzvita.pessoa.service.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaService service;
	
	@GetMapping
	public Page<PessoaDto> listar(@PageableDefault(size = 10) Pageable paginacao){
		return service.obterTodos(paginacao);
	}
	
	@GetMapping("/{cpf}")
	public PessoaDto listarPorId(@PathVariable String cpf) {
		return service.obterPorId(cpf);
	}
	
	@PostMapping
	public ResponseEntity<PessoaDto> cadastrar(@RequestBody @Valid PessoaDto dto, UriComponentsBuilder uriBuilder){
		PessoaDto pessoa = service.criarPessoa(dto);
		URI endereco = uriBuilder.path("/pessoa/{cpf}").buildAndExpand(pessoa.getCpf()).toUri();
		
		return ResponseEntity.created(endereco).body(pessoa);
	}
	
	@PutMapping("/{cpf}")
	public ResponseEntity<PessoaDto> atualizarPessoa(@PathVariable @NotNull String cpf, @RequestBody @Valid PessoaDto dto){
		PessoaDto atualizar = service.atualizarPessoa(cpf, dto);
		
		return ResponseEntity.ok(atualizar);
	}
	
	@DeleteMapping("/{cpf}")
	public ResponseEntity<PessoaDto> deletarPessoa(@PathVariable @NotNull String cpf){
		service.excluirPessoa(cpf);
		return ResponseEntity.ok(null);
	}
	
	

}
