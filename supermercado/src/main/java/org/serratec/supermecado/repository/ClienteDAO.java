package main.java.org.serratec.supermecado.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import main.java.org.serratec.supermecado.common.Util;
import main.java.org.serratec.supermecado.model.Cliente;

public class ClienteDAO {

    public boolean listarClientes(Connection c) {

        ArrayList<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM vendas.cliente";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdcliente(resultado.getInt("id_cliente"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setCpf(resultado.getString("cpf"));
                cliente.setDataNasc(resultado.getDate("data_nasc"));
                cliente.setEndereco(resultado.getString("endereco"));
                cliente.setTelefone(resultado.getString("telefone"));
                clientes.add(cliente);
            }

            if (!clientes.isEmpty()) {
                System.out.println("");
                Util.linhaSimples();
                System.out.printf("%-10s%-20s%-18s%-20s%-17s%-15s%n", "CÓDIGO", "NOME", "CPF", "DATA NASCIMENTO",
                        "ENDERECO", "TELEFONE");
                Util.linhaSimples();
                for (Cliente cliente : clientes) {
                    System.out.printf("%-10s%-20s%-18s%-20s%-17s%-15s%n", cliente.getIdcliente(), cliente.getNome(),
                            cliente.getCpf(), Util.converterDateParaString(cliente.getDataNasc()),
                            cliente.getEndereco(), cliente.getTelefone());
                    Util.linhaSimples();
                }
                return true;
            } else {
                System.out.println("Nenhum cliente encontrado. Tente novamente.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }
    }

}
