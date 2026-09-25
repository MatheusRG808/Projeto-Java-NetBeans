package br.com.sistema.dao;

import br.com.sistema.model.Cliente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO {
    private Connection conexao;

    public ClienteDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    // 1. SALVAR / CADASTRAR
    public void cadastrarCliente(Cliente obj) {
        String sql = "INSERT INTO clientes (nome, cpf, data_nascimento, telefone, email, endereco) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getCpf());
            
            // Converte a String (AAAA-MM-DD) para o tipo DATE do PostgreSQL
            if (obj.getDataNascimento() != null && !obj.getDataNascimento().trim().isEmpty()) {
                stmt.setDate(3, Date.valueOf(obj.getDataNascimento().trim()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }

            stmt.setString(4, obj.getTelefone());
            stmt.setString(5, obj.getEmail());
            stmt.setString(6, obj.getEndereco());

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar cliente: " + e.getMessage(), e);
        }
    }

    // 2. EDITAR / ALTERAR
    public void editarCliente(Cliente obj) {
        String sql = "UPDATE clientes SET nome = ?, data_nascimento = ?, telefone = ?, email = ?, endereco = ? WHERE cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            
            if (obj.getDataNascimento() != null && !obj.getDataNascimento().trim().isEmpty()) {
                stmt.setDate(2, Date.valueOf(obj.getDataNascimento().trim()));
            } else {
                stmt.setNull(2, java.sql.Types.DATE);
            }

            stmt.setString(3, obj.getTelefone());
            stmt.setString(4, obj.getEmail());
            stmt.setString(5, obj.getEndereco());
            stmt.setString(6, obj.getCpf());

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar cliente: " + e.getMessage(), e);
        }
    }

    // 3. EXCLUIR
    public void excluirCliente(String cpf) {
        String sql = "DELETE FROM clientes WHERE cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir cliente: " + e.getMessage(), e);
        }
    }
}