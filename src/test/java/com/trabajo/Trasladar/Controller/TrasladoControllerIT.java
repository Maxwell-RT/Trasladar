package com.trabajo.Trasladar.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.trabajo.Trasladar.model.EstadoTraslado;
import com.trabajo.Trasladar.model.Traslado;
import com.trabajo.Trasladar.repository.TrasladoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TrasladoControllerIT {

    private static final String BASE = "/api/v1/Traslado";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrasladoRepository trasladoRepository;

    @MockitoBean
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    public void cleandb() {
        trasladoRepository.deleteAll();
    }

    @Test
    public void testCreateTraslado() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setIdSucursalOrigen(1L);
        traslado.setIdSucursalDestino(2L);
        traslado.setFechaHora(10L);

        mockMvc.perform(post(BASE + "/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(traslado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTraslado").exists())
                .andExpect(jsonPath("$.idSucursalOrigen").value(1L))
                .andExpect(jsonPath("$.idSucursalDestino").value(2L))
                .andExpect(jsonPath("$.fechaHora").value(10L))
                .andExpect(jsonPath("$.estado").value("ESPERA"));
    }

    @Test
    public void testGetTrasladoById() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setIdSucursalOrigen(1L);
        traslado.setIdSucursalDestino(2L);
        traslado.setFechaHora(10L);
        Traslado saved = trasladoRepository.save(traslado);

        mockMvc.perform(get(BASE + "/listarPorId/{id}", saved.getIdTraslado()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTraslado").value(saved.getIdTraslado()))
                .andExpect(jsonPath("$.idSucursalOrigen").value(1L))
                .andExpect(jsonPath("$.idSucursalDestino").value(2L))
                .andExpect(jsonPath("$.fechaHora").value(10L));
    }

    @Test
    public void testGetAllTraslados() throws Exception {
        Traslado t1 = new Traslado();
        t1.setIdSucursalOrigen(1L);
        t1.setIdSucursalDestino(2L);
        t1.setFechaHora(10L);

        Traslado t2 = new Traslado();
        t2.setIdSucursalOrigen(3L);
        t2.setIdSucursalDestino(4L);
        t2.setFechaHora(20L);

        trasladoRepository.save(t1);
        trasladoRepository.save(t2);

        mockMvc.perform(get(BASE + "/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idSucursalOrigen").value(1L))
                .andExpect(jsonPath("$[0].idSucursalDestino").value(2L))
                .andExpect(jsonPath("$[1].idSucursalOrigen").value(3L))
                .andExpect(jsonPath("$[1].idSucursalDestino").value(4L));
    }

    @Test
    public void testUpdateTraslado() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setIdSucursalOrigen(1L);
        traslado.setIdSucursalDestino(2L);
        traslado.setFechaHora(10L);
        Traslado saved = trasladoRepository.save(traslado);

        saved.setIdSucursalOrigen(3L);
        saved.setIdSucursalDestino(4L);
        saved.setFechaHora(20L);

        mockMvc.perform(put(BASE + "/actualizar/{id}", saved.getIdTraslado())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSucursalOrigen").value(3L))
                .andExpect(jsonPath("$.idSucursalDestino").value(4L))
                .andExpect(jsonPath("$.fechaHora").value(20L));
    }

    @Test
    public void testDeleteTraslado() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setIdSucursalOrigen(1L);
        traslado.setIdSucursalDestino(2L);
        traslado.setFechaHora(10L);
        Traslado saved = trasladoRepository.save(traslado);

        mockMvc.perform(delete(BASE + "/eliminar/{id}", saved.getIdTraslado()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(BASE + "/listarPorId/{id}", saved.getIdTraslado()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetTrasladoNotFound() throws Exception {
        mockMvc.perform(get(BASE + "/listarPorId/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTrasladoNotFound() throws Exception {
        mockMvc.perform(delete(BASE + "/eliminar/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    //No puedes olvidarte el cancelar, que tira error el no tenerlo
    //QUE TE ACUERDES


    @Test
    public void testCancelarTraslado() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setIdSucursalOrigen(1L);
        traslado.setIdSucursalDestino(2L);
        traslado.setFechaHora(10L);
        traslado.setEstado(EstadoTraslado.APROBADO);
        Traslado saved = trasladoRepository.save(traslado);

        mockMvc.perform(put(BASE + "/cancelar/{id}", saved.getIdTraslado()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTraslado").value(saved.getIdTraslado()))
                .andExpect(jsonPath("$.estado").value("CANCELADO"));
    }
}