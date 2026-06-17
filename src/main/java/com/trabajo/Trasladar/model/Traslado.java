package com.trabajo.Trasladar.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Traslado {
    private Long idTraslado;
    private Long idSucursal;
    private Long idSucursalOrigen;
    private Long idSucursalDestino;
    private String fecha;
    private EstadoTraslado estado;

    public void setId(Long id) {
        this.idTraslado = id;
    }

    private Long fechaHora;
    private String motivo;

    public void setIdSucursalOrigen(Long idSucursalOrigen) {
    this.idSucursalOrigen = idSucursalOrigen;
}

public Long getIdSucursalOrigen() {
    return idSucursalOrigen;
}

public void setIdSucursalDestino(Long idSucursalDestino) {
    this.idSucursalDestino = idSucursalDestino;
}

public Long getIdSucursalDestino() {
    return idSucursalDestino;
}
}
