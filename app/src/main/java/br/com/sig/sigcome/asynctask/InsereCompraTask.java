package br.com.sig.sigcome.asynctask;

import android.os.AsyncTask;

import br.com.sig.sigcome.database.dao.CompraDAO;
import br.com.sig.sigcome.model.Compra;

public class InsereCompraTask extends AsyncTask<Void, Void, Long> {
    private final CompraDAO dao;
    private final Compra compra;
    private final BaseListenerComParametro<Long> listener;

    public InsereCompraTask(CompraDAO dao, Compra compra, BaseListenerComParametro<Long> listener) {
        this.dao = dao;
        this.compra = compra;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.quandoExecutado();
    }

    @Override
    protected Long doInBackground(Void... voids) {
        return dao.salva(compra);
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        listener.quandoFinalizado(aLong);
    }
}
