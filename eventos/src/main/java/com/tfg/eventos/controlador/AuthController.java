package com.tfg.eventos.controlador;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tfg.eventos.entidad.Usuario;
import com.tfg.eventos.entidad.enums.RolUsuario;
import com.tfg.eventos.servicio.UsuarioService;


@Controller
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService usuarioService, PasswordEncoder passwordEncoder){
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/registro_exito")
    public String registroExito() {
        return "registro_exito";
    }

    @PostMapping("/register")
    public String registrarUsuario(@RequestParam String nombre, @RequestParam String email, @RequestParam String contrasena) {
        String emailNormalizado = email.trim().toLowerCase();

        if (usuarioService.obtenerPorEmail(emailNormalizado).isPresent()) {
            return "redirect:/register?error=email";
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(emailNormalizado);
        usuario.setContrasenaCifrada(passwordEncoder.encode(contrasena));
        usuario.setRol(RolUsuario.USUARIO);
        usuario.setCreadoEn(LocalDateTime.now());
        usuarioService.guardar(usuario);
        return "redirect:/registro_exito";
    }
}
