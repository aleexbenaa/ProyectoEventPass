package com.tfg.eventos.controlador;

import com.tfg.eventos.servicio.EntradaService;
import com.tfg.eventos.servicio.RegistroAccesoService;

import org.springframework.web.bind.annotation.RestController;

import com.tfg.eventos.entidad.Entrada;
import com.tfg.eventos.entidad.RegistroAcceso;
import com.tfg.eventos.entidad.enums.EstadoEntrada;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ValidadorApiController {
    private final EntradaService entradaService;
    private final RegistroAccesoService registroAccesoService;
    ValidadorApiController(EntradaService entradaService, RegistroAccesoService registroAccesoService) {
        this.entradaService = entradaService;
        this.registroAccesoService = registroAccesoService;
    }

    @PostMapping("/api/validador/validar")
    public Map<String, String> validarEntradaApi(@RequestBody String qrToken) {
        Map<String, String> respuesta = new HashMap<>();
        Optional<Entrada> entradaABuscar = entradaService.obtenerPorQrToken(qrToken);
        
        if (entradaABuscar.isEmpty()){
            respuesta.put("estado", "noexiste");
            respuesta.put("mensaje", "Esta entrada no existe.");
            return respuesta;
        }

        Entrada entradaObtenida = entradaABuscar.get();

        if (entradaObtenida.getEstado() == EstadoEntrada.USADA){
            respuesta.put("estado", "yausada");
            respuesta.put("mensaje", "La entrada ya ha sido utilizada.");
            return respuesta;
        }
        if (entradaObtenida.getEstado() == EstadoEntrada.CANCELADA){
            respuesta.put("estado", "cancelada");
            respuesta.put("mensaje", "La entrada ha sido cancelada.");
            return respuesta;
        }
        entradaObtenida.setEstado(EstadoEntrada.USADA);
        entradaObtenida.setUsadaEn(LocalDateTime.now());
        entradaService.guardar(entradaObtenida);
        RegistroAcceso registro = new RegistroAcceso();
        registro.setEntrada(entradaObtenida);
        registro.setFechaAcceso(LocalDateTime.now());
        registro.setObservaciones("Entrada validada.");
        registroAccesoService.guardar(registro);
        respuesta.put("estado", "valida");
        respuesta.put("mensaje", "Entrada validada correctamente");
        return respuesta;
    }
    
}
