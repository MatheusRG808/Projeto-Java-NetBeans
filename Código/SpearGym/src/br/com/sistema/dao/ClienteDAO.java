package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void salvar(Cliente cliente) {

        String sql = "INSERT INTO clientes "
                + "(nome, cpf, data_nascimento, telefone, email, endereco) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getEndereco());

            stmt.executeUpdate();

        } catch (SQLException erro) {
            System.out.println("Erro ao salvar cliente: "
                    + erro.getMessage());
        }
    }

    public List<Cliente> pesquisarPorNome(String nome) {

        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM clientes "
                + "WHERE LOWER(nome) LIKE LOWER(?) "
                + "OR cpf LIKE ? "
                + "ORDER BY nome";

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, "%" + nome + "%");
            stmt.setString(2, "%" + nome + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Cliente cliente = new Cliente();

                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setDataNascimento(
                        rs.getDate("data_nascimento").toLocalDate()
                );
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                cliente.setEndereco(rs.getString("endereco"));

                Timestamp timestamp = rs.getTimestamp("criado_em");

                if (timestamp != null) {
                    cliente.setCriadoEm(timestamp.toLocalDateTime());
                }

                clientes.add(cliente);
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao pesquisar cliente: "
                    + erro.getMessage());
        }

        return clientes;
    }

    public List<Cliente> listar() {

        String sql = "SELECT * FROM clientes ORDER BY id";

        List<Cliente> clientes = new ArrayList<>();

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Cliente cliente = new Cliente();

                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setDataNascimento(
                        rs.getDate("data_nascimento").toLocalDate()
                );
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                cliente.setEndereco(rs.getString("endereco"));

                Timestamp timestamp = rs.getTimestamp("criado_em");

                if (timestamp != null) {
                    cliente.setCriadoEm(timestamp.toLocalDateTime());
                }

                clientes.add(cliente);
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao listar clientes: "
                    + erro.getMessage());
        }

        return clientes;
    }

    public void atualizar(Cliente cliente) {

        String sql = "UPDATE clientes SET "
                + "nome = ?, "
                + "cpf = ?, "
                + "data_nascimento = ?, "
                + "telefone = ?, "
                + "email = ?, "
                + "endereco = ? "
                + "WHERE id = ?";

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getEndereco());
            stmt.setInt(7, cliente.getId());

            stmt.executeUpdate();

        } catch (SQLException erro) {
            System.out.println("Erro ao atualizar cliente: "
                    + erro.getMessage());
        }
    }
    
    
    public void excluir(int id) {

        String sql = "DELETE FROM clientes WHERE id = ?";

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException erro) {
            System.out.println("Erro ao excluir cliente: "
                    + erro.getMessage());
        }
    }
}