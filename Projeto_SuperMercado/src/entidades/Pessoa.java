package entidades;

import java.time.LocalDate;

public abstract class Pessoa {

	// Atributos

	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private String endereco;
	private LocalDate dataNascimento;
	private boolean ativo;

	// Construtores

	public Pessoa() {

	}

	public Pessoa(String nome, String email, LocalDate dataNascimento, String cpf, String senha, String endereco,
			boolean ativo) {
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.senha = senha;
		this.endereco = endereco;
		this.ativo = ativo;
	}

	// Gets e sets

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean getAtivo() {
		return ativo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
