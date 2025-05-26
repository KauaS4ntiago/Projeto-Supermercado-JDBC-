package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import entidades.Cliente;
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
			if (servicoCliente.cadastro(cliente)) {
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
						System.out.println(servicoProduto.listarProdutos());
						System.out
								.println("Digite o ID do produto para adicionar ao carrinho (ou -1 para finalizar): ");
						idProduto = sc.nextInt();
						sc.nextLine();
						if (idProduto == -1) {
							break;
						}
						Produto produtoSelecionado = servicoProduto.buscarProduto(idProduto);
						if (produtoSelecionado == null) {
							System.out.println("Produto não encontrado!");
							continue;
						}
						System.out.println("Qual a quantidade a ser adicionada?");
						int quantidade = sc.nextInt();
						sc.nextLine();
						if (produtoSelecionado.getQuantidadeEstoque() < quantidade) {
							System.out.println("Estoque insuficiente!");
							continue;
						}
						ItemPedido item = new ItemPedido(produtoSelecionado, quantidade);
						carrinho.add(item);
						servicoProduto.reduzirEstoque(idProduto, quantidade);
						System.out.println("Item adicionado ao carrinho!");
					}
					if (carrinho.size() == 0) {
						System.out.println("Carrinho vazio. Pedido cancelado.");
						continue;
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
					case 2:
						pagamento = FormaDePagamento.PIX;
					case 3:
						pagamento = FormaDePagamento.DEBITO;
					case 4:
						pagamento = FormaDePagamento.CREDITO;
					default:
						System.out.println("Forma de pagamento inválida. Pedido cancelado.");
						break;
					}
					Pedido pedido = new Pedido(cliente.getCpf(), carrinho, LocalDate.now(), pagamento);
					servicoPedido.adicionarPedido(pedido);
					servicoPedido.gerarNotaFiscal(pedido.getId());
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
				case 5:
					System.out.println("Tem certeza que deseja desativar sua conta? (s/n)");
					resp = sc.next().charAt(0);
					if (resp == 's') {
						servicoCliente.desativarCliente(cliente.getCpf());
					} else {
						System.out.println("Informação inválida! Retornando ao menu...");
						System.out.println();
					}
					break;
				case 6:
					System.out.println("Retornando ao menu principal...");
					return;
				default:
					System.out.println("Comando inválida!");
					break;
				}
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

		// Cliente(nome,email,LocalDate.of(data),cpf,false,senha,endereco,true);
		// Funcionario(FuncaoFuncionario.FUNCAO,nome,email,LocalDate.of(data),cpf,senha,endereco,true);
		// Produto p1 = new Produto(nome,marca,preco,quantidade);
		// Pedido(cpf_cliente,carrinho,LocalDate.of(data),FormaDePagamento.PAGAMENTO);

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
				menuCliente(sc, servicoCliente, servicoPedido, servicoProduto, servicoItemPedido, cliente, fmt);
				break;
			case 2:
				System.out.println("---LOGIN---");
				System.out.print("Digite seu email:");
				cpf = sc.nextLine();
				System.out.println();
				System.out.println("Digite sua senha:");
				senha = sc.nextLine();
				System.out.println();
				if (servicoCliente.login(cpf, senha)) {
					menuCliente(sc, servicoCliente, servicoPedido, servicoProduto, servicoItemPedido,
							servicoCliente.buscar(cpf), fmt);
				}

				break;
			case 3:
				System.out.println("---LOGIN---");
				System.out.print("Digite seu email: ");
				cpf = sc.nextLine();
				System.out.print("Digite sua senha: ");
				senha = sc.nextLine();
				System.out.println();
				if (servicoFuncionario.login(cpf, senha)) {
					while (true) {
						System.out.println();
						System.out.println("---MENU FUNCIONÁRIO---");
						System.out.println("1. Menu Adm(Apenas para Gerentes)");
						System.out.println("2. Alterar informação de um produto");
						System.out.println("3. Adicionar produto");
						System.out.println("4. Remover produto");
						System.out.println("5. Alterar informação pessoal");
						System.out.println("6. Voltar ao menu principal");
						int m = sc.nextInt();
						switch (m) {
						case 1:
							if (servicoFuncionario.buscar(cpf).getFuncao() == FuncaoFuncionario.GERENTE) {
								System.out.println("---MENU ADM---");
								System.out.println("1. Alterar função de um funcionário");
								System.out.println("2. Apagar cliente ou funcionário");
								System.out.println("3. Voltar ao menu de Funcionário");
								m = sc.nextInt();
								sc.nextLine();
								switch (m) {
								case 1:
									System.out.println("Digite o cpf do funcionário:");
									cpf = sc.nextLine();
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
											servicoFuncionario.alterarFuncionario(cpf, "funcao",
													FuncaoFuncionario.GERENTE);
											break;
										case 2:
											servicoFuncionario.alterarFuncionario(cpf, "funcao",
													FuncaoFuncionario.ATENDENTE);
											break;
										case 3:
											servicoFuncionario.alterarFuncionario(cpf, "funcao",
													FuncaoFuncionario.CAIXA);
											break;
										case 4:
											servicoFuncionario.alterarFuncionario(cpf, "funcao",
													FuncaoFuncionario.ESTOQUISTA);
											break;
										case 5:
											servicoFuncionario.alterarFuncionario(cpf, "funcao",
													FuncaoFuncionario.ENTREGADOR);
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
									System.out.println("Digite o cpf do cliente:");
									cpf = sc.nextLine();
									if (servicoCliente.buscar(cpf) != null) {
										servicoCliente.removerCliente(cpf);
									} else {
										System.out.println("Cliente não encontrado");
										break;
									}
								case 3:
									System.out.println("Retornando ao menu...");
									return;
								default:
									System.out.println("Comando inválido!");
									System.out.println();
									break;
								}
							} else {
								System.out.println("Acesso negado!");
								break;
							}
						case 2:
							System.out.println("Digite o id do produto a ser alterado:");
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
									System.out.println("Digite o novo nome do produto:");
									String novoNome = sc.nextLine();
									servicoProduto.atualizarProduto(id, "nome", novoNome);
									break;
								case 2:
									System.out.println("Digite o novo nome do produto:");
									String novaMarca = sc.nextLine();
									servicoProduto.atualizarProduto(id, "marca", novaMarca);
									break;
								case 3:
									System.out.println("Digite o aumento a ser incrementado:");
									double aumento = sc.nextDouble();
									servicoProduto.aumentarPreco(id, aumento);
									break;
								case 4:
									System.out.println("Digite a redução a ser incrementada:");
									double reducao = sc.nextDouble();
									servicoProduto.diminuirPreco(id, reducao);
									break;
								default:
									System.out.println("Comando inválido!");
									System.out.println();
									break;
								}
							}
						case 3:
							System.out.println("Digite o nome do produto:");
							sc.nextLine();
							nome = sc.nextLine();
							System.out.println("Digite a marca do produto:");
							String marca = sc.nextLine();
							System.out.println("Digite o preço do produto:");
							double preco = sc.nextDouble();
							sc.nextLine();
							System.out.println("Digite a quantidade em estoque:");
							int qnt = sc.nextInt();
							sc.nextLine();
							Produto produto = new Produto(nome, marca, preco, qnt);
							servicoProduto.adicionar(produto);
							break;
						case 4:
							System.out.println("Digite o id do produto a ser removido:");
							id = sc.nextInt();
							sc.nextLine();
							servicoProduto.remover(id);
							break;
						case 5:
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
								servicoCliente.alterarCliente(servicoFuncionario.buscar(cpf).getCpf(), "nome",
										novoNome);
								break;
							case 2:
								System.out.println("Digite o seu novo email");
								String novoEmail = sc.nextLine();
								servicoCliente.alterarCliente(servicoFuncionario.buscar(cpf).getCpf(), "email",
										novoEmail);
								break;
							case 3:
								data = sc.nextLine();
								LocalDate novaData = LocalDate.parse(data, fmt);
								servicoCliente.alterarCliente(servicoFuncionario.buscar(cpf).getCpf(), "data_nasc",
										novaData);
								break;
							case 4:
								String novaSenha = sc.nextLine();
								servicoCliente.alterarCliente(servicoFuncionario.buscar(cpf).getCpf(), "senha",
										novaSenha);
								break;
							case 5:
								String novoEndereco = sc.nextLine();
								servicoCliente.alterarCliente(servicoFuncionario.buscar(cpf).getCpf(), "endereco",
										novoEndereco);
								break;
							default:
								System.out.println("Comando inválido!");
								System.out.println();
							}
						case 6:
							System.out.println("Retornando ao menu principal...");
							return;
						default:
							System.out.println("Comando inválido!");
							System.out.println();
							break;
						}
					}
				}
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