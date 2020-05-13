package br.com.sig.sigcome.model;

import java.util.Calendar;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Pessoa {

    @PrimaryKey(autoGenerate = true)
    private Integer cd_pessoa;
    private String ch_telefone;
    private String ch_email;
    private String ch_nome;
    private Calendar dt_cadastro;
    private Calendar dt_edicao;

    public Integer getCd_pessoa() {
        return cd_pessoa;
    }

    public void setCd_pessoa(Integer cd_pessoa) {
        this.cd_pessoa = cd_pessoa;
    }

    public String getCh_telefone() {
        return ch_telefone;
    }

    public void setCh_telefone(String ch_telefone) {
        this.ch_telefone = ch_telefone;
    }

    public String getCh_email() {
        return ch_email;
    }

    public void setCh_email(String ch_email) {
        this.ch_email = ch_email;
    }

    public String getCh_nome() {
        return ch_nome;
    }

    public void setCh_nome(String ch_nome) {
        this.ch_nome = ch_nome;
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
