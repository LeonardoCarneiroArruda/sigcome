package br.com.sig.sigcome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import br.com.sig.sigcome.R;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public static final String TITULO_APP_BAR = "Notificações";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle(TITULO_APP_BAR);

        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.bt_navbar_compras: {
                vaiParaTeladeCompras();
                break;
            }
            case R.id.bt_navbar_categorias: {
                vaiParaTelaDeCategorias();
                break;
            }
            case R.id.bt_navbar_produtos: {
                vaiParaTelaDeProdutos();
                break;
            }
            case R.id.bt_navbar_configuracoes: {
                vaiParaTelaDeConfiguracoes();
                break;
            }
            case R.id.bt_navbar_sobre: {
                Toast.makeText(this, "Sobre", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.bt_navbar_consumo: {
                vaiParaTelaDeConsumo();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void vaiParaTeladeCompras() {
        Intent intent = new Intent(this, ListagemComprasActivity.class);
        startActivity(intent);
    }

    private void vaiParaTelaDeConfiguracoes() {
        Intent intent = new Intent(this, ConfiguracoesActivity.class);
        startActivity(intent);
    }

    private void vaiParaTelaDeConsumo() {
        Intent intent = new Intent(this, ConsumoActivity.class);
        startActivity(intent);
    }

    private void vaiParaTelaDeProdutos() {
        Intent intent = new Intent(this, ListaProdutosActivity.class);
        startActivity(intent);
    }


    private void vaiParaTelaDeCategorias() {
        Intent intent = new Intent(this, ListaCategoriaActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }
}
