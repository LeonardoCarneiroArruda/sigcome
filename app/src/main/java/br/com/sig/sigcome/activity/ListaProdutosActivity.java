package br.com.sig.sigcome.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import br.com.sig.sigcome.R;
import br.com.sig.sigcome.adapter.ListaProdutoAdapter;
import br.com.sig.sigcome.database.SigcomeDatabase;
import br.com.sig.sigcome.database.dao.ProdutoDAO;
import br.com.sig.sigcome.database.services.ProdutoServices;
import br.com.sig.sigcome.helper.CONSTANTES;
import br.com.sig.sigcome.model.Produto;
import br.com.sig.sigcome.model.modelsLista.ProdutoModelLista;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListaProdutosActivity extends AppCompatActivity {

    public static final String TITULO_APP_BAR = "Produtos";
    private ListaProdutoAdapter adapter;
    private ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        setTitle(TITULO_APP_BAR);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        produtoDAO = SigcomeDatabase.getInstance(this).getRoomProdutoDAO();

        configuraBotaoAdicionarProduto();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configuraListaProdutos();
    }

    private void configuraBotaoAdicionarProduto() {
        FloatingActionButton bt_add_produto = findViewById(R.id.bt_add_produto);
        bt_add_produto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormularioProduto();
            }
        });
    }

    private void vaiParaFormularioProduto() {
        Intent intent = new Intent(ListaProdutosActivity.this, FormularioProdutoActivity.class);
        startActivity(intent);
    }

    private void configuraListaProdutos() {
        RecyclerView rv_produtos = findViewById(R.id.rv_produtos);
        adapter = new ListaProdutoAdapter(this, onClick());
        rv_produtos.setAdapter(adapter);

        carregaProdutos();
    }

    private ListaProdutoAdapter.OnItemClickListener onClick() {

        ListaProdutoAdapter.OnItemClickListener listener = new ListaProdutoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int posicao, ProdutoModelLista produto) {
                vaiParaTelaFormularioProdutoParaEdicao(produto.getProduto());
            }
        };

        return listener;
    }

    private void vaiParaTelaFormularioProdutoParaEdicao(Produto produto) {
        Intent intent = new Intent(ListaProdutosActivity.this,
                FormularioProdutoActivity.class);
        intent.putExtra(CONSTANTES.PRODUTO, produto);
        startActivity(intent);
    }

    private void carregaProdutos() {
        new ProdutoServices(produtoDAO).CarregarTodosProdutos(adapter);
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
