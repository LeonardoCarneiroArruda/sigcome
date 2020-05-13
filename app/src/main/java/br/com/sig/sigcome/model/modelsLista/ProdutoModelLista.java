package br.com.sig.sigcome.model.modelsLista;

import br.com.sig.sigcome.model.Produto;

public class ProdutoModelLista {

    private Produto produto;
    private Double vr_quantidade_estoque;

    public ProdutoModelLista(Produto produto) {
        this.produto = produto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getVr_quantidade_estoque() {
        return vr_quantidade_estoque;
    }

    public void setVr_quantidade_estoque(Double vr_quantidade_estoque) {
        this.vr_quantidade_estoque = vr_quantidade_estoque;
    }
}
