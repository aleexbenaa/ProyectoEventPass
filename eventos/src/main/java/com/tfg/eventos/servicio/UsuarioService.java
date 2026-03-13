package com.tfg.eventos.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.eventos.entidad.Usuario;
import com.tfg.eventos.repositorio.UsuarioRepository;
// Clase service de usuario con el repositorio, 
// dos métodos y sus respectivas validaciones.

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository urepo;

    public List<Usuario> listarUsuarios() {
        return urepo.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id){
        Optional<Usuario> opt = urepo.findById(id);
        if (opt.isPresent()){
            return opt.get();
        } else {
            return null;
        }
    }

    public Usuario guardarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("No existe el usuario a guardar.");
        }
        if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }
        if (usuario.getContrasenaCifrada() == null || usuario.getContrasenaCifrada().isBlank()) {
            throw new IllegalArgumentException("La contraseña cifrada es obligatoria.");
        }
        if (usuario.getRol() == null) {
            throw new IllegalArgumentException("El rol es obligatorio.");
        }
        if (usuario.getCreadoEn() == null) {
            throw new IllegalArgumentException("La fecha de creación es obligatoria.");
        }

        return urepo.save(usuario);
    }
}
