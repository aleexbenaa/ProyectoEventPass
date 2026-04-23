package com.tfg.eventos.controlador;

import com.tfg.eventos.servicio.EventoService;
import com.tfg.eventos.servicio.EntradaService;
import com.tfg.eventos.servicio.RegistroAccesoService;
import com.tfg.eventos.servicio.UsuarioService;

import org.springframework.web.bind.annotation.RestController;

import com.tfg.eventos.entidad.Evento;
import com.tfg.eventos.entidad.Entrada;
import com.tfg.eventos.entidad.RegistroAcceso;
import com.tfg.eventos.entidad.Usuario;
import com.tfg.eventos.entidad.enums.EstadoEntrada;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ValidadorApiController {
    private final EntradaService entradaService;
    private final RegistroAccesoService registroAccesoService;
    private final UsuarioService usuarioService;
    private final EventoService eventoService;

    ValidadorApiController(EntradaService entradaService,
                           RegistroAccesoService registroAccesoService,
                           UsuarioService usuarioService,
                           EventoService eventoService) {
        this.entradaService = entradaService;
        this.registroAccesoService = registroAccesoService;
        this.usuarioService = usuarioService;
        this.eventoService = eventoService;
    }

    @PostMapping("/api/validador/validar")
    public Map<String, String> validarEntradaApi(@RequestBody ValidacionRequest request, Authentication authentication) {
        Map<String, String> respuesta = new HashMap<>();

        if (request == null || request.getQrToken() == null || request.getQrToken().isBlank() || request.getIdEvento() == null) {
            respuesta.put("estado", "datosinvalidos");
            respuesta.put("mensaje", "Faltan datos para validar.");
            return respuesta;
        }

        Optional<Usuario> validadorLogueado = usuarioService.obtenerPorEmail(authentication.getName());
        if (validadorLogueado.isEmpty()) {
            respuesta.put("estado", "noautorizado");
            respuesta.put("mensaje", "Validador no encontrado.");
            return respuesta;
        }

        Optional<Evento> eventoSeleccionado = eventoService.obtenerPorId(request.getIdEvento());
        if (eventoSeleccionado.isEmpty()) {
            respuesta.put("estado", "eventonoexiste");
            respuesta.put("mensaje", "El evento seleccionado no existe.");
            return respuesta;
        }

        Evento evento = eventoSeleccionado.get();
        boolean validadorAsignado = false;
        if (evento.getValidadores() != null) {
            for (Usuario validador : evento.getValidadores()) {
                if (validador.getId().equals(validadorLogueado.get().getId())) {
                    validadorAsignado = true;
                    break;
                }
            }
        }
        if (!validadorAsignado) {
            respuesta.put("estado", "noasignado");
            respuesta.put("mensaje", "No tienes permiso para validar este evento.");
            return respuesta;
        }

        Optional<Entrada> entradaABuscar = entradaService.obtenerPorQrToken(request.getQrToken());
        
        if (entradaABuscar.isEmpty()){
            respuesta.put("estado", "noexiste");
            respuesta.put("mensaje", "Esta entrada no existe.");
            return respuesta;
        }

        Entrada entradaObtenida = entradaABuscar.get();
        if (!entradaObtenida.getAsistente().getEvento().getId().equals(evento.getId())) {
            respuesta.put("estado", "eventoincorrecto");
            respuesta.put("mensaje", "La entrada no pertenece al evento seleccionado.");
            return respuesta;
        }

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

    public static class ValidacionRequest {
        private String qrToken;
        private Long idEvento;

        public String getQrToken() {
            return qrToken;
        }

        public void setQrToken(String qrToken) {
            this.qrToken = qrToken;
        }

        public Long getIdEvento() {
            return idEvento;
        }

        public void setIdEvento(Long idEvento) {
            this.idEvento = idEvento;
        }
    }
}
