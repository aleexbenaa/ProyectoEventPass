package com.tfg.eventos.repositorio;
import com.tfg.eventos.entidad.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {

}
