package com.tfg.eventos.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tfg.eventos.entidad.Asistente;
import com.tfg.eventos.entidad.Entrada;
import com.tfg.eventos.entidad.Usuario;
import com.tfg.eventos.servicio.AsistenteService;
import com.tfg.eventos.servicio.EntradaService;
import com.tfg.eventos.servicio.UsuarioService;

@Controller
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AsistenteService asistenteService;
    private final EntradaService entradaService;
    public UsuarioController(UsuarioService usuarioService, AsistenteService asistenteService, EntradaService entradaService){
        this.usuarioService = usuarioService;
        this.asistenteService = asistenteService;
        this.entradaService = entradaService;
    }
    @GetMapping("/mis_entradas")
    public String obtenerEntradas(Model model, Authentication authentication){
        String emailLogueado = authentication.getName();
        Optional<Usuario> usuario = usuarioService.obtenerPorEmail(emailLogueado);
        if (usuario.isEmpty()){
            return "noexiste";
        }
        Usuario usuarioReal = usuario.get();
        List<Asistente> asistentes = asistenteService.obtenerTodos();
        List<Asistente> asistentesUsuario = new ArrayList<>();
        for (Asistente asistente : asistentes){
            if (asistente.getUsuario().getId() == usuarioReal.getId()){
                asistentesUsuario.add(asistente);
            }
        }
        List<Entrada> entradas = new ArrayList<>();
        for (Asistente as : asistentesUsuario){
            List<Entrada> entradasAsistente = entradaService.obtenerPorAsistente(as);
            entradas.addAll(entradasAsistente);
        }
        model.addAttribute("entradas", entradas);
        return "mis_entradas";
        }
    }
