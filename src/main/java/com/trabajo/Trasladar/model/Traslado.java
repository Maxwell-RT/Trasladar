package com.trabajo.Trasladar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Traslado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTraslado;
    private Long idSucursal;
    private String fecha;
    private EstadoTraslado estado;
    private Long idSucursalOrigen;
    private Long idSucursalDestino;
    private Long fechaHora;
    private String motivo;

    public void setId(Long id) {
        this.idTraslado = id;
    }
}