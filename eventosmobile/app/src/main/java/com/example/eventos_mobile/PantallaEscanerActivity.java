package com.example.eventos_mobile;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class PantallaEscanerActivity extends AppCompatActivity {

    private TextView textoEvento;
    private Button botonAbrirCamara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaner);

        textoEvento = findViewById(R.id.tvEventoSeleccionado);
        botonAbrirCamara = findViewById(R.id.btnAbrirCamara);

        String nombreEvento = getIntent().getStringExtra("nombreEvento");
        textoEvento.setText("Evento: " + (nombreEvento != null ? nombreEvento : ""));

        botonAbrirCamara.setOnClickListener(v -> abrirEscaner());
    }

    private void abrirEscaner() {
        IntentIntegrator integrador = new IntentIntegrator(this);
        integrador.setPrompt("Escanea el QR");
        integrador.setBeepEnabled(true);
        integrador.setOrientationLocked(false);
        integrador.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        IntentResult resultado = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (resultado != null) {
            String textoLeido = resultado.getContents();
            if (textoLeido == null) {
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "QR leído: " + textoLeido, Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
