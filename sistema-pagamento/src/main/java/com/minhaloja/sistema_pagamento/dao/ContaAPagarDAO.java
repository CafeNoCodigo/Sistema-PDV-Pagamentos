package com.minhaloja.sistema_pagamento.dao;

import com.minhaloja.sistema_pagamento.model.ContaAPagar;
import com.minhaloja.sistema_pagamento.util.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContaAPagarDAO {

    public ContaAPagarDAO() {
        criarTabelaSeNaoExistir();
    }
    
    public boolean atualizarDataPagamento(int id, LocalDate dataPagamento) {
        String sql = "UPDATE contas_pagar SET data_pagamento = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, dataPagamento); // Funciona para LocalDate no JDBC 4.2+
            stmt.setInt(2, id);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void criarTabelaSeNaoExistir() {
        String sql = """
            CREATE TABLE IF NOT EXISTS contas_pagar (
                id INT AUTO_INCREMENT PRIMARY KEY,
                descricao VARCHAR(255),
                fornecedor VARCHAR(100),
                valor DECIMAL(10, 2),
                data_emissao DATE,
                data_vencimento DATE,
                paga BOOLEAN DEFAULT FALSE,
                data_pagamento DATE,
                forma_pagamento VARCHAR(50)
            );
        """;

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela contas_pagar: " + e.getMessage());
        }
    }

    public boolean inserir(ContaAPagar conta) {
        String sql = "INSERT INTO contas_pagar (descricao, valor, data_emissao, data_vencimento, paga, data_pagamento, forma_pagamento) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, conta.getDescricao());
            stmt.setDouble(2, conta.getValor());

            if (conta.getDataEmissao() != null) {
                stmt.setDate(3, Date.valueOf(conta.getDataEmissao()));
            } else {
                stmt.setNull(3, Types.DATE);
            }

            if (conta.getDataVencimento() != null) {
                stmt.setDate(4, Date.valueOf(conta.getDataVencimento()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setBoolean(5, conta.isPaga());

            if (conta.getDataPagamento() != null) {
                stmt.setDate(6, Date.valueOf(conta.getDataPagamento()));
            } else {
                stmt.setNull(6, Types.DATE);
            }

            stmt.setString(7, conta.getFormaPagamento());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(ContaAPagar conta) {
        String sql = "UPDATE contas_pagar SET descricao=?, valor=?, data_emissao=?, data_vencimento=?, paga=?, data_pagamento=?, forma_pagamento=? WHERE id=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, conta.getDescricao());
            stmt.setDouble(2, conta.getValor());

            if (conta.getDataEmissao() != null) {
                stmt.setDate(3, Date.valueOf(conta.getDataEmissao()));
            } else {
                stmt.setNull(3, Types.DATE);
            }

            if (conta.getDataVencimento() != null) {
                stmt.setDate(4, Date.valueOf(conta.getDataVencimento()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setBoolean(5, conta.isPaga());

            if (conta.getDataPagamento() != null) {
                stmt.setDate(6, Date.valueOf(conta.getDataPagamento()));
            } else {
                stmt.setNull(6, Types.DATE);
            }

            stmt.setString(7, conta.getFormaPagamento());

            stmt.setInt(8, conta.getId());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM contas_pagar WHERE id=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ContaAPagar> listarTodas() {
        List<ContaAPagar> contas = new ArrayList<>();
        String sql = "SELECT * FROM contas_pagar ORDER BY data_vencimento ASC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ContaAPagar conta = new ContaAPagar();
                conta.setId(rs.getInt("id"));
                conta.setDescricao(rs.getString("descricao"));
                conta.setValor(rs.getDouble("valor"));

                Date dEmissao = rs.getDate("data_emissao");
                conta.setDataEmissao(dEmissao != null ? dEmissao.toLocalDate() : null);

                Date dVencimento = rs.getDate("data_vencimento");
                conta.setDataVencimento(dVencimento != null ? dVencimento.toLocalDate() : null);

                conta.setPaga(rs.getBoolean("paga"));

                Date dPagamento = rs.getDate("data_pagamento");
                conta.setDataPagamento(dPagamento != null ? dPagamento.toLocalDate() : null);

                conta.setFormaPagamento(rs.getString("forma_pagamento"));

                contas.add(conta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contas;
    }

    public ContaAPagar buscarPorId(int id) {
        String sql = "SELECT * FROM contas_pagar WHERE id=?";
        ContaAPagar conta = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                conta = new ContaAPagar();
                conta.setId(rs.getInt("id"));
                conta.setDescricao(rs.getString("descricao"));
                conta.setValor(rs.getDouble("valor"));

                Date dEmissao = rs.getDate("data_emissao");
                conta.setDataEmissao(dEmissao != null ? dEmissao.toLocalDate() : null);

                Date dVencimento = rs.getDate("data_vencimento");
                conta.setDataVencimento(dVencimento != null ? dVencimento.toLocalDate() : null);

                conta.setPaga(rs.getBoolean("paga"));

                Date dPagamento = rs.getDate("data_pagamento");
                conta.setDataPagamento(dPagamento != null ? dPagamento.toLocalDate() : null);

                conta.setFormaPagamento(rs.getString("forma_pagamento"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conta;
    }
}
