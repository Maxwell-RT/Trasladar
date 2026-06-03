package com.trabajo.Trasladar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trabajo.Trasladar.model.EstadoTraslado;
import com.trabajo.Trasladar.model.Traslado;
import com.trabajo.Trasladar.repository.TrasladoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TrasladoService {
    @Autowired
    private TrasladoRepository trasladoRepository;
    public TrasladoService(TrasladoRepository repository) {
        this.trasladoRepository=repository;
    }


public Traslado obtener(Long id){

        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser null");
        }
        return obtener(id);
                
}



public Traslado aprobar(Long id){
    Traslado traslado =  obtener(id);
    validarEstado(traslado, EstadoTraslado.ESPERA);

        traslado.setEstado(EstadoTraslado.APROBADO);
    return trasladoRepository.save(traslado);
}



private void validarEstado(Traslado traslado, EstadoTraslado esperado) {

        if (traslado.getEstado() != esperado) {
            throw new IllegalStateException(
                    "El traslado no puede cambiar desde estado "
                    + traslado.getEstado()
            );
    
}
}
public Traslado rechazar(Long id, String motivo) {

        Traslado traslado = obtener(id);

        validarEstado(traslado, EstadoTraslado.ESPERA);

        traslado.setEstado(EstadoTraslado.RECHAZADO);
    return trasladoRepository.save(traslado);
}

public Traslado cancelar(Long id) {

        Traslado traslado = obtener(id);

        validarEstado(traslado, EstadoTraslado.APROBADO);

        traslado.setEstado(EstadoTraslado.CANCELADO);
    return trasladoRepository.save(traslado);
    }




}