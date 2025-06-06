package com.minhaloja.sistema_pagamento.model;

import java.time.LocalDate;

public class Funcionario {

	private String nome;
	private String numeroBi;
	private String endereco;
	private String bairro;
	private String cidade;
	private String telefone1;
	private String telefone2;
	private String nuit;
	private String cargo;
	private String contaBancaria1;
	private String contaBancaria2;
	private int salario;
	private int alimentacao;
	private int transporte;
	private byte[] imagemFuncionario;
	private byte[] imagemBi;
	private double codigo;
	private String loja;
	private LocalDate dataNascido;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	
	public LocalDate getDataNascido() {
	    return dataNascido;
	}

	public void setDataNascido(LocalDate dataNascido) {
	    this.dataNascido = dataNascido;
	}

	
	public double getCodigo() {
		return codigo;
	}
	public void setCodigo(double codigo) {
		this.codigo = codigo;
	}
	public String getLoja() {
		return loja;
	}
	public void setLoja(String loja) {
		this.loja = loja;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNumeroBi() {
		return numeroBi;
	}
	public void setNumeroBi(String numeroBi) {
		this.numeroBi = numeroBi;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getTelefone1() {
		return telefone1;
	}
	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}
	public String getTelefone2() {
		return telefone2;
	}
	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	public String getNuit() {
		return nuit;
	}
	public void setNuit(String nuit) {
		this.nuit = nuit;
	}
	
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getContaBancaria1() {
		return contaBancaria1;
	}
	public void setContaBancaria1(String contaBancaria1) {
		this.contaBancaria1 = contaBancaria1;
	}
	public String getContaBancaria2() {
		return contaBancaria2;
	}
	public void setContaBancaria2(String contaBancaria2) {
		this.contaBancaria2 = contaBancaria2;
	}
	
	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public int getSalario() {
		return salario;
	}
	public void setSalario(int salario) {
		this.salario = salario;
	}
	public int getAlimentacao() {
		return alimentacao;
	}
	public void setAlimentacao(int alimentacao) {
		this.alimentacao = alimentacao;
	}
	public int getTransporte() {
		return transporte;
	}
	public void setTransporte(int transporte) {
		this.transporte = transporte;
	}
	public byte[] getImagemFuncionario() {
		return imagemFuncionario;
	}
	public void setImagemFuncionario(byte[] imagemFuncionario) {
		this.imagemFuncionario = imagemFuncionario;
	}
	public byte[] getImagemBi() {
		return imagemBi;
	}
	public void setImagemBi(byte[] imagemBi) {
		this.imagemBi = imagemBi;
	}	
}
