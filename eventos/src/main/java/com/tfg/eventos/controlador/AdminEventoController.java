package com.tfg.eventos.controlador;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.tfg.eventos.entidad.Evento;
import com.tfg.eventos.entidad.Usuario;
import com.tfg.eventos.entidad.enums.EstadoEvento;
import com.tfg.eventos.servicio.EventoService;
import com.tfg.eventos.servicio.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminEventoController{
    private final EventoService eventoService;
    private final UsuarioService usuarioService;
    public AdminEventoController(EventoService eventoService, UsuarioService usuarioService){
        this.eventoService = eventoService;
        this.usuarioService = usuarioService;
    }
    @GetMapping("/admin/eventos")
    public String eventosAdmin(Model model, Authentication authentication) {
        Optional<Usuario> adminLogueado = usuarioService.obtenerPorEmail(authentication.getName());
        if (adminLogueado.isEmpty()){
            return "noexiste";
        }
        List<Evento> eventosAdmin = eventoService.obtenerPorOrganizador(adminLogueado.get());
        model.addAttribute("eventos", eventosAdmin);
        return "admin_eventos";
    }
    @GetMapping("/admin/eventos/nuevo")
    public String nuevoEvento(Model model){
        Evento evento = new Evento();
        model.addAttribute("evento", evento);
        return "admin_evento_nuevo";
    }
    @PostMapping("/admin/eventos")
    public String postEvento(@ModelAttribute Evento evento, Authentication authentication) {
        Optional<Usuario> admin = usuarioService.obtenerPorEmail(authentication.getName());
        if (admin.isEmpty()){
            return "noexiste";
        }
        Usuario administrador = admin.get();
        evento.setOrganizador(administrador);
        evento.setEstado(EstadoEvento.PLANIFICADO);
        evento.setCreadoEn(LocalDateTime.now());
        eventoService.guardar(evento);
        return "redirect:/admin/eventos";
    }
    @GetMapping("/admin/eventos/{id}/editar")
    public String editarEvento(@PathVariable Long id, Model model){
        Optional<Evento> eventoAEditar = eventoService.obtenerPorId(id);
        if (eventoAEditar.isEmpty()){
            return "noexiste";
        }
        model.addAttribute("evento", eventoAEditar.get());
        return "admin_evento_editar";
    }
    @PostMapping("/admin/eventos/{id}")
    public String postEvento(@PathVariable Long id, @ModelAttribute Evento eventoEditado){
        Optional<Evento> eventoPost = eventoService.obtenerPorId(id);
        if (eventoPost.isEmpty()) {
            return "noexiste";
        }
        Evento eventoExistente = eventoPost.get();
        eventoExistente.setNombre(eventoEditado.getNombre());
        eventoExistente.setDescripcion(eventoEditado.getDescripcion());
        eventoExistente.setUbicacion(eventoEditado.getUbicacion());
        eventoExistente.setFechaInicio(eventoEditado.getFechaInicio());
        eventoExistente.setFechaFin(eventoEditado.getFechaFin());
        eventoExistente.setCapacidad(eventoEditado.getCapacidad());
        eventoService.guardar(eventoExistente);
        return "redirect:/admin/eventos";
    }
    @PostMapping("/admin/eventos/{id}/eliminar")
    public String eliminarEvento(@PathVariable Long id) {
        Optional<Evento> eventoBuscado = eventoService.obtenerPorId(id);
        if (eventoBuscado.isEmpty()){
            return "noexiste";
        }
        eventoService.eliminar(id);
        return "redirect:/admin/eventos";
    }
    
}
