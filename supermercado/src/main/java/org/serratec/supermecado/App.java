package main.java.org.serratec.supermecado;

import java.sql.SQLException;

import main.java.org.serratec.supermecado.config.Conexao;

public class App {
	public static void main(String[] args) throws SQLException, InterruptedException {
		Conexao conexao = new Conexao();
		conexao.conectar();
	}

}
