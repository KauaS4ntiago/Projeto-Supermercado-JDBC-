package entidades;

public class Produto {

	// Atributos

	private int id;
	private String nome;
	private String marca;
	private double preco;
	private int quantidadeEstoque;

	// Construtores

	public Produto() {

	}

	public Produto(String nome, String marca, double preco, int quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
		this.nome = nome;
		this.marca = marca;
		this.preco = preco;
	}

	// Gets e sets

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(int quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
}
