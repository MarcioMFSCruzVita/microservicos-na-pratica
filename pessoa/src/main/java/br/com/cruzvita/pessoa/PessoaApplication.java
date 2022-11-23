package br.com.cruzvita.pessoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PessoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PessoaApplication.class, args);
	}

}
