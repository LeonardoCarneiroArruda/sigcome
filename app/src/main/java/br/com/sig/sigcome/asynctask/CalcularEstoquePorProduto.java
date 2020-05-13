package br.com.sig.sigcome.asynctask;

import android.os.AsyncTask;

import br.com.sig.sigcome.database.dao.ProdutoDAO;
import br.com.sig.sigcome.model.Produto;

public class CalcularEstoquePorProduto extends AsyncTask<Void, Void, Double> {
    private final ProdutoDAO dao;
    private final Produto produto;
    private final BaseListenerComParametro<Double> listener;

    public CalcularEstoquePorProduto(ProdutoDAO produtoDAO, Produto produto,
                                     BaseListenerComParametro<Double> listener) {
        this.dao = produtoDAO;
        this.produto = produto;
        this.listener = listener;
    }


    @Override
    protected Double doInBackground(Void... voids) {
        return dao.calculaEstoqueProduto(produto.getCd_produto());
    }

    @Override
    protected void onPostExecute(Double estoque) {
        super.onPostExecute(estoque);
        listener.quandoFinalizado(estoque);
    }
}
