package br.com.sig.sigcome.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarHelper {

    public static String FormataData(Calendar data) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String data_formatada = formato.format(data.getTime());
        return data_formatada;
    }

}
