package com.example.persistencia_lab.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.example.persistencia_lab.exceptions.BancoDeDadosException;

public class ConexaoFactory {

    private static Connection conexao = null;

    public static Connection getConexao(){

        String database = "ifpr_cursos";
        String usuario  = "root";
        String senha    = "bancodedados";

        if (conexao == null) {

            try {
        
                return DriverManager.getConnection("jdbc:mysql://localhost/" + database, usuario, senha);
            
            } catch(SQLException e) {
                throw new BancoDeDadosException(e.getMessage());
            }
            
        } else {
            return conexao;
        }
    }
}
