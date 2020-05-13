package br.com.sig.sigcome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import br.com.sig.sigcome.R;
import br.com.sig.sigcome.adapter.ListaItensAdapter;
import br.com.sig.sigcome.database.SigcomeDatabase;
import br.com.sig.sigcome.database.dao.ProdutoDAO;
import br.com.sig.sigcome.database.services.ProdutoServices;
import br.com.sig.sigcome.helper.CONSTANTES;
import br.com.sig.sigcome.helper.DatePickerFragment;
import br.com.sig.sigcome.helper.ProdutoHelper;
import br.com.sig.sigcome.helper.TextInputLayoutHelper;
import br.com.sig.sigcome.model.Compra;
import br.com.sig.sigcome.model.Produto;

public class InserirItemCompraActivity extends AppCompatActivity {

    public static final String TITULO_APP_BAR = "Inserir itens";
    private ListaItensAdapter adapter;
    private ProdutoDAO produtoDAO;
    private Integer mQuantidadeMarcado = 0;
    private TextView tv_formulario_inserir_item_contagem_produtos;
    private ArrayList<Produto> mProdutosSelecionados = new ArrayList<Produto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_item_compra);

        setTitle(TITULO_APP_BAR);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        produtoDAO = SigcomeDatabase.getInstance(this).getRoomProdutoDAO();

        tv_formulario_inserir_item_contagem_produtos =
                findViewById(R.id.tv_formulario_inserir_item_contagem_produtos);
        tv_formulario_inserir_item_contagem_produtos.setText("0 produtos selecionados");

        configuraListaItens();
        configuraClickDataEntrada();
        configuraBotaoAvancar();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configuraBotaoAvancar() {
        Button bt_avancar = findViewById(R.id.bt_inserir_item_compra_avancar);
        bt_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    vaiParaTelaFinalizarCompra();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void vaiParaTelaFinalizarCompra() throws ParseException {
        Compra compra = gerarCompra();
        Intent intent = new Intent(InserirItemCompraActivity.this,
                FinalizarCompraActivity.class);

        intent.putExtra(CONSTANTES.COMPRA, compra);
        intent.putExtra(CONSTANTES.LISTA_PRODUTOS, mProdutosSelecionados);
        startActivity(intent);
    }

    private Compra gerarCompra() throws ParseException {
        Compra compra = new Compra();

        EditText et_data_compra = TextInputLayoutHelper.getEditText(this,
                R.id.et_formulario_inserir_item_data_compra);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formato.parse(et_data_compra.getText().toString().trim());

        Calendar data_compra = Calendar.getInstance();
        data_compra.setTime(data);

        compra.setDt_compra(data_compra);

        return compra;
    }

    private void configuraClickDataEntrada() {
        final EditText et_formulario_inserir_item_data_compra =
                TextInputLayoutHelper
                        .getEditText( InserirItemCompraActivity.this,
                                R.id.et_formulario_inserir_item_data_compra);

        et_formulario_inserir_item_data_compra.setKeyListener(null);
        et_formulario_inserir_item_data_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment ClasseData = new DatePickerFragment(et_formulario_inserir_item_data_compra);
                ClasseData.show(getSupportFragmentManager(), "datepicker");
            }
        });
    }

    private void configuraListaItens() {
        RecyclerView rv_itens = findViewById(R.id.rv_lista_inserir_item);
        adapter = new ListaItensAdapter(this, onChangedChecked());
        rv_itens.setAdapter(adapter);

        carregaListaItens();
    }

    private ListaItensAdapter.OnCheckedChangeListener onChangedChecked() {
        ListaItensAdapter.OnCheckedChangeListener checkedListener = new ListaItensAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Produto produto, boolean isChecked, View itemView) {
                if (isChecked) {
                    gerenciaQuantidade(1);
                    mProdutosSelecionados.add(produto);
                }
                else {
                    gerenciaQuantidade(-1);
                    mProdutosSelecionados = ProdutoHelper
                            .removeProdutoDaLista(produto, mProdutosSelecionados);
                }
            }
        };

        return checkedListener;
    }

    private void carregaListaItens() {
        new ProdutoServices(produtoDAO).CarregarTodosItens(adapter);
    }

    private void gerenciaQuantidade(int qtd) {
        mQuantidadeMarcado += qtd;
        tv_formulario_inserir_item_contagem_produtos
                .setText(mQuantidadeMarcado + " produtos selecionados");
    }

}
