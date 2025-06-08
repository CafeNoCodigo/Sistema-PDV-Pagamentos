package com.minhaloja.sistema_pagamento.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.minhaloja.sistema_pagamento.dao.ProdutoDAO;
import com.minhaloja.sistema_pagamento.model.Produto;
import com.minhaloja.sistema_pagamento.util.WindowManager;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class telaEstoqueAtualController {
	
	@FXML public TableView<Produto> tabelaProdutos2;
    @FXML private TableColumn<Produto, String> colCodigoBarra, colNome, colCategoria,colReferencia, colLoja, colFornecedor, colModelo, colCodigo;
    @FXML private TableColumn<Produto, Double> colEstoque, colPrecoCompra, colPrecoMestre, colPrecoVenda, colFabricante, colLucroBruto, colMargem;
    
    @FXML private Button btnFechar, btnExcluir, btnTodos, btnAtualizarPreco, btnAtualizarPreco1, btnExportarPdf;
    
    @FXML private TextField tfBusca, tfPreco;
    
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    
    @FXML
    public void initialize() {
        colCodigoBarra.setCellValueFactory(new PropertyValueFactory<>("codigoBarra"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        colPrecoVenda.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
        colPrecoCompra.setCellValueFactory(new PropertyValueFactory<>("precoCompra"));
        colPrecoMestre.setCellValueFactory(new PropertyValueFactory<>("precoMestre"));
        colReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
        colLoja.setCellValueFactory(new PropertyValueFactory<>("loja"));
        colFabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
        colLucroBruto.setCellValueFactory(new PropertyValueFactory<>("lucroBruto"));
        colMargem.setCellValueFactory(new PropertyValueFactory<>("Margem"));
        colFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        
        
        tabelaProdutos2.setItems(produtoDAO.listarProdutos());
        
        tfBusca.textProperty().addListener((obs, antigo, novo) -> {
            String texto = novo == null ? "" : novo.trim();

            if (texto.isEmpty()) {
                tabelaProdutos2.setItems(produtoDAO.listarProdutos());
            } else {
                tabelaProdutos2.setItems(produtoDAO.buscarProdutosPorTexto(texto));
            }
        });
    }
    
    @FXML
    private void exportarParaPDF(ActionEvent event) {
        Document documento = new Document();
        try {
            // Local que será salvo
            String userHome = System.getProperty("user.home");
            File pastaDestino = new File(userHome, "Desktop/FPS_COMMERCE/ESTOQUE DÍSPONIVEL");
            if (!pastaDestino.exists()) {
                pastaDestino.mkdirs();
            }

            String nomeArquivo = "Estoque de_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")) + ".pdf";
            File arquivo = new File(pastaDestino, nomeArquivo);

            PdfWriter.getInstance(documento, new FileOutputStream(arquivo));
            documento.open();

            Font fonteNegrito = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph titulo = new Paragraph("Relatório de Produtos Em Estoque", fonteNegrito);
            titulo.setAlignment(Element.ALIGN_CENTER); 
            documento.add(titulo);
            documento.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(4);
            tabela.setWidthPercentage(100);

            tabela.addCell("Nome");
            tabela.addCell("Código de Barra");
            tabela.addCell("Categoria");
            tabela.addCell("Quantidade");

            for (Produto p : tabelaProdutos2.getItems()) {
                tabela.addCell(p.getNome());
                tabela.addCell(p.getCodigoBarra());
                tabela.addCell(p.getCategoria());
                tabela.addCell(String.format(" %d", p.getEstoque()));
            }

            documento.add(tabela);
            documento.close();
            alerta(Alert.AlertType.INFORMATION, "Gerar PDF de Estoque", "PDF salvo em: " + arquivo.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            alerta(Alert.AlertType.ERROR, "Gerar PDF de Estoque", "Erro ao gerar PDF: " + e.getMessage());
        }
    }
    
    @FXML
    private void listarTodos() {
    	colCodigoBarra.setCellValueFactory(new PropertyValueFactory<>("codigoBarra"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        colPrecoVenda.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
        colPrecoCompra.setCellValueFactory(new PropertyValueFactory<>("precoCompra"));
        colPrecoMestre.setCellValueFactory(new PropertyValueFactory<>("precoMestre"));
        colReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
        colLoja.setCellValueFactory(new PropertyValueFactory<>("loja"));
        colFabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
        colLucroBruto.setCellValueFactory(new PropertyValueFactory<>("lucroBruto"));
        colMargem.setCellValueFactory(new PropertyValueFactory<>("Margem"));
        colFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        
        tabelaProdutos2.setItems(produtoDAO.listarProdutos());
    }
    
    @FXML
    private void atualizarPrecoSelecionado() {
        Produto produtoSelecionado = tabelaProdutos2.getSelectionModel().getSelectedItem();

        if (produtoSelecionado == null) {
            alerta(Alert.AlertType.WARNING, "Seleção necessária", "Por favor, selecione um item na tabela.");
            return;
        }

        String valorTexto = tfPreco.getText();
        double novoPreco;

        try {
            novoPreco = Double.parseDouble(valorTexto);
        } catch (NumberFormatException e) {
            alerta(Alert.AlertType.ERROR, "Valor inválido", "Insira um valor numérico válido.");
            return;
        }

        int codigo = produtoSelecionado.getId();

        boolean sucesso = new ProdutoDAO().atualizarPreco(codigo, novoPreco);

        if (sucesso) {
            alerta(Alert.AlertType.INFORMATION, "Sucesso", "Preço atualizado com sucesso.");
            tabelaProdutos2.setItems(produtoDAO.listarProdutos());
            tfPreco.clear();
        } else {
            alerta(Alert.AlertType.ERROR, "Erro", "Falha ao atualizar o preço.");
        }
    }
    
    @FXML
    private void atualizarPrecoCompraSelecionado() {
        Produto produtoSelecionado = tabelaProdutos2.getSelectionModel().getSelectedItem();

        if (produtoSelecionado == null) {
            alerta(Alert.AlertType.WARNING, "Seleção necessária", "Por favor, selecione um item na tabela.");
            return;
        }

        String valorTexto = tfPreco.getText();
        double novoPreco;

        try {
            novoPreco = Double.parseDouble(valorTexto);
        } catch (NumberFormatException e) {
            alerta(Alert.AlertType.ERROR, "Valor inválido", "Insira um valor numérico válido.");
            return;
        }

        int codigo = produtoSelecionado.getId();

        boolean sucesso = new ProdutoDAO().atualizarPrecoCompra(codigo, novoPreco);

        if (sucesso) {
            alerta(Alert.AlertType.INFORMATION, "Sucesso", "Preço atualizado com sucesso.");
            tabelaProdutos2.setItems(produtoDAO.listarProdutos());
            tfPreco.clear();
        } else {
            alerta(Alert.AlertType.ERROR, "Erro", "Falha ao atualizar o preço.");
        }
    }

	public void abrirTelaCadastroProduto2() {
		WindowManager.abrirTelaCadastroProduto();
	}
	
	
	
	@FXML
    private void fecharJanela() {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }
	
	@FXML
	private void excluirProduto() {
		Produto selecionado = tabelaProdutos2.getSelectionModel().getSelectedItem();
        
        if (selecionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Nenhuma seleção");
            alerta.setHeaderText("Nenhum produto selecionado");
            alerta.setContentText("Por favor, selecione um produto na tabela.");
            alerta.showAndWait();
            return;
        }

        // Confirmação
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText("Você tem certeza que deseja excluir este produto?");
        confirmacao.setContentText("Produto: " + selecionado.getNome());

        // Espera a resposta do usuário
        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == javafx.scene.control.ButtonType.OK) {
                boolean sucesso = produtoDAO.excluirProduto(selecionado.getCodigoBarra());

                if (sucesso) {
                    Alert sucessoAlerta = new Alert(Alert.AlertType.INFORMATION);
                    sucessoAlerta.setTitle("Sucesso");
                    sucessoAlerta.setHeaderText("Produto excluído com sucesso.");
                    sucessoAlerta.showAndWait();
                    carregarProdutosNaTabela();
                    
                } else {
                    Alert erro = new Alert(Alert.AlertType.ERROR);
                    erro.setTitle("Erro");
                    erro.setHeaderText("Erro ao excluir produto");
                    erro.setContentText("Não foi possível excluir o produto.");
                    erro.showAndWait();
                }
            }
        });
	}
	
	private void carregarProdutosNaTabela() {
        ProdutoDAO dao = new ProdutoDAO();
        ObservableList<Produto> lista = dao.listarProdutos();
        tabelaProdutos2.setItems(lista);
    }
	
	private void alerta(Alert.AlertType tipo, String titulo, String msg) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
}
