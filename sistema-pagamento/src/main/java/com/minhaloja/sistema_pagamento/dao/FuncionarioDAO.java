package com.minhaloja.sistema_pagamento.dao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.minhaloja.sistema_pagamento.model.Funcionario;
import com.minhaloja.sistema_pagamento.util.Conexao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

public class FuncionarioDAO {
	
	public FuncionarioDAO() {
		criarTabelaSeNaoExistir();
	}
	
	public Funcionario buscarFuncionarioPorNome(String nome) {
	    Funcionario funcionario = null;
	    String sql = "SELECT * FROM funcionarios WHERE nome = ?";

	    try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            funcionario = new Funcionario();
	            funcionario.setCodigo(rs.getDouble("codigo"));
	            funcionario.setNome(rs.getString("nome"));
	            funcionario.setNumeroBi(rs.getString("bi"));
	            funcionario.setTelefone1(rs.getString("telefone1"));
	            funcionario.setTelefone2(rs.getString("telefone2"));
	            funcionario.setEndereco(rs.getString("endereco"));
	            funcionario.setBairro(rs.getString("bairro"));
	            funcionario.setCidade(rs.getString("cidade"));
	            funcionario.setNuit(rs.getString("nuit"));
	            funcionario.setCargo(rs.getString("cargo"));
	            funcionario.setContaBancaria1(rs.getString("contaBancaria1"));
	            funcionario.setContaBancaria2(rs.getString("contaBancaria2"));
	            funcionario.setLoja(rs.getString("loja"));
	            funcionario.setSalario(rs.getInt("salario"));
	            funcionario.setTransporte(rs.getInt("transporte"));
	            funcionario.setAlimentacao(rs.getInt("alimentacao"));
	            funcionario.setDataNascido(rs.getDate("dataNascido") != null ? rs.getDate("dataNascido").toLocalDate() : null);
	            funcionario.setDataInicio(rs.getDate("dataInicio") != null ? rs.getDate("dataInicio").toLocalDate() : null);
	            funcionario.setDataFim(rs.getDate("dataFim") != null ? rs.getDate("dataFim").toLocalDate() : null);
	            funcionario.setImagemBi(rs.getBytes("imagemBi"));
	            funcionario.setImagemFuncionario(rs.getBytes("imagemFuncionario"));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return funcionario;
	}

	
	public Image obterImagem(String imagemFuncionario) {
        String sql = "SELECT imagemFuncionario FROM funcionarios WHERE bi = ?";
        
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, imagemFuncionario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                byte[] imagemBytes = rs.getBytes("imagemFuncionario");
                if (imagemBytes != null) {
                    InputStream is = new ByteArrayInputStream(imagemBytes);
                    return new Image(is);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar imagem do Funcionário: " + e.getMessage());
        }
        return null;
    }
	
	public List<String> listarFuncionariosChoiceBox() {
	    List<String> funcionarios = new ArrayList<>();
	    String sql = "SELECT DISTINCT nome FROM funcionarios ORDER BY nome";

	    try (PreparedStatement stmt = Conexao.conectar().prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            funcionarios.add(rs.getString("nome"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return funcionarios;
	}
	
	public int contarFuncionarios() {
        String sql = "SELECT COUNT(*) FROM funcionarios";

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
	
	public boolean excluirFuncionario(String NumeroBi) {
        String sql = "DELETE FROM funcionarios WHERE bi = ?";
        
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, NumeroBi);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir funcionário: " + e.getMessage());
            return false;
        }
    }
	
	public ObservableList<Funcionario> listarFuncionarios() {
	    ObservableList<Funcionario> lista = FXCollections.observableArrayList();
	    String sql = """
	        SELECT nome, bi, endereco, bairro, cidade, telefone1, telefone2,
	               nuit, dataNascido, cargo, contaBancaria1, contaBancaria2,
	               dataInicio, dataFim, salario, alimentacao, transporte,
	               imagemFuncionario, imagemBi, codigo, loja
	        FROM funcionarios
	    """;

	    try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Funcionario f = new Funcionario();
	            f.setNome(rs.getString("nome"));
	            f.setNumeroBi(rs.getString("bi"));
	            f.setEndereco(rs.getString("endereco"));
	            f.setBairro(rs.getString("bairro"));
	            f.setCidade(rs.getString("cidade"));
	            f.setTelefone1(rs.getString("telefone1"));
	            f.setTelefone2(rs.getString("telefone2"));
	            f.setNuit(rs.getString("nuit"));
	            f.setDataNascido(rs.getDate("dataNascido") != null ? rs.getDate("dataNascido").toLocalDate() : null);
	            f.setCargo(rs.getString("cargo"));
	            f.setContaBancaria1(rs.getString("contaBancaria1"));
	            f.setContaBancaria2(rs.getString("contaBancaria2"));
	            f.setDataInicio(rs.getDate("dataInicio") != null ? rs.getDate("dataInicio").toLocalDate() : null);
	            if (rs.getDate("dataFim") != null) {
	                f.setDataFim(rs.getDate("dataFim").toLocalDate());
	            }
	            f.setSalario(rs.getInt("salario"));
	            f.setAlimentacao(rs.getInt("alimentacao"));
	            f.setTransporte(rs.getInt("transporte"));
	            f.setImagemFuncionario(rs.getBytes("imagemFuncionario"));
	            f.setImagemBi(rs.getBytes("imagemBi"));
	            f.setCodigo(rs.getDouble("codigo"));
	            f.setLoja(rs.getString("loja"));

	            lista.add(f);
	        }
	    } catch (SQLException e) {
	        System.out.println("Erro ao listar funcionários: " + e.getMessage());
	    }

	    return lista;
	}


	private void criarTabelaSeNaoExistir() {
    	String sql = """
    		    CREATE TABLE IF NOT EXISTS funcionarios (
    		        id INT PRIMARY KEY AUTO_INCREMENT,
    		        nome VARCHAR(255) NOT NULL,
    		        bi VARCHAR(50) UNIQUE,
    		        endereco VARCHAR(255),
    		        bairro VARCHAR(255),
    		        cidade VARCHAR(100),
    		        telefone1 VARCHAR(100),
    		        telefone2 VARCHAR(100),
    		        nuit VARCHAR(100),
    		        dataNascido DATE,
    		        cargo VARCHAR(100),
    		        contaBancaria1 VARCHAR(100),
    		        contaBancaria2 VARCHAR(100),
    		        dataInicio DATE,
    		        dataFim DATE,
    		        salario INT,
    		        alimentacao INT,
    		        transporte INT,
    		        imagemFuncionario MEDIUMBLOB,
    		        imagemBi MEDIUMBLOB,
    		        codigo DOUBLE,
    		        loja VARCHAR(100)
    		    );
    		""";

        try (Connection conn = Conexao.conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }
	
	public void salvarFuncionario(Funcionario funcionario) {
	    String sql = """
	        INSERT INTO funcionarios(nome, bi, endereco, bairro, cidade, telefone1, 
	        telefone2, nuit, dataNascido, cargo, contaBancaria1, contaBancaria2, dataInicio, dataFim, salario,
	        alimentacao, transporte, imagemFuncionario, imagemBi, codigo, loja) 
	        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
	    """;

	    try (Connection conn = Conexao.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, funcionario.getNome());
	        pstmt.setString(2, funcionario.getNumeroBi());
	        pstmt.setString(3, funcionario.getEndereco());
	        pstmt.setString(4, funcionario.getBairro());
	        pstmt.setString(5, funcionario.getCidade());
	        pstmt.setString(6, funcionario.getTelefone1());
	        pstmt.setString(7, funcionario.getTelefone2());
	        pstmt.setString(8, funcionario.getNuit());

	        // Datas (LocalDate para java.sql.Date)
	        pstmt.setDate(9, funcionario.getDataNascido() != null ? java.sql.Date.valueOf(funcionario.getDataNascido()) : null);
	        pstmt.setString(10, funcionario.getCargo());
	        pstmt.setString(11, funcionario.getContaBancaria1());
	        pstmt.setString(12, funcionario.getContaBancaria2());
	        pstmt.setDate(13, funcionario.getDataInicio() != null ? java.sql.Date.valueOf(funcionario.getDataInicio()) : null);
	        pstmt.setDate(14, funcionario.getDataFim() != null ? java.sql.Date.valueOf(funcionario.getDataFim()) : null);
	        
	        pstmt.setInt(15, funcionario.getSalario());
	        pstmt.setInt(16, funcionario.getAlimentacao());
	        pstmt.setInt(17, funcionario.getTransporte());
	        pstmt.setBytes(18, funcionario.getImagemFuncionario());
	        pstmt.setBytes(19, funcionario.getImagemBi());
	        pstmt.setDouble(20, funcionario.getCodigo());
	        pstmt.setString(21, funcionario.getLoja());

	        pstmt.executeUpdate();

	        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
	        alerta.setTitle("Sucesso");
	        alerta.setHeaderText(null);
	        alerta.setContentText("Funcionário salvo com sucesso!");
	        alerta.showAndWait();
	    } catch (SQLIntegrityConstraintViolationException e) {
	    	Alert alerta = new Alert(Alert.AlertType.WARNING);
	        alerta.setTitle("Funcionário já existe");
	        alerta.setHeaderText("Cadastro duplicado");
	        alerta.setContentText("Já existe um funcionário com o mesmo número de BI.");
	        alerta.showAndWait();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        Alert alerta = new Alert(Alert.AlertType.ERROR);
	        alerta.setTitle("Erro");
	        alerta.setHeaderText("Erro ao salvar funcionário");
	        alerta.setContentText(e.getMessage());
	        alerta.showAndWait();
	    }
	    
	}    

}
