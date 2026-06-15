package com.trabajo.Trasladar.Controller;

import com.trabajo.Trasladar.model.*;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.trabajo.Trasladar.model.Traslado;
import com.trabajo.Trasladar.repository.TrasladoRepository;



@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TrasladarControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrasladoRepository trasladoRepository;

    @MockitoBean
    private RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @BeforeEach
    public void cleandb() {
        trasladoRepository.deleteAll();
    }

    @Test
    public void testCreateTraslado() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setIdSucursal(1L);
        traslado.setIdSucursal(2L);
        traslado.setFechaHora(10L);

        String trasladoJson = objectMapper.writeValueAsString(traslado);

        mockMvc.perform(post("/trasladar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(trasladoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.idSucursalOrigen").value(1L))
                .andExpect(jsonPath("$.idSucursalDestino").value(2L))
                .andExpect(jsonPath("$.fechaHora").value(10L));
    }

    @Test
    public void testGetTrasladoById() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setIdSucursal(1L);
        traslado.setIdSucursal(2L);
        traslado.setFechaHora(10L);
        Traslado savedTraslado = trasladoRepository.save(traslado);

        mockMvc.perform(get("/trasladar/{id}", savedTraslado.getIdTraslado()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedTraslado.getIdTraslado()))
                .andExpect(jsonPath("$.idSucursalOrigen").value(1L))
                .andExpect(jsonPath("$.idSucursalDestino").value(2L))
                .andExpect(jsonPath("$.fechaHora").value(10L));
    }



    @Test
    public void testGetAllTraslados() throws Exception {
        Traslado traslado1 = new Traslado();
        traslado1.setIdSucursal(1L);
        traslado1.setIdSucursal(2L);
        traslado1.setFechaHora(10L);
        Traslado traslado2 = new Traslado();
        traslado2.setIdSucursal(3L);
        traslado2.setIdSucursal(4L);
        traslado2.setFechaHora(20L);
        trasladoRepository.save(traslado1);
        trasladoRepository.save(traslado2);

        mockMvc.perform(get("/trasladar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(traslado1.getIdTraslado()))
                .andExpect(jsonPath("$[0].idSucursalOrigen").value(1L))
                .andExpect(jsonPath("$[0].idSucursalDestino").value(2L))
                .andExpect(jsonPath("$[0].fechaHora").value(10L))
                .andExpect(jsonPath("$[1].id").value(traslado2.getIdTraslado()))
                .andExpect(jsonPath("$[1].idSucursalOrigen").value(3L))
                .andExpect(jsonPath("$[1].idSucursalDestino").value(4L))
                .andExpect(jsonPath("$[1].fechaHora").value(20L));
    }

    @Test
    public void testDeleteTraslado() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setIdSucursal(1L);
        traslado.setIdSucursal(2L);
        traslado.setFechaHora(10L);
        Traslado savedTraslado = trasladoRepository.save(traslado);

        mockMvc.perform(delete("/trasladar/{id}", savedTraslado.getIdTraslado()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/trasladar/{id}", savedTraslado.getIdTraslado()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateTraslado() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setIdSucursal(1L);
        traslado.setIdSucursal(2L);
        traslado.setFechaHora(10L);
        Traslado savedTraslado = trasladoRepository.save(traslado);

        savedTraslado.setIdSucursal(3L);
        savedTraslado.setIdSucursal(4L);
        savedTraslado.setFechaHora(20L);

        String trasladoJson = objectMapper.writeValueAsString(savedTraslado);

        mockMvc.perform(put("/trasladar/{id}", savedTraslado.getIdTraslado())
                .contentType(MediaType.APPLICATION_JSON)
                .content(trasladoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedTraslado.getIdTraslado()))
                .andExpect(jsonPath("$.idSucursalOrigen").value(3L))
                .andExpect(jsonPath("$.idSucursalDestino").value(4L))
                .andExpect(jsonPath("$.fechaHora").value(20L));
    }

    @Test 
    public void testGetTrasladoNotFound() throws Exception {
        mockMvc.perform(get("/trasladar/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTrasladoNotFound() throws Exception {
        mockMvc.perform(delete("/trasladar/{id}", 999L))
                .andExpect(status().isNotFound());
    }


    /// EL cancelar, NO OLVIDES HACER EL CANCELAR 
    
    @Test
    public void cancelartraslado() throws Exception {
        Traslado traslado = new Traslado();
        traslado.setIdSucursal(1L);
        traslado.setIdSucursal(2L);
        traslado.setFechaHora(10L);
        Traslado savedTraslado = trasladoRepository.save(traslado);

        mockMvc.perform(post("/trasladar/{id}/cancelar", savedTraslado.getIdTraslado()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedTraslado.getIdTraslado()))
                .andExpect(jsonPath("$.idSucursalOrigen").value(1L))
                .andExpect(jsonPath("$.idSucursalDestino").value(2L))
                .andExpect(jsonPath("$.fechaHora").value(10L))
                .andExpect(jsonPath("$.estado").value("CANCELADO"));
    }
    
}




