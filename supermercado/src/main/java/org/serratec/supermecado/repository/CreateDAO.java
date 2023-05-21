package main.java.org.serratec.supermecado.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDAO {
    public static boolean existeBD(Connection conexaoInicial, String bd, String url, String user, String password) {

        String sql = "SELECT FROM pg_database WHERE datname = '" + bd + "';";

        try (PreparedStatement consulta = conexaoInicial.prepareStatement(sql)) {

            ResultSet resultado = consulta.executeQuery();
            System.out.println("Conectando...");

            if (!resultado.next()) {
                criarBD(conexaoInicial, bd, url, user, password);
            } else {
                Connection conexaoFinal = DriverManager.getConnection(url, user, password);
                criarSchema(conexaoFinal);
            }

            resultado.close();

            return true;

        } catch (Exception e1) {
            System.err.println("Não foi possível se conectar com o database");
            e1.printStackTrace();
            return false;
        }
    }

    private static boolean criarBD(Connection conexaoInicial, String bdCriado, String url, String user,
            String password) {

        try (Statement stmt = conexaoInicial.createStatement()) {

            String sql = "CREATE DATABASE " + bdCriado + ";";

            stmt.executeUpdate(sql);
            stmt.close();

            Connection conexaoFinal = DriverManager.getConnection(url, user, password);
            criarSchema(conexaoFinal);

            return true;

        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao criar o database: " + e.getMessage());
            return false;
        }

    }

    private static boolean criarSchema(Connection conexaoFinal) {

        try (Statement stmt = conexaoFinal.createStatement()) {

            String schema = "vendas";

            String sql = "CREATE SCHEMA IF NOT EXISTS " + schema + ";";

            stmt.executeUpdate(sql);
            stmt.close();

            criarTabelas(conexaoFinal, schema);

            return true;

        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao criar o schema: " + e.getMessage());
            return false;
        }
    }

    private static boolean criarTabelas(Connection conexaoFinal, String schema) {

        try (Statement stmt = conexaoFinal.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS " + schema + ".cliente (\r\n"
                    + "    id_cliente SERIAL PRIMARY KEY,\r\n" + "    nome VARCHAR(50),\r\n"
                    + "    cpf CHAR(11) UNIQUE,\r\n" + "    data_nasc DATE,\r\n" + "    endereco VARCHAR(100),\r\n"
                    + "    telefone VARCHAR(20)\r\n" + "); " + "CREATE TABLE IF NOT EXISTS " + schema + ".produto (\r\n"
                    + "    id_produto SERIAL PRIMARY KEY,\r\n" + "    descricao VARCHAR(50) UNIQUE,\r\n"
                    + "    custo DOUBLE PRECISION,\r\n" + "    valor_unitario DOUBLE PRECISION,\r\n"
                    + "    categoria VARCHAR(20)\r\n" + "); " + "CREATE TABLE IF NOT EXISTS " + schema + ".pedido (\r\n"
                    + "    id_pedido SERIAL PRIMARY KEY,\r\n" + "    data_emissao DATE,\r\n"
                    + "    data_entrega DATE,\r\n" + "    valor_total DOUBLE PRECISION CHECK(valor_total > 0),\r\n"
                    + "    observacao VARCHAR(100),\r\n" + "    id_cliente INT REFERENCES vendas.cliente\r\n" + "); "
                    + "CREATE TABLE IF NOT EXISTS " + schema + ".pedido_itens (\r\n"
                    + "    id_pedido_itens SERIAL PRIMARY KEY,\r\n" + "    valor_unitario DOUBLE PRECISION,\r\n"
                    + "    qtd_produto INT CHECK(qtd_produto > 0)," + "    valor_desconto DOUBLE PRECISION,\r\n"
                    + "    id_produto INT REFERENCES vendas.produto,\r\n"
                    + "    id_pedido INT REFERENCES vendas.pedido\r\n" + "); " + "INSERT INTO " + schema
                    + ".cliente\r\n" + "(nome, cpf, data_nasc, endereco, telefone)\r\n" + "VALUES\r\n"
                    + "('Amanda Rodrigues', '98776565443', '1988-12-30', 'Rua A', '24988345675'),\r\n"
                    + "('André Lomba', '12343223445', '1995-03-03', 'Rua B', '21987766643'),\r\n"
                    + "('Fabiano Correa', '34567654334', '1993-01-26', 'Rua C', '21988654323'),\r\n"
                    + "('Keren Martins', '23221223445', '1998-05-23', 'Rua D', '24981123345'),\r\n"
                    + "('Júlia Soares', '09889776856', '2005-08-12', 'Rua E', '24987733442'),\r\n"
                    + "('Pedro Oliveira', '12233454334', '1993-02-01', 'Rua F', '24981125668')"
                    + "ON CONFLICT DO NOTHING;\r\n" + "\r\n" + "INSERT INTO " + schema + ".produto\r\n"
                    + "(descricao, custo, valor_unitario, categoria)\r\n" + "VALUES\r\n"
                    + "('Arroz Branco 5kg', 6.50, 18.29, 'Cereais'),\r\n"
                    + "('Lava-louças 300ml', 2.50, 6.50, 'Prod. Limpeza'),\r\n"
                    + "('Sabonete barra', 0.59, 3.49, 'Hig. Pessoal'),\r\n"
                    + "('Refri. de Cola 2L', 2.50, 9.49, 'Bebidas'),\r\n"
                    + "('Tomate italiano Kg', 1.89, 7.89, 'Hortifruti')" + "ON CONFLICT DO NOTHING;";

            stmt.executeUpdate(sql);
            stmt.close();

            return true;
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao criar as tabelas: " + e.getMessage());
            return false;
        }

    }
}
