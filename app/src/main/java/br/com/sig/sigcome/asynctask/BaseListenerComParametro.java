package br.com.sig.sigcome.asynctask;

public interface BaseListenerComParametro<T> {
    void quandoExecutado();
    void quandoFinalizado(T resultado);
}
