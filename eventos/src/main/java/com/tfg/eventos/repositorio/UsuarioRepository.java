package com.tfg.eventos.repositorio;
import com.tfg.eventos.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repositorio de Usuario, contiene sus métodos de búsqueda personalizados.

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}