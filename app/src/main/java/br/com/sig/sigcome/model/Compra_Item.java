package br.com.sig.sigcome.model;

import java.util.Calendar;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Compra_Item {

    @PrimaryKey(autoGenerate = true)
    private Integer cd_compra_item;

    @ForeignKey(entity = Compra.class,
            parentColumns = "cd_compra",
            childColumns = "cd_compra",
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE)
    private Integer cd_compra;

    @ForeignKey(entity = Produto.class,
            parentColumns = "cd_produto",
            childColumns = "cd_produto",
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE)
    private Integer cd_produto;

    private Double vr_compra;
    private Double vr_quantidade;
    private Calendar dt_cadastro;
    private Calendar dt_edicao;

    public Integer getCd_compra_item() {
        return cd_compra_item;
    }

    public void setCd_compra_item(Integer cd_compra_item) {
        this.cd_compra_item = cd_compra_item;
    }

    public Integer getCd_compra() {
        return cd_compra;
    }

    public void setCd_compra(Integer cd_compra) {
        this.cd_compra = cd_compra;
    }

    public Integer getCd_produto() {
        return cd_produto;
    }

    public void setCd_produto(Integer cd_produto) {
        this.cd_produto = cd_produto;
    }

    public Double getVr_compra() {
        return vr_compra;
    }

    public void setVr_compra(Double vr_compra) {
        this.vr_compra = vr_compra;
    }

    public Double getVr_quantidade() {
        return vr_quantidade;
    }

    public void setVr_quantidade(Double vr_quantidade) {
        this.vr_quantidade = vr_quantidade;
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
}
