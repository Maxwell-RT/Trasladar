package com.trabajo.Trasladar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trabajo.Trasladar.model.Traslado;
import com.trabajo.Trasladar.service.TrasladoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/v1/Traslado")
public class TrasladoController {

    @Autowired
    private TrasladoService trasladoService;

    public TrasladoController(TrasladoService trasladoService) {
        this.trasladoService = trasladoService;
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarTraslados() {
        return ResponseEntity.ok(trasladoService.listar());
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearTraslado(@RequestBody Traslado traslado) {
        return ResponseEntity.ok(trasladoService.crear(traslado));
    }

    @PutMapping("/aprobar/{id}")
    public ResponseEntity<?> aprobarTraslado(@PathVariable Long id) {
        return ResponseEntity.ok(trasladoService.aprobar(id));
    }

    @PutMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTraslado(@PathVariable Long id) {
        trasladoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<?> listarPorId1(@PathVariable Long id) {
        return ResponseEntity.ok(trasladoService.listarPorId(id));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarTraslado(@PathVariable Long id, @RequestBody Traslado traslado) {
        return ResponseEntity.ok(trasladoService.actualizar(id));
    }

@PutMapping("/asignarSucursales/{id}")
public ResponseEntity<?> asignarSucursales(
        @PathVariable Long id,
        @RequestParam Long idSucursalOrigen,
        @RequestParam Long idSucursalDestino) {
    return ResponseEntity.ok(trasladoService.asignarSucursales(id, idSucursalOrigen, idSucursalDestino));
}
}
