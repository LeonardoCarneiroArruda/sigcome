package br.com.sig.sigcome.asynctask;

import android.os.AsyncTask;

import br.com.sig.sigcome.database.dao.ProdutoDAO;
import br.com.sig.sigcome.model.Produto;

public class SalvaProdutoTask extends AsyncTask<Void, Void, Void> {
    private final ProdutoDAO dao;
    private final Produto produto;
    private final BaseListenerSemParametro listener;

    public SalvaProdutoTask(ProdutoDAO dao, Produto produto, BaseListenerSemParametro listener) {
        this.dao = dao;
        this.produto = produto;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.salva(produto);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoFinalizado();
    }
}
