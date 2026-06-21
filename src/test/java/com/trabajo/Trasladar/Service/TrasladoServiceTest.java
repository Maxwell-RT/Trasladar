package com.trabajo.Trasladar.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trabajo.Trasladar.model.EstadoTraslado;
import com.trabajo.Trasladar.model.Traslado;
import com.trabajo.Trasladar.repository.TrasladoRepository;
import com.trabajo.Trasladar.service.TrasladoService;

@ExtendWith(MockitoExtension.class)
public class TrasladoServiceTest {

    @Mock
    private TrasladoRepository trasladoRepository;

    @InjectMocks
    private TrasladoService trasladoService;

    private Traslado trasladoEjemplo;

    @BeforeEach
    void setup() {
        trasladoEjemplo = new Traslado();
        trasladoEjemplo.setId(1L);
        trasladoEjemplo.setIdSucursalOrigen(10L);
        trasladoEjemplo.setIdSucursalDestino(20L);
        trasladoEjemplo.setFechaHora(1700000000L);
        trasladoEjemplo.setEstado(EstadoTraslado.ESPERA);
        trasladoEjemplo.setMotivo("Traslado de insumos médicos");
    }

    @Test
    void Asignarestadoyguardar() {
        when(trasladoRepository.save(any(Traslado.class))).thenReturn(trasladoEjemplo);

        Traslado resultado = trasladoService.crear(trasladoEjemplo);

        assertEquals(EstadoTraslado.ESPERA, resultado.getEstado());
        assertNotNull(resultado);
        verify(trasladoRepository, times(1)).save(trasladoEjemplo);
    }

    @Test
    void retornarEstadoexistente() {
        when(trasladoRepository.findById(1L)).thenReturn(Optional.of(trasladoEjemplo));

        Traslado resultado = trasladoService.obtener(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdTraslado());
        assertEquals(10L, resultado.getIdSucursalOrigen());
        assertEquals(20L, resultado.getIdSucursalDestino());
    }

    @Test
    void Obtenernull() {
        assertThrows(IllegalArgumentException.class, () -> trasladoService.obtener(null));
    }

    @Test
    void CasoEstadoNoExiste() {
        when(trasladoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> trasladoService.obtener(999L));
    }

    @Test
    void Aprobar() {
        when(trasladoRepository.findById(1L)).thenReturn(Optional.of(trasladoEjemplo));
        when(trasladoRepository.save(any(Traslado.class))).thenReturn(trasladoEjemplo);

        Traslado resultado = trasladoService.aprobar(1L);

        assertEquals(EstadoTraslado.APROBADO, resultado.getEstado());
        verify(trasladoRepository, times(1)).save(trasladoEjemplo);
    }

    @Test
    void AprobaroExcepcion() {
        trasladoEjemplo.setEstado(EstadoTraslado.APROBADO); // ya fue aprobado
        when(trasladoRepository.findById(1L)).thenReturn(Optional.of(trasladoEjemplo));

        assertThrows(IllegalStateException.class, () -> trasladoService.aprobar(1L));
    }

    @Test
    void ActualizarArechazado() {
        when(trasladoRepository.findById(1L)).thenReturn(Optional.of(trasladoEjemplo));
        when(trasladoRepository.save(any(Traslado.class))).thenReturn(trasladoEjemplo);

        Traslado resultado = trasladoService.rechazar(1L, "Sucursal destino sin capacidad");

        assertEquals(EstadoTraslado.RECHAZADO, resultado.getEstado());
        verify(trasladoRepository, times(1)).save(trasladoEjemplo);
    }


    @Test
    void actualizarAestadoCancelado() {
        trasladoEjemplo.setEstado(EstadoTraslado.APROBADO);
        when(trasladoRepository.findById(1L)).thenReturn(Optional.of(trasladoEjemplo));
        when(trasladoRepository.save(any(Traslado.class))).thenReturn(trasladoEjemplo);

        Traslado resultado = trasladoService.cancelar(1L);

        assertEquals(EstadoTraslado.CANCELADO, resultado.getEstado());
        verify(trasladoRepository, times(1)).save(trasladoEjemplo);
    }

    @Test
    void cancelarEnEspera() {
        when(trasladoRepository.findById(1L)).thenReturn(Optional.of(trasladoEjemplo));

        assertThrows(IllegalStateException.class, () -> trasladoService.cancelar(1L));
    }

    @Test
    void PonerEstadoFinalizado() {
        trasladoEjemplo.setEstado(EstadoTraslado.APROBADO);
        when(trasladoRepository.findById(1L)).thenReturn(Optional.of(trasladoEjemplo));
        when(trasladoRepository.save(any(Traslado.class))).thenReturn(trasladoEjemplo);

        Traslado resultado = trasladoService.finalizar(1L);

        assertEquals(EstadoTraslado.FINALIZADO, resultado.getEstado());
        verify(trasladoRepository, times(1)).save(trasladoEjemplo);
    }

    @Test
    void DeleteTraslado() {
        when(trasladoRepository.findById(1L)).thenReturn(Optional.of(trasladoEjemplo));

        trasladoService.eliminar(1L);

        verify(trasladoRepository, times(1)).delete(trasladoEjemplo);
    }

    @Test
    void ListarTodos() {
        Traslado t2 = new Traslado();
        t2.setId(2L);
        t2.setEstado(EstadoTraslado.APROBADO);
        t2.setMotivo("Traslado de equipos de radiología");

        when(trasladoRepository.findAll()).thenReturn(Arrays.asList(trasladoEjemplo, t2));

        List<Traslado> resultado = trasladoService.listar();

        assertEquals(2, resultado.size());
        assertEquals(EstadoTraslado.ESPERA, resultado.get(0).getEstado());
        assertEquals(EstadoTraslado.APROBADO, resultado.get(1).getEstado());
    }
}