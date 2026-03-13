package com.tfg.eventos.repositorio;
import com.tfg.eventos.entidad.Validacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ValidacionRepository extends JpaRepository<Validacion, Long>{

}