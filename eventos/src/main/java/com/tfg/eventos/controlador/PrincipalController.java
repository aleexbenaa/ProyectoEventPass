package main.java.com.tfg.eventos.controlador;

import com.tfg.eventos.entidad.EstadoEvento;
import com.tfg.eventos.entidad.Evento;
import com.tfg.eventos.servicio.EventoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PrincipalController {
    private final EventoService eventoService;

    public PrincipalController(EventoService eventoService){
        this.eventoService = eventoService;
    }

@GetMapping("/")
public String home(Model model) {
    List<Evento> todos = eventoService.obtenerTodos();
    List<Evento> publicados = new ArrayList<>();

    for (Evento evento : todos) {
        if (evento.getEstado() == EstadoEvento.PUBLICADO) {
            publicados.add(evento);
        }
    }

    model.addAttribute("eventos", publicados);
    return "principal";
}
}