package br.com.sig.sigcome.model;


import java.util.Calendar;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Categoria {

    @PrimaryKey(autoGenerate = true)
    private Integer cd_categoria;
    private String ch_descricao;

    @ForeignKey(entity = Pessoa.class,
            parentColumns = "cd_pessoa",
            childColumns = "cd_pessoa",
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE)
    private Integer cd_pessoa;

    private Calendar dt_cadastro;
    private Calendar dt_edicao;

    public Integer getCd_categoria() {
        return cd_categoria;
    }

    public void setCd_categoria(Integer cd_categoria) {
        this.cd_categoria = cd_categoria;
    }

    public String getCh_descricao() {
        return ch_descricao;
    }

    public void setCh_descricao(String ch_descricao) {
        this.ch_descricao = ch_descricao;
    }

    public Integer getCd_pessoa() {
        return cd_pessoa;
    }

    public void setCd_pessoa(Integer cd_pessoa) {
        this.cd_pessoa = cd_pessoa;
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

    @Override
    public String toString() {
        return this.getCh_descricao();
    }
}
