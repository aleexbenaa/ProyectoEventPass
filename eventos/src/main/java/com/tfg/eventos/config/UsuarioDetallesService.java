package com.tfg.eventos.config;

import com.tfg.eventos.entidad.Usuario;
import com.tfg.eventos.repositorio.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioDetallesService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetallesService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        String authority = "ROLE_" + usuario.getRol().name();
        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getContrasenaCifrada(),
                List.of(new SimpleGrantedAuthority(authority))
        );
    }
}
