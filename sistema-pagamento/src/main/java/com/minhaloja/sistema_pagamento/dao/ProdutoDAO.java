package com.minhaloja.sistema_pagamento.dao;

import com.minhaloja.sistema_pagamento.model.Produto;
import com.minhaloja.sistema_pagamento.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProdutoDAO {

    public void salvarProduto(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, quantidade) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // exibir alerta JavaFX
        }
    }
}
