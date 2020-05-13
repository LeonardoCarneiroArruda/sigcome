package br.com.sig.sigcome.database.services;

import br.com.sig.sigcome.asynctask.BaseListenerSemParametro;
import br.com.sig.sigcome.asynctask.InserirMovimentacaoTask;
import br.com.sig.sigcome.database.dao.MovimentacaoDAO;
import br.com.sig.sigcome.model.Movimentacao;

public class MovimentacaoServices {

    private final MovimentacaoDAO movimentacaoDAO;

    public MovimentacaoServices(MovimentacaoDAO movimentacaoDAO) {
        this.movimentacaoDAO = movimentacaoDAO;
    }

    public void InserirMovimentacao(Movimentacao movimentacao, boolean consumo, BaseListenerSemParametro listener) {
        if (consumo)
            movimentacao.setVr_quantidade(- movimentacao.getVr_quantidade());

        new InserirMovimentacaoTask(movimentacaoDAO, movimentacao, listener).execute();
    }
}
