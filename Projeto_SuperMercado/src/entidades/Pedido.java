package entidades;

import java.time.LocalDate;

import enums.FormaDePagamento;
import lista_encadeada.ListaEncadeada;

public class Pedido {

	// Atributos

	private int id;
	private String cpf_cliente;
	private ListaEncadeada<ItemPedido> carrinho = new ListaEncadeada<>();
	private FormaDePagamento formaPagamento;
	private LocalDate data;
	private double total;

	// Construtores

	public Pedido() {

	}

	public Pedido(String cpf_cliente, ListaEncadeada<ItemPedido> carrinho, LocalDate data,
			FormaDePagamento formaPagamento) {
		this.cpf_cliente = cpf_cliente;
		this.carrinho = carrinho;
		this.data = data;
		this.formaPagamento = formaPagamento;
		this.total = getTotal();
		this.id++;
	}

	// Gets e sets

	public FormaDePagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaDePagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCpf_cliente() {
		return cpf_cliente;
	}

	public void setCpf_cliente(String cpf_cliente) {
		this.cpf_cliente = cpf_cliente;

	}

	public ListaEncadeada<ItemPedido> getCarrinho() {
		return carrinho;
	}

	public void adicionarProduto(ItemPedido p) {
		this.carrinho.add(p);
	}

	public void removerProduto(ItemPedido p) {
		this.carrinho.remove(p);
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public double getTotal() {
		total = 0;
		for (int i = 0; i < carrinho.size(); i++) {
			total += (carrinho.get(i).getProduto().getPreco() * carrinho.get(i).getQuantidade());
		}
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "------PEDIDO_" + id + "------\n" + "Cpf do cliente: " + cpf_cliente + "\n" + "Forma de pagamento: "
				+ formaPagamento + "\n" + "Data da compra: " + data + "\n" + "Produtos: " + carrinho + "\n"
				+ "Total: " + getTotal() * (1 - formaPagamento.getDesconto());
	}
}
