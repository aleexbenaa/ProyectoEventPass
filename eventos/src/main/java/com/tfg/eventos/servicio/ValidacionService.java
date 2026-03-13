package com.tfg.eventos.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.eventos.entidad.Validacion;
import com.tfg.eventos.repositorio.ValidacionRepository;
// Clase service de validacion con el repositorio, 
// dos métodos y sus respectivas validaciones. 

@Service
public class ValidacionService {

    @Autowired
    private ValidacionRepository vrepo;

    public List<Validacion> listarValidaciones() {
        return vrepo.findAll();
    }

    public Validacion buscarValidacionPorId(Long id){
        Optional<Validacion> opt = vrepo.findById(id);
        if (opt.isPresent()){
            return opt.get();
        } else {
            return null;
        }
    }

    public Validacion guardarValidacion(Validacion validacion) {
        if (validacion == null) {
            throw new IllegalArgumentException("No existe la validación a guardar.");
        }
        if (validacion.getEntrada() == null) {
            throw new IllegalArgumentException("La validación debe tener entrada.");
        }
        if (validacion.getEvento() == null) {
            throw new IllegalArgumentException("La validación debe tener evento.");
        }
        if (validacion.getValidador() == null) {
            throw new IllegalArgumentException("La validación debe tener validador.");
        }
        if (validacion.getFechaValidacion() == null) {
            throw new IllegalArgumentException("La fecha de validación es obligatoria.");
        }
        if (validacion.getResultado() == null) {
            throw new IllegalArgumentException("El resultado de validación es obligatorio.");
        }

        return vrepo.save(validacion);
    }
}
