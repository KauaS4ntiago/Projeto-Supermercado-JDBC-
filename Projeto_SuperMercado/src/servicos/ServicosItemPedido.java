package servicos;

import entidades.ItemPedido;
import lista_encadeada.ListaEncadeada;
import repositorios.ItemPedidoRepositorio;

public class ServicosItemPedido {

	private final ItemPedidoRepositorio repositorio;

	public ServicosItemPedido() {
		this.repositorio = new ItemPedidoRepositorio();
	}

	public ServicosItemPedido(ItemPedidoRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	// Método de adicionar e remover produtos do pedido

	public void adicionarItem(ItemPedido item) {
		if (item == null || item.getQuantidade() <= 0) {
			System.out.println("Item inválido ou quantidade menor ou igual a zero.");
			System.out.println();
			return;
		}
		repositorio.salvar(item);
	}

	public void removerItem(int id) {
		repositorio.remover(id);
	}

	// Métodos de retorno
	public ListaEncadeada<ItemPedido> listarItens() {
		return repositorio.listarTodos();
	}

	public ItemPedido buscarItemPorId(int id) {
		return repositorio.buscar(id);
	}

	// Atualiza um campo específico de um item

	public void atualizarItem(int id, String campo, Object novoValor) {
		repositorio.atualizar(id, campo, novoValor);
	}

	// Retorna a quantidade de itens
	public int quantidadeItens() {
		return repositorio.size();
	}

	// Verifica se a tabela está vazia
	public boolean estaVazio() {
		return repositorio.empty();
	}
}