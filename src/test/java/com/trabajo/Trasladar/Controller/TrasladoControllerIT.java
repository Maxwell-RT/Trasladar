package com.trabajo.Trasladar.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trabajo.Trasladar.controller.TrasladoController;
import com.trabajo.Trasladar.model.EstadoTraslado;
import com.trabajo.Trasladar.model.Traslado;
import com.trabajo.Trasladar.service.TrasladoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;

@WebMvcTest(TrasladoController.class)
@ActiveProfiles("test")
public class TrasladoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TrasladoService trasladoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ── Datos de prueba para comprobar si
    // funciona─────────────────────────────────────────
    private Traslado trasladoEjemplo(Long id, EstadoTraslado estado, String motivo) {
        Traslado t = new Traslado();
        t.setId(id);
        t.setEstado(estado);
        t.setMotivo("Envio de suministros");
        t.setIdSucursalOrigen(10L);
        t.setIdSucursalDestino(20L);
        t.setFechaHora(1700000000L);
        return t;
    }

    // Quien lo diria, funciona!
    @Test
    public void listarPorId() throws Exception {
        Traslado traslado = trasladoEjemplo(1L, EstadoTraslado.ESPERA, "Traslado de insumos médicos");

        when(trasladoService.listarPorId(1L)).thenReturn(traslado);

        mockMvc.perform(get("/api/v1/Traslado/listarPorId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTraslado").value(1L))
                .andExpect(jsonPath("$.idSucursalOrigen").value(10L))
                .andExpect(jsonPath("$.idSucursalDestino").value(20L))
                .andExpect(jsonPath("$.motivo").value("Traslado de insumos médicos"))
                .andExpect(jsonPath("$.estado").value("ESPERA"));

        
    }

    @Test
    public void crearTraslado() throws Exception {
        Traslado entrada = new Traslado();
        entrada.setIdSucursalOrigen(10L);
        entrada.setIdSucursalDestino(20L);
        entrada.setFechaHora(1700000000L);
        entrada.setMotivo("Reposición de stock urgente");

        Traslado guardado = trasladoEjemplo(1L, EstadoTraslado.ESPERA, "Reposición de stock urgente");

        when(trasladoService.crear(any(Traslado.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/v1/Traslado/crear")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTraslado").value(1L))
                .andExpect(jsonPath("$.idSucursalOrigen").value(10L))
                .andExpect(jsonPath("$.idSucursalDestino").value(20L))
                .andExpect(jsonPath("$.motivo").value("Reposición de stock urgente"))
                .andExpect(jsonPath("$.estado").value("ESPERA"));
    }

    @Test
    public void aprobarTraslado() throws Exception {
        Traslado aprobado = trasladoEjemplo(1L, EstadoTraslado.APROBADO, "Traslado de equipos");

        when(trasladoService.aprobar(1L)).thenReturn(aprobado);

        mockMvc.perform(put("/api/v1/Traslado/aprobar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTraslado").value(1L))
                .andExpect(jsonPath("$.estado").value("APROBADO"));
    }

    @Test
    public void rechazarTraslado() throws Exception {
        Traslado rechazado = trasladoEjemplo(1L, EstadoTraslado.RECHAZADO, "Sucursal destino sin capacidad");

        when(trasladoService.rechazar(eq(1L), any(String.class))).thenReturn(rechazado);

        // Se manda el DTO correcto, no un Traslado entero
        Traslado request = new Traslado();
        request.setMotivo("Sucursal destino sin capacidad");

        mockMvc.perform(put("/api/v1/Traslado/rechazar/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTraslado").value(1L))
                .andExpect(jsonPath("$.motivo").value("Sucursal destino sin capacidad"))
                .andExpect(jsonPath("$.estado").value("RECHAZADO"));
    }

    @Test
    public void eliminarTraslado() throws Exception {
        // eliminar no devuelve body, solo verifica que el status sea 204
        mockMvc.perform(delete("/api/v1/Traslado/eliminar/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void actualizarTraslado() throws Exception {
        Traslado entrada = new Traslado();
        entrada.setIdSucursalOrigen(30L);
        entrada.setIdSucursalDestino(40L);
        entrada.setFechaHora(1800000000L);
        entrada.setMotivo("Traslado reprogramado");

        Traslado actualizado = new Traslado();
        actualizado.setId(1L);
        actualizado.setEstado(EstadoTraslado.ESPERA);
        actualizado.setIdSucursalOrigen(30L);
        actualizado.setIdSucursalDestino(40L);
        actualizado.setFechaHora(1800000000L);
        actualizado.setMotivo("Traslado reprogramado");

        // firma correcta: actualizar(id, traslado)
        when(trasladoService.actualizar(eq(1L), any(Traslado.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/Traslado/actualizar/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTraslado").value(1L))
                .andExpect(jsonPath("$.idSucursalOrigen").value(30L))
                .andExpect(jsonPath("$.idSucursalDestino").value(40L))
                .andExpect(jsonPath("$.motivo").value("Traslado reprogramado"))
                .andExpect(jsonPath("$.estado").value("ESPERA"));
    }

    @Test
    public void listarTodos() throws Exception {
        Traslado t1 = trasladoEjemplo(1L, EstadoTraslado.ESPERA, "Traslado de insumos");
        Traslado t2 = trasladoEjemplo(2L, EstadoTraslado.APROBADO, "Traslado de equipos");

        when(trasladoService.listar()).thenReturn(Arrays.asList(t1, t2));

        mockMvc.perform(get("/api/v1/Traslado/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idTraslado").value(1L))
                .andExpect(jsonPath("$[0].motivo").value("Traslado de insumos"))
                .andExpect(jsonPath("$[0].estado").value("ESPERA"))
                .andExpect(jsonPath("$[1].idTraslado").value(2L))
                .andExpect(jsonPath("$[1].motivo").value("Traslado de equipos"))
                .andExpect(jsonPath("$[1].estado").value("APROBADO"));
    }
}