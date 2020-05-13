package br.com.sig.sigcome.helper;

import android.content.Context;
import android.widget.Toast;

public class MensagemHelper {

    public static void GerarToastRapidoSalvo(Context context){
        GerarToastRapido(context, "Salvo");
    }

    public static void GerarToastRapidoCancelado(Context context){
        GerarToastRapido(context, "Cancelado");
    }

    public static void GerarToastRapido(Context context, String mensagem){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, mensagem, duration);
        toast.show();
    }

    public static void GerarToastLongo(Context context, String mensagem){
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, mensagem, duration);
        toast.show();
    }

}
