package br.com.sig.sigcome.asynctask;

import android.os.AsyncTask;

import br.com.sig.sigcome.database.dao.PessoaDAO;
import br.com.sig.sigcome.model.Pessoa;

public class BuscaPessoaCadastradaTask extends AsyncTask<Void, Pessoa, Pessoa> {
    private final PessoaDAO dao;
    private final BaseListenerComParametro<Pessoa> listener;

    public BuscaPessoaCadastradaTask(PessoaDAO dao, BaseListenerComParametro<Pessoa> listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected Pessoa doInBackground(Void... voids) {
        return dao.retornaPessoaCadastrada();
    }

    @Override
    protected void onPostExecute(Pessoa pessoa) {
        super.onPostExecute(pessoa);
        if (pessoa != null)
            listener.quandoFinalizado(pessoa);
    }
}
