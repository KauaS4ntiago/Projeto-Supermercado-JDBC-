package entidades;

import java.time.LocalDate;

import enums.FuncaoFuncionario;

public class Funcionario extends Pessoa {

	// Atributos

	private FuncaoFuncionario funcao;
	private double salario;
	private int cargaSemanal;

	// Construtores

	public Funcionario() {

	}

	public Funcionario(FuncaoFuncionario funcao, String nome, String email, LocalDate dataNascimento, String cpf,
			String senha, String endereco, boolean ativo) {
		super(nome, email, dataNascimento, cpf, senha, endereco, ativo);
		this.funcao = funcao;
		this.salario = funcao.getSalario();
		this.cargaSemanal = funcao.getCargaSemanal();
	}

	// Gets e sets

	public FuncaoFuncionario getFuncao() {
		return funcao;
	}

	public void setFuncao(FuncaoFuncionario funcao) {
		this.funcao = funcao;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public int getCargaSemanal() {
		return cargaSemanal;
	}

	public void setCargaSemanal(int cargaSemanal) {
		this.cargaSemanal = cargaSemanal;
	}

	@Override
	public String toString() {
		return "Funcionario {" + "nome='" + getNome() + '\'' + ", email='" + getEmail() + '\'' + ", dataNascimento="
				+ getDataNascimento() + ", cpf='" + getCpf() + '\'' + ", funcao=" + funcao + ", salario=" + salario
				+ ", cargaSemanal=" + cargaSemanal + ", senha='" + getSenha() + '\'' + ", endereco='" + getEndereco()
				+ '\'' + ", ativo=" + getAtivo() + '}';
	}
}