package com.tfg.eventos.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.eventos.entidad.Entrada;
import com.tfg.eventos.repositorio.EntradaRepository;
// Clase service de entrada con el repositorio, 
// dos métodos y sus respectivas validaciones. 

@Service
public class EntradaService {

    @Autowired
    private EntradaRepository enrepo;

    public List<Entrada> listarEntradas() {
        return enrepo.findAll();
    }

    public Entrada buscarEntradaPorId(Long id){
        Optional<Entrada> opt = enrepo.findById(id);
        if (opt.isPresent()){
            return opt.get();
        } else {
            return null;
        }
    }

    public Entrada guardarEntrada(Entrada entrada) {
        if (entrada == null) {
            throw new IllegalArgumentException("No existe la entrada a guardar.");
        }
        if (entrada.getQrToken() == null || entrada.getQrToken().isBlank()) {
            throw new IllegalArgumentException("El QR es obligatorio.");
        }
        if (entrada.getEstado() == null) {
            throw new IllegalArgumentException("El estado de la entrada es obligatorio.");
        }
        if (entrada.getEstadoPago() == null) {
            throw new IllegalArgumentException("El estado de pago es obligatorio.");
        }
        if (entrada.getCompradaEn() == null) {
            throw new IllegalArgumentException("La fecha de compra es obligatoria.");
        }
        if (entrada.getEvento() == null) {
            throw new IllegalArgumentException("La entrada debe estar asociada a un evento.");
        }
        if (entrada.getComprador() == null) {
            throw new IllegalArgumentException("La entrada debe tener comprador.");
        }

        return enrepo.save(entrada);
    }
}
