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

        // Método auxiliar para no repetir construcción de objetos
        private Traslado buildTraslado(Long id, Long origen, Long destino,
                        EstadoTraslado estado, String motivo) {
                Traslado t = new Traslado();
                t.setId(id);
                t.setIdSucursalOrigen(origen);
                t.setIdSucursalDestino(destino);
                t.setFechaHora(1700000000L);
                t.setEstado(estado);
                t.setMotivo(motivo);
                return t;
        }

        @Test
        public void listarPorId() throws Exception {
                Traslado traslado = buildTraslado(1L, 10L, 20L,
                                EstadoTraslado.ESPERA, "Reposición de insumos médicos");

                when(trasladoService.listarPorId(1L)).thenReturn(traslado);

                mockMvc.perform(get("/api/v1/Traslado/listarPorId/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.idTraslado").value(1L))
                                .andExpect(jsonPath("$.idSucursalOrigen").value(10L))
                                .andExpect(jsonPath("$.idSucursalDestino").value(20L))
                                .andExpect(jsonPath("$.motivo").value("Reposición de insumos médicos"))
                                .andExpect(jsonPath("$.estado").value("ESPERA"));
        }

        @Test
        public void crearTraslado() throws Exception {
                Traslado entrada = new Traslado();
                entrada.setIdSucursalOrigen(10L);
                entrada.setIdSucursalDestino(20L);
                entrada.setFechaHora(1700000000L);
                entrada.setMotivo("Traslado de equipos quirúrgicos");

                Traslado guardado = buildTraslado(1L, 10L, 20L,
                                EstadoTraslado.ESPERA, "Traslado de equipos quirúrgicos");

                when(trasladoService.crear(any(Traslado.class))).thenReturn(guardado);

                mockMvc.perform(post("/api/v1/Traslado/crear")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(entrada)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.idTraslado").value(1L))
                                .andExpect(jsonPath("$.idSucursalOrigen").value(10L))
                                .andExpect(jsonPath("$.idSucursalDestino").value(20L))
                                .andExpect(jsonPath("$.motivo").value("Traslado de equipos quirúrgicos"))
                                .andExpect(jsonPath("$.estado").value("ESPERA"));
        }

        @Test
        public void aprobarTraslado() throws Exception {
                Traslado aprobado = buildTraslado(1L, 10L, 20L,
                                EstadoTraslado.APROBADO, "Traslado de medicamentos aprobado");

                when(trasladoService.aprobar(1L)).thenReturn(aprobado);

                mockMvc.perform(put("/api/v1/Traslado/aprobar/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.idTraslado").value(1L))
                                .andExpect(jsonPath("$.idSucursalOrigen").value(10L))
                                .andExpect(jsonPath("$.idSucursalDestino").value(20L))
                                .andExpect(jsonPath("$.estado").value("APROBADO"));
        }

        @Test
        public void rechazarTraslado() throws Exception {
                Traslado rechazado = buildTraslado(1L, 10L, 20L,
                                EstadoTraslado.RECHAZADO, "Sucursal destino sin capacidad de almacenamiento");

                when(trasladoService.rechazar(eq(1L), any(String.class))).thenReturn(rechazado);

                Traslado request = new Traslado();
                request.setMotivo("Sucursal destino sin capacidad de almacenamiento");

                mockMvc.perform(put("/api/v1/Traslado/rechazar/1")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.idTraslado").value(1L))
                                .andExpect(jsonPath("$.motivo")
                                                .value("Sucursal destino sin capacidad de almacenamiento"))
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
                entrada.setIdSucursalOrigen(30L);
                entrada.setIdSucursalDestino(40L);
                entrada.setFechaHora(1800000000L);
                entrada.setMotivo("Traslado reprogramado por cierre de sucursal");

                Traslado actualizado = buildTraslado(1L, 30L, 40L,
                                EstadoTraslado.ESPERA, "Traslado reprogramado por cierre de sucursal");

                when(trasladoService.actualizar(eq(1L), any(Traslado.class))).thenReturn(actualizado);

                mockMvc.perform(put("/api/v1/Traslado/actualizar/1")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(entrada)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.idTraslado").value(1L))
                                .andExpect(jsonPath("$.idSucursalOrigen").value(30L))
                                .andExpect(jsonPath("$.idSucursalDestino").value(40L))
                                .andExpect(jsonPath("$.motivo").value("Traslado reprogramado por cierre de sucursal"))
                                .andExpect(jsonPath("$.estado").value("ESPERA"));
        }

        @Test
        public void listarTodos() throws Exception {
                Traslado t1 = buildTraslado(1L, 10L, 20L,
                                EstadoTraslado.ESPERA, "Traslado de insumos de laboratorio");
                Traslado t2 = buildTraslado(2L, 30L, 40L,
                                EstadoTraslado.APROBADO, "Traslado de equipos de radiología");

                when(trasladoService.listar()).thenReturn(Arrays.asList(t1, t2));

                mockMvc.perform(get("/api/v1/Traslado/listar"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2))
                                .andExpect(jsonPath("$[0].idTraslado").value(1L))
                                .andExpect(jsonPath("$[0].idSucursalOrigen").value(10L))
                                .andExpect(jsonPath("$[0].idSucursalDestino").value(20L))
                                .andExpect(jsonPath("$[0].motivo").value("Traslado de insumos de laboratorio"))
                                .andExpect(jsonPath("$[0].estado").value("ESPERA"))
                                .andExpect(jsonPath("$[1].idTraslado").value(2L))
                                .andExpect(jsonPath("$[1].idSucursalOrigen").value(30L))
                                .andExpect(jsonPath("$[1].idSucursalDestino").value(40L))
                                .andExpect(jsonPath("$[1].motivo").value("Traslado de equipos de radiología"))
                                .andExpect(jsonPath("$[1].estado").value("APROBADO"));
        }
}