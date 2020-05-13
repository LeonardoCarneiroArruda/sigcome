package br.com.sig.sigcome.database.services;

import java.util.Calendar;
import java.util.List;

import br.com.sig.sigcome.adapter.ListaCategoriaAdapter;
import br.com.sig.sigcome.asynctask.BaseListenerComParametro;
import br.com.sig.sigcome.asynctask.BaseListenerSemParametro;
import br.com.sig.sigcome.asynctask.CarregaTodasCategoriasTask;
import br.com.sig.sigcome.asynctask.EditaCategoriaTask;
import br.com.sig.sigcome.asynctask.SalvaCategoriaTask;
import br.com.sig.sigcome.database.dao.CategoriaDAO;
import br.com.sig.sigcome.model.Categoria;
import br.com.sig.sigcome.model.Pessoa;

public class CategoriaServices {
    private final CategoriaDAO categoriaDAO;

    public CategoriaServices(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public void CarregaTodasCategorias(final ListaCategoriaAdapter adapter) {
        new CarregaTodasCategoriasTask(categoriaDAO, new BaseListenerComParametro<List<Categoria>>() {
            @Override
            public void quandoExecutado() {

            }

            @Override
            public void quandoFinalizado(List<Categoria> resultado) {
                adapter.atualiza(resultado);
            }
        }).execute();
    }

    public void SalvaCategoria(Categoria categoria, Pessoa pessoa, final ListaCategoriaAdapter adapter) {
        if (pessoa != null) {
            categoria.setDt_cadastro(Calendar.getInstance());
            categoria.setCd_pessoa(pessoa.getCd_pessoa());

            new SalvaCategoriaTask(categoria, categoriaDAO, new BaseListenerComParametro<Categoria>() {
                @Override
                public void quandoExecutado() {

                }

                @Override
                public void quandoFinalizado(Categoria resultado) {
                    adapter.adiciona(resultado);
                }
            }).execute();

        }
    }

    public void EditaCategoria(final Categoria categoria, final int posicao, Pessoa pessoa,
                               final ListaCategoriaAdapter adapter) {
        if (pessoa != null) {
            categoria.setDt_edicao(Calendar.getInstance());

            new EditaCategoriaTask(categoriaDAO, categoria, new BaseListenerSemParametro() {
                @Override
                public void quandoExecutado() {

                }

                @Override
                public void quandoFinalizado() {
                    adapter.edita(posicao, categoria);
                }
            }).execute();
        }
    }


}
