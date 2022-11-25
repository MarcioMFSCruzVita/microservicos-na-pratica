package br.com.cruzvita.pessoa.service;


import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cruzvita.pessoa.dto.PessoaDto;
import br.com.cruzvita.pessoa.model.Pessoa;
import br.com.cruzvita.pessoa.model.Status;
import br.com.cruzvita.pessoa.repository.PessoaRepository;

@Service
public class PessoaService{

	@Autowired
	private PessoaRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Page<PessoaDto> obterTodos(Pageable paginacao){
		return repository
				.findAll(paginacao)
				.map(p -> modelMapper.map(p, PessoaDto.class));
	}
	
	public PessoaDto obterPorId(String cpf) {
		Pessoa pessoa = repository.findByCpf(cpf).
				orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(pessoa, PessoaDto.class);
	}
	
	public PessoaDto criarPessoa(PessoaDto dto) {
		Pessoa pessoa = modelMapper.map(dto, Pessoa.class);
		
		pessoa.setStatus(Status.ATIVO);
		repository.save(pessoa);
		
		return modelMapper.map(pessoa, PessoaDto.class);	
	}
	
	public PessoaDto atualizarPessoa(String cpf, PessoaDto dto) {
		Pessoa pessoa = modelMapper.map(dto, Pessoa.class);
		pessoa.setCpf(cpf);
		pessoa = repository.save(pessoa);		
		return modelMapper.map(pessoa, PessoaDto.class);	
	}
	
	public void excluirPessoa(String cpf) {
		repository.deleteByCpf(cpf);
	}
}
