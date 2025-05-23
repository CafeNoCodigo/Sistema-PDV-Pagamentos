package com.minhaloja.sistema_pagamento.controller;

import com.minhaloja.sistema_pagamento.dao.ProdutoDAO;
import com.minhaloja.sistema_pagamento.model.Produto;
import com.minhaloja.sistema_pagamento.util.WindowManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class telaEstoqueAtualController {
	
	@FXML public TableView<Produto> tabelaProdutos2;
	@FXML private TableColumn<Produto, String> colId;
    @FXML private TableColumn<Produto, String> colCodigoBarra;
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TableColumn<Produto, String> colCategoria;
    @FXML private TableColumn<Produto, Double> colEstoque;
    @FXML private TableColumn<Produto, Double> colPrecoVenda;
    @FXML private TableColumn<Produto, Double> colPrecoMestre;
    @FXML private TableColumn<Produto, Double> colPrecoCompra;
   // @FXML private TableColumn<Produto, Double> colReferencia;
    
    @FXML private Button btnFechar;
    
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

	public void abrirTelaCadastroProduto2() {
		WindowManager.abrirTelaCadastroProduto();
	}
	
	public void initialize() {
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCodigoBarra.setCellValueFactory(new PropertyValueFactory<>("codigoBarra"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        colPrecoVenda.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
        colPrecoCompra.setCellValueFactory(new PropertyValueFactory<>("precoCompra"));
        colPrecoMestre.setCellValueFactory(new PropertyValueFactory<>("precoMestre"));
        //colReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
        
        tabelaProdutos2.setItems(produtoDAO.listarProdutos()); 
    }
	
	@FXML
    private void fecharJanela() {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }
}
