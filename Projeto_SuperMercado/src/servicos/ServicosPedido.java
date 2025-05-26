package servicos;

import entidades.ItemPedido;
import entidades.Pedido;
import lista_encadeada.ListaEncadeada;
import repositorios.PedidoRepositorio;

public class ServicosPedido {

	private PedidoRepositorio repositorio;

	// Construtor

	public ServicosPedido(PedidoRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	// Métodos de adição e remoção de pedidos

	public void adicionarPedido(Pedido pedido) {
		repositorio.salvar(pedido);
	}

	public void removerPedido(int id) {
		repositorio.remover(id);
	}
	
	public void removerPedidosDoCliente(String cpf) {
		repositorio.remover(cpf);
	}

	// Métodos de retorno

	public ListaEncadeada<Pedido> listarPedidos() {
		return repositorio.listarTodos();
	}

	public Pedido buscarPedido(int id) {
		return repositorio.buscar(id);
	}

	public ListaEncadeada<ItemPedido> listarProdutos(int id) {
		return repositorio.buscar(id).getCarrinho();
	}

	// Método de alteração de informação do pedido

	public void alterarPedido(int id, String campo, Object novoValor) {
		repositorio.atualizar(id, campo, novoValor);
	}

	// Métodos de adição ou remoção de produtos do pedido

	public void adicionarProduto(ItemPedido produto, int id) {
		repositorio.buscar(id).adicionarProduto(produto);
		System.out.println("Produto adicionado com sucesso!");
		System.out.println();
	}

	public void removerProduto(ItemPedido produto, int id) {
		repositorio.buscar(id).removerProduto(produto);
	}

	// Retorna a quantidade de itens
	public int quantidadePedido() {
		return repositorio.size();
	}

	// Verifica se a tabela está vazia
	public boolean estaVazio() {
		return repositorio.empty();
	}

}