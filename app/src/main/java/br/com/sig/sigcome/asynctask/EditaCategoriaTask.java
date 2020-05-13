package br.com.sig.sigcome.asynctask;

import android.os.AsyncTask;

import br.com.sig.sigcome.database.dao.CategoriaDAO;
import br.com.sig.sigcome.model.Categoria;

public class EditaCategoriaTask extends AsyncTask<Void, Void, Void> {

    private final CategoriaDAO dao;
    private final Categoria categoria;
    private final BaseListenerSemParametro listener;

    public EditaCategoriaTask(CategoriaDAO dao, Categoria categoria, BaseListenerSemParametro listener) {
        this.dao = dao;
        this.categoria = categoria;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.edita(categoria);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoFinalizado();
    }
}
