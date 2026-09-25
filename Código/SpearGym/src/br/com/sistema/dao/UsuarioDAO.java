package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    private Connection conexao;

    public UsuarioDAO() {
        this.conexao = ConnectionFactory.getConnection();
    }

    // =========================================================
    // CADASTRAR
    // =========================================================

    public void cadastrarUsuario(Usuario obj) {

        String sql =
                "INSERT INTO usuarios (usuario, senha) "
                + "VALUES (?, ?)";

        try (PreparedStatement stmt =
                conexao.prepareStatement(sql)) {

            stmt.setString(1, obj.getUsuario());
            stmt.setString(2, obj.getSenha());

            stmt.execute();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao salvar usuário: "
                    + e.getMessage()
            );
        }
    }

    // =========================================================
    // LOGIN
    // =========================================================

    public Usuario efetuarLogin(String usuario, String senha) {
        String sql = "SELECT * FROM usuarios "
                   + "WHERE usuario = ? AND senha = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    Usuario obj = new Usuario();
                    obj.setId(rs.getInt("id"));
                    obj.setUsuario(rs.getString("usuario"));
                    obj.setSenha(rs.getString("senha"));
                    obj.setAtivo(rs.getBoolean("ativo"));

                    return obj;
                }
            }

        } catch (SQLException erro) {

            System.out.println(
                "Erro ao efetuar login: " + erro.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // EXCLUIR
    // =========================================================

    public void excluirUsuario(int id) {

        String sql =
                "DELETE FROM usuarios "
                + "WHERE id = ?";

        try (PreparedStatement stmt =
                conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.execute();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao excluir usuário: "
                    + e.getMessage()
            );
        }
    }
}