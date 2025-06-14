package com.minhaloja.sistema_pagamento.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private static final String IP_SERVIDOR = "localhost"; //"192.168.1.101";
    private static final String PORTA = "3306";
    private static final String BANCO = "minha_loja";

    private static final String URL = "jdbc:mysql://" + IP_SERVIDOR + ":" + PORTA + "/" + BANCO +
                                      "?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String URL_SEM_BD = "jdbc:mysql://" + IP_SERVIDOR + ":" + PORTA + "/" +
                                             "?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String SENHA = "22089mysql";

    private static Connection conexaoUnica;

    // Conecta ao banco de dados, criando-o se necessário
    public static Connection conectar() throws SQLException {
        if (conexaoUnica == null || conexaoUnica.isClosed()) {
            criarBancoSeNaoExistir();
            conexaoUnica = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão com o banco de dados estabelecida.");
        }
        return conexaoUnica;
    }

    // Cria o banco de dados se ainda não existir
    private static void criarBancoSeNaoExistir() {
        try (Connection conexao = DriverManager.getConnection(URL_SEM_BD, USUARIO, SENHA);
             Statement stmt = conexao.createStatement()) {

            String sql = "CREATE DATABASE IF NOT EXISTS " + BANCO;
            stmt.executeUpdate(sql);
            System.out.println("Banco de dados verificado/criado com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao criar/verificar banco de dados: " + e.getMessage());
        }
    }

    // Fecha a conexão, se necessário
    public static void fecharConexao() {
        try {
            if (conexaoUnica != null && !conexaoUnica.isClosed()) {
                conexaoUnica.close();
                System.out.println("Conexão com o banco de dados encerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }
}
