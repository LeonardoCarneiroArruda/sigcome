package br.com.sig.sigcome.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import br.com.sig.sigcome.R;
import br.com.sig.sigcome.adapter.ListaComprasAdapter;
import br.com.sig.sigcome.database.SigcomeDatabase;
import br.com.sig.sigcome.database.dao.CompraDAO;
import br.com.sig.sigcome.database.services.CompraServices;
import br.com.sig.sigcome.helper.CONSTANTES;
import br.com.sig.sigcome.model.Compra;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListagemComprasActivity extends AppCompatActivity {

    public static final String TITULO_APP_BAR = "Compras";
    private ListaComprasAdapter adapter;
    private CompraDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_compras);

        setTitle(TITULO_APP_BAR);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dao = SigcomeDatabase.getInstance(this).getRoomCompraDAO();

        configurarListaCompras();
        configuraBotaoAddCompra();
    }

    private void configurarListaCompras() {
        RecyclerView rv_compras = findViewById(R.id.rv_compras);
        adapter = new ListaComprasAdapter(this, onClick());
        rv_compras.setAdapter(adapter);

        carregarCompras();
    }

    private ListaComprasAdapter.OnItemClickListener onClick() {
        ListaComprasAdapter.OnItemClickListener listenerClickItem = new ListaComprasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int posicao, Compra compra) {
                vaiParaTelaDeVisualizarCompra(compra);
            }
        };

        return listenerClickItem;
    }

    private void vaiParaTelaDeVisualizarCompra(Compra compra) {
        Intent intent = new Intent(ListagemComprasActivity.this,
                FinalizarCompraActivity.class);

        intent.putExtra(CONSTANTES.COMPRA, compra);
        intent.putExtra(CONSTANTES.VISUALIZA_COMPRAS, "true");
        startActivity(intent);
    }

    private void carregarCompras() {
        new CompraServices(dao).CarregarTodasCompras(adapter);
    }

    private void configuraBotaoAddCompra() {
        FloatingActionButton bt_add_compra = findViewById(R.id.bt_add_compra);
        bt_add_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaTelaInserirItensCompra();
            }
        });
    }

    private void vaiParaTelaInserirItensCompra() {
        Intent intent = new Intent(ListagemComprasActivity.this,
                InserirItemCompraActivity.class);
        startActivity(intent);
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
