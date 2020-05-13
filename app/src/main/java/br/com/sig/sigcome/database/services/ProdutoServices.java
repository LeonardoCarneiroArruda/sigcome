package br.com.sig.sigcome.database.services;

import java.util.ArrayList;
import java.util.List;

import br.com.sig.sigcome.adapter.ListaItensAdapter;
import br.com.sig.sigcome.adapter.ListaProdutoAdapter;
import br.com.sig.sigcome.asynctask.AtualizarPrecosProdutosTask;
import br.com.sig.sigcome.asynctask.BaseListenerComParametro;
import br.com.sig.sigcome.asynctask.BaseListenerSemParametro;
import br.com.sig.sigcome.asynctask.CalcularEstoquePorProduto;
import br.com.sig.sigcome.asynctask.CarregaTodosProdutosTask;
import br.com.sig.sigcome.asynctask.ConstroiListaProdutoDeListaDeItensTask;
import br.com.sig.sigcome.asynctask.SalvaProdutoTask;
import br.com.sig.sigcome.database.dao.ProdutoDAO;
import br.com.sig.sigcome.model.Compra_Item;
import br.com.sig.sigcome.model.Produto;
import br.com.sig.sigcome.model.modelsLista.ProdutoModelLista;

public class ProdutoServices {

    private final ProdutoDAO produtoDAO;

    public ProdutoServices(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public void SalvarProduto(Produto produto, BaseListenerSemParametro listener) {
        new SalvaProdutoTask(produtoDAO, produto, listener).execute();
    }

    public void CarregarTodosProdutos(final ListaProdutoAdapter adapter) {
        new CarregaTodosProdutosTask(produtoDAO, new BaseListenerComParametro<List<Produto>>() {
            @Override
            public void quandoExecutado() {

            }

            @Override
            public void quandoFinalizado(List<Produto> resultado) {
                List<ProdutoModelLista> lista = new ArrayList<>();
                for (Produto item : resultado) {
                    lista.add(new ProdutoModelLista(item));
                }

                adapter.atualiza(lista);

                carregarQuantidadeEstoqueProduto(adapter, produtoDAO);
            }
        }).execute();
    }

    private void carregarQuantidadeEstoqueProduto(final ListaProdutoAdapter adapter,
                                                  ProdutoDAO produtoDAO) {

        List<ProdutoModelLista> produtos = adapter.getListaProdutos();
        for (int i = 0; i < produtos.size(); i++) {

            final int position = i;

            new CalcularEstoquePorProduto(produtoDAO,
                    produtos.get(i).getProduto(),
                    new BaseListenerComParametro<Double>() {
                @Override
                public void quandoExecutado() {

                }

                @Override
                public void quandoFinalizado(Double resultado) {
                    adapter.atualizaEstoqueNaLista(position, resultado);
                }
            }).execute();
        }

    }

    public void CarregarTodosItens(final ListaItensAdapter adapter) {
        new CarregaTodosProdutosTask(produtoDAO, new BaseListenerComParametro<List<Produto>>() {
            @Override
            public void quandoExecutado() {

            }

            @Override
            public void quandoFinalizado(List<Produto> resultado) {
                adapter.atualiza(resultado);
            }
        }).execute();
    }

    public void AtualizarPrecosProdutos(List<Compra_Item> mCompra_itens) {
        new AtualizarPrecosProdutosTask(produtoDAO, mCompra_itens).execute();
    }

    public void ConstroiListaProdutosPorListaDeItens(List<Compra_Item> lista_itens,
                                                     BaseListenerComParametro<List<Produto>> listener) {
        new ConstroiListaProdutoDeListaDeItensTask(lista_itens, produtoDAO, listener).execute();
    }
}
