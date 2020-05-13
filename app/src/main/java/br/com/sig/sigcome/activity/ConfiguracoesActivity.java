package br.com.sig.sigcome.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import br.com.sig.sigcome.R;
import br.com.sig.sigcome.asynctask.BaseListenerComParametro;
import br.com.sig.sigcome.asynctask.BaseListenerSemParametro;
import br.com.sig.sigcome.database.SigcomeDatabase;
import br.com.sig.sigcome.database.dao.PessoaDAO;
import br.com.sig.sigcome.database.services.PessoaServices;
import br.com.sig.sigcome.helper.MensagemHelper;
import br.com.sig.sigcome.helper.TextInputLayoutHelper;
import br.com.sig.sigcome.model.Pessoa;

public class ConfiguracoesActivity extends AppCompatActivity {

    public static final String TITULO_APP_BAR = "Configurações";
    private PessoaDAO pessoaDAO;
    EditText et_formulario_configuracoes_nome, et_formulario_configuracoes_telefone,
            et_formulario_configuracoes_email;
    private Pessoa mPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        setTitle(TITULO_APP_BAR);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        et_formulario_configuracoes_nome = TextInputLayoutHelper.getEditText(this, R.id.et_formulario_configuracoes_nome);
        et_formulario_configuracoes_telefone = TextInputLayoutHelper.getEditText(this, R.id.et_formulario_configuracoes_telefone);
        et_formulario_configuracoes_email = TextInputLayoutHelper.getEditText(this, R.id.et_formulario_configuracoes_email);

        pessoaDAO = SigcomeDatabase.getInstance(this).getRoomPessoaDAO();
    }

    @Override
    protected void onResume() {
        new PessoaServices(pessoaDAO).BuscaPessoaCadastrada(new BaseListenerComParametro<Pessoa>() {
            @Override
            public void quandoExecutado() {

            }

            @Override
            public void quandoFinalizado(Pessoa resultado) {
                mPessoa = resultado;
                preencherInformacoesJaCadastradas();
            }
        });
        super.onResume();
    }

    private void preencherInformacoesJaCadastradas() {
        et_formulario_configuracoes_nome.setText(mPessoa.getCh_nome());
        et_formulario_configuracoes_telefone.setText(mPessoa.getCh_telefone());
        et_formulario_configuracoes_email.setText(mPessoa.getCh_email());
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
        if (mPessoa == null)
            mPessoa = new Pessoa();

        mPessoa.setCh_nome(et_formulario_configuracoes_nome.getText().toString());
        mPessoa.setCh_telefone(et_formulario_configuracoes_telefone.getText().toString());
        mPessoa.setCh_email(et_formulario_configuracoes_email.getText().toString());

        new PessoaServices(pessoaDAO).SalvarPessoa(mPessoa, new BaseListenerSemParametro() {
            private ProgressDialog pDialog;
            @Override
            public void quandoExecutado() {
                pDialog = new ProgressDialog(ConfiguracoesActivity.this);
                pDialog.setTitle("Salvando");
                pDialog.setMessage("Aguarde...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            public void quandoFinalizado() {
                pDialog.dismiss();
                MensagemHelper.GerarToastRapidoSalvo(ConfiguracoesActivity.this);
                finish();
            }
        });
    }

    public void voltar() {
        finish();
    }

    public void cancelar() {
        MensagemHelper.GerarToastRapidoCancelado(this);
        voltar();
    }

    @Override
    public void onBackPressed() {
        voltar();
        super.onBackPressed();
    }
}
