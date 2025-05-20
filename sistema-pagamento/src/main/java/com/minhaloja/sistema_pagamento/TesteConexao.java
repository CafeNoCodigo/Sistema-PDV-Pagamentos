package com.minhaloja.sistema_pagamento;

import java.sql.Connection;
import java.sql.DriverManager;

public class TesteConexao {
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
}
