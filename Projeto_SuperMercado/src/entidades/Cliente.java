package entidades;

import java.time.LocalDate;

public class Cliente extends Pessoa {

	// Atributos

	private boolean membro;

	// Construtores

	public Cliente() {

	}

	public Cliente(String nome, String email, LocalDate dataNascimento, String cpf, boolean membro, String senha,
			String endereco, boolean ativo) {
		super(nome, email, dataNascimento, cpf, senha, endereco, ativo);
		this.membro = membro;
	}

	// Gets e sets

	public boolean getMembro() {
		return membro;
	}

	public void setMembro(boolean membro) {
		this.membro = membro;
	}

	@Override
	public String toString() {
		return "Cliente {" + "nome='" + getNome() + '\'' + ", email='" + getEmail() + '\'' + ", dataNascimento="
				+ getDataNascimento() + ", cpf='" + getCpf() + '\'' + ", membro=" + membro + ", senha='" + getSenha()
				+ '\'' + ", endereco='" + getEndereco() + '\'' + ", ativo=" + getAtivo() + '}';
	}
}
