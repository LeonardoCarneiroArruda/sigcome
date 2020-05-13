package br.com.sig.sigcome.model;

import java.io.Serializable;
import java.util.Calendar;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Compra implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer cd_compra;
    private Calendar dt_compra;
    private Calendar dt_cadastro;


    public Integer getCd_compra() {
        return cd_compra;
    }

    public void setCd_compra(Integer cd_compra) {
        this.cd_compra = cd_compra;
    }

    public Calendar getDt_compra() {
        return dt_compra;
    }

    public void setDt_compra(Calendar dt_compra) {
        this.dt_compra = dt_compra;
    }

    public Calendar getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(Calendar dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }
}
