package br.com.sig.sigcome.asynctask;

import android.os.AsyncTask;

import br.com.sig.sigcome.database.dao.PessoaDAO;
import br.com.sig.sigcome.model.Pessoa;

public class SalvaPessoaTask extends AsyncTask<Void, Void, Void> {
    private final BaseListenerSemParametro listener;
    private final PessoaDAO dao;
    private final Pessoa pessoa;

    public SalvaPessoaTask(PessoaDAO dao, Pessoa pessoa, BaseListenerSemParametro listener) {
        this.listener = listener;
        this.dao = dao;
        this.pessoa = pessoa;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.quandoExecutado();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.salva(pessoa);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoFinalizado();
    }
}
