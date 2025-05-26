package repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conexao.ConexaoMySql;
import entidades.Produto;
import interfaces.ICrudGenerico;
import lista_encadeada.ListaEncadeada;

public class ProdutoRepositorio implements ICrudGenerico<Produto, Integer> {

	// CREATE(C)
	@Override
	public void salvar(Produto produto) {
		String sql = "INSERT INTO Produto (nome,marca,preco,qnt_estoque) VALUES (?,?,?,?)";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, produto.getNome());
			stmt.setString(2, produto.getMarca());
			stmt.setDouble(3, produto.getPreco());
			stmt.setInt(4, produto.getQuantidadeEstoque());
			stmt.executeUpdate();
			System.out.println("Produto adicionado com sucesso!");
			System.out.println();
		} catch (SQLException e) {
			System.err.println("Erro:" + e.getMessage());
		}
	}

	// READ(R)
	@Override
	public ListaEncadeada<Produto> listarTodos() {
		ListaEncadeada<Produto> produtos = new ListaEncadeada<>();
		String sql = "SELECT * FROM Produto";
		try (Connection conn = ConexaoMySql.conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Produto p = new Produto();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setMarca(rs.getString("marca"));
				p.setPreco(rs.getDouble("preco"));
				p.setQuantidadeEstoque(rs.getInt("qnt_estoque"));
				produtos.add(p);
			}
		} catch (SQLException e) {
			System.err.println("Erro:" + e.getErrorCode());
		}
		return produtos;
	}

	@Override
	public Produto buscar(Integer id) {
		String sql = "SELECT * FROM Produto WHERE id = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Produto p = new Produto();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setMarca(rs.getString("marca"));
				p.setPreco(rs.getDouble("preco"));
				p.setQuantidadeEstoque(rs.getInt("qnt_estoque"));
				return p;
			}
		} catch (SQLException e) {
			System.err.println("Erro:" + e.getErrorCode());
		}
		return null;
	}

	// UPDATE(U)
	@Override
	public void atualizar(Integer id, String valor, Object novoValor) {
		String sql = "UPDATE Produto SET " + valor + " = ? WHERE id = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			if (novoValor instanceof String) {
				stmt.setString(1, (String) novoValor);
			} else if (novoValor instanceof Integer) {
				stmt.setInt(1, (Integer) novoValor);
			} else if (novoValor instanceof Double) {
				stmt.setDouble(1, (double) novoValor);
			} else {
				System.out.println("Tipo de dado não suportado.");
				System.out.println();
				return;
			}
			stmt.setInt(2, id);
			stmt.executeUpdate();
			System.out.println("Atualização feita com sucesso!");
			System.out.println();
		} catch (SQLException e) {
			System.err.println("Erro:" + e.getErrorCode());
		}
	}

	// DELETE(D)
	@Override
	public void remover(Integer id) {
		String sql = "DELETE FROM Produto WHERE id = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
			System.out.println("Produto removido com sucesso!");
			System.out.println();
		} catch (SQLException e) {
			System.err.println("Erro:" + e.getErrorCode());
		}
	}

	public int size() {
		String sql = "SELECT COUNT(*) AS total FROM Produto";
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
