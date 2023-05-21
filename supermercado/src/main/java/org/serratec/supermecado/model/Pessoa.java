package main.java.org.serratec.supermecado.model;

import java.sql.Date;

public class Pessoa {
    
    private String nome;
	private String cpf;
	private Date data_nasc;
	private String endereco;
	private String telefone;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNasc() {
		return data_nasc;
	}

	public void setDataNasc(Date data_nasc) {
		this.data_nasc = data_nasc;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}
