package repositorios;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conexao.ConexaoMySql;
import entidades.Pedido;
import enums.FormaDePagamento;
import interfaces.ICrudGenerico;
import lista_encadeada.ListaEncadeada;

public class PedidoRepositorio implements ICrudGenerico<Pedido, Integer> {

	// CREATE (C)
	@Override
	public void salvar(Pedido pedido) {
		String sql = "INSERT INTO Pedido (cpf_cliente,forma_pagamento,data_pedido,total) VALUES (?,?,?,?)";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, pedido.getCpf_cliente());
			stmt.setString(2, pedido.getFormaPagamento().name());
			stmt.setDate(3, Date.valueOf(pedido.getData()));
			stmt.setDouble(4, pedido.getTotal());
			stmt.executeUpdate();
			System.out.println("Pedido adicionado com sucesso!");
			System.out.println();
		} catch (SQLException e) {
			System.err.println("ERRO:" + e.getMessage());
		}
	}

	// READ (R)
	@Override
	public ListaEncadeada<Pedido> listarTodos() {
		ListaEncadeada<Pedido> pedidos = new ListaEncadeada<>();
		String sql = "SELECT * FROM Pedido";
		try (Connection conn = ConexaoMySql.conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Pedido p = new Pedido();
				p.setId(rs.getInt("id"));
				p.setCpf_cliente(rs.getString("cpf_cliente"));
				p.setFormaPagamento(FormaDePagamento.valueOf(rs.getString("forma_pagamento")));
				p.setData(rs.getDate("data_pedido").toLocalDate());
				p.setTotal(rs.getDouble("total"));
				pedidos.add(p);
			}
		} catch (SQLException e) {
			 System.err.println("ERRO SQL [" + e.getErrorCode() + "]: " + e.getMessage());
			    e.printStackTrace();
		}
		return pedidos;
	}

	@Override
	public Pedido buscar(Integer id) {
		String sql = "SELECT * FROM Pedido WHERE id = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Pedido p = new Pedido();
				p.setCpf_cliente(rs.getString("cpf_cliente"));
				p.setFormaPagamento(FormaDePagamento.valueOf(rs.getString("forma_pagamento")));
				p.setData(rs.getDate("data_nasc").toLocalDate());
				p.setTotal(rs.getDouble("total"));
				return p;
			}

		} catch (SQLException e) {
			System.err.println("ERRO:" + e.getErrorCode());
		}
		return null;
	}

	// UPDATE (U)
	@Override
	public void atualizar(Integer id, String valor, Object novoValor) {
		String sql = "UPDATE Pedido SET " + valor + " = ? WHERE id = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			if (novoValor instanceof String) {
				stmt.setString(1, (String) novoValor);
			} else if (novoValor instanceof Date) {
				stmt.setDate(1, (Date) novoValor);
			} else if (novoValor instanceof FormaDePagamento) {
				stmt.setString(1, ((FormaDePagamento) novoValor).name());
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
			System.err.println("ERRO:" + e.getErrorCode());
		}
	}

	// DELETE (D)
	@Override
	public void remover(Integer id) {
		String sql = "DELETE FROM Pedido WHERE id = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
			System.out.println("Pedido removido com sucesso!");
			System.out.println();
		} catch (SQLException e) {
			System.err.println("ERRO::" + e.getErrorCode());
		}
	}

	public void remover(String cpf) {
		String sql = "DELETE FROM Pedido WHERE cpf_cliente = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, cpf);
			stmt.executeUpdate();
			System.out.println("Pedidos relacionados ao cliente removido com sucesso!");
			System.out.println();
		} catch (SQLException e) {
			System.err.println("ERRO::" + e.getErrorCode());
		}
	}

	// METODOS ADICIONAIS
	public int size() {
		String sql = "SELECT COUNT(*) AS total FROM Pedido";
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