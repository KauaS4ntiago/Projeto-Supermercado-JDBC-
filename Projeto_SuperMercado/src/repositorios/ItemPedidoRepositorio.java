package repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conexao.ConexaoMySql;
import entidades.ItemPedido;
import interfaces.ICrudGenerico;
import lista_encadeada.ListaEncadeada;

public class ItemPedidoRepositorio implements ICrudGenerico<ItemPedido, Integer> {

	// CREATE(C)
	@Override
	public void salvar(ItemPedido itemPedido) {
		String sql = "INSERT INTO Produto_Pedido (pedido_id,produto_id,quantidade) VALUES (?,?,?)";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, itemPedido.getId_pedido());
			stmt.setInt(2, itemPedido.getId_produto());
			stmt.setInt(3, itemPedido.getQuantidade());
			stmt.executeUpdate();
			System.out.println("Produto adicionado com sucesso!");
		} catch (SQLException e) {
			System.err.println("Erro:" + e.getMessage());
		}
	}

	// READ(R)
	@Override
	public ListaEncadeada<ItemPedido> listarTodos() {
		ListaEncadeada<ItemPedido> itensPedido = new ListaEncadeada<>();
		String sql = "SELECT * FROM Produto_Pedido";
		try (Connection conn = ConexaoMySql.conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				ItemPedido i = new ItemPedido();
				i.setId_pedido(rs.getInt("pedido_id"));
				i.setId_produto(rs.getInt("produto_id"));
				i.setQuantidade(rs.getInt("quantidade"));
				itensPedido.add(i);
			}
		} catch (SQLException e) {
			System.err.println("Erro:" + e.getErrorCode());
		}
		return itensPedido;
	}

	@Override
	public ItemPedido buscar(Integer id) {
		String sql = "SELECT * FROM Produto_Pedido WHERE id = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ItemPedido i = new ItemPedido();
				i.setId_pedido(rs.getInt("pedido_id"));
				i.setId_produto(rs.getInt("produto_id"));
				i.setQuantidade(rs.getInt("quantidade"));
				return i;
			}
		} catch (SQLException e) {
			System.err.println("Erro:" + e.getErrorCode());
		}
		return null;
	}

	// UPDATE(U)
	@Override
	public void atualizar(Integer id, String valor, Object novoValor) {
		String sql = "UPDATE Produto_Pedido SET " + valor + " = ? WHERE id = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			if (novoValor instanceof String) {
				stmt.setString(1, (String) novoValor);
			} else {
				System.out.println("Tipo de dado não suportado.");
				return;
			}
			stmt.setInt(2, id);
			stmt.executeUpdate();
			System.out.println("Atualização feita com sucesso!");
		} catch (SQLException e) {
			System.err.println("Erro:" + e.getErrorCode());
		}
	}

	// DELETE(D)
	@Override
	public void remover(Integer id) {
		String sql = "DELETE FROM Produto_Pedido WHERE id = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
			System.out.println("Item removido do pedido com sucesso!");
		} catch (SQLException e) {
			System.err.println("Erro:" + e.getErrorCode());
		}
	}

	public int size() {
		String sql = "SELECT COUNT(*) AS total FROM Produto_Pedido";
		try (Connection conn = ConexaoMySql.conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				return rs.getInt("total");
			}
		} catch (SQLException e) {
			System.err.println("Erro:" + e.getErrorCode());
		}
		return 0;
	}

	public boolean empty() {
		return size() == 0;
	}
}
