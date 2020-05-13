package br.com.sig.sigcome.asynctask;


import android.os.AsyncTask;

import java.util.List;

import br.com.sig.sigcome.database.dao.CompraDAO;
import br.com.sig.sigcome.model.Compra;

public class CarregarTodasComprasTask extends AsyncTask<Void, Void, List<Compra>> {
    private final CompraDAO dao;
    private final BaseListenerComParametro<List<Compra>> listener;

    public CarregarTodasComprasTask(CompraDAO dao, BaseListenerComParametro<List<Compra>> listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Compra> doInBackground(Void... voids) {
        return dao.todos();
    }

    @Override
    protected void onPostExecute(List<Compra> compras) {
        super.onPostExecute(compras);
        listener.quandoFinalizado(compras);
    }
}
