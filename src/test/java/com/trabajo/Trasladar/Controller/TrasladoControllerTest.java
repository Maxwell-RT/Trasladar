package com.trabajo.Trasladar.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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


@WebMvcTest(TrasladoController.class)
@ActiveProfiles("test")
public class TrasladoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TrasladoService trasladoService;


    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void ListarPorId() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setId(1L);
        traslado.setEstado(EstadoTraslado.ESPERA);
        traslado.setMotivo("Motivo de prueba");


        when(trasladoService.listarPorId(1L)).thenReturn(traslado);

        mockMvc.perform(get("/api/v1/Traslado/listarPorId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.motivo").value("Motivo de prueba"))
                .andExpect(jsonPath("$.estado").value("ESPERA"));
    }

    @Test
    public void CrearTraslado() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setId(1L);
        traslado.setEstado(EstadoTraslado.ESPERA);
        traslado.setMotivo("Motivo de prueba");

        when(trasladoService.crear(traslado)).thenReturn(traslado);

        mockMvc.perform(get("/api/v1/Traslado/crear")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(traslado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.motivo").value("Motivo de prueba"))
                .andExpect(jsonPath("$.estado").value("ESPERA"));
    }
    

    @Test
    public void AprobarTraslado() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setId(1L);
        traslado.setEstado(EstadoTraslado.APROBADO);
        traslado.setMotivo("Motivo de prueba");

        when(trasladoService.aprobar(1L)).thenReturn(traslado);

        mockMvc.perform(get("/api/v1/Traslado/aprobar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.motivo").value("Motivo de prueba"))
                .andExpect(jsonPath("$.estado").value("APROBADO"));
    }

    @Test
    public void RechazarTraslado() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setId(1L);
        traslado.setEstado(EstadoTraslado.RECHAZADO);
        traslado.setMotivo("Motivo de prueba");

        when(trasladoService.rechazar(1L, "Motivo de rechazo")).thenReturn(traslado);

        mockMvc.perform(get("/api/v1/Traslado/rechazar/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString("Motivo de rechazo")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.motivo").value("Motivo de prueba"))
                .andExpect(jsonPath("$.estado").value("RECHAZADO"));
    }

    @Test
    public void EliminarTraslado() throws Exception {
        mockMvc.perform(get("/api/v1/Traslado/eliminar/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void ActualizarTraslado() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setId(1L);
        traslado.setEstado(EstadoTraslado.ESPERA);
        traslado.setMotivo("Motivo de prueba");

        when(trasladoService.actualizar(1L)).thenReturn(traslado);

        mockMvc.perform(get("/api/v1/Traslado/actualizar/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(traslado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.motivo").value("Motivo de prueba"))
                .andExpect(jsonPath("$.estado").value("ESPERA"));
    }

    @Test
    public void ListarPorId1() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setId(1L);
        traslado.setEstado(EstadoTraslado.ESPERA);
        traslado.setMotivo("Motivo de prueba");

        when(trasladoService.listarPorId(1L)).thenReturn(traslado);

        mockMvc.perform(get("/api/v1/Traslado/listarPorId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.motivo").value("Motivo de prueba"))
                .andExpect(jsonPath("$.estado").value("ESPERA"));
    }

    @Test
    public void ListarTodos() throws Exception {    
        Traslado traslado1 = new Traslado();
        traslado1.setId(1L);
        traslado1.setEstado(EstadoTraslado.ESPERA);
        traslado1.setMotivo("Motivo de prueba 1");

        Traslado traslado2 = new Traslado();
        traslado2.setId(2L);
        traslado2.setEstado(EstadoTraslado.APROBADO);
        traslado2.setMotivo("Motivo de prueba 2");

        when(trasladoService.listar()).thenReturn(java.util.Arrays.asList(traslado1, traslado2));

        mockMvc.perform(get("/api/v1/Traslado/listarTodos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].motivo").value("Motivo de prueba 1"))
                .andExpect(jsonPath("$[0].estado").value("ESPERA"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].motivo").value("Motivo de prueba 2"))
                .andExpect(jsonPath("$[1].estado").value("APROBADO"));

    }
}
