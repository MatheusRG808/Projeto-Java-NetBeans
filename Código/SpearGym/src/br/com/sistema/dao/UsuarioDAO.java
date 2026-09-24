package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario login(String usuario, String senha) {

        String sql = "SELECT id, usuario, senha FROM usuarios WHERE email = ? AND senha = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                Usuario u = new Usuario();
                u.setId(result.getInt("id"));
                u.setUsuario(result.getString("email"));
                u.setSenha(result.getString("senha"));
                return u;
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
        return null;
    }
    
    public void salvar(Usuario usuario) {
        String sql = "INSERT INTO usuarios " + "(usuario, senha) " + "VALUES (?, ?)";

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getSenha());

            stmt.executeUpdate();

        } catch (SQLException erro) {
            System.out.println("Erro ao salvar usuario: " + erro.getMessage());
        }
    } 
}
