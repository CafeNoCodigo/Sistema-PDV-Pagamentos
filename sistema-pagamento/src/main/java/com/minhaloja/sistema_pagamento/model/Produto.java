package com.minhaloja.sistema_pagamento.model;

import java.time.LocalDate;

public class Produto {
	
	private String nome;
    private double precoCompra;
    private double precoVenda;
    private double precoMestre;
	private double margem;
    private double lucroBruto;
    private String categoria;
    private String garantia;
    private String referencia;
    private int estoque;
    private String loja;
    private String fabricante;
    private String fornecedor;
    private String infoAdicional;
    private String codigoBarra;
    private byte[] qrCode;
    private String modelo;
    private String codigo;
    private String cor;
    private byte[] imagem;
    private int id;
    private LocalDate data;

    public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    
    public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }
    
   /* public int getId() {
		return id;
	}
    
	public void setId(int id) {
		this.id = id;
	}*/
    
    public double getPrecoMestre() {
		return precoMestre;
	}
    
	public void setPrecoMestre(double precoMestre) {
		this.precoMestre = precoMestre;
	}
	
	public String getCodigoBarra() {
		return codigoBarra;
	}
	
	public void setCodigoBarra(String string) {
		this.codigoBarra = string;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public double getPrecoCompra() {
		return precoCompra;
	}
	
	public void setPrecoCompra(double precoCompra) {
		this.precoCompra = precoCompra;
	}
	
	public double getPrecoVenda() {
		return precoVenda;
	}
	
	public void setPrecoVenda(double precoVenda) {
		this.precoVenda = precoVenda;
	}
	
	public double getMargem() {
		double margemLucro = (getLucroBruto()/getPrecoCompra())*100;
		margem = margemLucro;
		return margem;
	}
	
	public void setMargem(double margem) {
		this.margem = margem;
	}
	
	public double getLucroBruto() {
		double lucro = getPrecoMestre() - getPrecoCompra();
		this.lucroBruto = lucro;
		return lucroBruto;
	}
	
	public void setLucroBruto(double lucroBruto) {
		this.lucroBruto = lucroBruto;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getGarantia() {
		return garantia;
	}
	
	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}
	
	public String getReferencia() {
		return referencia;
	}
	
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	public int getEstoque() {
		return estoque;
	}
	
	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}
	
	public String getLoja() {
		return loja;
	}
	
	public void setLoja(String loja) {
		this.loja = loja;
	}
	
	public String getFabricante() {
		return fabricante;
	}
	
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	
	public String getFornecedor() {
		return fornecedor;
	}
	
	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}
	
}
