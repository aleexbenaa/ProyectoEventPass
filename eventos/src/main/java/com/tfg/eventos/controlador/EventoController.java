package com.tfg.eventos.controlador;

import com.tfg.eventos.servicio.UsuarioService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tfg.eventos.entidad.Asistente;
import com.tfg.eventos.entidad.Entrada;
import com.tfg.eventos.entidad.Evento;
import com.tfg.eventos.entidad.Usuario;
import com.tfg.eventos.entidad.enums.EstadoEntrada;
import com.tfg.eventos.entidad.enums.EstadoEvento;
import com.tfg.eventos.entidad.enums.EstadoPago;
import com.tfg.eventos.servicio.AsistenteService;
import com.tfg.eventos.servicio.EntradaService;
import com.tfg.eventos.servicio.EventoService;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class EventoController {
     private final UsuarioService usuarioService;
     private final EventoService eventoService;
     private final AsistenteService asistenteService;
     private final EntradaService entradaService;
    public EventoController(EventoService eventoService, UsuarioService usuarioService, AsistenteService asistenteService, EntradaService entradaService){
        this.eventoService = eventoService;
        this.usuarioService = usuarioService; 
        this.asistenteService = asistenteService;
        this.entradaService = entradaService;
    }

    @GetMapping("/eventos")
    public String listarEventos(Model model){
        List<Evento> todos = eventoService.obtenerTodos();
        List<Evento> publicados = new ArrayList<>();
        for (Evento evento : todos){
            if (evento.getEstado() == EstadoEvento.PUBLICADO){
                publicados.add(evento);
            }
        }
        model.addAttribute("eventos", publicados);
        return "eventos_lista";
    }
    @GetMapping("/eventos/{id}")
    public String mostrarEvento(@PathVariable Long id, Model model){
        Optional<Evento> evento = eventoService.obtenerPorId(id);
        if (evento.isEmpty()){
            return "noexiste";
        }
        model.addAttribute("evento", evento.get());
        return "evento_detalle";
    }
    @PostMapping("/eventos/{id}/reservar")
    public String reservarEvento(@PathVariable Long id, Authentication authentication) {
        Optional<Evento> evento = eventoService.obtenerPorId(id);
        if (evento.isEmpty()){
            return "noexiste";
        }
        String usuariologueado = authentication.getName();
        Optional<Usuario> usuarioExiste = usuarioService.obtenerPorEmail(usuariologueado);
        if (usuarioExiste.isEmpty()){
            return "noexiste";
        }
        Evento eventoReal = evento.get();
        if (eventoReal.getEstado() != EstadoEvento.PUBLICADO){
            return "redirect:/eventos/" + id + "?error=no-publicado";
        }
        Usuario usuarioReal = usuarioExiste.get();
        Optional<Asistente> asistenteDuplicado = asistenteService.obtenerPorUsuarioYEvento(usuarioReal, eventoReal);
        if (asistenteDuplicado.isPresent()){
            return "redirect:/eventos/" + id + "?error=yareservado";
        }
        int asistentesActuales = asistenteService.obtenerPorEvento(eventoReal).size();
        if (asistentesActuales >= eventoReal.getCapacidad()){
            return "redirect:/eventos/" + id + "?error=aforo-completo";
        }
        Asistente asistenteNuevo = new Asistente(usuarioReal, eventoReal);
        asistenteService.guardar(asistenteNuevo);
        String qr_token = UUID.randomUUID().toString();
        Entrada entradaNueva = new Entrada(qr_token, EstadoEntrada.ACTIVA, EstadoPago.PENDIENTE, LocalDateTime.now(), asistenteNuevo);
        entradaService.guardar(entradaNueva);
        return "redirect:/eventos/" + id + "?ok=reservada";
    }
    
}
