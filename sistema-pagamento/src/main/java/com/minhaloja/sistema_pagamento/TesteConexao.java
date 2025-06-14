package com.minhaloja.sistema_pagamento;

/*import java.sql.Connection;
import java.sql.DriverManager;*/

/*public class TesteConexao {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/minha_loja";
        String user = "root";
        String password = "22089mysql";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conectado com sucesso!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {
        String ipServidor = "192.168.1.101"; // IP do servidor MySQL
        String porta = "3306";               // Porta padrão do MySQL
        String nomeBanco = "minha_loja";      // Nome do banco de dados
        String usuario = "root";     // Usuário com permissão remota
        String senha = "22089mysql";         // Senha do usuário

        String url = "jdbc:mysql://" + ipServidor + ":" + porta + "/" + nomeBanco + "?useSSL=false&allowPublicKeyRetrieval=true";

        try {
            Connection conn = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão remota estabelecida com sucesso!");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

