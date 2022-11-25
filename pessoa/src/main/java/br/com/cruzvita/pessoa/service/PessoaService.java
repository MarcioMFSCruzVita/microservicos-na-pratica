package br.com.cruzvita.pessoa.service;


import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cruzvita.pessoa.dto.PessoaDto;
import br.com.cruzvita.pessoa.dto.StatusReq;
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
		
		
		Optional<Pessoa> buscaPessoa = repository.findByCpf(dto.getCpf());
		
		if (buscaPessoa.isEmpty()) {
			Pessoa pessoa = modelMapper.map(dto, Pessoa.class);
			pessoa.setStatus(Status.ATIVO);
			dto.setStatus(Status.ATIVO);
			repository.save(pessoa);
			dto.setStatusReq(StatusReq.CRIADO);
			
			return dto;	
		}
		
		dto.setStatus(Status.NAO_CRIADO);
		dto.setStatusReq(StatusReq.JA_EXISTE);
		
		return dto;
		
	}
	
	public PessoaDto atualizarPessoa(String cpf, PessoaDto dto) {
		Pessoa pessoa = modelMapper.map(dto, Pessoa.class);
		pessoa.setCpf(cpf);
		pessoa = repository.save(pessoa);
		dto.setStatusReq(StatusReq.SUCESSO);
		return modelMapper.map(pessoa, PessoaDto.class);	
	}
	
	public void excluirPessoa(String cpf) {
		try {
			repository.deleteByCpf(cpf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
