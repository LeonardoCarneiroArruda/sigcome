package br.com.sig.sigcome.helper;

import java.util.ArrayList;
import java.util.List;

import br.com.sig.sigcome.model.Compra_Item;
import br.com.sig.sigcome.model.Produto;

public class Compra_ItemHelper {
    public static List<Compra_Item> retornaListaDeItensPorListaDeProdutos(List<Produto> mProdutosCarregados) {
        List<Compra_Item> lista = new ArrayList<>();
        Compra_Item item;

        for(int i = 0; i < mProdutosCarregados.size(); i++) {
            item = new Compra_Item();
            item.setCd_produto(mProdutosCarregados.get(i).getCd_produto());

            lista.add(item);
        }

        return lista;
    }

    public static Compra_Item retornaItemNaListaPorCodProduto(Integer cd_produto,
                                                              List<Compra_Item> listaDeComprasVisualizacao) {
        Compra_Item item = null;
        for (int i = 0; i < listaDeComprasVisualizacao.size(); i++) {
            if (listaDeComprasVisualizacao.get(i).getCd_produto() == cd_produto) {
                item = listaDeComprasVisualizacao.get(i);
                break;
            }
        }

        return item;
    }
}
