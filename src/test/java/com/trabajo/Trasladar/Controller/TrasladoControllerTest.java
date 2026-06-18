package com.trabajo.Trasladar.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trabajo.Trasladar.controller.TrasladoController;
import com.trabajo.Trasladar.service.TrasladoService;
import com.trabajo.Trasladar.model.EstadoTraslado;
import com.trabajo.Trasladar.model.Traslado;

import java.util.Arrays;

@WebMvcTest(TrasladoController.class)
@ActiveProfiles("test")
public class TrasladoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TrasladoService trasladoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void listarPorId() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setId(1L);
        traslado.setEstado(EstadoTraslado.ESPERA);
        traslado.setMotivo("Motivo de prueba");

        when(trasladoService.listarPorId(1L)).thenReturn(traslado);

        mockMvc.perform(get("/api/v1/Traslado/listarPorId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTraslado").value(1L))
                .andExpect(jsonPath("$.motivo").value("Motivo de prueba"))
                .andExpect(jsonPath("$.estado").value("ESPERA"));
    }

    @Test
    public void crearTraslado() throws Exception {
        Traslado entrada = new Traslado();
        entrada.setMotivo("Motivo de prueba");

        Traslado guardado = new Traslado();
        guardado.setId(1L);
        guardado.setEstado(EstadoTraslado.ESPERA);
        guardado.setMotivo("Motivo de prueba");

        when(trasladoService.crear(any(Traslado.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/v1/Traslado/crear")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTraslado").value(1L))
                .andExpect(jsonPath("$.motivo").value("Motivo de prueba"))
                .andExpect(jsonPath("$.estado").value("ESPERA"));
    }

    @Test
    public void aprobarTraslado() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setId(1L);
        traslado.setEstado(EstadoTraslado.APROBADO);
        traslado.setMotivo("Motivo de prueba");

        when(trasladoService.aprobar(1L)).thenReturn(traslado);

        mockMvc.perform(put("/api/v1/Traslado/aprobar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTraslado").value(1L))
                .andExpect(jsonPath("$.motivo").value("Motivo de prueba"))
                .andExpect(jsonPath("$.estado").value("APROBADO"));
    }

    @Test
    public void rechazarTraslado() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setId(1L);
        traslado.setEstado(EstadoTraslado.RECHAZADO);
        traslado.setMotivo("Motivo de rechazo");

        when(trasladoService.rechazar(eq(1L), any(String.class))).thenReturn(traslado);

        Traslado request = new Traslado();
        request.setMotivo("Motivo de rechazo");

        mockMvc.perform(put("/api/v1/Traslado/rechazar/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTraslado").value(1L))
                .andExpect(jsonPath("$.motivo").value("Motivo de rechazo"))
                .andExpect(jsonPath("$.estado").value("RECHAZADO"));
    }

    @Test
    public void eliminarTraslado() throws Exception {
        mockMvc.perform(delete("/api/v1/Traslado/eliminar/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void actualizarTraslado() throws Exception {
        Traslado entrada = new Traslado();
        entrada.setMotivo("Motivo actualizado");

        Traslado actualizado = new Traslado();
        actualizado.setId(1L);
        actualizado.setEstado(EstadoTraslado.ESPERA);
        actualizado.setMotivo("Motivo actualizado");

        when(trasladoService.actualizar(null));

        mockMvc.perform(put("/api/v1/Traslado/actualizar/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTraslado").value(1L))
                .andExpect(jsonPath("$.motivo").value("Motivo actualizado"))
                .andExpect(jsonPath("$.estado").value("ESPERA"));
    }

    @Test
    public void listarTodos() throws Exception {
        Traslado traslado1 = new Traslado();
        traslado1.setId(1L);
        traslado1.setEstado(EstadoTraslado.ESPERA);
        traslado1.setMotivo("Motivo de prueba 1");

        Traslado traslado2 = new Traslado();
        traslado2.setId(2L);
        traslado2.setEstado(EstadoTraslado.APROBADO);
        traslado2.setMotivo("Motivo de prueba 2");

        when(trasladoService.listar()).thenReturn(Arrays.asList(traslado1, traslado2));

        mockMvc.perform(get("/api/v1/Traslado/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idTraslado").value(1L))
                .andExpect(jsonPath("$[0].motivo").value("Motivo de prueba 1"))
                .andExpect(jsonPath("$[0].estado").value("ESPERA"))
                .andExpect(jsonPath("$[1].idTraslado").value(2L))
                .andExpect(jsonPath("$[1].motivo").value("Motivo de prueba 2"))
                .andExpect(jsonPath("$[1].estado").value("APROBADO"));
    }
}