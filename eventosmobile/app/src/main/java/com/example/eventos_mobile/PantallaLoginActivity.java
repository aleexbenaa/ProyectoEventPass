package com.example.eventos_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eventos_mobile.ClienteRetrofit;
import com.example.eventos_mobile.ServicioApi;
import okhttp3.ResponseBody;
import retrofit2.*;

public class PantallaLoginActivity extends AppCompatActivity {

    private EditText campoEmail;
    private EditText campoContrasena;
    private Button botonEntrar;
    private ServicioApi servicioApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.etEmail);
        campoContrasena = findViewById(R.id.etPass);
        botonEntrar = findViewById(R.id.btnLogin);

        servicioApi = ClienteRetrofit.obtenerServicio();

        botonEntrar.setOnClickListener(v -> hacerLogin());
    }

    private void hacerLogin() {
        String email = campoEmail.getText().toString().trim();
        String contrasena = campoContrasena.getText().toString().trim();

        if (email.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Rellena email y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        servicioApi.iniciarSesion(email, contrasena).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() || response.code() == 302) {
                    Intent irEventos = new Intent(PantallaLoginActivity.this, PantallaEventosActivity.class);
                    startActivity(irEventos);
                    finish();
                } else {
                    Toast.makeText(PantallaLoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("LOGIN_API", "Fallo login", t);
                Toast.makeText(PantallaLoginActivity.this, "Error: " + t.getClass().getSimpleName() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
