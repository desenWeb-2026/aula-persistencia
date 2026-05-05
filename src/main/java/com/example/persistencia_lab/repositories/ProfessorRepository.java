package com.example.persistencia_lab.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.persistencia_lab.exceptions.BancoDeDadosException;
import com.example.persistencia_lab.infrastructure.ConexaoFactory;
import com.example.persistencia_lab.models.Curso;
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
            resultado = consulta.executeQuery(
                "SELECT p.*, c.nome as curso_nome " 
                + "FROM professores as p "
                + "JOIN cursos as c " 
                + "ON \tp.curso_id = c.curso_id");


            Map<Integer, Curso> cursosMap = new HashMap<>();

            //Iterar nossa tabela de resultado
            while (resultado.next()) {

                Curso curso = cursosMap.get(resultado.getInt("curso_id"));

                if (curso == null) {
                    curso = new Curso();
                    curso.setCurso_id(resultado.getInt("curso_id"));
                    curso.setNome(resultado.getString("curso_nome"));

                    cursosMap.put(resultado.getInt("curso_id"), curso);
                }

                Professor professor = resultadoToProfessor(resultado, curso);
                professores.add(professor);
                
            }

        } catch(SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {

            ConexaoFactory.fecharStatement(consulta);
            ConexaoFactory.fecharResultSet(resultado);

        }

        return professores;

    }

    public Professor findProfessorById(Integer id){
                
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        Professor professor = null;

        try {
            
            String sql = "SELECT p.*, c.nome as curso_nome " 
                + "FROM professores as p "
                + "JOIN cursos as c " 
                + "ON p.curso_id = c.curso_id "
                + "WHERE p.professor_id = ?";

            consulta = conexao.prepareStatement(sql);

            consulta.setInt(1, id);

            resultado = consulta.executeQuery();


            //Iterar nossa tabela de resultado
            while (resultado.next()) {

                Curso curso = new Curso();
                curso.setCurso_id(resultado.getInt("curso_id"));
                curso.setNome(resultado.getString("curso_nome"));

                professor = resultadoToProfessor(resultado, curso);
            }

        } catch(SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {

            ConexaoFactory.fecharStatement(consulta);
            ConexaoFactory.fecharResultSet(resultado);

        }

        return professor;

    }

    public Professor inserir(Professor professor){

        PreparedStatement consulta = null;

        try {

            String sql  = "INSERT INTO professores (nome, email, data_nascimento, salario_base, curso_id) " +
            "VALUES(?, ?, ?, ?, ?)";

            consulta = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            consulta.setString(1, professor.getNome());
            consulta.setString(2, professor.getEmail());
            consulta.setDate(3, Date.valueOf(professor.getDataNascimento()));
            consulta.setDouble(4, professor.getSalarioBase());
            consulta.setInt(5, professor.getCurso().getCurso_id());

            int linhasAfetadas = consulta.executeUpdate();

            if (linhasAfetadas > 0) {

                System.out.println("Pronto! " + linhasAfetadas + " linhas afetadas!");

                ResultSet ids = consulta.getGeneratedKeys();

                ids.next();
                int id = ids.getInt(1);

                professor.setId(id);
            } else {
                System.out.println("Nenhum registro inserido!");
            }
            
        } catch (SQLException e) {
            throw new BancoDeDadosException("erro ao inserir: " + e.getMessage());
        } finally {
            ConexaoFactory.fecharStatement(consulta);
        }

        return professor;

    }

    public Professor resultadoToProfessor(ResultSet resultado, Curso curso) throws SQLException{

        Professor professor = new Professor();
        professor.setId(resultado.getInt("professor_id"));
        professor.setNome(resultado.getString("nome"));
        professor.setEmail(resultado.getString("email"));
        professor.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
        professor.setSalarioBase(resultado.getDouble("salario_base"));

        professor.setCurso(curso);

        return professor;
    }

}
