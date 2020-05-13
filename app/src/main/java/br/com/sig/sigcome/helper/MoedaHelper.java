package br.com.sig.sigcome.helper;

import java.text.NumberFormat;
import java.util.Locale;

public class MoedaHelper {

    public static String DoubleParaStringMoeda(Double valor) {
        if (valor == null)
            valor = 0.0;

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return nf.format (valor);
    }
}
