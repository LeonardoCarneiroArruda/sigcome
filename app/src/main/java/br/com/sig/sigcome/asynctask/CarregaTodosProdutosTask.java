package br.com.sig.sigcome.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.sig.sigcome.database.dao.ProdutoDAO;
import br.com.sig.sigcome.model.Produto;

public class CarregaTodosProdutosTask extends AsyncTask<Void, Void, List<Produto>> {

    private final ProdutoDAO dao;
    private final BaseListenerComParametro<List<Produto>> listener;

    public CarregaTodosProdutosTask(ProdutoDAO produtoDAO,
                                    BaseListenerComParametro<List<Produto>> baseListenerComParametro) {
        this.dao = produtoDAO;
        this.listener = baseListenerComParametro;
    }


    @Override
    protected List<Produto> doInBackground(Void... voids) {
        return dao.todos();
    }

    @Override
    protected void onPostExecute(List<Produto> produtos) {
        super.onPostExecute(produtos);
        listener.quandoFinalizado(produtos);
    }
}
