package br.com.sig.sigcome.model;

import java.util.Calendar;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Movimentacao {

    @PrimaryKey(autoGenerate = true)
    private Integer cd_movimentacao;

    @ForeignKey(entity = Produto.class,
            parentColumns = "cd_produto",
            childColumns = "cd_produto",
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE)
    private Integer cd_produto;

    private Calendar dt_cadastro;
    private Double vr_quantidade;

    public Integer getCd_movimentacao() {
        return cd_movimentacao;
    }

    public void setCd_movimentacao(Integer cd_movimentacao) {
        this.cd_movimentacao = cd_movimentacao;
    }

    public Integer getCd_produto() {
        return cd_produto;
    }

    public void setCd_produto(Integer cd_produto) {
        this.cd_produto = cd_produto;
    }

    public Calendar getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(Calendar dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }

    public Double getVr_quantidade() {
        return vr_quantidade;
    }

    public void setVr_quantidade(Double vr_quantidade) {
        this.vr_quantidade = vr_quantidade;
    }
}
