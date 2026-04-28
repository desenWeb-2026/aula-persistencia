package com.example.persistencia_lab.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.persistencia_lab.exceptions.BancoDeDadosException;
import com.example.persistencia_lab.infrastructure.ConexaoFactory;
import com.example.persistencia_lab.models.Professor;

public class ProfessorRepository {

    private Connection conexao;

    public ProfessorRepository(){
        conexao = ConexaoFactory.getConexao();
    }

    public List<Professor> getProfessores(){
        
        List<Professor> professores = new ArrayList<>();
        
        Statement consulta = null;
        ResultSet resultado = null;

        try {
            
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery("SELECT * FROM professores");

            //Iterar nossa tabela de resultado
            while (resultado.next()) {

                Professor professor = resultadoToProfessor(resultado);
                professores.add(professor);
                
            }

        } catch(SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        }

        return professores;

    }

    public Professor resultadoToProfessor(ResultSet resultado) throws SQLException{

        Professor professor = new Professor();
        professor.setId(resultado.getInt("professor_id"));
        professor.setNome(resultado.getString("nome"));
        professor.setEmail(resultado.getString("email"));
        professor.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
        professor.setSalarioBase(resultado.getDouble("salario_base"));

        return professor;
    }

}
