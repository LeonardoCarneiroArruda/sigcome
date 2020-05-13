package br.com.sig.sigcome.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import br.com.sig.sigcome.R;
import br.com.sig.sigcome.adapter.ListaItensFinaisAdapter;
import br.com.sig.sigcome.asynctask.BaseListenerComParametro;
import br.com.sig.sigcome.asynctask.BaseListenerSemParametro;
import br.com.sig.sigcome.asynctask.InsereCompraTask;
import br.com.sig.sigcome.database.SigcomeDatabase;
import br.com.sig.sigcome.database.dao.CompraDAO;
import br.com.sig.sigcome.database.dao.Compra_ItemDAO;
import br.com.sig.sigcome.database.dao.ProdutoDAO;
import br.com.sig.sigcome.database.services.CompraServices;
import br.com.sig.sigcome.database.services.Compra_ItemServices;
import br.com.sig.sigcome.database.services.ProdutoServices;
import br.com.sig.sigcome.helper.CONSTANTES;
import br.com.sig.sigcome.helper.Compra_ItemHelper;
import br.com.sig.sigcome.helper.MensagemHelper;
import br.com.sig.sigcome.model.Compra;
import br.com.sig.sigcome.model.Compra_Item;
import br.com.sig.sigcome.model.Produto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class FinalizarCompraActivity extends AppCompatActivity {

    public static final String TITULOA_APP_BAR = "Finalizar Compra";
    private ListaItensFinaisAdapter adapter;
    private List<Produto> mProdutosCarregados;
    private Compra mCompra;
    private List<Compra_Item> mCompra_Itens = new ArrayList<>();
    private CompraDAO compraDAO;
    private Compra_ItemDAO compra_itemDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_compra);

        setTitle(TITULOA_APP_BAR);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SigcomeDatabase instance = SigcomeDatabase.getInstance(this);
        compraDAO = instance.getRoomCompraDAO();
        compra_itemDAO = instance.getRoomCompraItemDAO();

        configuraListaItens();
        configuraBotaoFinalizar();
    }

    private void configuraBotaoFinalizar() {
        Button bt_finalizar_compra = findViewById(R.id.bt_finalizar_compra);
        bt_finalizar_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getIntent().hasExtra(CONSTANTES.VISUALIZA_COMPRAS)) {
                    new CompraServices(compraDAO).InsereCompra(mCompra,
                            ListenerParaInserirCompra());
                }
                else {
                    MensagemHelper.GerarToastLongo(FinalizarCompraActivity.this,
                            "Você está visualizando uma compra ja inserida");
                }
            }
        });
    }

    private BaseListenerComParametro<Long> ListenerParaInserirCompra() {
        final ProgressDialog pDialog =
                new ProgressDialog(FinalizarCompraActivity.this);

        return new BaseListenerComParametro<Long>() {
            @Override
            public void quandoExecutado() {
                pDialog.setTitle("Inserindo compra");
                pDialog.setMessage("Aguarde...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            public void quandoFinalizado(Long cd_compra) {
                InserirItens(cd_compra, pDialog);
            }
        };

    }

    private void InserirItens(Long cd_compra, final ProgressDialog pDialog) {
        new Compra_ItemServices(compra_itemDAO).InsereItens(cd_compra,
                mCompra_Itens,
                FinalizarCompraActivity.this,
                new BaseListenerSemParametro() {
            @Override
            public void quandoExecutado() {

            }

            @Override
            public void quandoFinalizado() {
                pDialog.dismiss();
                atualizaPrecosProdutos();
                vaiParaTelaInicial();
            }
        });
    }

    private void atualizaPrecosProdutos() {
        new ProdutoServices(SigcomeDatabase.getInstance(FinalizarCompraActivity.this)
                .getRoomProdutoDAO()).AtualizarPrecosProdutos(mCompra_Itens);
    }

    private void vaiParaTelaInicial() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void configuraListaItens() {
        RecyclerView rv_itens_compra = findViewById(R.id.rv_itens_compra);
        adapter = new ListaItensFinaisAdapter(this, ClickListenersButtons(),
                onFocusChangeListener());
        rv_itens_compra.setAdapter(adapter);

        carregarItensLista();
    }

    private ListaItensFinaisAdapter.OnFocusChange onFocusChangeListener() {
        ListaItensFinaisAdapter.OnFocusChange listenerFocus = new ListaItensFinaisAdapter.OnFocusChange() {
            @Override
            public void QuandoSairDoFocoCampoPreco(boolean hasFocus,
                                                   EditText et_finalizando_compra_preco_produto,
                                                   int position) {
                if (!hasFocus) {
                    String valor = et_finalizando_compra_preco_produto.getText().toString();
                    Double preco = Double
                            .valueOf(!valor.equals("") ? valor : "0");
                    atualizaPreco(preco, position);
                }
            }

            @Override
            public void QuandoSairDoFocoCampoQuantidade(boolean hasFocus,
                                                         EditText et_quantidade_produto,
                                                         int position) {

                if (!hasFocus) {
                    String qtd = et_quantidade_produto.getText().toString();
                    Double quantidade;
                    try {
                            quantidade = Double.valueOf(!qtd.equals("") ? qtd : "0");
                            alteraQuantidade(position, quantidade);
                    }
                    catch(Exception ex) {
                        MensagemHelper.GerarToastRapido(FinalizarCompraActivity.this,
                                "Valor invalido");
                    }
                }

            }
        };

        return listenerFocus;
    }

    private void atualizaPreco(Double preco, int position) {
        mCompra_Itens.get(position).setVr_compra(preco);
    }

    private ListaItensFinaisAdapter.ClickListenersButtons ClickListenersButtons() {
       ListaItensFinaisAdapter.ClickListenersButtons listenersButtons =
               new ListaItensFinaisAdapter.ClickListenersButtons() {
           @Override
           public void ClickMenos(EditText et_quantidade_produto, int position) {
                Double qtd = Double.valueOf(et_quantidade_produto.getText().toString());
                qtd--;
                et_quantidade_produto.setText(String.valueOf(qtd));
                alteraQuantidade(position, qtd);
           }

           @Override
           public void ClickMais(EditText et_quantidade_produto, int position) {
               Double qtd = Double.valueOf(et_quantidade_produto.getText().toString());
               qtd++;
               et_quantidade_produto.setText(String.valueOf(qtd));
               alteraQuantidade(position, qtd);
           }
       };

       return listenersButtons;
    }

    private void alteraQuantidade(int position, Double qtd) {
        mCompra_Itens.get(position).setVr_quantidade(qtd);
    }

    private void carregarItensLista() {
        if (getIntent().hasExtra(CONSTANTES.VISUALIZA_COMPRAS)) {
            mCompra = (Compra) getIntent().getSerializableExtra(CONSTANTES.COMPRA);

            carregarCompra_ItensParaVisualizacao();
        }
        else {
            if (getIntent().hasExtra(CONSTANTES.LISTA_PRODUTOS)) {
                mProdutosCarregados = (List<Produto>) getIntent()
                        .getSerializableExtra(CONSTANTES.LISTA_PRODUTOS);

                adapter.atualiza(mProdutosCarregados);
            }

            if (getIntent().hasExtra(CONSTANTES.COMPRA)) {
                mCompra = (Compra) getIntent().getSerializableExtra(CONSTANTES.COMPRA);

                mCompra_Itens = Compra_ItemHelper
                        .retornaListaDeItensPorListaDeProdutos(mProdutosCarregados);
            }
        }
    }

    private void carregarCompra_ItensParaVisualizacao() {
        new Compra_ItemServices(compra_itemDAO).CarregaTodosItensPelaCompra(mCompra.getCd_compra(),
                new BaseListenerComParametro<List<Compra_Item>>() {
            @Override
            public void quandoExecutado() {

            }

            @Override
            public void quandoFinalizado(List<Compra_Item> resultado) {
                mCompra_Itens = resultado;
                adapter.setListaDeComprasVisualizacao(mCompra_Itens);

                constroiListaProdutos();
            }
        });
    }

    private void constroiListaProdutos() {
        ProdutoDAO produtoDAO = SigcomeDatabase
                .getInstance(FinalizarCompraActivity.this).getRoomProdutoDAO();
        new ProdutoServices(produtoDAO).ConstroiListaProdutosPorListaDeItens(mCompra_Itens,
                new BaseListenerComParametro<List<Produto>>() {
                    @Override
                    public void quandoExecutado() {

                    }

                    @Override
                    public void quandoFinalizado(List<Produto> resultado) {
                        mProdutosCarregados = resultado;
                        adapter.atualiza(mProdutosCarregados);
                    }
                });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
