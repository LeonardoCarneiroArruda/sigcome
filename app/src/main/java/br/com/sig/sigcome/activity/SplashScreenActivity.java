package br.com.sig.sigcome.activity;

import androidx.appcompat.app.AppCompatActivity;
import br.com.sig.sigcome.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override public void run() {
                vaiParaTelaInicial();
            }
        }, 2000);
    }

    private void vaiParaTelaInicial() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
