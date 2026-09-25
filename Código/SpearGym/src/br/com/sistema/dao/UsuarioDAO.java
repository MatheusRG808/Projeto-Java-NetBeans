package br.com.sistema.dao;

import br.com.sistema.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UsuarioDAO {
    private Connection conexao;

    public UsuarioDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    // 1. SALVAR / CADASTRAR
    public void cadastrarUsuario(Usuario obj) {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getEmail());
            stmt.setString(3, obj.getSenha());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    // 2. EDITAR / ALTERAR
    public void editarUsuario(Usuario obj) {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getEmail());
            stmt.setString(3, obj.getSenha());
            stmt.setInt(4, obj.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar usuário: " + e.getMessage());
        }
    }

    // 3. EXCLUIR
    public void excluirUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuário: " + e.getMessage());
        }
    }

    public Usuario efetuarLogin(String usuario, String senha) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}