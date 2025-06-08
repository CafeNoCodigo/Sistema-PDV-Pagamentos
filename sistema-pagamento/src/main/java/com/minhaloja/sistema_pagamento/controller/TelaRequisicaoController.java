package com.minhaloja.sistema_pagamento.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.minhaloja.sistema_pagamento.dao.ProdutoDAO;
import com.minhaloja.sistema_pagamento.dao.ProdutoEsgotadoDAO;
import com.minhaloja.sistema_pagamento.model.Produto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TelaRequisicaoController {

    @FXML private TableView<Produto> tabelaRequisicao;

    @FXML private TableColumn<Produto, String> colNome, colFornecedor, colCategoria, colLoja, colModelo, colCor, colId;
    @FXML private TableColumn<Produto, Double> colPrecoMestre;
    
    @FXML private ImageView imgProduto;
    
    @FXML private Button btnFechar, btnAdicionar, btnExportarPdf;
    @FXML private TextField tfQTD;

    private ProdutoEsgotadoDAO produtoEsgotadoDAO = new ProdutoEsgotadoDAO();

    @FXML
    public void initialize() {
        configurarTabelaRequisicao();

        // Registra produtos esgotados e atualiza tabela
        produtoEsgotadoDAO.verificarProdutosEsgotados();
        carregarProdutosEsgotados();
        
        tabelaRequisicao.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	try {
            	    Produto produto = newSelection; // usa o item selecionado
            	    byte[] imagemBytes = produto.getImagem();
            	    
            	    if (imagemBytes != null && imagemBytes.length > 0) {
            	        ByteArrayInputStream bis = new ByteArrayInputStream(imagemBytes);
            	        Image imagemP = new Image(bis);
            	        imgProduto.setImage(imagemP);
            	    } else {
            	        Image imagemPadrao = new Image(getClass().getResourceAsStream("/img/semImg.png"));
            	        imgProduto.setImage(imagemPadrao);
            	    }
            	} catch (Exception e) {
            	    e.printStackTrace();
            	}

            }
        });
    }
    
    @FXML
    private void exportarParaPDF(ActionEvent event) {
        Document documento = new Document();
        try {
            // Local que será salvo
            String userHome = System.getProperty("user.home");
            File pastaDestino = new File(userHome, "Desktop/FPS_COMMERCE/REQUISICOES");
            if (!pastaDestino.exists()) {
                pastaDestino.mkdirs();
            }

            String nomeArquivo = "Requisição de_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".pdf";
            File arquivo = new File(pastaDestino, nomeArquivo);

            PdfWriter.getInstance(documento, new FileOutputStream(arquivo));
            documento.open();

            Font fonteNegrito = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph titulo = new Paragraph("Relatório de Produtos Esgotados", fonteNegrito);
            titulo.setAlignment(Element.ALIGN_CENTER); 
            documento.add(titulo);
            documento.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(5);
            tabela.setWidthPercentage(100);

            tabela.addCell("Nome");
            tabela.addCell("Código de Barra");
            tabela.addCell("Categoria");
            tabela.addCell("Preço");
            tabela.addCell("Status");

            for (Produto p : tabelaRequisicao.getItems()) {
                tabela.addCell(p.getNome());
                tabela.addCell(p.getCodigoBarra());
                tabela.addCell(p.getCategoria());
                tabela.addCell(String.format("MZN %.2f", p.getPrecoCompra()));
                tabela.addCell("");
            }

            documento.add(tabela);
            documento.close();

            alerta(Alert.AlertType.INFORMATION, "Gerar PDF de Requisições", "PDF salvo em: " + arquivo.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            alerta(Alert.AlertType.ERROR, "Gerar PDF de Requisições", "Erro ao gerar PDF: " + e.getMessage());
        }
    }



    private void configurarTabelaRequisicao() {
        colId.setCellValueFactory(new PropertyValueFactory<>("codigoBarra"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        colPrecoMestre.setCellValueFactory(new PropertyValueFactory<>("precoMestre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colLoja.setCellValueFactory(new PropertyValueFactory<>("loja"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
    }

    private void carregarProdutosEsgotados() {
        List<Produto> produtos = produtoEsgotadoDAO.listarProdutosEsgotados();
        ObservableList<Produto> observableList = FXCollections.observableArrayList(produtos);
        tabelaRequisicao.setItems(observableList);
    }
    
    //----Eventos----
    @FXML
    private void atualizarEstoqueSelecionado() {
        Produto produtoSelecionado = tabelaRequisicao.getSelectionModel().getSelectedItem();

        if (produtoSelecionado == null) {
            alerta(Alert.AlertType.WARNING, "Seleção necessária", "Por favor, selecione um item na tabela.");
            return;
        }

        String valorTexto = tfQTD.getText();
        double novoPreco;

        try {
            novoPreco = Double.parseDouble(valorTexto);
        } catch (NumberFormatException e) {
            alerta(Alert.AlertType.ERROR, "Valor inválido", "Insira um valor numérico válido.");
            return;
        }

        int codigo = produtoSelecionado.getId();

        boolean sucesso = new ProdutoDAO().atualizarEstoque(codigo, novoPreco);

        if (sucesso) {
            alerta(Alert.AlertType.INFORMATION, "Sucesso", "Preço atualizado com sucesso.");
            produtoEsgotadoDAO.removerProdutoSeEstoqueMaiorQueZero(codigo);
            carregarProdutosEsgotados();
            tfQTD.clear();
        } else {
            alerta(Alert.AlertType.ERROR, "Erro", "Falha ao atualizar o preço.");
        }
    }

    
    @FXML
    private void fecharJanela() {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }
    
    //----Utilitários----
    private void alerta(Alert.AlertType tipo, String titulo, String msg) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
}