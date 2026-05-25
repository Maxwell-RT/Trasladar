package com.trabajo.model;
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


}
