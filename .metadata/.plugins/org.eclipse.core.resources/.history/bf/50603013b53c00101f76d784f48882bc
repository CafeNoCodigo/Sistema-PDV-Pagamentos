package com.minhaloja.sistema_pagamento.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.minhaloja.sistema_pagamento.util.Conexao;

public class VendaDAO {
	
	public VendaDAO() {
		criarTabelaSeNaoExistir();
	}
	
	public void criarTabelaSeNaoExistir() {
    	String sql = """
    		    CREATE TABLE IF NOT EXISTS vendas (
    		        nome VARCHAR(255) NOT NULL,
    		        precoVenda DOUBLE,
    		        categoria VARCHAR(100),
    		        formaDePagamento VARCHAR(50),
    		        garantia VARCHAR(100),
    		        referencia VARCHAR(100),
    		        qtdVendida INT,
    		        loja VARCHAR(100),
    		        vendedor VARCHAR(100),
    		        fabricante VARCHAR(100),
    		        fornecedor VARCHAR(100),
    		        codigoBarra VARCHAR(50) UNIQUE,
    		        imgQrCode BLOB,
    		        modelo VARCHAR(100),
    		        codigo VARCHAR(50),
    		        cor VARCHAR (50),
    		        imagem BLOB,
    		        dataVenda DATE
    		    );
    		""";

        try (Connection conn = Conexao.conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
