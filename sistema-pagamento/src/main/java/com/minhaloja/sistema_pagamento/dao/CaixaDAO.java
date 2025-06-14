package com.minhaloja.sistema_pagamento.dao;

import com.minhaloja.sistema_pagamento.model.Caixa;
import com.minhaloja.sistema_pagamento.util.Conexao;

import java.sql.*;
import java.time.LocalDateTime;

public class CaixaDAO {

    public CaixaDAO() {
        criarTabelaSeNaoExistir();
    }
    
    public int buscarIdCaixaAberto() {
        String sql = "SELECT id FROM caixa WHERE aberto = TRUE ORDER BY id DESC LIMIT 1";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar ID do caixa aberto: " + e.getMessage());
        }

        return -1;
    }

    
    public void incrementarContadorVendas() {
        String sql = """
            UPDATE caixa
            SET contador_vendas = contador_vendas + 1
            WHERE id = (
                SELECT id FROM (
                    SELECT id FROM caixa WHERE aberto = TRUE ORDER BY id DESC LIMIT 1
                ) AS ultimo
            )
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao incrementar contador de vendas: " + e.getMessage());
        }
    }



    private void criarTabelaSeNaoExistir() {
        String sql = """
            CREATE TABLE IF NOT EXISTS caixa (
                id INT AUTO_INCREMENT PRIMARY KEY,
                dataHoraAbertura DATETIME NOT NULL,
                dataHoraFechamento DATETIME,
                valorAbertura DOUBLE NOT NULL,
                valorFechamento DOUBLE,
                aberto BOOLEAN NOT NULL DEFAULT TRUE,
                contador_vendas INT NOT NULL DEFAULT 0
            );
        """;

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela caixa: " + e.getMessage());
        }
    }

    public int abrirCaixa(Caixa caixa) {
        String sql = "INSERT INTO caixa (dataHoraAbertura, valorAbertura, aberto, contador_vendas) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(caixa.getDataHoraAbertura()));
            stmt.setDouble(2, caixa.getValorAbertura());
            stmt.setBoolean(3, true);
            stmt.setInt(4, 0);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao abrir caixa: " + e.getMessage());
        }

        return -1; // Retorna -1 em caso de falha
    }


    public boolean fecharCaixa(double valorFechamento, int contadorVendas) {
    	String sql = """
    		    UPDATE caixa
    		    SET dataHoraFechamento = ?, valorFechamento = ?, aberto = FALSE, contador_vendas = ?
    		    WHERE id = (
    		        SELECT id FROM (
    		            SELECT id FROM caixa WHERE aberto = TRUE ORDER BY id DESC LIMIT 1
    		        ) AS ultimo
    		    )
    		""";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setDouble(2, valorFechamento);
            stmt.setInt(3, contadorVendas);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao fechar caixa: " + e.getMessage());
            return false;
        }
    }


    public double calcularValorTotalVendasCaixaAtual() {
        String sql = """
            SELECT SUM(totalProduto)
            FROM venda
            WHERE id_caixa = (
                SELECT id FROM caixa WHERE aberto = TRUE ORDER BY id DESC LIMIT 1
            )
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);  // Se não houver vendas, retornará 0.0
            }

        } catch (SQLException e) {
            System.err.println("Erro ao calcular valor total de vendas do caixa atual: " + e.getMessage());
        }

        return 0.0;
    }

    public int contarVendasCaixaAtual() {
        String sql = """
            SELECT COUNT(*)
            FROM venda
            WHERE id_caixa = (
                SELECT id FROM caixa WHERE aberto = TRUE ORDER BY id DESC LIMIT 1
            )
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao contar vendas do caixa atual: " + e.getMessage());
        }

        return 0;
    }


    public boolean isCaixaAberto() {
        String sql = "SELECT COUNT(*) FROM caixa WHERE aberto = TRUE";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar status do caixa: " + e.getMessage());
            return false;
        }
    }
}