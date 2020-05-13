package br.com.sig.sigcome.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import br.com.sig.sigcome.R;
import br.com.sig.sigcome.asynctask.BaseListenerComParametro;
import br.com.sig.sigcome.asynctask.BaseListenerSemParametro;
import br.com.sig.sigcome.asynctask.CarregaTodosProdutosTask;
import br.com.sig.sigcome.database.SigcomeDatabase;
import br.com.sig.sigcome.database.dao.MovimentacaoDAO;
import br.com.sig.sigcome.database.dao.ProdutoDAO;
import br.com.sig.sigcome.database.services.MovimentacaoServices;
import br.com.sig.sigcome.helper.TextInputLayoutHelper;
import br.com.sig.sigcome.model.Movimentacao;
import br.com.sig.sigcome.model.Produto;

public class ConsumoActivity extends AppCompatActivity {

    public static final String TITULO_APP_BAR = "Consumo";
    private Spinner spinner_produtos;
    private ProdutoDAO produtoDAO;
    private List<Produto> mProdutos;
    private Produto mProdutoSelecionado;
    private EditText et_formulario_consumo_quantidade;
    private MovimentacaoDAO movimentacaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumo);

        setTitle(TITULO_APP_BAR);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SigcomeDatabase instance = SigcomeDatabase.getInstance(this);
        produtoDAO = instance.getRoomProdutoDAO();
        movimentacaoDAO = instance.getRoomMovimentacaoDAO();

        et_formulario_consumo_quantidade = TextInputLayoutHelper
                .getEditText(ConsumoActivity.this, R.id.et_formulario_consumo_quantidade);

        configuraBotaoFinalizarConsumo();
    }

    @Override
    protected void onResume() {
        super.onResume();

        new CarregaTodosProdutosTask(produtoDAO, new BaseListenerComParametro<List<Produto>>() {
            @Override
            public void quandoExecutado() {

            }

            @Override
            public void quandoFinalizado(List<Produto> resultado) {
                mProdutos = resultado;
                configuraSpinnerProdutos();
            }
        }).execute();
    }

    private void configuraSpinnerProdutos() {
        spinner_produtos = findViewById(R.id.spinner_produto);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                R.layout.support_simple_spinner_dropdown_item, mProdutos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_produtos.setAdapter(adapter);

        spinner_produtos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mProdutoSelecionado = mProdutos.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void configuraBotaoFinalizarConsumo() {
        Button bt_finalizar_consumo = findViewById(R.id.bt_finalizar_consumo);
        bt_finalizar_consumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.AlertDialog.Builder builder =
                        new android.app.AlertDialog.Builder(ConsumoActivity.this);

                String mensagem = "Produto: " + mProdutoSelecionado.getCh_descricao() + "\n"
                        + "Quantidade: " + et_formulario_consumo_quantidade.getText().toString();
                builder.setTitle("Foi isso mesmo que você acabou de comer?")
                        .setMessage(mensagem)
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Movimentacao movimentacao = new Movimentacao();
                                movimentacao.setCd_produto(mProdutoSelecionado.getCd_produto());
                                movimentacao
                                        .setVr_quantidade(Double
                                                .valueOf( et_formulario_consumo_quantidade
                                                        .getText().toString()));
                                movimentacao.setDt_cadastro(Calendar.getInstance());
                                new MovimentacaoServices(movimentacaoDAO)
                                        .InserirMovimentacao(movimentacao, true, listener());
                            }
                        })
                        .setNegativeButton("Cancelar", null);

                android.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private BaseListenerSemParametro listener() {
        BaseListenerSemParametro listener = new BaseListenerSemParametro() {
            @Override
            public void quandoExecutado() {

            }

            @Override
            public void quandoFinalizado() {
                voltar();
            }
        };

        return listener;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        voltar();
        super.onBackPressed();
    }

    public void voltar() {
        finish();
    }

}
