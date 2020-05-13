package br.com.sig.sigcome.asynctask;

import android.os.AsyncTask;

import br.com.sig.sigcome.database.dao.CategoriaDAO;
import br.com.sig.sigcome.model.Categoria;

public class SalvaCategoriaTask extends AsyncTask<Void, Void, Categoria> {

    private final BaseListenerComParametro<Categoria> listener;
    private final Categoria categoria;
    private final CategoriaDAO dao;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.quandoExecutado();
    }

    public SalvaCategoriaTask(Categoria categoria, CategoriaDAO dao, BaseListenerComParametro<Categoria> listener) {
        this.listener = listener;
        this.categoria = categoria;
        this.dao = dao;
    }

    @Override
    protected Categoria doInBackground(Void... voids) {
        dao.salva(categoria);
        return categoria;
    }

    @Override
    protected void onPostExecute(Categoria categoria) {
        super.onPostExecute(categoria);
        listener.quandoFinalizado(categoria);
    }
}
