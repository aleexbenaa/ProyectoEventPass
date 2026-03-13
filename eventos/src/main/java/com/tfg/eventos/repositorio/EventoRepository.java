package com.tfg.eventos.repositorio;
import com.tfg.eventos.entidad.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long>{

}
