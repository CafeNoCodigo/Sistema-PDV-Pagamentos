package com.minhaloja.sistema_pagamento.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.minhaloja.sistema_pagamento.dao.FuncionarioDAO;
import com.minhaloja.sistema_pagamento.dao.ServicoDAO;
import com.minhaloja.sistema_pagamento.model.Servico;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TelaCadastroServicoController {

	@FXML private TextField tfNome;
    @FXML private TextArea taDescricao;
    @FXML private TextField tfPreco;
    @FXML private ComboBox<String> cbCategoria, cbResponsavel;
    @FXML private TextField tfDuracao;
    @FXML private CheckBox chkAtivo, chkDesativo;
    @FXML private TableView<Servico> tabelaServicos;
    @FXML private TableColumn<Servico, String> colNome;
    @FXML private TableColumn<Servico, Double> colPreco;
    @FXML private TableColumn<Servico, String> colCategoria, colResponsavel;
    @FXML private TableColumn<Servico, Boolean> colAtivo;
    @FXML private Button btnSalvar, btnLimpar, btnEliminar, btnFechar, btnExportar;

    //private byte[] imagemSelecionada;
    private ServicoDAO servicoDAO = new ServicoDAO();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public void initialize() {
    	colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
    	colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
    	colAtivo.setCellValueFactory(new PropertyValueFactory<>("ativo"));
    	colResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
    	
        cbCategoria.getItems().addAll("Beleza", "Mecânica", "TI", "Outro");
        
        
        carregarFuncionariosNoComboBox();
        carregarServicos();
    }
    
    private void carregarFuncionariosNoComboBox() {
        cbResponsavel.setItems(FXCollections.observableArrayList(funcionarioDAO.listarFuncionariosChoiceBox()));
    }
    
    @FXML
    private void exportarParaPDF(ActionEvent event) {
		
		// Confirmação
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText("Você tem certeza que deseja Exportar o PDF da lista de Serviços?");

        // Espera a resposta do usuário
        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == javafx.scene.control.ButtonType.OK) {
            	Document documento = new Document();
                try {
                    // Local que será salvo
                    String userHome = System.getProperty("user.home");
                    File pastaDestino = new File(userHome, "Desktop/FPS_COMMERCE/SERVIÇOS_CADASTRADOS");
                    if (!pastaDestino.exists()) {
                        pastaDestino.mkdirs();
                    }

                    String nomeArquivo = "Serviços Cadastrados _" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".pdf";
                    File arquivo = new File(pastaDestino, nomeArquivo);

                    PdfWriter.getInstance(documento, new FileOutputStream(arquivo));
                    documento.open();

                    Font fonteNegrito = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
                    Paragraph titulo = new Paragraph("Serviços Cadastrados", fonteNegrito);
                    titulo.setAlignment(Element.ALIGN_CENTER); 
                    documento.add(titulo);
                    documento.add(new Paragraph(" "));

                    PdfPTable tabela = new PdfPTable(5);
                    tabela.setWidthPercentage(100);

                    tabela.addCell("Nome");
                    tabela.addCell("Preço");
                    tabela.addCell("Categoria");
                    tabela.addCell("Estado");
                    tabela.addCell("Responsável");

                    for (Servico servico : tabelaServicos.getItems()) {
                        tabela.addCell(servico.getNome());
                        tabela.addCell(String.valueOf(servico.getPreco()));
                        tabela.addCell(servico.getCategoria());
                        tabela.addCell(String.valueOf(servico.isAtivo()));
                        tabela.addCell(servico.getResponsavel());
                    }

                    documento.add(tabela);
                    documento.close();
                    alerta(Alert.AlertType.INFORMATION, "Gerar PDF de lista de Serviços", "PDF salvo em: " + arquivo.getAbsolutePath());

                } catch (Exception e) {
                    e.printStackTrace();
                    alerta(Alert.AlertType.ERROR, "Gerar PDF de lista de Serviços", "Erro ao gerar PDF: " + e.getMessage());
                }
            }
        });
        
    }
    
    @FXML
    private void removerServico() {
        Servico servicoSelecionado = tabelaServicos.getSelectionModel().getSelectedItem();
        if (servicoSelecionado != null) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmação");
            alerta.setHeaderText("Deseja realmente remover este serviço?");
            alerta.setContentText("Serviço: " + servicoSelecionado.getNome());

            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                try {
                    servicoDAO.remover(servicoSelecionado.getId());
                    carregarServicos(); // Atualiza a tabela
                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert erro = new Alert(Alert.AlertType.ERROR);
                    erro.setTitle("Erro");
                    erro.setHeaderText("Não foi possível remover o serviço");
                    erro.setContentText(e.getMessage());
                    erro.showAndWait();
                }
            }
        } else {
            Alert aviso = new Alert(Alert.AlertType.WARNING);
            aviso.setTitle("Aviso");
            aviso.setHeaderText("Nenhum serviço selecionado");
            aviso.setContentText("Por favor, selecione um serviço na tabela para remover.");
            aviso.showAndWait();
        }
    }


    @FXML
    private void salvarServico() {
        try {
            Servico s = new Servico();
            if(tfNome.getText().matches("")) {
            	alerta(Alert.AlertType.INFORMATION, "Atenção", "Insira o nome do serviço.");
            	return;
            }
            
            s.setNome(tfNome.getText());
            if(taDescricao.getText().matches("")) {
            	alerta(Alert.AlertType.INFORMATION, "Atenção", "Insira a descricao do serviço.");
            	return;
            }
            
            s.setDescricao(taDescricao.getText());
            if(tfPreco.getText().matches("")) {
            	alerta(Alert.AlertType.INFORMATION, "Atenção", "Insira o preço do serviço.");
            	return;
            }
            
            s.setPreco(Double.parseDouble(tfPreco.getText()));
            s.setCategoria(cbCategoria.getValue());
            s.setDuracao(Integer.parseInt(tfDuracao.getText()));
            if(chkAtivo.isSelected()) {
                s.setAtivo(true);
            }else if(chkDesativo.isSelected()) {
            	s.setAtivo(false);
            }
            
            String responsavel = cbResponsavel.getValue();
            s.setResponsavel(responsavel);
            
            servicoDAO.salvar(s);
            limparCampos();
            carregarServicos();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao salvar serviço" + e.getMessage());
        }
    }

    /*@FXML
    private void carregarImagem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Imagem");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                imagemSelecionada = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    private void carregarServicos() {
        try {
            List<Servico> lista = servicoDAO.listar();
            tabelaServicos.getItems().setAll(lista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        tfNome.clear();
        taDescricao.clear();
        tfPreco.clear();
        tfDuracao.clear();
        cbCategoria.setValue(null);
        chkAtivo.setSelected(false);
        //imagemSelecionada = null;
    }
    
    private void alerta(Alert.AlertType tipo, String titulo, String msg) {
    	Alert alerta = new Alert(tipo);
    	alerta.setTitle(titulo);
    	alerta.setHeaderText(null);
    	alerta.setContentText(msg);
    	alerta.showAndWait();
    }
    
    @FXML
    private void fecharJanela() {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }
}
