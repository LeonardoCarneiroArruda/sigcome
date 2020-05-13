package br.com.sig.sigcome.helper;

import java.util.List;

public class UnidadeHelper {

    public static int retornarPosicaoItemNaLista(String item, List<String> lista) {
        for(int i = 0; i < lista.size(); i++) {
            if (lista.get(i).equals(item))
                return i;
        }
        return 0;
    }
}

