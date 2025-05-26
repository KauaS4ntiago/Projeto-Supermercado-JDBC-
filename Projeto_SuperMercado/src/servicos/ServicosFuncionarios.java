package servicos;

import entidades.Funcionario;
import enums.FuncaoFuncionario;
import lista_encadeada.ListaEncadeada;
import repositorios.FuncionarioRepositorio;

public class ServicosFuncionarios {

	private FuncionarioRepositorio repositorio;

	// Construtor

	public ServicosFuncionarios(FuncionarioRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	// Método de cadastrar

	public boolean cadastro(Funcionario funcionario) {
		if (funcionario.getCpf().length() == 11) {
			if (repositorio.buscar(funcionario.getCpf()) == null) {
				// Cadastro sendo efetuado
				repositorio.salvar(funcionario);
				return true;
			} else {
				System.out.println("Funcionario já cadastrado!");
				// Login sendo efetuado
				return login(funcionario.getCpf(), funcionario.getSenha());
			}
		}
		System.out.println("Funcionario inválido!");
		return false;
	}

	// Método de login

	public boolean login(String cpf, String senha) {
		for (int i = 0; i < repositorio.size(); i++) {
			Funcionario funcionario = repositorio.listarTodos().get(i);
			if (funcionario.getCpf().equals(cpf) && funcionario.getSenha().equals(senha)) {
				System.out.println("Login efetuado com sucesso!");
				return true;
			}
		}
		System.out.println("Email ou senha inválidos.");
		return false;
	}

	// Método de alteração de algum dado

	public void alterarFuncionario(String cpf, String campo, Object novoValor) {
		repositorio.atualizar(cpf, campo, novoValor);
	}

	// Métodos de desativação ou ativação do funcionario

	public void desativarFuncionario(String cpf) {
		repositorio.atualizar(cpf, "ativo", false);
	}

	public void ativarFuncionario(String cpf) {
		repositorio.atualizar(cpf, "ativo", true);
	}

	// Método listar todos

	public ListaEncadeada<Funcionario> listarFuncionarios() {
		return repositorio.listarTodos();
	}

	// Método de buscar por meio do Cpf

	public Funcionario buscar(String cpf) {
		return repositorio.buscar(cpf);
	}

	// Altera o cargo

	public void promover(String cpf, FuncaoFuncionario novaFuncao) {
		Funcionario funcionario = repositorio.buscar(cpf);
		if (funcionario != null) {
			funcionario.setFuncao(novaFuncao);
			funcionario.setSalario(novaFuncao.getSalario());
			funcionario.setCargaSemanal(novaFuncao.getCargaSemanal());

			// Atualiza também no banco de dados
			repositorio.atualizar(cpf, "funcao", novaFuncao);
			repositorio.atualizar(cpf, "salario", novaFuncao.getSalario());
			repositorio.atualizar(cpf, "carga_semanal", novaFuncao.getCargaSemanal());

			System.out.println("Promoção aplicada com sucesso.");
		} else {
			System.out.println("Funcionário não encontrado.");
		}
	}

	// Retorna a quantidade de itens
	public int quantidadeFuncionario() {
		return repositorio.size();
	}

	// Verifica se a tabela está vazia
	public boolean estaVazio() {
		return repositorio.empty();
	}

}