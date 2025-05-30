package com.minhaloja.sistema_pagamento.dao;

import com.minhaloja.sistema_pagamento.model.Produto;
import com.minhaloja.sistema_pagamento.util.Conexao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class ProdutoDAO {
	
	public ProdutoDAO() {
        criarTabelaSeNaoExistir();
    }
	
	public ObservableList<Produto> buscarProdutosPorTexto(String texto) {
	    ObservableList<Produto> lista = FXCollections.observableArrayList();
	    boolean isNumero = texto.matches("\\d+"); // Verifica se o texto é inteiro

	    String sql = "SELECT * FROM produtos WHERE "
	               + "nome LIKE ? OR categoria LIKE ? OR referencia LIKE ? OR modelo LIKE ? OR fornecedor LIKE ?";

	    if (isNumero) {
	        sql += " OR estoque LIKE ? OR precoCompra LIKE ? ";
	    }

	    try (Connection conn = Conexao.conectar();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        String buscaComLike = "%" + texto + "%";
	        stmt.setString(1, buscaComLike); // nome
	        stmt.setString(2, buscaComLike); // categoria
	        stmt.setString(3, buscaComLike); // referencia
	        stmt.setString(4, buscaComLike); // modelo
	        stmt.setString(5, buscaComLike); // fornecedor

	        if (isNumero) {
	            stmt.setInt(6, Integer.parseInt(texto)); // quantidade
	            stmt.setInt(7, Integer.parseInt(texto)); // preco de compra
	        }

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Produto p = new Produto();
	            p.setCodigoBarra(rs.getString("codigoBarra"));
	            p.setNome(rs.getString("nome"));
	            p.setCategoria(rs.getString("categoria"));
	            p.setEstoque(rs.getInt("estoque"));
	            p.setPrecoVenda(rs.getDouble("precoVenda"));
	            p.setPrecoMestre(rs.getDouble("precoMestre"));
	            p.setPrecoCompra(rs.getDouble("precoCompra"));
	            p.setReferencia(rs.getString("referencia"));
	            p.setLoja(rs.getString("loja"));
	            p.setFabricante(rs.getString("fabricante"));
	            p.setFornecedor(rs.getString("fornecedor"));
	            p.setQrCode(rs.getBytes("imgQrCode"));
	            p.setModelo(rs.getString("modelo"));
	            p.setCodigo(rs.getString("codigo"));
	            p.setGarantia(rs.getString("garantia"));
	            p.setCor(rs.getString("cor"));
	            p.setInfoAdicional(rs.getString("infoAdicional"));
	            p.setImagem(rs.getBytes("imagem"));
	            lista.add(p);
	        }


	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return lista;
	}

	
	public List<String> listarFornecedores() {
	    List<String> fornecedores = new ArrayList<>();
	    String sql = "SELECT DISTINCT fornecedor FROM produtos ORDER BY fornecedor";

	    try (PreparedStatement stmt = Conexao.conectar().prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            fornecedores.add(rs.getString("fornecedor"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return fornecedores;
	}
	
	public boolean excluirProduto(String codigoBarra) {
        String sql = "DELETE FROM produtos WHERE codigoBarra = ?";
        
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigoBarra);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir produto: " + e.getMessage());
            return false;
        }
    }
	
	public List<String> listarCategoriasUnicas() {
	    List<String> categorias = new ArrayList<>();
	    String sql = "SELECT DISTINCT categoria FROM produtos ORDER BY categoria";

	    try (PreparedStatement stmt = Conexao.conectar().prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            categorias.add(rs.getString("categoria"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return categorias;
	}    

	
	public int contarProdutos() {
        String sql = "SELECT COUNT(*) FROM produtos";

        try (PreparedStatement stmt = Conexao.conectar().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private void criarTabelaSeNaoExistir() {
    	String sql = """
    		    CREATE TABLE IF NOT EXISTS produtos (
    		        id INT PRIMARY KEY AUTO_INCREMENT,
    		        nome VARCHAR(255) NOT NULL,
    		        precoCompra DOUBLE,
    		        precoVenda DOUBLE,
    		        precoMestre DOUBLE,
    		        categoria VARCHAR(100),
    		        garantia VARCHAR(100),
    		        referencia VARCHAR(100),
    		        estoque INT,
    		        loja VARCHAR(100),
    		        fabricante VARCHAR(100),
    		        fornecedor VARCHAR(100),
    		        infoAdicional VARCHAR(255),
    		        codigoBarra VARCHAR(50) UNIQUE,
    		        imgQrCode BLOB,
    		        modelo VARCHAR(100),
    		        codigo VARCHAR(50),
    		        cor VARCHAR (50),
    		        imagem BLOB
    		    );
    		""";

        try (Connection conn = Conexao.conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    public void salvarProduto(Produto produto) {
    	if (produtoExiste(produto)) {
            System.out.println("Produto já cadastrado. Cadastro cancelado.");
         
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Produto Já Existe!");
            alerta.showAndWait();
            return;
        }
    	
    	if (produtoExistePorNomeECategoria(produto.getNome(), produto.getCategoria())) {
            System.out.println("Já existe um produto com este nome na mesma categoria.");
        
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Atenção!");
            alerta.setHeaderText(null);
            alerta.setContentText("Já existe um produto com este nome na mesma categoria!");
            alerta.showAndWait();
            return;
        }
    	
    	if (produtoExistePorCodigoBarra(produto.getCodigoBarra())) {
            System.out.println("Produto com este código de barras já existe.");
            
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Atenção!");
            alerta.setHeaderText(null);
            alerta.setContentText("Produto com este código de barras já existe!");
            alerta.showAndWait();
            return;
        }
    	
        String sql = """
            INSERT INTO produtos(nome, precoCompra, precoVenda, precoMestre, categoria, garantia, 
            referencia, estoque, loja, fabricante, fornecedor, infoAdicional, codigoBarra, imgQrCode, modelo, codigo, cor, imagem) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;

        try (Connection conn = Conexao.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, produto.getNome());
            pstmt.setDouble(2, produto.getPrecoCompra());
            pstmt.setDouble(3, produto.getPrecoVenda());
            pstmt.setDouble(4, produto.getPrecoMestre());
            pstmt.setString(5, produto.getCategoria());
            pstmt.setString(6, produto.getGarantia());
            pstmt.setString(7, produto.getReferencia());
            pstmt.setInt(8, produto.getEstoque());
            pstmt.setString(9, produto.getLoja());
            pstmt.setString(10, produto.getFabricante());
            pstmt.setString(11, produto.getFornecedor());
            pstmt.setString(12, produto.getInfoAdicional());
            pstmt.setString(13, produto.getCodigoBarra());
            pstmt.setBytes(14, produto.getQrCode());
            pstmt.setString(15, produto.getModelo());
            pstmt.setString(16, produto.getCodigo());
            pstmt.setString(17, produto.getCor());
            pstmt.setBytes(18, produto.getImagem());

            pstmt.executeUpdate();
            System.out.println("Produto salvo com sucesso.");
         
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Sucesso");
            alerta.setHeaderText(null);
            alerta.setContentText("Produto salvo com sucesso!");
            alerta.showAndWait();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar produto: " + e.getMessage());
        }
    }
    
    public boolean produtoExiste(Produto produto) {
        String sql = """
            SELECT COUNT(*) FROM produtos 
            WHERE codigoBarra = ? AND nome = ? AND categoria = ?
        """;

        try (Connection conn = Conexao.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, produto.getCodigoBarra());
            pstmt.setString(2, produto.getNome());
            pstmt.setString(3, produto.getCategoria());

            var rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }   

        } catch (SQLException e) {
            System.out.println("Erro ao verificar duplicidade: " + e.getMessage());
        }
        return false;
    }
    
    public boolean produtoExistePorNomeECategoria(String nome, String categoria) {
        String sql = "SELECT COUNT(*) FROM produtos WHERE nome = ? AND categoria = ?";

        try (Connection conn = Conexao.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, categoria);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar nome e categoria: " + e.getMessage());
        }

        return false;
    }

    public boolean produtoExistePorCodigoBarra(String codigoBarra) {
        String sql = "SELECT COUNT(*) FROM produtos WHERE codigoBarra = ?";

        try (Connection conn = Conexao.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigoBarra);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar código de barras: " + e.getMessage());
        }

        return false;
    }

    public ObservableList<Produto> listarProdutos() {
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        String sql = "SELECT codigoBarra, nome, categoria, estoque, precoVenda, precoMestre, precoCompra, "
        		+ "referencia, loja, fabricante, fornecedor, imgQrCode, modelo, codigo, garantia, cor, infoAdicional, imagem FROM produtos";

        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setCodigoBarra(rs.getString("codigoBarra"));
                p.setNome(rs.getString("nome"));
                p.setCategoria(rs.getString("categoria"));
                p.setEstoque(rs.getInt("estoque"));
                p.setPrecoVenda(rs.getDouble("precoVenda"));
                p.setPrecoMestre(rs.getDouble("precoMestre"));
                p.setPrecoVenda(rs.getDouble("precoVenda"));
                p.setPrecoCompra(rs.getDouble("precoCompra"));
                p.setReferencia(rs.getString("referencia"));
                p.setLoja(rs.getString("loja"));
                p.setFabricante(rs.getString("fabricante"));
                p.setFornecedor(rs.getString("fornecedor"));
                p.setQrCode(rs.getBytes("imgQrCode"));
                p.setModelo(rs.getString("modelo"));
                p.setCodigo(rs.getString("codigo"));
                p.setGarantia(rs.getString("garantia"));
                p.setCor(rs.getString("cor"));
                p.setInfoAdicional(rs.getString("infoAdicional"));
                p.setImagem(rs.getBytes("imagem"));
                
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }

        return lista;
    }
    
    public Image obterImagemQrCode(String codigoBarra) {
        String sql = "SELECT imgQrCode FROM produtos WHERE codigoBarra = ?";
        
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigoBarra);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                byte[] imagemBytes = rs.getBytes("imgQrCode");
                if (imagemBytes != null) {
                    InputStream is = new ByteArrayInputStream(imagemBytes);
                    return new Image(is); // javafx.scene.image.Image
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar imagem QR Code: " + e.getMessage());
        }
        return null;
    }

}
