package br.com.sig.sigcome.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.sig.sigcome.database.dao.CategoriaDAO;
import br.com.sig.sigcome.model.Categoria;

public class CarregaTodasCategoriasTask extends AsyncTask<Void, Void, List<Categoria>> {
    private final CategoriaDAO dao;
    private final BaseListenerComParametro<List<Categoria>> listener;

    public CarregaTodasCategoriasTask(CategoriaDAO dao, BaseListenerComParametro<List<Categoria>> listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Categoria> doInBackground(Void... voids) {
        return dao.todos();
    }

    @Override
    protected void onPostExecute(List<Categoria> categorias) {
        super.onPostExecute(categorias);
        listener.quandoFinalizado(categorias);
    }
}
