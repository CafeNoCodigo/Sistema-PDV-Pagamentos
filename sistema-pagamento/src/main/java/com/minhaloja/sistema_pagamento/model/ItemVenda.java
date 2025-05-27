package com.minhaloja.sistema_pagamento.model;

import java.time.LocalDate;


public class ItemVenda {
    public Produto produto;
    public int quantidade;
    private LocalDate data;
	private double troco;
	private double valPago;
	private double totalProduto;
	private String FormaPagamento;
	
	public void decrementarQuantidade() {
	    if (quantidade > 0) {
	        quantidade--;
	    }
	}


    public ItemVenda(Produto produto) {
        this.produto = produto;
        this.quantidade = 1;
    }
    
    public ItemVenda() {
		this.totalProduto = 0.0;
	}
    
    public void adicionarPreco(double preco) {
		totalProduto += preco;
	}
	
	public void resetar () {
		totalProduto = 0.0;
	}
	
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public double getTroco() {
		return troco;
	}
	public void setTroco(double troco) {
		this.troco = troco;
	}
	public double getValPago() {
		return valPago;
	}
	public void setValPago(double valPago) {
		this.valPago = valPago;
	}
	public double getTotalProduto() {
		return totalProduto;
	}
	public void setTotalProduto(double totalProduto) {
		this.totalProduto = totalProduto;
	}
	public String getFormaPagamento() {
		return FormaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		FormaPagamento = formaPagamento;
	}

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void incrementarQuantidade() {
        quantidade++;
    }

    public String getNome() {
        return produto.getNome();
    }

    public double getPrecoUnitario() {
        return produto.getPrecoVenda();
    }

    public double getSubtotal() {
        return produto.getPrecoVenda() * quantidade;
    }
}
