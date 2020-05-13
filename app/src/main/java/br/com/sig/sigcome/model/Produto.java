package br.com.sig.sigcome.model;

import java.io.Serializable;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Produto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer cd_produto;
    private String ch_descricao;
    private String ch_tipo_unidade;

    @ForeignKey(entity = Categoria.class,
            parentColumns = "cd_categoria",
            childColumns = "cd_categoria",
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE)
    private Integer cd_categoria;

    private Calendar dt_cadastro;
    private Calendar dt_edicao;
    private Integer in_quantidade_minima;
    private Double vr_ultima_compra;
    private Integer in_dias_duracao;

    public Integer getCd_produto() {
        return cd_produto;
    }

    public void setCd_produto(Integer cd_produto) {
        this.cd_produto = cd_produto;
    }

    public String getCh_descricao() {
        return ch_descricao;
    }

    public void setCh_descricao(String ch_descricao) {
        this.ch_descricao = ch_descricao;
    }

    public String getCh_tipo_unidade() {
        return ch_tipo_unidade;
    }

    public void setCh_tipo_unidade(String ch_tipo_unidade) {
        this.ch_tipo_unidade = ch_tipo_unidade;
    }

    public Integer getCd_categoria() {
        return cd_categoria;
    }

    public void setCd_categoria(Integer cd_categoria) {
        this.cd_categoria = cd_categoria;
    }

    public Calendar getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(Calendar dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }

    public Calendar getDt_edicao() {
        return dt_edicao;
    }

    public void setDt_edicao(Calendar dt_edicao) {
        this.dt_edicao = dt_edicao;
    }

    public Integer getIn_quantidade_minima() {
        return in_quantidade_minima;
    }

    public void setIn_quantidade_minima(Integer in_quantidade_minima) {
        this.in_quantidade_minima = in_quantidade_minima;
    }

    public Double getVr_ultima_compra() {
        return vr_ultima_compra;
    }

    public void setVr_ultima_compra(Double vr_ultima_compra) {
        this.vr_ultima_compra = vr_ultima_compra;
    }

    public Integer getIn_dias_duracao() {
        return in_dias_duracao;
    }

    public void setIn_dias_duracao(Integer in_dias_duracao) {
        this.in_dias_duracao = in_dias_duracao;
    }

    @NonNull
    @Override
    public String toString() {
        return getCh_descricao();
    }
}
