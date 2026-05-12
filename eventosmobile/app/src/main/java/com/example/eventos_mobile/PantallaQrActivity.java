package com.example.eventos_mobile;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class PantallaQrActivity extends AppCompatActivity {

    private WebView webViewQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_qr);

        webViewQr = findViewById(R.id.webViewQr);
        webViewQr.setWebViewClient(new WebViewClient());

        Long entradaId = getIntent().getLongExtra("entradaId", -1L);

        if (entradaId != -1L) {
            String urlQr = ClienteRetrofit.obtenerBaseUrl() + "entradas/" + entradaId + "/qr";
            webViewQr.loadUrl(urlQr);
        }
    }
}
