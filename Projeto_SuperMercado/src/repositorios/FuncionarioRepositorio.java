package repositorios;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conexao.ConexaoMySql;
import entidades.Funcionario;
import enums.FuncaoFuncionario;
import interfaces.ICrudGenerico;
import lista_encadeada.ListaEncadeada;

public class FuncionarioRepositorio implements ICrudGenerico<Funcionario, String> {

	// CREATE (C)
	@Override
	public void salvar(Funcionario funcionario) {
		String sql = "INSERT INTO Funcionario (funcao,salario,nome,email,data_nasc,cpf,carga_semanal,senha,endereco,ativo) VALUES (?,?,?,?,?,?,?,?,?,?)";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, funcionario.getFuncao().name());
			stmt.setDouble(2, funcionario.getSalario());
			stmt.setString(3, funcionario.getNome());
			stmt.setString(4, funcionario.getEmail());
			stmt.setDate(5, Date.valueOf(funcionario.getDataNascimento()));
			stmt.setString(6, funcionario.getCpf());
			stmt.setInt(7, funcionario.getCargaSemanal());
			stmt.setString(8, funcionario.getSenha());
			stmt.setString(9, funcionario.getEndereco());
			stmt.setBoolean(10, funcionario.getAtivo());
			stmt.executeUpdate();
			System.out.println("Funcionário adicionado com sucesso!");
		} catch (SQLException e) {
			System.err.println("ERRO:" + e.getMessage());
		}
	}

	// READ (R)
	@Override
	public ListaEncadeada<Funcionario> listarTodos() {
		ListaEncadeada<Funcionario> funcionarios = new ListaEncadeada<>();
		String sql = "SELECT * FROM Funcionario";
		try (Connection conn = ConexaoMySql.conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Funcionario f = new Funcionario();
				f.setFuncao(FuncaoFuncionario.valueOf(rs.getString("funcao")));
				f.setSalario(rs.getDouble("salario"));
				f.setNome(rs.getString("nome"));
				f.setEmail(rs.getString("email"));
				f.setDataNascimento(rs.getDate("data_nasc").toLocalDate());
				f.setCpf(rs.getString("cpf"));
				f.setCargaSemanal(rs.getInt("carga_semanal"));
				f.setSenha(rs.getString("senha"));
				f.setEndereco(rs.getString("endereco"));
				f.setAtivo(rs.getBoolean("ativo"));
				funcionarios.add(f);
			}
		} catch (SQLException e) {
			System.err.println("ERRO:" + e.getErrorCode());
		}
		return funcionarios;
	}

	@Override
	public Funcionario buscar(String cpf) {
		String sql = "SELECT * FROM Funcionario WHERE cpf = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, cpf);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Funcionario f = new Funcionario();
				f.setFuncao(FuncaoFuncionario.valueOf(rs.getString("funcao")));
				f.setSalario(rs.getDouble("salario"));
				f.setNome(rs.getString("nome"));
				f.setEmail(rs.getString("email"));
				f.setDataNascimento(rs.getDate("data_nasc").toLocalDate());
				f.setCpf(rs.getString("cpf"));
				f.setCargaSemanal(rs.getInt("carga_semanal"));
				f.setSenha(rs.getString("senha"));
				f.setEndereco(rs.getString("endereco"));
				f.setAtivo(rs.getBoolean("ativo"));
				return f;
			}

		} catch (SQLException e) {
			System.err.println("ERRO:" + e.getErrorCode());
		}
		return null;
	}

	// UPDATE (U)
	@Override
	public void atualizar(String cpf, String valor, Object novoValor) {
		String sql = "UPDATE Funcionario SET " + valor + " = ? WHERE cpf = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			if (novoValor instanceof String) {
				stmt.setString(1, (String) novoValor);
			} else if (novoValor instanceof Date) {
				stmt.setDate(1, (Date) novoValor);
			} else if (novoValor instanceof FuncaoFuncionario) {
				stmt.setString(1, ((FuncaoFuncionario) novoValor).name());
			} else if (novoValor instanceof Boolean) {
				stmt.setBoolean(1, (boolean) novoValor);
			} else if (novoValor instanceof Integer) {
				stmt.setInt(1, (Integer) novoValor);
			} else {
				System.out.println("Tipo de dado não suportado.");
				return;
			}
			stmt.setString(2, cpf);
			stmt.executeUpdate();
			System.out.println("Atualização feita com sucesso!");
		} catch (SQLException e) {
			System.err.println("ERRO:" + e.getErrorCode());
		}
	}

	// DELETE (D)
	@Override
	public void remover(String cpf) {
		String sql = "DELETE FROM Funcionario WHERE cpf = ?";
		try (Connection conn = ConexaoMySql.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, cpf);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("ERRO::" + e.getErrorCode());
		}
	}

	public int size() {
		String sql = "SELECT COUNT(*) AS total FROM Funcionario";
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