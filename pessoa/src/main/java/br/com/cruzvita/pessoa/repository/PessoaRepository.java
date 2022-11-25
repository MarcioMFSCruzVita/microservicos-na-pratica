package br.com.cruzvita.pessoa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cruzvita.pessoa.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	public Page<Pessoa> findAll(Pageable paginacao);

	public Optional<Pessoa> findByCpf(String cpf);

	public void deleteByCpf(String cpf);

	public Pessoa getByCpf(String cpf);

}
