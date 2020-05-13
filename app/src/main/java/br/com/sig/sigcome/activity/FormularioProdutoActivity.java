package br.com.sig.sigcome.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import br.com.sig.sigcome.R;
import br.com.sig.sigcome.asynctask.BaseListenerComParametro;
import br.com.sig.sigcome.asynctask.BaseListenerSemParametro;
import br.com.sig.sigcome.asynctask.CarregaTodasCategoriasTask;
import br.com.sig.sigcome.database.SigcomeDatabase;
import br.com.sig.sigcome.database.dao.CategoriaDAO;
import br.com.sig.sigcome.database.dao.ProdutoDAO;
import br.com.sig.sigcome.database.services.ProdutoServices;
import br.com.sig.sigcome.helper.CONSTANTES;
import br.com.sig.sigcome.helper.CategoriaHelper;
import br.com.sig.sigcome.helper.MensagemHelper;
import br.com.sig.sigcome.helper.TextInputLayoutHelper;
import br.com.sig.sigcome.helper.UnidadeHelper;
import br.com.sig.sigcome.model.Categoria;
import br.com.sig.sigcome.model.Produto;

public class FormularioProdutoActivity extends AppCompatActivity {

    public static final String TITULO_APP_BAR = "Produto";
    private List<String> mUnidades = new ArrayList<>();
    private String mUnidadeSelecionada;
    private Categoria mCategoriaSelecionada;
    private List<Categoria> mCategorias;
    private CategoriaDAO categoriaDAO;
    private Produto mProduto;
    private ProdutoDAO dao;
    private EditText et_formulario_descricao_produto, et_formulario_produto_tempo_duracao,
            et_formulario_produto_unidades_minima;
    private Spinner spinner_categorias, spinner_unidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_produto);

        setTitle(TITULO_APP_BAR);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iniciandoComponentes();
        configuraSpinnerUnidades();

        dao = SigcomeDatabase.getInstance(this).getRoomProdutoDAO();
    }

    private void iniciandoComponentes() {
        et_formulario_descricao_produto = TextInputLayoutHelper
                .getEditText(this, R.id.et_formulario_descricao_produto);
        et_formulario_produto_tempo_duracao = TextInputLayoutHelper
                .getEditText(this, R.id.et_formulario_produto_tempo_duracao);
        et_formulario_produto_unidades_minima = TextInputLayoutHelper
                .getEditText(this, R.id.et_formulario_produto_unidades_minima);
        spinner_categorias = findViewById(R.id.spinner_categorias);
        spinner_unidades = findViewById(R.id.spinner_tipo_unidades);
    }


    private void configuraSpinnerUnidades() {
        mUnidades.add("UN");
        mUnidades.add("KG");
        mUnidades.add("L");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, mUnidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_unidades.setAdapter(adapter);

        spinner_unidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mUnidadeSelecionada = mUnidades.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        categoriaDAO = SigcomeDatabase.getInstance(this).getRoomCategoriaDAO();
        new CarregaTodasCategoriasTask(categoriaDAO, new BaseListenerComParametro<List<Categoria>>() {
            @Override
            public void quandoExecutado() {

            }

            @Override
            public void quandoFinalizado(List<Categoria> resultado) {
                mCategorias = resultado;
                configuraSpinnerCategorias();

                preencherInformacoes();
            }
        }).execute();

    }

    private void preencherInformacoes() {
        if (getIntent().hasExtra(CONSTANTES.PRODUTO)) {
            mProduto = (Produto) getIntent().getSerializableExtra(CONSTANTES.PRODUTO);

            et_formulario_descricao_produto.setText(mProduto.getCh_descricao());
            et_formulario_produto_tempo_duracao.setText(mProduto.getIn_dias_duracao().toString());
            et_formulario_produto_unidades_minima.setText(mProduto.getIn_quantidade_minima().toString());

            spinner_unidades
                    .setSelection(UnidadeHelper
                            .retornarPosicaoItemNaLista(mProduto.getCh_tipo_unidade(),
                                    mUnidades));

            spinner_categorias
                    .setSelection(CategoriaHelper
                            .retornarPosicaoItemNaLista(mProduto.getCd_categoria(),
                                    mCategorias));
        }
    }

    private void configuraSpinnerCategorias() {
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, mCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_categorias.setAdapter(adapter);

        spinner_categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mCategoriaSelecionada = mCategorias.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.bt_formulario_cancelar:
                cancelar();
                return true;
            case R.id.bt_formulario_salvar:
                salvar();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void salvar() {

        Produto produtoParaSalvar = preencherObjeto();

        if (mProduto == null) {
            produtoParaSalvar.setDt_cadastro(Calendar.getInstance());
        }
        else {
            produtoParaSalvar.setCd_produto(mProduto.getCd_produto());
            produtoParaSalvar.setDt_cadastro(mProduto.getDt_cadastro());
            produtoParaSalvar.setDt_edicao(Calendar.getInstance());
        }

        new ProdutoServices(dao).SalvarProduto(produtoParaSalvar, new BaseListenerSemParametro() {
            @Override
            public void quandoExecutado() {

            }

            @Override
            public void quandoFinalizado() {
                voltar();
            }
        });

    }

    private Produto preencherObjeto() {
        Produto produto = new Produto();

        produto.setVr_ultima_compra(0.0);
        produto.setIn_quantidade_minima(Integer
                .valueOf(et_formulario_produto_unidades_minima.getText().toString()));
        produto.setIn_dias_duracao(Integer
                .valueOf(et_formulario_produto_tempo_duracao.getText().toString()));
        produto.setCh_descricao(et_formulario_descricao_produto.getText().toString());
        produto.setCd_categoria(mCategoriaSelecionada.getCd_categoria());
        produto.setCh_tipo_unidade(mUnidadeSelecionada);

        return produto;
    }

    public void cancelar() {
        MensagemHelper.GerarToastRapidoCancelado(this);
        voltar();
    }

    public void voltar() {
        finish();
    }

    @Override
    public void onBackPressed() {
        voltar();
        super.onBackPressed();
    }

}
