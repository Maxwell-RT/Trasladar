package com.trabajo.Trasladar.Controller;

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
    public void testCrearTraslado() throws Exception {  
        
    }




}
