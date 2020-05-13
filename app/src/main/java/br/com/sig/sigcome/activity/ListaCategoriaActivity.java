package br.com.sig.sigcome.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import br.com.sig.sigcome.R;
import br.com.sig.sigcome.adapter.ListaCategoriaAdapter;
import br.com.sig.sigcome.asynctask.BaseListenerComParametro;
import br.com.sig.sigcome.database.SigcomeDatabase;
import br.com.sig.sigcome.database.dao.CategoriaDAO;
import br.com.sig.sigcome.database.dao.PessoaDAO;
import br.com.sig.sigcome.database.services.CategoriaServices;
import br.com.sig.sigcome.database.services.PessoaServices;
import br.com.sig.sigcome.model.Categoria;
import br.com.sig.sigcome.model.Pessoa;

public class ListaCategoriaActivity extends AppCompatActivity {

    private static final String TITULO_APP_BAR = "Categorias";
    private ListaCategoriaAdapter adapter;
    private CategoriaDAO categoriaDAO;
    private PessoaDAO pessoaDAO;
    private Pessoa mPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categoria);
        setTitle(TITULO_APP_BAR);

        SigcomeDatabase instance = SigcomeDatabase.getInstance(this);
        categoriaDAO = instance.getRoomCategoriaDAO();
        pessoaDAO = instance.getRoomPessoaDAO();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        configuraBotaoAdicionarCategoria();
        configuraListaCategorias();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new PessoaServices(pessoaDAO).BuscaPessoaCadastrada(new BaseListenerComParametro<Pessoa>() {
            @Override
            public void quandoExecutado() {

            }

            @Override
            public void quandoFinalizado(Pessoa resultado) {
                mPessoa = resultado;
            }
        });
    }

    private void configuraListaCategorias() {
        RecyclerView rv_categorias = findViewById(R.id.rv_categorias);
        adapter = new ListaCategoriaAdapter(this, onClick());
        rv_categorias.setAdapter(adapter);

        carregaCategorias();
    }

    private void carregaCategorias() {
        new CategoriaServices(categoriaDAO).CarregaTodasCategorias(adapter);
    }

    private ListaCategoriaAdapter.OnItemClickListener onClick() {
        ListaCategoriaAdapter.OnItemClickListener listener = new ListaCategoriaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int posicao, final Categoria categoria) {
                android.app.AlertDialog.Builder builder =
                        new android.app.AlertDialog.Builder(ListaCategoriaActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                final View viewCriadaDialog = inflater.inflate(R.layout.formulario_categoria,
                        null);

                final TextView tv_formulario_id_categoria = viewCriadaDialog
                        .findViewById(R.id.tv_formulario_id_categoria);
                tv_formulario_id_categoria.setText("#" + String.valueOf(categoria.getCd_categoria()));
                tv_formulario_id_categoria.setVisibility(View.VISIBLE);

                final EditText et_formulario_descricao_categoria =
                        ((TextInputLayout)viewCriadaDialog
                                .findViewById(R.id.et_formulario_descricao_categoria))
                                .getEditText();
                et_formulario_descricao_categoria.setText(categoria.getCh_descricao());

                builder.setView(viewCriadaDialog)
                        .setTitle("Editar Categoria")
                        .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                categoria.setCh_descricao(et_formulario_descricao_categoria
                                        .getText().toString());

                                new CategoriaServices(categoriaDAO)
                                        .EditaCategoria(categoria, posicao, mPessoa, adapter);

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                android.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        };

        return listener;
    }

    private void configuraBotaoAdicionarCategoria() {
        FloatingActionButton bt_add_categoria = findViewById(R.id.bt_add_categoria);
        bt_add_categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.AlertDialog.Builder builder =
                        new android.app.AlertDialog.Builder(ListaCategoriaActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                final View viewCriadaDialog = inflater.inflate(R.layout.formulario_categoria,
                        null);

                builder.setView(viewCriadaDialog)
                        .setTitle("Adicionar Categoria")
                        .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                EditText et_formulario_descricao_categoria =
                                        ((TextInputLayout)viewCriadaDialog
                                                .findViewById(R.id.et_formulario_descricao_categoria))
                                                .getEditText();

                                Categoria categoria = new Categoria();
                                categoria.setCh_descricao(et_formulario_descricao_categoria
                                        .getText().toString());

                                new CategoriaServices(categoriaDAO)
                                        .SalvaCategoria(categoria, mPessoa, adapter);

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                android.app.AlertDialog dialog = builder.create();
                dialog.show();
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

    @Override
    public void onBackPressed() {
        voltar();
        super.onBackPressed();
    }

    public void voltar() {
        finish();
    }

}
