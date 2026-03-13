package com.tfg.eventos.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.eventos.entidad.Evento;
import com.tfg.eventos.repositorio.EventoRepository;
// Clase service de evento con el repositorio, 
// dos métodos y sus respectivas validaciones. 

@Service
public class EventoService {

    @Autowired
    private EventoRepository evrepo;

    public List<Evento> listarEventos() {
        return evrepo.findAll();
    }

    public Evento buscarEventoPorId(Long id){
        Optional<Evento> opt = evrepo.findById(id);
        if (opt.isPresent()){
            return opt.get();
        } else {
            return null;
        }
    }

    public Evento guardarEvento(Evento evento) {
        if (evento == null) {
            throw new IllegalArgumentException("No existe el evento a guardar.");
        }
        if (evento.getTitulo() == null || evento.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio.");
        }
        if (evento.getDescripcion() == null || evento.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripción es obligatoria.");
        }
        if (evento.getUbicacion() == null || evento.getUbicacion().isBlank()) {
            throw new IllegalArgumentException("La ubicación es obligatoria.");
        }
        if (evento.getFechaInicio() == null) {
            throw new IllegalArgumentException("La fecha de inicio es obligatoria.");
        }
        if (evento.getFechaFin() == null) {
            throw new IllegalArgumentException("La fecha de fin es obligatoria.");
        }
        if (!evento.getFechaFin().isAfter(evento.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la de inicio.");
        }
        if (evento.getAforo() <= 0) {
            throw new IllegalArgumentException("El aforo debe ser mayor que 0.");
        }
        if (evento.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        if (evento.getEstado() == null) {
            throw new IllegalArgumentException("El estado es obligatorio.");
        }
        if (evento.getCreadoEn() == null) {
            throw new IllegalArgumentException("La fecha de creación es obligatoria.");
        }
        if (evento.getCreadoPor() == null) {
            throw new IllegalArgumentException("El evento debe estar creado por alguien.");
        }

        return evrepo.save(evento);
    }
}
