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
public class TrasladarControllerTest {
    
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



}
