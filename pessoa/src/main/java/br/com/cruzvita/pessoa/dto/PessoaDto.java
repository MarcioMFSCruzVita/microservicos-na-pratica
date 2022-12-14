package br.com.cruzvita.pessoa.dto;

import br.com.cruzvita.pessoa.model.Status;
import lombok.Data;

@Data
public class PessoaDto {

	
	private String nome;
	
	private String cpf;
	
	private String dataNascimento;
	
	private Status status;
	
	private StatusReq statusReq;
}
