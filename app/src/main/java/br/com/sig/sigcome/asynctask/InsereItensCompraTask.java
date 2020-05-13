package br.com.sig.sigcome.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Calendar;
import java.util.List;

import br.com.sig.sigcome.database.SigcomeDatabase;
import br.com.sig.sigcome.database.dao.Compra_ItemDAO;
import br.com.sig.sigcome.database.dao.MovimentacaoDAO;
import br.com.sig.sigcome.model.Compra_Item;
import br.com.sig.sigcome.model.Movimentacao;

public class InsereItensCompraTask extends AsyncTask<Void, Void, Void> {
    private final Compra_ItemDAO dao;
    private final List<Compra_Item> itens;
    private final BaseListenerSemParametro listener;
    private final Context context;

    public InsereItensCompraTask(Compra_ItemDAO dao, List<Compra_Item> itens,
                                 BaseListenerSemParametro listener, Context context) {
        this.dao = dao;
        this.itens = itens;
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.salva(itens);

        MovimentacaoDAO movimentacaoDAO = SigcomeDatabase.getInstance(context)
                .getRoomMovimentacaoDAO();

        Movimentacao movimentacao;
        for(int i = 0; i < itens.size(); i++) {
            movimentacao = new Movimentacao();
            movimentacao.setDt_cadastro(Calendar.getInstance());
            movimentacao.setVr_quantidade(itens.get(i).getVr_quantidade());
            movimentacao.setCd_produto(itens.get(i).getCd_produto());

            movimentacaoDAO.salva(movimentacao);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoFinalizado();
    }
}
