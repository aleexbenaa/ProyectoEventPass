package com.example.eventos_mobile;

import com.example.eventos_mobile.EventoAsignado;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ServicioApi {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> iniciarSesion(
            @Field("email") String email,
            @Field("contrasena") String contrasena
    );

    @GET("api/validador/eventos")
    Call<List<EventoAsignado>> obtenerEventosAsignados();

}
