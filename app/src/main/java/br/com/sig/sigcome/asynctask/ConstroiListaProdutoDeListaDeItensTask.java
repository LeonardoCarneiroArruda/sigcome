package br.com.sig.sigcome.asynctask;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.sig.sigcome.database.dao.ProdutoDAO;
import br.com.sig.sigcome.model.Compra_Item;
import br.com.sig.sigcome.model.Produto;

public class ConstroiListaProdutoDeListaDeItensTask extends AsyncTask<Void, Void, List<Produto>> {
    private final List<Compra_Item> lista_itens;
    private final ProdutoDAO produtoDAO;
    private final BaseListenerComParametro<List<Produto>> listener;

    public ConstroiListaProdutoDeListaDeItensTask(List<Compra_Item> lista_itens,
                                                  ProdutoDAO produtoDAO,
                                                  BaseListenerComParametro<List<Produto>> listener) {
        this.lista_itens = lista_itens;
        this.produtoDAO = produtoDAO;
        this.listener = listener;
    }


    @Override
    protected List<Produto> doInBackground(Void... voids) {
        List<Produto> lista = new ArrayList<>();
        Produto prod;

        for (Compra_Item item: lista_itens) {
            prod = new Produto();
            prod = produtoDAO.buscaProdutoPorId(item.getCd_produto());
            lista.add(prod);
        }

        return lista;
    }

    @Override
    protected void onPostExecute(List<Produto> produtos) {
        super.onPostExecute(produtos);
        listener.quandoFinalizado(produtos);
    }
}
