package entidades;

public class ItemPedido {

	private Produto produto;
	private int id_pedido;
	private int id_produto;
	private int quantidade;

	// Construtor

	public ItemPedido() {

	}

	public ItemPedido(Produto produto, int quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
	}

	public ItemPedido(Produto produto, int id_pedido, int id_produto, int quantidade) {
		this.produto = produto;
		this.id_pedido = id_pedido;
		this.id_produto = id_produto;
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}

	public int getId_produto() {
		return id_produto;
	}

	public void setId_produto(int id_produto) {
		this.id_produto = id_produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}
