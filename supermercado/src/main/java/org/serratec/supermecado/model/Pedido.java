package main.java.org.serratec.supermecado.model;

import java.sql.Date;
import java.util.ArrayList;

public class Pedido {

    private int id_pedido;
    private Date data_emissao;
    private Date data_entrega;
    private double valor_total;
    private String observacao;
    private Cliente cliente = new Cliente();
    private ArrayList<PedidoItens> itens = new ArrayList<>();

    public int getIdPedido() {
        return id_pedido;
    }

    public void setIdPedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Date getDataEmissao() {
        return data_emissao;
    }

    public void setDataEmissao(Date data_emissao) {
        this.data_emissao = data_emissao;
    }

    public Date getDataEntrega() {
        return data_entrega;
    }

    public void setDataEntrega(Date data_entrega) {
        this.data_entrega = data_entrega;
    }

    public double getValorTotal() {
        return valor_total;
    }

    public void setValorTotal(double valor_total) {
        this.valor_total = valor_total;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<PedidoItens> getItens() {
        return itens;
    }

    public void adicionarProduto(PedidoItens itens) {
        PedidoItens peditem = new PedidoItens();

        peditem.setIdPedidoItens(itens.getIdPedidoItens());
        peditem.setProduto(itens.getProduto());
        peditem.setQtdProduto(itens.getQtdProduto());
        peditem.setValorUnitario(itens.getValorUnitario());
        peditem.setValorDesconto(itens.getValorDesconto());

        this.itens.add(peditem);
    }
}
