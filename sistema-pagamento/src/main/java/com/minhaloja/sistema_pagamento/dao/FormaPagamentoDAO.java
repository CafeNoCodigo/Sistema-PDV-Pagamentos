package com.minhaloja.sistema_pagamento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import com.minhaloja.sistema_pagamento.model.FormaPagamento;
import com.minhaloja.sistema_pagamento.util.Conexao;

import javafx.scene.control.Alert;

public class FormaPagamentoDAO {
	
	public FormaPagamentoDAO() {
		criarTabelaSeNaoExistir();
	}
	
	public String gerarProximoIdFormaPagamentoMpesa() {
	    String prefixo = "Mpesa-";
	    int proximoNumero = 1;

	    String sql = "SELECT MAX(id) FROM formaPagamento WHERE id LIKE 'Mpesa-%'";

	    try (Connection conn = Conexao.conectar();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        if (rs.next()) {
	            String ultimoCodigo = rs.getString(1);
	            if (ultimoCodigo != null && ultimoCodigo.startsWith(prefixo)) {
	                // Extrai o número após "VENDA-"
	                String numeroStr = ultimoCodigo.substring(prefixo.length());
	                try {
	                    proximoNumero = Integer.parseInt(numeroStr) + 1;
	                } catch (NumberFormatException e) {
	                    proximoNumero = 1; // fallback se algo der errado
	                }
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return prefixo + proximoNumero;
	}
	
	public String gerarProximoIdFormaPagamentoEmola() {
	    String prefixo = "Emola-";
	    int proximoNumero = 1;

	    String sql = "SELECT MAX(id) FROM formaPagamento WHERE id LIKE 'Emola-%'";

	    try (Connection conn = Conexao.conectar();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        if (rs.next()) {
	            String ultimoCodigo = rs.getString(1);
	            if (ultimoCodigo != null && ultimoCodigo.startsWith(prefixo)) {
	                // Extrai o número após "VENDA-"
	                String numeroStr = ultimoCodigo.substring(prefixo.length());
	                try {
	                    proximoNumero = Integer.parseInt(numeroStr) + 1;
	                } catch (NumberFormatException e) {
	                    proximoNumero = 1; // fallback se algo der errado
	                }
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return prefixo + proximoNumero;
	}

	
	public void registrarConta(FormaPagamento formaPagamento) {
	    String sql = """
	        INSERT INTO formaPagamento(id, nome, numeroConta, nomeResponsavel, email) 
	        VALUES (?, ?, ?, ?, ?);
	    """;

	    try (Connection conn = Conexao.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, formaPagamento.getId());
	        pstmt.setString(2, formaPagamento.getNome());
	        pstmt.setString(3, formaPagamento.getConta());
	        pstmt.setString(4, formaPagamento.getResponsavel());
	        pstmt.setString(5, formaPagamento.getEmail());

	        pstmt.executeUpdate();

	        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
	        alerta.setTitle("Sucesso");
	        alerta.setHeaderText(null);
	        alerta.setContentText("Forma de Pagamento salva com sucesso!");
	        alerta.showAndWait();
	    } catch (SQLIntegrityConstraintViolationException e) {
	    	Alert alerta = new Alert(Alert.AlertType.WARNING);
	        alerta.setTitle("Forma de Pagamento já Existe!");
	        alerta.setHeaderText("Cadastro duplicado");
	        alerta.setContentText("Já existe uma forma de pagamento com o mesmo número de conta.");
	        alerta.showAndWait();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        Alert alerta = new Alert(Alert.AlertType.ERROR);
	        alerta.setTitle("Erro");
	        alerta.setHeaderText("Erro ao salvar forma de pagamento");
	        alerta.setContentText(e.getMessage());
	        alerta.showAndWait();
	    }
	    
	}

	private void criarTabelaSeNaoExistir() {
    	String sql = """
    		    CREATE TABLE IF NOT EXISTS formaPagamento (
    			    id VARCHAR (100) PRIMARY KEY,
    		        nome VARCHAR(255) NOT NULL,
    		        numeroConta VARCHAR (100) UNIQUE,
    		        nomeResponsavel VARCHAR (255),
    		        email VARCHAR (255)
    		    );
    		""";

        try (Connection conn = Conexao.conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }
}
