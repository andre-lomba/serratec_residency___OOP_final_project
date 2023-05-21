package main.java.org.serratec.supermecado.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import main.java.org.serratec.supermecado.common.Util;
import main.java.org.serratec.supermecado.model.Produto;

public class ProdutoDAO {

    public boolean listarProdutos(Connection c) {

        ArrayList<Produto> produtos = new ArrayList<>();

        String sql = "SELECT * FROM vendas.produto";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(resultado.getInt("id_produto"));
                produto.setDescricao(resultado.getString("descricao"));
                produto.setCusto(resultado.getDouble("custo"));
                produto.setValorUnitario(resultado.getDouble("valor_unitario"));
                produto.setCategoria(resultado.getString("categoria"));
                produtos.add(produto);
            }

            if (!produtos.isEmpty()) {
                System.out.println("");
                Util.linhaSimples();
                System.out.printf("%-10s%-30s%-15s%-15s%-30s%n", "CÓDIGO", "NOME", "CATEGORIA", "CUSTO", "VALOR UN.");
                Util.linhaSimples();
                for (Produto produto : produtos) {
                    System.out.printf("%-10s%-30s%-15s%-15s%-30s%n", produto.getIdProduto(), produto.getDescricao(),
                            produto.getCategoria(), Util.decimal(produto.getCusto()),
                            Util.decimal(produto.getValorUnitario()));
                    Util.linhaSimples();
                }
                return true;
            } else {
                System.out.println("Nenhum produto encontrado. Tente novamente.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }
    }

}
