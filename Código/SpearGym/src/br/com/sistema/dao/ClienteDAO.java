package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.Cliente;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // =========================================================
    // SALVAR
    // =========================================================
    
    public void salvar(Cliente cliente) {

        if (cliente.getId() == 0) {
            inserir(cliente);
        } else {
            atualizar(cliente);
        }
    }

    // =========================================================
    // INSERIR
    // =========================================================

    private void inserir(Cliente cliente) {

        String sql = "INSERT INTO clientes "
                + "(nome, cpf, data_nascimento, telefone, email, endereco) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());

            if (cliente.getDataNascimento() != null) {
                stmt.setDate(
                        3,
                        Date.valueOf(cliente.getDataNascimento())
                );
            } else {
                stmt.setNull(
                        3,
                        java.sql.Types.DATE
                );
            }

            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getEndereco());

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch (SQLException erro) {

            System.out.println(
                    "Erro ao inserir cliente: "
                    + erro.getMessage()
            );
        }
    }

    // =========================================================
    // ATUALIZAR
    // =========================================================

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

            if (cliente.getDataNascimento() != null) {
                stmt.setDate(
                        3,
                        Date.valueOf(cliente.getDataNascimento())
                );
            } else {
                stmt.setNull(
                        3,
                        java.sql.Types.DATE
                );
            }

            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getEndereco());

            stmt.setInt(
                    7,
                    cliente.getId()
            );

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch (SQLException erro) {

            System.out.println(
                    "Erro ao atualizar cliente: "
                    + erro.getMessage()
            );
        }
    }

    // =========================================================
    // PESQUISAR POR NOME OU CPF
    // =========================================================

    public List<Cliente> pesquisarPorNome(String nome) {

        List<Cliente> clientes =
                new ArrayList<>();

        String sql =
                "SELECT * FROM clientes "
                + "WHERE LOWER(nome) LIKE LOWER(?) "
                + "OR cpf LIKE ? "
                + "ORDER BY nome";

        try {
            Connection con =
                    ConnectionFactory.getConnection();

            PreparedStatement stmt =
                    con.prepareStatement(sql);

            stmt.setString(
                    1,
                    "%" + nome + "%"
            );

            stmt.setString(
                    2,
                    "%" + nome + "%"
            );

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                Cliente cliente =
                        criarCliente(rs);

                clientes.add(cliente);
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException erro) {

            System.out.println(
                    "Erro ao pesquisar cliente: "
                    + erro.getMessage()
            );
        }

        return clientes;
    }

    // =========================================================
    // LISTAR TODOS
    // =========================================================

    public List<Cliente> listar() {

        List<Cliente> clientes =
                new ArrayList<>();

        String sql =
                "SELECT * FROM clientes "
                + "ORDER BY id";

        try {
            Connection con =
                    ConnectionFactory.getConnection();

            PreparedStatement stmt =
                    con.prepareStatement(sql);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                Cliente cliente =
                        criarCliente(rs);

                clientes.add(cliente);
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException erro) {

            System.out.println(
                    "Erro ao listar clientes: "
                    + erro.getMessage()
            );
        }

        return clientes;
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    public Cliente buscarPorId(int id) {

        String sql =
                "SELECT * FROM clientes "
                + "WHERE id = ?";

        try {
            Connection con =
                    ConnectionFactory.getConnection();

            PreparedStatement stmt =
                    con.prepareStatement(sql);

            stmt.setInt(
                    1,
                    id
            );

            ResultSet rs =
                    stmt.executeQuery();

            if (rs.next()) {

                Cliente cliente =
                        criarCliente(rs);

                rs.close();
                stmt.close();
                con.close();

                return cliente;
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException erro) {

            System.out.println(
                    "Erro ao buscar cliente: "
                    + erro.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // EXCLUIR POR ID
    // =========================================================

    public void excluir(int id) {

        String sql =
                "DELETE FROM clientes "
                + "WHERE id = ?";

        try {
            Connection con =
                    ConnectionFactory.getConnection();

            PreparedStatement stmt =
                    con.prepareStatement(sql);

            stmt.setInt(
                    1,
                    id
            );

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch (SQLException erro) {

            System.out.println(
                    "Erro ao excluir cliente: "
                    + erro.getMessage()
            );
        }
    }

    public void excluirPorCpf(String cpf) {
        String sql =
                "DELETE FROM clientes "
                + "WHERE cpf = ?";

        try {
            Connection con =
                    ConnectionFactory.getConnection();

            PreparedStatement stmt =
                    con.prepareStatement(sql);

            stmt.setString(
                    1,
                    cpf
            );

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch (SQLException erro) {

            System.out.println(
                    "Erro ao excluir cliente: "
                    + erro.getMessage()
            );
        }
    }
    
    // =========================================================
    // CRIAR OBJETO CLIENTE A PARTIR DO RESULTSET
    // =========================================================

    private Cliente criarCliente(ResultSet rs)
            throws SQLException {

        Cliente cliente =
                new Cliente();

        cliente.setId(
                rs.getInt("id")
        );

        cliente.setNome(
                rs.getString("nome")
        );

        cliente.setCpf(
                rs.getString("cpf")
        );

        Date dataNascimento =
                rs.getDate("data_nascimento");

        if (dataNascimento != null) {

            cliente.setDataNascimento(
                    dataNascimento.toLocalDate().toString()
            );
        }

        cliente.setTelefone(
                rs.getString("telefone")
        );

        cliente.setEmail(
                rs.getString("email")
        );

        cliente.setEndereco(
                rs.getString("endereco")
        );

        return cliente;
    }
}