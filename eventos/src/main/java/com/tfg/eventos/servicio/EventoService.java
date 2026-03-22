package com.tfg.eventos.servicio;
import com.tfg.eventos.entidad.Evento;
import com.tfg.eventos.entidad.Usuario;
import com.tfg.eventos.repositorio.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Clase servicio de Evento, contiene la lógica de negocio relacionada con eventos.

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> obtenerTodos() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> obtenerPorId(Long id) {
        return eventoRepository.findById(id);
    }

    public List<Evento> obtenerPorOrganizador(Usuario organizador) {
        return eventoRepository.findByOrganizador(organizador);
    }

    public Evento guardar(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Evento actualizar(Evento evento) {
        return eventoRepository.save(evento);
    }

    public void eliminar(Long id) {
        eventoRepository.deleteById(id);
    }
}