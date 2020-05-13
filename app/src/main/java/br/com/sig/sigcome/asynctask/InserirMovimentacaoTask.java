package br.com.sig.sigcome.asynctask;

import android.os.AsyncTask;

import br.com.sig.sigcome.database.dao.MovimentacaoDAO;
import br.com.sig.sigcome.model.Movimentacao;

public class InserirMovimentacaoTask extends AsyncTask<Void, Void, Void> {
    private final MovimentacaoDAO dao;
    private final Movimentacao movimentacao;
    private final BaseListenerSemParametro listener;

    public InserirMovimentacaoTask(MovimentacaoDAO dao, Movimentacao movimentacao, BaseListenerSemParametro listener) {
        this.dao = dao;
        this.movimentacao = movimentacao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.salva(movimentacao);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoFinalizado();
    }
}
