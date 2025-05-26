package repositorios;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conexao.ConexaoMySql;
import entidades.Cliente;
import interfaces.ICrudGenerico;
import lista_encadeada.ListaEncadeada;

public class ClienteRepositorio implements ICrudGenerico<Cliente, String> {

	// CREATE (C)
	@Override
	public void salvar(Cliente cliente) {
		String sql = "INSERT INTO Cliente (nome,email,data_nasc,cpf,membro,senha,endereco,ativo) VALUES (?,?,?,?,?,?,?,?)";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getEmail());
			stmt.setDate(3, Date.valueOf(cliente.getDataNascimento()));
			stmt.setString(4, cliente.getCpf());
			stmt.setBoolean(5, cliente.getMembro());
			stmt.setString(6, cliente.getSenha());
			stmt.setString(7, cliente.getEndereco());
			stmt.setBoolean(8, cliente.getAtivo());
			stmt.executeUpdate();
			System.out.println("Cliente cadastrado com sucesso!");
			System.out.println();
		} catch (SQLException e) {
			System.err.println("ERRO:" + e.getMessage());
		}
	}

	// READ (R)
	@Override
	public ListaEncadeada<Cliente> listarTodos() {
		ListaEncadeada<Cliente> clientes = new ListaEncadeada<>();
		String sql = "SELECT * FROM Cliente WHERE ativo = true";
		try (Connection conn = ConexaoMySql.conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				if(rs.getBoolean("ativo")) {
					Cliente c = new Cliente();
					c.setNome(rs.getString("nome"));
					c.setEmail(rs.getString("email"));
					c.setDataNascimento(rs.getDate("data_nasc").toLocalDate());
					c.setCpf(rs.getString("cpf"));
					c.setMembro(rs.getBoolean("membro"));
					c.setSenha(rs.getString("senha"));
					c.setEndereco(rs.getString("endereco"));
					c.setAtivo(rs.getBoolean("ativo"));
					clientes.add(c);
				}
			}
		} catch (SQLException e) {
			System.err.println("ERRO:" + e.getErrorCode());
		}
		return clientes;
	}

	@Override
	public Cliente buscar(String cpf) {
		String sql = "SELECT * FROM Cliente WHERE cpf = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, cpf);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Cliente c = new Cliente();
				c.setNome(rs.getString("nome"));
				c.setEmail(rs.getString("email"));
				c.setCpf(rs.getString("cpf"));
				c.setMembro(rs.getBoolean("membro"));
				c.setSenha(rs.getString("senha"));
				c.setEndereco(rs.getString("endereco"));
				c.setAtivo(rs.getBoolean("ativo"));
				return c;
			}

		} catch (SQLException e) {
			System.err.println("ERRO:" + e.getErrorCode());
		}
		return null;
	}

	// UPDATE (U)
	@Override
	public void atualizar(String cpf, String valor, Object novoValor) {
		String sql = "UPDATE Cliente SET " + valor + " = ? WHERE cpf = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			if (novoValor instanceof String) {
				stmt.setString(1, (String) novoValor);
			} else if (novoValor instanceof Date) {
				stmt.setDate(1, (Date) novoValor);
			} else if (novoValor instanceof Boolean) {
				stmt.setBoolean(1, (boolean) novoValor);
			} else {
				System.out.println("Tipo de dado não suportado.");
				System.out.println();
				return;
			}
			stmt.setString(2, cpf);
			stmt.executeUpdate();
			System.out.println("Atualização feita com sucesso!");
			System.out.println();
		} catch (SQLException e) {
			System.err.println("ERRO:" + e.getErrorCode());
		}
	}

	// DELETE (D)
	@Override
	public void remover(String cpf) {
		String sql = "DELETE FROM Cliente WHERE cpf = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, cpf);
			stmt.executeUpdate();
			System.out.println("Cliente apagado com sucesso!");
			System.out.println();
		} catch (SQLException e) {
			System.err.println("ERRO::" + e.getErrorCode());
		}
	}

	public int size() {
		String sql = "SELECT COUNT(*) AS total FROM Cliente WHERE ativo = true";
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