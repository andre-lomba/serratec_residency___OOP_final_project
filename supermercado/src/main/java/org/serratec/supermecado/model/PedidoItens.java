package main.java.org.serratec.supermecado.model;

public class PedidoItens {

    private int id_pedido_itens;
    private double valor_unitario;
    private int qtd_produto;
    private double valor_desconto;
    private Produto produto = new Produto();

    public double getValorUnitario() {
        return valor_unitario;
    }

    public void setValorUnitario(double valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    public int getQtdProduto() {
        return qtd_produto;
    }

    public void setQtdProduto(int qtd_produto) {
        this.qtd_produto = qtd_produto;
    }

    public double getValorDesconto() {
        return valor_desconto;
    }

    public void setValorDesconto(double valor_desconto) {
        this.valor_desconto = valor_desconto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getIdPedidoItens() {
        return id_pedido_itens;
    }

    public void setIdPedidoItens(int id_pedido_itens) {
        this.id_pedido_itens = id_pedido_itens;
    }

}
