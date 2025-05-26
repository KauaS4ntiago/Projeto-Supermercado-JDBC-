package servicos;

import entidades.Produto;
import lista_encadeada.ListaEncadeada;
import repositorios.ProdutoRepositorio;

public class ServicosProduto {

	private ProdutoRepositorio repositorio;

	// Construtor

	public ServicosProduto(ProdutoRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	// Métodos de adição e remoção de produtos

	public void adicionar(Produto produto) {
		repositorio.salvar(produto);
	}

	public void remover(int id) {
		repositorio.remover(id);
	}

	// Método de alteração de informação

	public void atualizarProduto(int id, String campo, Object novoValor) {
		repositorio.atualizar(id, campo, novoValor);
	}

	// Método de retorno de produtos

	public ListaEncadeada<Produto> listarProdutos() {
		return repositorio.listarTodos();
	}

	public Produto buscarProduto(int id) {
		return repositorio.buscar(id);
	}

	// Métodos de aumento e redução de preços

	public void aumentarPreco(int id, double aumento) {
		Double novoPreco = repositorio.buscar(id).getPreco() + aumento;
		repositorio.buscar(id).setPreco(novoPreco);
	}

	public void diminuirPreco(int id, double reducao) {
		Double novoPreco = repositorio.buscar(id).getPreco() - reducao;
		repositorio.buscar(id).setPreco(novoPreco);
	}

	// Métodos de aumento e redução de quantidade de produtos em estoque

	public void reduzirEstoque(int id, int qnt) {
		if (qnt > repositorio.buscar(id).getQuantidadeEstoque()) {
			System.out.println("Quantidade fora de estoque!");
		} else {
			repositorio.atualizar(id, "qnt_estoque", repositorio.buscar(id).getQuantidadeEstoque() - qnt);
		}

	}

	public void aumentarEstoque(int id, int qnt) {
		repositorio.atualizar(id, "qnt_estoque", repositorio.buscar(id).getQuantidadeEstoque() + qnt);
	}

	// Retorna a quantidade de itens
	public int quantidadeProduto() {
		return repositorio.size();
	}

	// Verifica se a tabela está vazia
	public boolean estaVazio() {
		return repositorio.empty();
	}

}