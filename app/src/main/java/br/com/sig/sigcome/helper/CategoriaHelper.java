package br.com.sig.sigcome.helper;

import java.util.List;

import br.com.sig.sigcome.model.Categoria;

public class CategoriaHelper {


    public static int retornarPosicaoItemNaLista(int cd_categoria, List<Categoria> categorias) {
        for(int i = 0; i < categorias.size(); i++) {
            int id_categoria = categorias.get(i).getCd_categoria();
            if (cd_categoria == id_categoria)
                return i;
        }

        return 0;
    }
}
