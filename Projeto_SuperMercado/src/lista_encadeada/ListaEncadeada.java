package lista_encadeada;

public class ListaEncadeada<T> {

	// Classe privada auxiliar

	private class No<T> {

		private T dado;
		private No<T> proximo;

		No(T dado) {
			this.dado = dado;
			this.proximo = null;
		}

	}

	// Atributos auxiliares da Lista

	private No<T> cabeca;
	private int tamanho;

	// Construtor da lista

	public ListaEncadeada() {
		this.cabeca = null;
		this.tamanho = 0;
	}

	// Métodos adicionar(no final e adicionar em um índice específico)

	public void add(T elemento) {
		add(tamanho, elemento);
	}

	public void add(int i, T elemento) {
		if (i < 0 || i > tamanho)
			throw new IndexOutOfBoundsException("Índice inválido: " + i);

		No<T> novo = new No<>(elemento);

		if (i == 0) {
			novo.proximo = cabeca;
			cabeca = novo;
		} else {
			No<T> anterior = getNo(i - 1);
			novo.proximo = anterior.proximo;
			anterior.proximo = novo;
		}

		tamanho++;
	}

	// Métodos de retorno de informações

	public T get(int i) {
		return getNo(i).dado;
	}

	private No<T> getNo(int i) {
		if (i < 0 || i >= tamanho)
			throw new IndexOutOfBoundsException("Índice inválido: " + i);

		No<T> atual = cabeca;
		for (int j = 0; j < i; j++) {
			atual = atual.proximo;
		}

		return atual;
	}

	public int indexOf(T elemento) {
		No<T> atual = cabeca;
		int i = 0;

		while (atual != null) {
			if (atual.dado.equals(elemento)) {
				return i;
			}
			atual = atual.proximo;
			i++;
		}

		return -1;
	}

	// Método de troca de dados

	public T set(int i, T elemento) {
		No<T> no = getNo(i);
		T antigo = no.dado;
		no.dado = elemento;
		return antigo;
	}

	// Métodos de remover (por índice ou pelo próprio elemento)

	public T remove(int i) {
		if (i < 0 || i >= tamanho)
			throw new IndexOutOfBoundsException("Índice inválido: " + i);

		T removido;

		if (i == 0) {
			removido = cabeca.dado;
			cabeca = cabeca.proximo;
		} else {
			No<T> anterior = getNo(i - 1);
			removido = anterior.proximo.dado;
			anterior.proximo = anterior.proximo.proximo;
		}

		tamanho--;
		return removido;
	}

	public boolean remove(T elemento) {
		if (cabeca == null)
			return false;

		if (cabeca.dado.equals(elemento)) {
			cabeca = cabeca.proximo;
			tamanho--;
			return true;
		}

		No<T> atual = cabeca;

		while (atual.proximo != null && !atual.proximo.dado.equals(elemento)) {
			atual = atual.proximo;
		}

		if (atual.proximo != null) {
			atual.proximo = atual.proximo.proximo;
			tamanho--;
			return true;
		}

		return false;
	}

	// Métodos adicionais

	public boolean contains(T elemento) {
		return indexOf(elemento) != -1;
	}

	public boolean isEmpty() {
		return tamanho == 0;
	}

	public int size() {
		return tamanho;
	}

	public void clear() {
		cabeca = null;
		tamanho = 0;
	}

}
