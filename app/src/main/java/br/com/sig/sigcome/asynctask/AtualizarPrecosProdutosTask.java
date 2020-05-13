package br.com.sig.sigcome.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.sig.sigcome.database.dao.ProdutoDAO;
import br.com.sig.sigcome.model.Compra_Item;
import br.com.sig.sigcome.model.Produto;

public class AtualizarPrecosProdutosTask extends AsyncTask<Void, Void, Void> {
    private final ProdutoDAO dao;
    private final List<Compra_Item> compra_items;

    public AtualizarPrecosProdutosTask(ProdutoDAO dao, List<Compra_Item> compra_items) {
        this.dao = dao;
        this.compra_items = compra_items;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Produto produto;

        for(int i = 0; i < compra_items.size(); i++) {
            produto = dao.buscaProdutoPorId(compra_items.get(i).getCd_produto());

            Double vr_compra = compra_items.get(i).getVr_compra();
            Double vr_quantidade = compra_items.get(i).getVr_quantidade();
            Double valorUnitario =  vr_compra / vr_quantidade;
            produto.setVr_ultima_compra(valorUnitario);
            dao.salva(produto);
        }

        return null;
    }
}
