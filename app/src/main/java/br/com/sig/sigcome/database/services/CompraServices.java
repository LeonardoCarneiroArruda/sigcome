package br.com.sig.sigcome.database.services;

import java.util.Calendar;
import java.util.List;

import br.com.sig.sigcome.adapter.ListaComprasAdapter;
import br.com.sig.sigcome.asynctask.BaseListenerComParametro;
import br.com.sig.sigcome.asynctask.CarregarTodasComprasTask;
import br.com.sig.sigcome.asynctask.InsereCompraTask;
import br.com.sig.sigcome.database.dao.CompraDAO;
import br.com.sig.sigcome.model.Compra;

public class CompraServices {
    private final CompraDAO compraDAO;

    public CompraServices(CompraDAO compraDAO) {
        this.compraDAO = compraDAO;
    }


    public void InsereCompra(Compra compra, BaseListenerComParametro<Long> listenerComParametro) {
        compra.setDt_cadastro(Calendar.getInstance());
        new InsereCompraTask(compraDAO, compra, listenerComParametro).execute();
    }

    public void CarregarTodasCompras(final ListaComprasAdapter adapter) {
        new CarregarTodasComprasTask(compraDAO, new BaseListenerComParametro<List<Compra>>() {
            @Override
            public void quandoExecutado() {

            }

            @Override
            public void quandoFinalizado(List<Compra> resultado) {
                adapter.atualiza(resultado);
            }
        }).execute();
    }
}
