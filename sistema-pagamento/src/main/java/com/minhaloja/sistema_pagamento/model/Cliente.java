package com.minhaloja.sistema_pagamento.model;

import java.time.LocalDate;

public class Cliente {

	//Declarações
	private String nome;
	private String endereco;
	private String email;
	private String cidade;
	private String telefone1;
	private String telefone2;
	private String nuit;
	private LocalDate dataNascido;
	private String info;
	private String conta1;
	private String conta2;
	private LocalDate dataReg;
	private String genero;
	private String status, bi;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBi() {
		return bi;
	}
	public void setBi(String bi) {
		this.bi = bi;
	}
	private byte[] imagem;
	
	//Getters e Setters
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getNuit() {
		return nuit;
	}
	public void setNuit(String nuit) {
		this.nuit = nuit;
	}
	public LocalDate getDataNascido() {
		return dataNascido;
	}
	public void setDataNascido(LocalDate dataNascido) {
		this.dataNascido = dataNascido;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getConta1() {
		return conta1;
	}
	public void setConta1(String conta1) {
		this.conta1 = conta1;
	}
	public String getConta2() {
		return conta2;
	}
	public void setConta2(String conta2) {
		this.conta2 = conta2;
	}
	public LocalDate getDataReg() {
		return dataReg;
	}
	public void setDataReg(LocalDate dataReg) {
		this.dataReg = dataReg;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public byte[] getImagem() {
		return imagem;
	}
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
	public String getTelefone2() {
		return telefone2;
	}
	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	public String getTelefone1() {
		return telefone1;
	}
	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
