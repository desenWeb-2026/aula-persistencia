package com.example.persistencia_lab;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.persistencia_lab.infrastructure.ConexaoFactory;
import com.example.persistencia_lab.models.Professor;
import com.example.persistencia_lab.repositories.ProfessorRepository;

@SpringBootTest
class PersistenciaLabApplicationTests {

	@Test
	void databaseTest() {
		ConexaoFactory.getConexao();
	}

	@Test
	public void deveObterUmaListaDeProfesores(){

		ProfessorRepository repository = new ProfessorRepository();

		List<Professor> professores = repository.getProfessores();

		for (Professor professor : professores) {
			System.out.println(professor);
		}

	} 

}
