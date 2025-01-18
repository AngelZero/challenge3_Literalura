package com.angel.literalura;

import com.angel.literalura.principal.Principal;
import com.angel.literalura.repository.CAutorRepository;
import com.angel.literalura.repository.CIdiomaRepository;
import com.angel.literalura.repository.CLibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private CLibrosRepository repository;
	@Autowired
	private CAutorRepository autorRepository;
	@Autowired
	private CIdiomaRepository idiomaRepository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository, idiomaRepository, autorRepository );
		principal.mostrarMenu();


		
	}
}
