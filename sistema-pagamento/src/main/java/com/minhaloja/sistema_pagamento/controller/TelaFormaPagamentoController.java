package com.minhaloja.sistema_pagamento.controller;


import com.minhaloja.sistema_pagamento.dao.FormaPagamentoDAO;
import com.minhaloja.sistema_pagamento.model.FormaPagamento;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.effect.DropShadow;

public class TelaFormaPagamentoController {
    
	@FXML private VBox menuPane;
    @FXML private VBox vbText;
    @FXML private Label lb1;

    @FXML private Button menuButton, btnMpesa, btnFechar, btnFechar2, btnEmola, btnBim, btnRegistrar; 
    
    @FXML private TextField tfNome, tfConta, tfId, tfEmail, tfResponsavel;

    private boolean menuAberto = false;
    
    private final FormaPagamentoDAO formaPagamentoDAO = new FormaPagamentoDAO();
    private String match = "";
    private String msgErro = "";
    
    private boolean emailValido(TextField tfEmail) {
        String email = tfEmail.getText().trim();
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }
    
    private void exibirErroEmail(String mensagem) {
        tfEmail.setStyle("-fx-border-color: red; -fx-border-width: 2px;");

        FadeTransition ft = new FadeTransition(Duration.millis(100), tfEmail);
        ft.setFromValue(1.0);
        ft.setToValue(0.5);
        ft.setCycleCount(6);
        ft.setAutoReverse(true);
        ft.play();

        Tooltip tooltip = new Tooltip(mensagem);
        tooltip.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
        Tooltip.install(tfEmail, tooltip);

        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("E-mail Inválido");
        alerta.setHeaderText("Formato de e-mail incorreto");
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }


    
    @FXML
    private void registrarFormaPagamento() {
        limparEstilosErros();

        String conta = tfConta.getText();

        if (!conta.matches(match)) {
            exibirErroConta(msgErro);
            return;
        }

        if (!emailValido(tfEmail)) {
            exibirErroEmail("E-mail inválido! Verifique o formato (ex: nome@exemplo.com)");
            return;
        }


        FormaPagamento formaPagamento = new FormaPagamento();

        try {
            formaPagamento.setNome(tfNome.getText());
            formaPagamento.setConta(conta);
            formaPagamento.setId(tfId.getText());
            formaPagamento.setEmail(tfEmail.getText());
            formaPagamento.setResponsavel(tfResponsavel.getText());

            formaPagamentoDAO.registrarConta(formaPagamento);
            limparCampos();
            desabilitarTf();
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Erro ao salvar");
            alerta.setContentText("Não foi possível salvar a forma de pagamento: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    private void exibirErroConta(String mensagem) {
        tfConta.setStyle("-fx-border-color: red; -fx-border-width: 2px;");

        // Fade (piscar borda vermelha)
        FadeTransition ft = new FadeTransition(Duration.millis(100), tfConta);
        ft.setFromValue(1.0);
        ft.setToValue(0.5);
        ft.setCycleCount(6);
        ft.setAutoReverse(true);
        ft.play();

        // Tooltip de erro (ícone de aviso ao passar o mouse)
        Tooltip tooltip = new Tooltip(mensagem);
        tooltip.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
        Tooltip.install(tfConta, tooltip);

        // Alerta padrão também (opcional)
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Conta Inválida");
        alerta.setHeaderText("Número de conta inválido");
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }



    @FXML
    public void initialize() {
        aplicarFadeTransition(lb1);         // Fade na label ao iniciar
        aplicarEscalaFadeLabel(lb1);        // Destaque de escala + fade
        configurarBotaoAnimado(btnMpesa);
        configurarBotaoAnimado(btnEmola);
        configurarBotaoAnimado(btnBim);
        configurarBotaoAnimado(btnFechar);
        configurarBotaoAnimado(btnFechar2);
        configurarBotaoAnimado(menuButton);
        configurarBotaoAnimado(btnRegistrar); 
        
    }

    @FXML
    private void registrarMpesa() {
    	match = "^(84|85)\\d{7}$";
    	msgErro = "A conta deve começar com '84' ou '85' e ter exatamente 9 dígitos numéricos.";
    	selecionado();
    	String novoId = formaPagamentoDAO.gerarProximoIdFormaPagamentoMpesa();
        tfId.setText(novoId);
    }

    @FXML
    private void registrarEmola() {
    	msgErro = "A conta deve começar com '86' ou '87' e ter exatamente 9 dígitos numéricos.";
    	match = "^(86|87)\\d{7}$";
    	selecionado();
    	String novoId = formaPagamentoDAO.gerarProximoIdFormaPagamentoEmola();
        tfId.setText(novoId);
    }
    
    private void selecionado() {
    	menuAberto = true;
        esconderMenu();
        habilitarTf();
    }
    
    private void limparCampos() {
    	tfNome.setText("");
    	tfConta.setText("");
    	tfId.setText("");
    	tfResponsavel.setText("");
    	tfEmail.setText("");
    }
    
    private void habilitarTf() {
    	tfNome.setDisable(false);
    	tfConta.setDisable(false);
    	tfResponsavel.setDisable(false);
    	tfEmail.setDisable(false);
    }
    
    private void desabilitarTf() {
    	tfNome.setDisable(true);
    	tfConta.setDisable(true);
    	tfResponsavel.setDisable(true);
    	tfId.setDisable(true);
    	tfEmail.setDisable(true);
    }

    @FXML
    private void esconderMenu() {
    	limparCampos();
    	desabilitarTf();
        TranslateTransition slide = new TranslateTransition(Duration.millis(250), menuPane);
        TranslateTransition slide2 = new TranslateTransition(Duration.millis(250), vbText);
        TranslateTransition slide3 = new TranslateTransition(Duration.millis(250), lb1);

        if (menuAberto) {
            slide.setToX(-200);  // Esconde menu
            slide2.setToY(-58);
            slide3.setToY(-48);
            menuAberto = false;
        } else {
            slide.setToX(200);   // Mostra menu
            slide2.setToY(58);
            slide3.setToY(58);
            menuAberto = true;
        }

        slide.play();
        slide2.play();
        slide3.play();
    }

    @FXML
    private void fecharJanela() {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }

    // 🔄 Efeito de fade simples
    private void aplicarFadeTransition(javafx.scene.Node node) {
        FadeTransition fade = new FadeTransition(Duration.millis(500), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    // ✨ Escala + fade para Label
    private void aplicarEscalaFadeLabel(Label label) {
        ScaleTransition st = new ScaleTransition(Duration.millis(300), label);
        st.setFromX(1);
        st.setFromY(1);
        st.setToX(1.15);
        st.setToY(1.15);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();

        FadeTransition ft = new FadeTransition(Duration.millis(300), label);
        ft.setFromValue(0.6);
        ft.setToValue(1.0);
        ft.play();
    }

    // 🖱️ Efeito hover em botões
    private void configurarBotaoAnimado(Button btn) {
        DropShadow shadow = new DropShadow();

        btn.setOnMouseEntered(e -> {
            btn.setEffect(shadow);
            btn.setCursor(Cursor.HAND);
            ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        btn.setOnMouseExited(e -> {
            btn.setEffect(null);
            btn.setCursor(Cursor.DEFAULT);
            ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }
    
    private void limparEstilosErros() {
        tfConta.setStyle(""); // Remove borda vermelha
        Tooltip.uninstall(tfConta, null); // Remove qualquer tooltip anterior
        tfConta.setDisable(true);
    }

}
