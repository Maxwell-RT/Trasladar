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


    private String motivo;

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}

