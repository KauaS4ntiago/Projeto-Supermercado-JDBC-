package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySql {

	// Estabelecendo conexão com o banco de dados

	private static final String URL = "jdbc:mysql://localhost:3306/projetosupermercado";
	private static final String USUARIO = "root";
	private static final String SENHA = "";

	public static Connection conectar() throws SQLException {
		return DriverManager.getConnection(URL, USUARIO, SENHA);
	}

}

//   BANCO DE DADOS MYSQL
/*
 * create database projetosupermercado; use projetosupermercado;
 * 
 * create table Cliente( nome varchar(30), email varchar(30) unique, data_nasc
 * date, cpf varchar(11) primary key, membro boolean, senha varchar(60),
 * endereco varchar(100), ativo boolean );
 * 
 * create table Funcionario( funcao
 * enum('GERENTE','ATENDENTE','CAIXA','ESTOQUISTA','ENTREGADOR'), salario
 * decimal(5,2), nome varchar(30), email varchar(30) unique, data_nasc date, cpf
 * varchar(11) primary key, carga_semanal tinyint, senha varchar(60), endereco
 * varchar(100), ativo boolean );
 * 
 * create table Produto( id int primary key auto_increment, nome varchar(50),
 * marca varchar(30), preco decimal(4,2), qnt_estoque int );
 * 
 * create table Pedido( id int primary key auto_increment, cpf_cliente
 * varchar(11), forma_pagamento enum('PIX','DINHEIRO','DEBITO','CREDITO'),
 * data_pedido date, total decimal(7,2), foreign key(cpf_cliente) references
 * Cliente(cpf) );
 * 
 * create table Produto_Pedido( pedido_id int, produto_id int, quantidade int
 * default '1', primary key(pedido_id,produto_id), foreign key(pedido_id)
 * references Pedido(id), foreign key(produto_id) references Produto(id) );
 * 
 */
