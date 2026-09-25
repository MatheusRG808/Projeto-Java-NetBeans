package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.FichaTreino;
import br.com.sistema.utils.TreinoJsonUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FichaTreinoDAO {

    public void salvar(int clienteId, FichaTreino ficha) {

        String json = TreinoJsonUtil.paraJson(ficha.getTreino());

        if (existePorCliente(clienteId)) {
            atualizar(clienteId, json);
        } else {
            inserir(clienteId, json);
        }
    }

    private void inserir(int clienteId, String json) {

        String sql = "INSERT INTO fichas_treino " + "(cliente_id, treino) " + "VALUES (?, ?::jsonb)";

        try {
            Connection con = ConnectionFactory.getConnection();

            PreparedStatement stmt =
                    con.prepareStatement(sql);

            stmt.setInt(1, clienteId);
            stmt.setString(2, json);

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch (SQLException erro) {

            System.out.println(
                    "Erro ao inserir ficha de treino: "
                    + erro.getMessage()
            );
        }
    }

    private void atualizar(int clienteId, String json) {

        String sql = "UPDATE fichas_treino "
                + "SET treino = ?::jsonb, "
                + "atualizado_em = NOW() "
                + "WHERE cliente_id = ?";

        try {
            Connection con = ConnectionFactory.getConnection();

            PreparedStatement stmt =
                    con.prepareStatement(sql);

            stmt.setString(1, json);
            stmt.setInt(2, clienteId);

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch (SQLException erro) {

            System.out.println(
                    "Erro ao atualizar ficha de treino: "
                    + erro.getMessage()
            );
        }
    }

    public boolean existePorCliente(int clienteId) {

        String sql = "SELECT 1 " + "FROM fichas_treino " + "WHERE cliente_id = ?";

        try {
            Connection con = ConnectionFactory.getConnection();

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, clienteId);

            ResultSet rs = stmt.executeQuery();

            boolean existe = rs.next();

            rs.close();
            stmt.close();
            con.close();

            return existe;

        } catch (SQLException erro) {
            System.out.println("Erro ao verificar ficha de treino: " + erro.getMessage());
            return false;
        }
    }

    /**
     * Busca a ficha de treino do cliente (usada para abrir a tela já
     * preenchida, no modo de edição).
     */
    public FichaTreino buscarPorCliente(int clienteId) {

        String sql = "SELECT treino " + "FROM fichas_treino " + "WHERE cliente_id = ?";

        try {
            Connection con = ConnectionFactory.getConnection();

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, clienteId);

            ResultSet rs = stmt.executeQuery();

            FichaTreino ficha = null;

            if (rs.next()) {
                ficha = new FichaTreino();
                ficha.setTreino(TreinoJsonUtil.deJson(rs.getString("treino")));
            }

            rs.close();
            stmt.close();
            con.close();

            return ficha;

        } catch (SQLException erro) {
            System.out.println("Erro ao buscar ficha de treino: " + erro.getMessage());
            return null;
        }
    }

    /**
     * Exclui a ficha de treino do cliente, se existir.
     */
    public void excluir(int clienteId) {

        String sql = "DELETE FROM fichas_treino WHERE cliente_id = ?";

        try {
            Connection con = ConnectionFactory.getConnection();

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, clienteId);

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch (SQLException erro) {
            System.out.println("Erro ao excluir ficha de treino: " + erro.getMessage());
        }
    }
}