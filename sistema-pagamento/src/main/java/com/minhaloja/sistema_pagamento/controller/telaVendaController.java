package com.minhaloja.sistema_pagamento.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class telaVendaController implements Initializable {

    @FXML
    private TableView<?> tabela;

    @FXML
    private TableColumn<?, ?> coluna1;

    @FXML
    private TableColumn<?, ?> coluna2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Label placeholder = new Label("Nenhum dado disponível");
        tabela.setPlaceholder(placeholder);
    }
}
