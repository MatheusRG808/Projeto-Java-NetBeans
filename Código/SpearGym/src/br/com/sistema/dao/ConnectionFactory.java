package br.com.sistema.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    public Connection getConnection() {
        try {
            // Regista o driver do PostgreSQL
            Class.forName("org.postgresql.Driver");
            
            // Ligação à base de dados 'trabalho' do PostgreSQL
            return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/trabalho", 
                "postgres", 
                "mirellyxavier123" // <--- Substitua pela sua palavra-passe do PostgreSQL
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao ligar à base de dados: " + e.getMessage(), e);
        }
    }
}