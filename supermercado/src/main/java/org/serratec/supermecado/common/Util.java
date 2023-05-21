package main.java.org.serratec.supermecado.common;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Util {
    public static void linhaDupla() {
		System.out.println("=".repeat(100));
	}

	public static final void linhaSimples() {
		System.out.println("-".repeat(100));
	}

	public static void menuItens() {

		System.out.println("""
				1 - Cadastrar novo pedido
				2 - Pesquisar pedidos
				0 - Sair do programa""");
		linhaDupla();
	}

	public static void menuPesquisar() {

		System.out.println("");
		linhaDupla();
		System.out.println("""
				1 - Listar todos os pedidos
				2 - Filtrar por código
				3 - Filtrar por cliente
				4 - Filtrar por data
				0 - Voltar pro menu principal""");
		linhaDupla();
	}

	public static void menuFiltrarPorCodigo() {

		System.out.println("");
		linhaDupla();
		System.out.println("""
				1 - Alterar dados do pedido
				2 - Excluir o pedido
				0 - Voltar prara o menu""");
		linhaDupla();
	}

	public static void menuListarTodos() {

		System.out.println("");
		linhaDupla();
		System.out.println("""
				1 - Alterar dados de um pedido
				2 - Excluir um pedido
				0 - Voltar prara o menu""");
		linhaDupla();
	}

	public static void menuAlterar() {

		System.out.println("");
		linhaDupla();
		System.out.println("""
				1 - Alterar cliente
				2 - Alterar/adicionar produtos
				3 - Alterar data de emissão
				4 - Alterar data de entrega
				5 - Alterar observação
				0 - Voltar""");
		linhaDupla();
	}

	public static void menuAlterarAddProd() {

		System.out.println("");
		linhaDupla();
		System.out.println("""
				1 - Alterar um produto do pedido
				2 - Adicionar um produto ao pedido
				3 - Excluir um produto do pedido
				0 - Voltar""");
		linhaDupla();
	}

	public static void menuAlteraQtdProd() {
		System.out.println("");
		linhaDupla();
		System.out.println("""
				1 - Alterar quantidade
				2 - Alterar o produto""");
		linhaDupla();
	}

	public static void menuCadastrar() {

	}

	public static void cabecalho() {
		Util.linhaDupla();
		System.out.printf("%55s%n", "SUPERMERCADO DO GRUPO 6");
		Util.linhaDupla();
	}

	public static String converterDateParaString(Date data) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");

			String dataString = sdf.format(data);

			return dataString;
		} catch (Exception e) {
			return "SEM DATA";
		}
	}

	public static Date converterStringParaDate(String data) {

		String[] partes = data.split("/");
		int dia = Integer.parseInt(partes[0]);
		int mes = Integer.parseInt(partes[1]);
		int ano = Integer.parseInt(partes[2]);
		Date dataSQL = Date.valueOf(LocalDate.of(ano, mes, dia));

		return dataSQL;
	}

	public static double decimal(double num) {

		double numCerto = (double) (Math.round(num * 100.0) / 100.0);

		return numCerto;
	}
}
