package br.com.sig.sigcome.database.services;

import java.util.Calendar;

import br.com.sig.sigcome.asynctask.BaseListenerComParametro;
import br.com.sig.sigcome.asynctask.BaseListenerSemParametro;
import br.com.sig.sigcome.asynctask.BuscaPessoaCadastradaTask;
import br.com.sig.sigcome.asynctask.SalvaPessoaTask;
import br.com.sig.sigcome.database.dao.PessoaDAO;
import br.com.sig.sigcome.model.Pessoa;

public class PessoaServices {

    private final PessoaDAO dao;

    public PessoaServices(PessoaDAO dao) {
        this.dao = dao;
    }

    public void SalvarPessoa(Pessoa pessoa, BaseListenerSemParametro listener) {
        if (pessoa.getDt_cadastro() == null)
            pessoa.setDt_cadastro(Calendar.getInstance());
        else
            pessoa.setDt_edicao(Calendar.getInstance());

        new SalvaPessoaTask(dao, pessoa, listener).execute();
    }

    public void BuscaPessoaCadastrada(BaseListenerComParametro<Pessoa> listener) {
        new BuscaPessoaCadastradaTask(dao, listener).execute();
    }


}
