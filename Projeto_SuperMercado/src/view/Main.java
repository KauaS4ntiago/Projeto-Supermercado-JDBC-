package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import entidades.Cliente;
import entidades.Funcionario;
import entidades.ItemPedido;
import entidades.Pedido;
import entidades.Produto;
import enums.FormaDePagamento;
import enums.FuncaoFuncionario;
import lista_encadeada.ListaEncadeada;
import repositorios.ClienteRepositorio;
import repositorios.FuncionarioRepositorio;
import repositorios.ItemPedidoRepositorio;
import repositorios.PedidoRepositorio;
import repositorios.ProdutoRepositorio;
import servicos.ServicosClientes;
import servicos.ServicosFuncionarios;
import servicos.ServicosItemPedido;
import servicos.ServicosPedido;
import servicos.ServicosProduto;

public class Main {
	public static void menuCliente(Scanner sc, ServicosClientes servicoCliente, ServicosPedido servicoPedido,
			ServicosProduto servicoProduto, ServicosItemPedido servicoItemPedido, Cliente cliente,
			DateTimeFormatter fmt) {
		while (true) {
			System.out.println();
			System.out.println("---MENU CLIENTE---");
			System.out.println("1. Fazer Compras");
			System.out.println("2. Tornar-se Membro");
			System.out.println("3. Cancelar assinatura");
			System.out.println("4. Alterar informação pessoal");
			System.out.println("5. Desativar conta");
			System.out.println("6. Voltar ao Menu principal");
			int m = sc.nextInt();
			switch (m) {
			case 1:
				ListaEncadeada<ItemPedido> carrinho = new ListaEncadeada<>();
				int idProduto;
				while (true) {
					System.out.println("---MENU COMPRAS---");
					System.out.println(servicoProduto.listarProdutos().toString());
					System.out.println("Digite o ID do produto para adicionar ao carrinho (ou -1 para finalizar): ");
					idProduto = sc.nextInt();
					sc.nextLine();
					if (idProduto == -1) {
						break;
					}
					Produto produtoSelecionado = servicoProduto.buscarProduto(idProduto);
					if (produtoSelecionado == null) {
						System.out.println("Produto não encontrado!");
						break;
					}
					System.out.println("Qual a quantidade a ser adicionada?");
					int quantidade = sc.nextInt();
					sc.nextLine();
					if (produtoSelecionado.getQuantidadeEstoque() < quantidade) {
						System.out.println("Estoque insuficiente!");
						break;
					}
					ItemPedido item = new ItemPedido(produtoSelecionado, quantidade);
					carrinho.add(item);
					System.out.println();
					System.out.println("Item adicionado ao carrinho!");
					System.out.println();
					servicoProduto.reduzirEstoque(idProduto, quantidade);
				}
				if (carrinho.size() == 0) {
					System.out.println("Carrinho vazio. Pedido cancelado.");
					break;
				}
				// Selecionar forma de pagamento
				System.out.println("Escolha a forma de pagamento:");
				System.out.println("1. Dinheiro (20% de desconto)");
				System.out.println("2. PIX (20% de desconto)");
				System.out.println("3. Débito (10% de desconto)");
				System.out.println("4. Crédito (0% de desconto)");
				int opcaoPagamento = sc.nextInt();
				sc.nextLine();
				FormaDePagamento pagamento = null;
				switch (opcaoPagamento) {
				case 1:
					pagamento = FormaDePagamento.DINHEIRO;
					break;
				case 2:
					pagamento = FormaDePagamento.PIX;
					break;
				case 3:
					pagamento = FormaDePagamento.DEBITO;
					break;
				case 4:
					pagamento = FormaDePagamento.CREDITO;
					break;
				default:
					System.out.println("Forma de pagamento inválida. Pedido cancelado.");
					break;
				}
				Pedido pedido = new Pedido(servicoCliente.buscar(cliente.getCpf()).getCpf(), carrinho, LocalDate.now(),
						pagamento);
				servicoPedido.adicionarPedido(pedido);
				System.out.println(pedido.toString());
				break;
			case 2:
				System.out.println("Seus benefícios como membro são: ");
				System.out.println("1- Prioridade nas entregas");
				System.out.println("2- Desconto em compras de grande valor");
				System.out.println("3- Acesso ao nosso supermercado presencial");
				System.out.println("Tudo isso por apenas 29.99 por mês!");
				System.out.println("Deseja tornar-se membro? (s/n)");
				char resp = sc.next().charAt(0);
				if (resp == 's') {
					servicoCliente.tornarMembro(cliente.getCpf());
				} else {
					System.out.println("Informação inválida! Retornando ao menu...");
				}
				break;
			case 3:
				System.out.println("Tem certeza que deseja cancelar sua assinatura? (s/n)");
				resp = sc.next().charAt(0);
				if (resp == 's') {
					servicoCliente.retirarMembro(cliente.getCpf());
				} else {
					System.out.println("Informação inválida! Retornando ao menu...");
					System.out.println();
				}
				break;
			case 4:
				System.out.println("Qual informação vocÊ gostaria de alterar:");
				System.out.println("1. Nome");
				System.out.println("2. Email");
				System.out.println("3. Data de Nascimento");
				System.out.println("4. Senha");
				System.out.println("5. Endereço");
				int alter = sc.nextInt();
				sc.nextLine();
				switch (alter) {
				case 1:
					System.out.println("Digite o seu novo nome:");
					String novoNome = sc.nextLine();
					servicoCliente.alterarCliente(cliente.getCpf(), "nome", novoNome);
					break;
				case 2:
					System.out.println("Digite o seu novo email");
					String novoEmail = sc.nextLine();
					servicoCliente.alterarCliente(cliente.getCpf(), "email", novoEmail);
					break;
				case 3:
					String data = sc.nextLine();
					LocalDate novaData = LocalDate.parse(data, fmt);
					servicoCliente.alterarCliente(cliente.getCpf(), "data_nasc", novaData);
					break;
				case 4:
					String novaSenha = sc.nextLine();
					servicoCliente.alterarCliente(cliente.getCpf(), "senha", novaSenha);
					break;
				case 5:
					String novoEndereco = sc.nextLine();
					servicoCliente.alterarCliente(cliente.getCpf(), "endereco", novoEndereco);
					break;
				default:
					System.out.println("Comando inválido!");
					System.out.println();
				}
				break;
			case 5:
				System.out.println("Tem certeza que deseja desativar sua conta? (s/n)");
				resp = sc.next().charAt(0);
				if (resp == 's') {
					servicoCliente.desativarCliente(cliente.getCpf());
					return;
				} else {
					System.out.println("Informação inválida! Retornando ao menu...");
					System.out.println();
				}
				break;
			case 6:
				System.out.println("Retornando ao menu principal...");
				System.out.println();
				return;
			default:
				System.out.println("Comando inválida!");
				System.out.println();
				break;
			}
		}
	}

	public static void menuFuncionario(Scanner sc, ServicosFuncionarios servicoFuncionario,
			ServicosClientes servicoCliente, ServicosProduto servicoProduto, Funcionario funcionario,
			DateTimeFormatter fmt, ServicosPedido servicoPedido) {
		while (true) {
			System.out.println("---MENU FUNCIONÁRIO---");
			System.out.println("1. Menu Adm(Apenas para Gerentes)");
			System.out.println("2. Alterar informação de um produto");
			System.out.println("3. Adicionar produto");
			System.out.println("4. Remover produto");
			System.out.println("5. Listar todos os pedidos");
			System.out.println("6. Alterar informação pessoal");
			System.out.println("7. Voltar ao menu principal");
			int m = sc.nextInt();
			sc.nextLine();
			switch (m) {
			case 1:
				if (servicoFuncionario.buscar(funcionario.getCpf()).getFuncao() == FuncaoFuncionario.GERENTE) {
					Boolean menuADM = true;
					while (menuADM) {
						System.out.println("---MENU ADM---");
						System.out.println("1. Alterar função de um funcionário");
						System.out.println("2. Cadastrar um novo funcionário");
						System.out.println("3. Apagar cliente ou funcionário");
						System.out.println("4. Listar todos os funcionários");
						System.out.println("5. Listar todos os clientes");
						System.out.println("6. Voltar ao menu de Funcionário");
						m = sc.nextInt();
						sc.nextLine();
						switch (m) {
						case 1:
							System.out.println("Digite o cpf do funcionário:");
							String cpf = sc.nextLine();
							if (servicoFuncionario.buscar(cpf) != null) {
								System.out.println("Para qual função deseja atualizar:");
								System.out.println("1.GERENTE");
								System.out.println("2.ATENDENTE");
								System.out.println("3.CAIXA");
								System.out.println("4.ESTOQUISTA");
								System.out.println("5.ENTREGADOR");
								m = sc.nextInt();
								sc.nextLine();
								switch (m) {
								case 1:
									servicoFuncionario.alterarFuncionario(cpf, "funcao", FuncaoFuncionario.GERENTE);
									break;
								case 2:
									servicoFuncionario.alterarFuncionario(cpf, "funcao", FuncaoFuncionario.ATENDENTE);
									break;
								case 3:
									servicoFuncionario.alterarFuncionario(cpf, "funcao", FuncaoFuncionario.CAIXA);
									break;
								case 4:
									servicoFuncionario.alterarFuncionario(cpf, "funcao", FuncaoFuncionario.ESTOQUISTA);
									break;
								case 5:
									servicoFuncionario.alterarFuncionario(cpf, "funcao", FuncaoFuncionario.ENTREGADOR);
									break;
								default:
									System.out.println("Comando inválido!");
									System.out.println();
									break;
								}
								break;
							} else {
								System.out.println("Funcionário não encontrado");
								break;
							}
						case 2:
							System.out.println("---CADASTRO---");
							System.out.print("Digite o nome:");
							String nome = sc.nextLine();
							System.out.print("Digite o cpf:");
							cpf = sc.nextLine();
							if (servicoCliente.buscar(cpf) != null) {
								System.out.println("Funcionário já cadastrado!");
								break;
							}
							System.out.print("Digite a função:");
							String funcao = sc.nextLine();
							System.out.print("Digite a Data de Nascimento:");
							String data = sc.nextLine();
							LocalDate aux = LocalDate.parse(data, fmt);
							System.out.print("Digite o endereco:");
							String endereco = sc.nextLine();
							System.out.print("Digite o email:");
							String email = sc.nextLine();
							System.out.print("Digite a senha:");
							String senha = sc.nextLine();
							Funcionario f = new Funcionario(FuncaoFuncionario.valueOf(funcao), nome, email, aux, cpf,
									senha, endereco, true);
							servicoFuncionario.cadastro(f);
							System.out.println();
							break;
						case 3:
							System.out.println("Qual deseja apagar:");
							System.out.println("1.Cliente");
							System.out.println("2.Funcionário");
							int r = sc.nextInt();
							sc.nextLine();
							if (r == 1) {
								System.out.print("Digite o cpf do cliente que será removido:");
								cpf = sc.nextLine();
								if (servicoCliente.buscar(cpf) != null) {
									servicoPedido.removerPedidosDoCliente(cpf);
									servicoCliente.removerCliente(cpf);
								} else {
									System.out.println("Cliente não encontrado");

								}
							} else if (r == 2) {
								System.out.print("Digite o cpf do funcionário que será removido:");
								cpf = sc.nextLine();
								if (servicoFuncionario.buscar(cpf) != null) {
									servicoFuncionario.removerFuncionario(cpf);
								} else {
									System.out.println("Funcionário não encontrado");

								}
							} else {
								System.out.println("Comando inválido!");
							}
							break;
						case 4:
							if (servicoFuncionario.estaVazio()) {
								System.out.println("Nenhum funcionário registrado.");
							} else {
								System.out.println("--Funcionarios--");
								System.out.println(servicoFuncionario.listarFuncionarios());
							}
							break;
						case 5:
							if (servicoCliente.estaVazio()) {
								System.out.println("Não existe pedidos no momento.");
							} else {
								System.out.println("--Clientes--");
								System.out.println(servicoCliente.listarClientes());
							}
							break;
						case 6:
							System.out.println("Retornando ao menu...");
							menuADM = false;
							break;
						default:
							System.out.println("Comando inválido!");
							System.out.println();
							break;
						}
					}
				} else {
					System.out.println("Acesso negado!");
				}
				break;
			case 2:
				System.out.print("Digite o id do produto a ser alterado:");
				int id = sc.nextInt();
				sc.nextLine();
				if (servicoProduto.buscarProduto(id) == null) {
					System.out.println("Produto não existe");
				} else {
					System.out.println("O que deseja alterar:");
					System.out.println("1. Alterar nome do produto");
					System.out.println("2. Alterar marca do produto");
					System.out.println("3. Aumentar preço");
					System.out.println("4. Diminuir preço");
					m = sc.nextInt();
					sc.nextLine();
					switch (m) {
					case 1:
						System.out.print("Digite o novo nome do produto:");
						String novoNome = sc.nextLine();
						servicoProduto.atualizarProduto(id, "nome", novoNome);
						break;
					case 2:
						System.out.print("Digite a nova marca do produto:");
						String novaMarca = sc.nextLine();
						servicoProduto.atualizarProduto(id, "marca", novaMarca);
						break;
					case 3:
						System.out.print("Digite o aumento a ser incrementado:");
						double aumento = sc.nextDouble();
						sc.nextLine();
						servicoProduto.aumentarPreco(id, aumento);
						break;
					case 4:
						System.out.print("Digite a redução a ser incrementada:");
						double reducao = sc.nextDouble();
						sc.nextLine();
						servicoProduto.diminuirPreco(id, reducao);
						break;
					default:
						System.out.println("Comando inválido!");
						System.out.println();
						break;
					}
				}
				break;
			case 3:
				System.out.print("Digite o nome do produto:");
				String nome = sc.nextLine();
				System.out.print("Digite a marca do produto:");
				String marca = sc.nextLine();
				System.out.print("Digite o preço do produto:");
				double preco = sc.nextDouble();
				sc.nextLine();
				System.out.print("Digite a quantidade em estoque:");
				int qnt = sc.nextInt();
				sc.nextLine();
				Produto produto = new Produto(nome, marca, preco, qnt);
				System.out.println();
				servicoProduto.adicionar(produto);
				break;
			case 4:
				System.out.print("Digite o id do produto a ser removido:");
				id = sc.nextInt();
				sc.nextLine();
				servicoProduto.remover(id);
				break;
			case 5:
				if (servicoPedido.estaVazio()) {
					System.out.println("Não existe pedidos no momento.");
				} else {
					System.out.println("---PEDIDOS---");
					System.out.println(servicoPedido.listarPedidos());
				}
				break;
			case 6:
				System.out.println("Qual informação vocÊ gostaria de alterar:");
				System.out.println("1. Nome");
				System.out.println("2. Email");
				System.out.println("3. Data de Nascimento");
				System.out.println("4. Senha");
				System.out.println("5. Endereço");
				int alter = sc.nextInt();
				sc.nextLine();
				switch (alter) {
				case 1:
					System.out.print("Digite o novo nome:");
					String novoNome = sc.nextLine();
					servicoFuncionario.alterarFuncionario(funcionario.getCpf(), "nome", novoNome);
					break;
				case 2:
					System.out.print("Digite o novo email:");
					String novoEmail = sc.nextLine();
					servicoFuncionario.alterarFuncionario(funcionario.getCpf(), "email", novoEmail);
					break;
				case 3:
					System.out.print("Digite a nova data de nascimento:");
					String data = sc.nextLine();
					LocalDate novaData = LocalDate.parse(data, fmt);
					servicoFuncionario.alterarFuncionario(funcionario.getCpf(), "data_nasc", novaData);
					break;
				case 4:
					System.out.print("Digite a nova senha:");
					String novaSenha = sc.nextLine();
					servicoFuncionario.alterarFuncionario(funcionario.getCpf(), "senha", novaSenha);
					break;
				case 5:
					System.out.print("Digite o novo endereço:");
					String novoEndereco = sc.nextLine();
					servicoFuncionario.alterarFuncionario(funcionario.getCpf(), "endereco", novoEndereco);
					break;
				default:
					System.out.println("Comando inválido!");
					System.out.println();
				}
				break;
			case 7:
				System.out.println("Retornando ao menu principal...");
				return;
			default:
				System.out.println("Comando inválido!");
				System.out.println();
				break;
			}
		}

	}

	public static void main(String[] args) {
		// --- Repositórios ---
		ClienteRepositorio clienteRepo = new ClienteRepositorio();
		ProdutoRepositorio produtoRepo = new ProdutoRepositorio();
		PedidoRepositorio pedidoRepo = new PedidoRepositorio();
		ItemPedidoRepositorio itemPedidoRepo = new ItemPedidoRepositorio();
		FuncionarioRepositorio funcionarioRepo = new FuncionarioRepositorio();

		// --- Serviços ---
		ServicosFuncionarios servicoFuncionario = new ServicosFuncionarios(funcionarioRepo);
		ServicosClientes servicoCliente = new ServicosClientes(clienteRepo);
		ServicosProduto servicoProduto = new ServicosProduto(produtoRepo);
		ServicosPedido servicoPedido = new ServicosPedido(pedidoRepo);
		ServicosItemPedido servicoItemPedido = new ServicosItemPedido(itemPedidoRepo);
		Scanner sc = new Scanner(System.in);

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		int n = 0;

		// Primeiro Login para acesso ao Menu ADM
		// cpf = "12345678910"
		// senha = "adm1234"

		while (true) {
			System.out.println("---MENU PRINCIPAL---");
			System.out.println("1. Cadastrar-se como cliente");
			System.out.println("2. Entrar como cliente");
			System.out.println("3. Entrar como funcionário");
			System.out.println("4. Encerrar programa");
			n = sc.nextInt();
			sc.nextLine();
			switch (n) {
			case 1:
				System.out.println("---CADASTRO---");
				System.out.print("Digite seu nome:");
				String nome = sc.nextLine();
				System.out.print("Digite seu cpf:");
				String cpf = sc.nextLine();
				if (servicoCliente.buscar(cpf) != null) {
					System.out.println("Cliente já cadastrado!");
					break;
				}
				System.out.print("Digite sua Data de Nascimento:");
				String data = sc.nextLine();
				LocalDate aux = LocalDate.parse(data, fmt);
				System.out.print("Digite seu endereco:");
				String endereco = sc.nextLine();
				System.out.print("Digite seu email:");
				String email = sc.nextLine();
				System.out.print("Digite sua senha:");
				String senha = sc.nextLine();
				Cliente cliente = new Cliente(nome, email, aux, cpf, false, senha, endereco, true);
				System.out.println();
				if (servicoCliente.cadastro(cliente)) {
					menuCliente(sc, servicoCliente, servicoPedido, servicoProduto, servicoItemPedido,
							servicoCliente.buscar(cpf), fmt);
				}
				break;
			case 2:
				System.out.println("---LOGIN---");
				System.out.print("Digite seu cpf:");
				cpf = sc.nextLine();
				System.out.print("Digite sua senha:");
				senha = sc.nextLine();
				System.out.println();
				if (servicoCliente.login(cpf, senha)) {
					menuCliente(sc, servicoCliente, servicoPedido, servicoProduto, servicoItemPedido,
							servicoCliente.buscar(cpf), fmt);
				}
				break;
			case 3:
				System.out.println("---LOGIN---");
				System.out.print("Digite seu cpf: ");
				cpf = sc.nextLine();
				System.out.print("Digite sua senha: ");
				senha = sc.nextLine();
				System.out.println();
				if (servicoFuncionario.login(cpf, senha)) {
					menuFuncionario(sc, servicoFuncionario, servicoCliente, servicoProduto,
							servicoFuncionario.buscar(cpf), fmt, servicoPedido);
				}
				break;
			case 4:
				System.out.println("Encerrando o Programa...");
				sc.close();
				return;
			default:
				System.out.println("Comando inválido!");
				System.out.println();
				break;
			}
		}

	}
}