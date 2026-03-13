package com.tfg.eventos.repositorio;
import com.tfg.eventos.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
// Clase repositorio vacía de momento ya que utilizamos los métodos
// de jpa que vienen por defecto.

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}