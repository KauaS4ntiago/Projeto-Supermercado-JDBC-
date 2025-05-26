package interfaces;

import lista_encadeada.ListaEncadeada;

public interface ICrudGenerico<T, ID> {

	void salvar(T entidade);

	T buscar(ID id);

	void atualizar(ID id, String entidade, Object novoValor);

	void remover(ID id);

	ListaEncadeada<T> listarTodos();
}
