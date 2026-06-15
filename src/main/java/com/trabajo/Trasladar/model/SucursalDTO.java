package com.trabajo.Trasladar.model;

import lombok.Data;

@Data
public class SucursalDTO {
    private Long idSucursal;
    private String direccion;
    private String horario;
    private Long hora;
    private Long idSucursalOrigen;
    private Long idSucursalDestino;
}
