package servicos;

import entidades.Cliente;
import lista_encadeada.ListaEncadeada;
import repositorios.ClienteRepositorio;

public class ServicosClientes {

	private ClienteRepositorio repositorio;

	// Construtor

	public ServicosClientes(ClienteRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	// Método de cadastrar

	public boolean cadastro(Cliente cliente) {
		if (cliente.getCpf().length() == 11) {
			if (repositorio.buscar(cliente.getCpf()) == null) {
				// Cadastro sendo efetuado
				repositorio.salvar(cliente);
				System.out.println("Bem-Vindo!" + repositorio.buscar(cliente.getCpf()).getNome()+"!");
				return true;
			} else {
				System.out.println("Cliente já cadastrado!");
				System.out.println();
				// Login sendo efetuado
				return login(cliente.getCpf(), cliente.getSenha());
			}
		}
		System.out.println("Cliente inválido!");
		System.out.println();
		return false;
	}

	// Método de login

	public boolean login(String cpf, String senha) {
		for (int i = 0; i < repositorio.size(); i++) {
			repositorio.atualizar(cpf,"ativo",true);
			Cliente cliente = repositorio.listarTodos().get(i);
			if (cliente.getCpf().equals(cpf) && cliente.getSenha().equals(senha)) {
				System.out.println("Login efetuado com sucesso!");
				System.out.println("Bem-Vindo!" + repositorio.buscar(cpf).getNome()+"!");
				System.out.println();
				return true;
			}
		}
		System.out.println("Cpf ou senha inválidos.");
		System.out.println();
		return false;
	}

	// Método de remoção de cliente

	public void removerCliente(String cpf) {
		repositorio.remover(cpf);

	}

	// Método de alteração de algum dado

	public void alterarCliente(String cpf, String campo, Object novoValor) {
		repositorio.atualizar(cpf, campo, novoValor);
	}

	// Métodos de desativação ou ativação do cliente

	public void desativarCliente(String cpf) {
		repositorio.atualizar(cpf, "ativo", false);
		if (repositorio.buscar(cpf).getMembro() == true) {
			repositorio.atualizar(cpf, "membro", false);
		}
	}

	public void ativarCliente(String cpf) {
		repositorio.atualizar(cpf, "ativo", true);
	}

	public void tornarMembro(String cpf) {
		if (!(repositorio.buscar(cpf) == null)) {
			System.out.println("Parabéns! Cliente virou membro com sucesso!");
			System.out.println();
			repositorio.atualizar(cpf, "membro", true);
		} else {
			System.out.println("Cliente não existe!");
			System.out.println();
		}

	}

	public void retirarMembro(String cpf) {
		if (!(repositorio.buscar(cpf) == null)) {
			System.out.println("Cliente cancelou sua assinatura com sucesso!");
			System.out.println();
			repositorio.atualizar(cpf, "membro", false);
		} else {
			System.out.println("Cliente não existe!");
			System.out.println();
		}
	}

	// Método listar todos

	public ListaEncadeada<Cliente> listarClientes() {
		return repositorio.listarTodos();
	}

	// Método de buscar por meio do Cpf

	public Cliente buscar(String cpf) {
		return repositorio.buscar(cpf);
	}

	// Retorna a quantidade de itens
	public int quantidadeClientes() {
		return repositorio.size();
	}

	// Verifica se a tabela está vazia
	public boolean estaVazio() {
		return repositorio.empty();
	}

}