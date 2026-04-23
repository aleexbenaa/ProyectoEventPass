package com.tfg.eventos.controlador;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping("/")
    public String inicio(Authentication authentication) {
        if (authentication == null) {
            return "redirect:/eventos";
        }

        if (authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
            return "redirect:/admin/eventos";
        }

        return "redirect:/eventos";
    }
}
