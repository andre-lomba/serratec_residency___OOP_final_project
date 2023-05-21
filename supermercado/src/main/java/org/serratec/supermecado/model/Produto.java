package main.java.org.serratec.supermecado.model;

public class Produto {

    private int id_produto;
    private String descricao;
    private double custo;
    private double valor_unitario;
    private String categoria;

    public int getIdProduto() {
        return id_produto;
    }

    public void setIdProduto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public double getValorUnitario() {
        return valor_unitario;
    }

    public void setValorUnitario(double valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
