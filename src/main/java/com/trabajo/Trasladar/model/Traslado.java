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
    private String fecha;
    private EstadoTraslado estado;

    public void setId(Long id) {
        this.idTraslado = id;
    }

    private Long fechaHora;
    private String motivo;

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
