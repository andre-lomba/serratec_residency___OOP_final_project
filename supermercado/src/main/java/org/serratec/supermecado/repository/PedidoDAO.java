package main.java.org.serratec.supermecado.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.postgresql.util.PSQLException;

import main.java.org.serratec.supermecado.common.Util;
import main.java.org.serratec.supermecado.model.Pedido;
import main.java.org.serratec.supermecado.model.PedidoItens;

public class PedidoDAO {

    public boolean listarTodos(Connection c) {

        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id_pedido, " + "data_emissao, " + "data_entrega, " + "valor_total," + "nome "
                + "FROM vendas.pedido ped " + "LEFT JOIN vendas.cliente c ON c.id_cliente = ped.id_cliente "
                + "ORDER BY data_emissao;";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(resultado.getInt("id_pedido"));
                pedido.setDataEmissao(resultado.getDate("data_emissao"));
                pedido.setDataEntrega(resultado.getDate("data_entrega"));
                pedido.setValorTotal(resultado.getDouble("valor_total"));
                pedido.getCliente().setNome(resultado.getString("nome"));
                pedidos.add(pedido);
            }

            if (!pedidos.isEmpty()) {
                System.out.println("");
                Util.linhaSimples();
                System.out.printf("%-20s%-20s%-20s%-20s%s%n", "CÓDIGO", "CLIENTE", "DATA EMISSÃO", "DATA ENTREGA",
                        "VALOR TOTAL");
                Util.linhaSimples();
                for (Pedido pedido : pedidos) {
                    System.out.printf("%-20s%-20s%-20s%-20s%s%n", pedido.getIdPedido(), pedido.getCliente().getNome(),
                            Util.converterDateParaString(pedido.getDataEmissao()),
                            Util.converterDateParaString(pedido.getDataEntrega()),
                            Util.decimal(pedido.getValorTotal()));
                    Util.linhaSimples();
                }
                return true;
            } else {
                System.out.println("Nenhum pedido registrado ainda.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }

    }

    public boolean listarPorCod(Connection c, int cod) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id_pedido, " + "data_emissao, " + "data_entrega, " + "valor_total," + "nome "
                + "FROM vendas.pedido ped " + "LEFT JOIN vendas.cliente c ON c.id_cliente = ped.id_cliente "
                + "WHERE id_pedido = " + cod + " ORDER BY data_emissao;";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(resultado.getInt("id_pedido"));
                pedido.setDataEmissao(resultado.getDate("data_emissao"));
                pedido.setDataEntrega(resultado.getDate("data_entrega"));
                pedido.setValorTotal(resultado.getDouble("valor_total"));
                pedido.getCliente().setNome(resultado.getString("nome"));
                pedidos.add(pedido);
            }

            if (!pedidos.isEmpty()) {
                System.out.println("");
                Util.linhaSimples();
                System.out.printf("%-20s%-20s%-20s%-20s%s%n", "CÓDIGO", "CLIENTE", "DATA EMISSÃO", "DATA ENTREGA",
                        "VALOR TOTAL");
                Util.linhaSimples();
                for (Pedido pedido : pedidos) {
                    System.out.printf("%-20s%-20s%-20s%-20s%s%n", pedido.getIdPedido(), pedido.getCliente().getNome(),
                            Util.converterDateParaString(pedido.getDataEmissao()),
                            Util.converterDateParaString(pedido.getDataEntrega()),
                            Util.decimal(pedido.getValorTotal()));
                    Util.linhaSimples();
                }
                return true;
            } else {
                System.out.println("Nenhum pedido encontrado com esse código. Tente novamente.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }
    }

    public boolean listarPorNome(Connection c, String nome) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id_pedido, " + "data_emissao, " + "data_entrega, " + "valor_total, " + "nome "
                + "FROM vendas.pedido ped " + "LEFT JOIN vendas.cliente c ON c.id_cliente = ped.id_cliente  "
                + "WHERE nome LIKE ? " + "ORDER BY data_emissao;";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.setString(1, "%" + nome + "%");
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(resultado.getInt("id_pedido"));
                pedido.setDataEmissao(resultado.getDate("data_emissao"));
                pedido.setDataEntrega(resultado.getDate("data_entrega"));
                pedido.setValorTotal(resultado.getDouble("valor_total"));
                pedido.getCliente().setNome(resultado.getString("nome"));
                pedidos.add(pedido);
            }

            if (!pedidos.isEmpty()) {
                System.out.println("");
                Util.linhaSimples();
                System.out.printf("%-20s%-20s%-20s%-20s%s%n", "CÓDIGO", "CLIENTE", "DATA EMISSÃO", "DATA ENTREGA",
                        "VALOR TOTAL");
                Util.linhaSimples();
                for (Pedido pedido : pedidos) {
                    System.out.printf("%-20s%-20s%-20s%-20s%s%n", pedido.getIdPedido(), pedido.getCliente().getNome(),
                            Util.converterDateParaString(pedido.getDataEmissao()),
                            Util.converterDateParaString(pedido.getDataEntrega()),
                            Util.decimal(pedido.getValorTotal()));
                    Util.linhaSimples();
                }
                return true;
            } else {
                System.out.println("Nenhum pedido encontrado com esse cliente. Tente novamente.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }
    }

    public boolean listarPorData(Connection c, Date data) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id_pedido, " + "data_emissao, " + "data_entrega, " + "valor_total, " + "nome "
                + "FROM vendas.pedido ped " + "LEFT JOIN vendas.cliente c ON c.id_cliente = ped.id_cliente  "
                + "WHERE data_emissao = ? " + "ORDER BY id_pedido;";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.setDate(1, data);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(resultado.getInt("id_pedido"));
                pedido.setDataEmissao(resultado.getDate("data_emissao"));
                pedido.setDataEntrega(resultado.getDate("data_entrega"));
                pedido.setValorTotal(resultado.getDouble("valor_total"));
                pedido.getCliente().setNome(resultado.getString("nome"));
                pedidos.add(pedido);
            }

            if (!pedidos.isEmpty()) {
                System.out.println("");
                Util.linhaSimples();
                System.out.printf("%-20s%-20s%-20s%-20s%s%n", "CÓDIGO", "CLIENTE", "DATA EMISSÃO", "DATA ENTREGA",
                        "VALOR TOTAL");
                Util.linhaSimples();
                for (Pedido pedido : pedidos) {
                    System.out.printf("%-20s%-20s%-20s%-20s%s%n", pedido.getIdPedido(), pedido.getCliente().getNome(),
                            Util.converterDateParaString(pedido.getDataEmissao()),
                            Util.converterDateParaString(pedido.getDataEntrega()),
                            Util.decimal(pedido.getValorTotal()));
                    Util.linhaSimples();
                }
                return true;
            } else {
                System.out.println("Nenhum pedido encontrado com essa data. Tente novamente.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }
    }

    public boolean detalharPedido(Connection c, int cod) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT ped.id_pedido, " + "ped.data_emissao, " + "ped.data_entrega, " + "ped.valor_total, "
                + "observacao, c.nome, " + "c.cpf, " + "c.endereco," + "pi.valor_unitario, " + "pi.qtd_produto, "
                + "pi.valor_desconto, " + "prod.descricao, " + "prod.categoria " + "FROM vendas.pedido ped "
                + "LEFT JOIN vendas.pedido_itens pi ON pi.id_pedido = ped.id_pedido "
                + "LEFT JOIN vendas.cliente c ON c.id_cliente = ped.id_cliente "
                + "LEFT JOIN vendas.produto prod ON prod.id_produto = pi.id_produto " + "WHERE ped.id_pedido = " + cod
                + " ORDER BY ped.id_pedido;";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(resultado.getInt("id_pedido"));
                pedido.setDataEmissao(resultado.getDate("data_emissao"));
                pedido.setDataEntrega(resultado.getDate("data_entrega"));
                pedido.setValorTotal(resultado.getDouble("valor_total"));
                pedido.setObservacao(resultado.getString("observacao"));
                pedido.getCliente().setNome(resultado.getString("nome"));
                pedido.getCliente().setCpf(resultado.getString("cpf"));
                pedido.getCliente().setEndereco(resultado.getString("endereco"));
                PedidoItens itens = new PedidoItens();
                itens.setValorUnitario(resultado.getDouble("valor_unitario"));
                itens.setQtdProduto(resultado.getInt("qtd_produto"));
                itens.setValorDesconto(resultado.getDouble("valor_desconto"));
                itens.getProduto().setDescricao(resultado.getString("descricao"));
                itens.getProduto().setCategoria(resultado.getString("categoria"));
                pedido.adicionarProduto(itens);
                pedidos.add(pedido);
            }

            if (!pedidos.isEmpty()) {
                Util.linhaDupla();
                System.out.printf("%-2s%-20s%-38s%-38s%2s%n", "|", "CÓDIGO: " + pedidos.get(0).getIdPedido(),
                        "DATA EMISSÃO: " + Util.converterDateParaString(pedidos.get(0).getDataEmissao()),
                        "DATA ENTREGA: " + Util.converterDateParaString(pedidos.get(0).getDataEntrega()), "|");
                System.out.println("|" + "-".repeat(98) + "|");
                System.out.printf("%-2s%-38s%-20s%-38s%2s%n", "|", "CLIENTE: " + pedidos.get(0).getCliente().getNome(),
                        "CPF: " + pedidos.get(0).getCliente().getCpf(),
                        "ENDEREÇO: " + pedidos.get(0).getCliente().getEndereco(), "|");
                System.out.println("|" + "_".repeat(98) + "|");

                for (Pedido pedido : pedidos) {
                    for (PedidoItens itens : pedido.getItens()) {
                        System.out.printf("%-2s%-2s%-6s%-22s%-20s%-22s%-22s%2s%2s%n", "|", "|",
                                itens.getQtdProduto() + "x", itens.getProduto().getDescricao(),
                                " - " + itens.getProduto().getCategoria(),
                                "| VALOR UN.: R$" + Util.decimal(itens.getValorUnitario()),
                                "| DESCONTO UN.: R$" + Util.decimal(itens.getValorDesconto()), "|", "|");
                    }

                }
                System.out.println(
                        "|" + "_|" + "_".repeat(49) + "|" + "_".repeat(21) + "|" + "_".repeat(22) + "|_" + "|");
                System.out.printf("%-2s%-74s%12s%10s%2s%n", "|",
                        "OBS: " + (pedidos.get(0).getObservacao() == null ? "" : pedidos.get(0).getObservacao()),
                        "VALOR TOTAL:", "R$" + Util.decimal(pedidos.get(0).getValorTotal()), "|");
                Util.linhaDupla();

                return true;
            } else {
                System.out.println("Nenhum pedido encontrado com esse código. Tente novamente.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.getStackTrace();
            return false;
        }
    }

    public boolean detalharPedidoItens(Connection c, int cod) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT ped.id_pedido, " + "ped.data_emissao, " + "ped.data_entrega, " + "ped.valor_total, "
                + "observacao, c.nome, " + "c.cpf, " + "c.endereco," + "pi.id_pedido_itens, pi.valor_unitario, "
                + "pi.qtd_produto, " + "pi.valor_desconto, " + "prod.descricao, " + "prod.categoria "
                + "FROM vendas.pedido ped " + "LEFT JOIN vendas.pedido_itens pi ON pi.id_pedido = ped.id_pedido "
                + "LEFT JOIN vendas.cliente c ON c.id_cliente = ped.id_cliente "
                + "LEFT JOIN vendas.produto prod ON prod.id_produto = pi.id_produto " + "WHERE ped.id_pedido = " + cod
                + " ORDER BY ped.id_pedido;";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(resultado.getInt("id_pedido"));
                pedido.setDataEmissao(resultado.getDate("data_emissao"));
                pedido.setDataEntrega(resultado.getDate("data_entrega"));
                pedido.setValorTotal(resultado.getDouble("valor_total"));
                pedido.setObservacao(resultado.getString("observacao"));
                pedido.getCliente().setNome(resultado.getString("nome"));
                pedido.getCliente().setCpf(resultado.getString("cpf"));
                pedido.getCliente().setEndereco(resultado.getString("endereco"));
                PedidoItens itens = new PedidoItens();
                itens.setIdPedidoItens(resultado.getInt("id_pedido_itens"));
                itens.setValorUnitario(resultado.getDouble("valor_unitario"));
                itens.setQtdProduto(resultado.getInt("qtd_produto"));
                itens.setValorDesconto(resultado.getDouble("valor_desconto"));
                itens.getProduto().setDescricao(resultado.getString("descricao"));
                itens.getProduto().setCategoria(resultado.getString("categoria"));
                pedido.adicionarProduto(itens);
                pedidos.add(pedido);
            }

            if (!pedidos.isEmpty()) {
                Util.linhaDupla();
                System.out.printf("%-2s%-20s%-38s%-38s%2s%n", "|", "CÓDIGO: " + pedidos.get(0).getIdPedido(),
                        "DATA EMISSÃO: " + Util.converterDateParaString(pedidos.get(0).getDataEmissao()),
                        "DATA ENTREGA: " + Util.converterDateParaString(pedidos.get(0).getDataEntrega()), "|");
                System.out.println("|" + "-".repeat(98) + "|");
                System.out.printf("%-2s%-38s%-20s%-38s%2s%n", "|", "CLIENTE: " + pedidos.get(0).getCliente().getNome(),
                        "CPF: " + pedidos.get(0).getCliente().getCpf(),
                        "ENDEREÇO: " + pedidos.get(0).getCliente().getEndereco(), "|");
                System.out.println("|" + "_".repeat(98) + "|");
                for (Pedido pedido : pedidos) {
                    for (PedidoItens itens : pedido.getItens()) {
                        System.out.printf("%-2s%-2s%-4s%-27s%-17s%-22s%-22s%2s%2s%n", "|", "|",
                                itens.getQtdProduto() + "x",
                                "COD: " + itens.getIdPedidoItens() + " " + itens.getProduto().getDescricao(),
                                " - " + itens.getProduto().getCategoria(),
                                "| VALOR UN.: R$" + Util.decimal(itens.getValorUnitario()),
                                "| DESCONTO UN.: R$" + Util.decimal(itens.getValorDesconto()), "|", "|");
                    }
                }
                System.out.println(
                        "|" + "_|" + "_".repeat(49) + "|" + "_".repeat(21) + "|" + "_".repeat(22) + "|_" + "|");
                System.out.printf("%-2s%-74s%12s%10s%2s%n", "|",
                        "OBS: " + (pedidos.get(0).getObservacao() == null ? "" : pedidos.get(0).getObservacao()),
                        "VALOR TOTAL:", "R$" + Util.decimal(pedidos.get(0).getValorTotal()), "|");
                Util.linhaDupla();

                return true;
            } else {
                System.out.println("Nenhum pedido encontrado com esse código. Tente novamente.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.getStackTrace();
            return false;
        }
    }

    public boolean excluirPedido(Connection c, int cod) {

        String sql = "DELETE FROM vendas.pedido_itens WHERE id_pedido = " + cod + ";"
                + " DELETE FROM vendas.pedido WHERE id_pedido = " + cod + ";";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.execute();

            return true;

        } catch (PSQLException e) {
            return true;
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage() + " " + e.getClass());
            return false;
        }

    }

    public boolean alterarClientePedido(Connection c, int codPed, int codCliente) {

        String sql = "UPDATE vendas.pedido SET id_cliente = " + codCliente + " WHERE id_pedido = " + codPed + ";";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.execute();

            return true;

        } catch (PSQLException e) {
            if (e.getMessage().equals("Nenhum resultado foi retornado pela consulta.")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public boolean adicionarProdutoPedido(Connection c, int codPed, int codProd, int quant) {

        String sql = "INSERT INTO vendas.pedido_itens "
                + "(qtd_produto, valor_unitario, valor_desconto, id_produto, id_pedido) " + "VALUES " + "(" + quant
                + ", (SELECT valor_unitario FROM vendas.produto WHERE id_produto = " + codProd + "), "
                + "(SELECT valor_unitario FROM vendas.produto WHERE id_produto = " + codProd + ")*0.10, " + codProd
                + ", " + codPed + "); " + "UPDATE vendas.pedido p SET valor_total = "
                + "(SELECT SUM(valor_unitario * qtd_produto)FROM vendas.pedido_itens pi WHERE pi.id_pedido = p.id_pedido) -\r\n"
                + "(SELECT SUM(valor_desconto * qtd_produto)FROM vendas.pedido_itens pi WHERE pi.id_pedido = p.id_pedido); ";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.execute();

            return true;

        } catch (PSQLException e) {
            if (e.getMessage().equals("Nenhum resultado foi retornado pela consulta.")) {
                return true;
            } else {
                System.out.println("Produto e/ou quantidade informada inválidos.");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            return false;
        }

    }

    public boolean alterarQuantidade(Connection c, int codItens, int quant) {

        String sql = "UPDATE vendas.pedido_itens SET qtd_produto = ? WHERE id_pedido_itens = ?; "
                + " UPDATE vendas.pedido p SET valor_total = "
                + "(SELECT SUM(valor_unitario * qtd_produto) FROM vendas.pedido_itens pi WHERE pi.id_pedido = p.id_pedido) - "
                + "(SELECT SUM(valor_desconto * qtd_produto) FROM vendas.pedido_itens pi WHERE pi.id_pedido = p.id_pedido);";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.setInt(1, quant);
            consulta.setInt(2, codItens);
            consulta.execute();

            return true;

        } catch (PSQLException e) {
            if (e.getMessage().equals("Nenhum resultado foi retornado pela consulta.")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            return false;
        }

    }

    public boolean alterarProduto(Connection c, int codItens, int codProd, int quant) {

        String sql = "UPDATE vendas.pedido_itens SET id_produto = ?, qtd_produto = ?, valor_unitario = (SELECT valor_unitario FROM vendas.produto WHERE id_produto = ?),"
                + "valor_desconto = (SELECT valor_unitario FROM vendas.produto WHERE id_produto = ?)*0.1 WHERE id_pedido_itens = ?; "
                + " UPDATE vendas.pedido p SET valor_total = "
                + "(SELECT SUM(valor_unitario * qtd_produto) FROM vendas.pedido_itens pi WHERE pi.id_pedido = p.id_pedido) - "
                + "(SELECT SUM(valor_desconto * qtd_produto) FROM vendas.pedido_itens pi WHERE pi.id_pedido = p.id_pedido);";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.setInt(1, codProd);
            consulta.setInt(2, quant);
            consulta.setInt(3, codProd);
            consulta.setInt(4, codProd);
            consulta.setInt(5, codItens);
            consulta.execute();

            return true;

        } catch (PSQLException e) {
            if (e.getMessage().equals("Nenhum resultado foi retornado pela consulta.")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            return false;
        }

    }

    public boolean excluirProduto(Connection c, int codItens) {

        String sql = "DELETE FROM vendas.pedido_itens WHERE id_pedido_itens = ?; "
                + " UPDATE vendas.pedido p SET valor_total = "
                + "(SELECT SUM(valor_unitario * qtd_produto) FROM vendas.pedido_itens pi WHERE pi.id_pedido = p.id_pedido) - "
                + "(SELECT SUM(valor_desconto * qtd_produto) FROM vendas.pedido_itens pi WHERE pi.id_pedido = p.id_pedido);";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.setInt(1, codItens);
            consulta.execute();

            return true;

        } catch (PSQLException e) {
            if (e.getMessage().equals("Nenhum resultado foi retornado pela consulta.")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            return false;
        }

    }

    public Date dataEntrega(Connection c, int cod) {
        Date dataEntrega = null;
        String sql = "SELECT data_entrega FROM vendas.pedido WHERE id_pedido = ?;";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.setInt(1, cod);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                dataEntrega = resultado.getDate("data_entrega");
            }

            return dataEntrega;

        } catch (PSQLException e) {
            if (e.getMessage().equals("Nenhum resultado foi retornado pela consulta.")) {
                System.out.println(e.getClass());
                e.printStackTrace();
                return dataEntrega;
            } else {
                System.out.println(e.getClass());
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            e.printStackTrace();
            return null;
        }
    }

    public boolean alterarDataEmissao(Connection c, int cod, Date dataEmissao) {

        String sql = "UPDATE vendas.pedido SET data_emissao = ? WHERE id_pedido = ?;";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.setDate(1, dataEmissao);
            consulta.setInt(2, cod);
            consulta.execute();

            return true;

        } catch (PSQLException e) {
            if (e.getMessage().equals("Nenhum resultado foi retornado pela consulta.")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            return false;
        }
    }

    public Date dataEmissao(Connection c, int cod) {
        Date dataEmissao = null;
        String sql = "SELECT data_emissao FROM vendas.pedido WHERE id_pedido = ?;";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.setInt(1, cod);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                dataEmissao = resultado.getDate("data_emissao");
            }

            return dataEmissao;

        } catch (PSQLException e) {
            if (e.getMessage().equals("Nenhum resultado foi retornado pela consulta.")) {
                System.out.println(e.getClass());
                e.printStackTrace();
                return dataEmissao;
            } else {
                System.out.println(e.getClass());
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            e.printStackTrace();
            return null;
        }
    }

    public boolean alterarDataEntrega(Connection c, int cod, Date dataEntrega) {

        String sql = "UPDATE vendas.pedido SET data_entrega = ? WHERE id_pedido = ?;";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.setDate(1, dataEntrega);
            consulta.setInt(2, cod);
            consulta.execute();

            return true;

        } catch (PSQLException e) {
            if (e.getMessage().equals("Nenhum resultado foi retornado pela consulta.")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            return false;
        }
    }

    public boolean alterarObs(Connection c, int cod, String obs) {

        String sql = "UPDATE vendas.pedido SET observacao = ? WHERE id_pedido = ?;";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.setString(1, obs);
            consulta.setInt(2, cod);
            consulta.execute();

            return true;

        } catch (PSQLException e) {
            if (e.getMessage().equals("Nenhum resultado foi retornado pela consulta.")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            return false;
        }
    }

    public int cadastrarPedido(Connection c, Pedido pedido) {

        String sql = "INSERT INTO vendas.pedido (data_emissao, data_entrega, id_cliente) " + "VALUES (?, ?, ?); ";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.setDate(1, pedido.getDataEmissao());
            consulta.setDate(2, pedido.getDataEntrega());
            consulta.setInt(3, pedido.getCliente().getIdcliente());

            consulta.execute();
            consulta.close();

            sql = "SELECT id_pedido FROM vendas.pedido ORDER BY id_pedido DESC LIMIT 1;";

            PreparedStatement consulta2 = c.prepareStatement(sql);
            ResultSet resultado = consulta2.executeQuery();

            while (resultado.next()) {
                pedido.setIdPedido(resultado.getInt("id_pedido"));
            }

            return pedido.getIdPedido();

        } catch (PSQLException e) {
            if (e.getMessage().equals("Nenhum resultado foi retornado pela consulta.")) {
                return pedido.getIdPedido();
            } else {
                System.out.println(e.getClass());
                System.out.println(e.getMessage());
                return 0;
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            return 0;
        }

    }

    public boolean cadastrarPedidoCompleto(Connection c, int codPed, PedidoItens itens) {

        String sql = "INSERT INTO vendas.pedido_itens (valor_unitario, valor_desconto, qtd_produto, id_produto, id_pedido) "
                + " VALUES ((SELECT valor_unitario FROM vendas.produto WHERE id_produto = ?), "
                + " (SELECT valor_unitario FROM vendas.produto WHERE id_produto = ?)*0.1, ?, ?, ?); "
                + " UPDATE vendas.pedido p SET valor_total = "
                + " (SELECT SUM(valor_unitario * qtd_produto) FROM vendas.pedido_itens pi WHERE pi.id_pedido = p.id_pedido) - "
                + " (SELECT SUM(valor_desconto * qtd_produto) FROM vendas.pedido_itens pi WHERE pi.id_pedido = p.id_pedido); ";

        try (PreparedStatement consulta = c.prepareStatement(sql)) {

            consulta.setInt(1, itens.getProduto().getIdProduto());
            consulta.setInt(2, itens.getProduto().getIdProduto());
            consulta.setInt(3, itens.getQtdProduto());
            consulta.setInt(4, itens.getProduto().getIdProduto());
            consulta.setInt(5, codPed);
            consulta.execute();
            consulta.close();

            return true;

        } catch (PSQLException e) {
            if (e.getMessage().equals("Nenhum resultado foi retornado pela consulta.")) {
                return true;
            } else {
                System.out.println("Erro: ");
                System.out.println(e.getMessage());
                System.out.println(e.getClass());
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            return false;
        }

    }

}
