package br.com.sig.sigcome.database.services;

import android.content.Context;

import java.util.Calendar;
import java.util.List;

import br.com.sig.sigcome.asynctask.BaseListenerComParametro;
import br.com.sig.sigcome.asynctask.BaseListenerSemParametro;
import br.com.sig.sigcome.asynctask.CarregarItensPorCompraTask;
import br.com.sig.sigcome.asynctask.InsereItensCompraTask;
import br.com.sig.sigcome.database.dao.Compra_ItemDAO;
import br.com.sig.sigcome.model.Compra_Item;

public class Compra_ItemServices {
    private final Compra_ItemDAO dao;

    public Compra_ItemServices(Compra_ItemDAO dao) {
        this.dao = dao;
    }


    public void InsereItens(Long cd_compra, List<Compra_Item> itens, Context context,
                            BaseListenerSemParametro listener) {
        for(int i = 0; i < itens.size(); i++) {
            itens.get(i).setCd_compra(cd_compra.intValue());
            itens.get(i).setDt_cadastro(Calendar.getInstance());
        }

        new InsereItensCompraTask(dao, itens, listener, context).execute();
    }

    public void CarregaTodosItensPelaCompra(Integer cd_compra,
                                            BaseListenerComParametro<List<Compra_Item>> listener) {

        new CarregarItensPorCompraTask(cd_compra, dao, listener).execute();
    }

}
