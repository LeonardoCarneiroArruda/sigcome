package br.com.sig.sigcome.helper;

import java.util.ArrayList;
import java.util.List;

import br.com.sig.sigcome.model.Produto;

public class ProdutoHelper {

    public static ArrayList<Produto> removeProdutoDaLista(Produto produto, ArrayList<Produto> lista) {
        for (int i = 0; i < lista.size(); i ++) {
            if (produto.getCd_produto() == lista.get(i).getCd_produto()) {
                lista.remove(i);
                break;
            }
        }

        return lista;
    }

}
