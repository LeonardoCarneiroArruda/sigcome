package br.com.sig.sigcome.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.sig.sigcome.database.dao.Compra_ItemDAO;
import br.com.sig.sigcome.model.Compra_Item;

public class CarregarItensPorCompraTask extends AsyncTask<Void, Void, List<Compra_Item>> {
    private final Integer cd_compra;
    private final Compra_ItemDAO dao;
    private final BaseListenerComParametro<List<Compra_Item>> listener;

    public CarregarItensPorCompraTask(Integer cd_compra,
                                      Compra_ItemDAO dao,
                                      BaseListenerComParametro<List<Compra_Item>> listener) {
        this.cd_compra = cd_compra;
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Compra_Item> doInBackground(Void... voids) {
        return dao.retornaItensPorCompra(cd_compra);
    }

    @Override
    protected void onPostExecute(List<Compra_Item> compra_items) {
        super.onPostExecute(compra_items);
        listener.quandoFinalizado(compra_items);
    }
}
