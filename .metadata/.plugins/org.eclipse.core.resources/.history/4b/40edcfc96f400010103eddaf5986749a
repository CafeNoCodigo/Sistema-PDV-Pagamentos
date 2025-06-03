package com.minhaloja.sistema_pagamento;

import java.sql.SQLException;

import com.minhaloja.sistema_pagamento.util.Conexao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/telaInicial.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setResizable(false);         
        stage.setMaximized(false);         
        stage.setTitle("FPS_Commerce");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
    	Conexao.conectar();
    	launch(args);
    }
}
