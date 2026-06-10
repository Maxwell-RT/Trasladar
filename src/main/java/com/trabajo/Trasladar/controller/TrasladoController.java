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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("api/v1/Traslado")
public class TrasladoController {

    @Autowired
    private TrasladoService trasladoService;

    public TrasladoController(TrasladoService trasladoService) {
        this.trasladoService = trasladoService;
    }

    @GetMapping("/{id}/aprobar")
    public ResponseEntity<Traslado> aprobar(@PathVariable Long id) {
        return ResponseEntity.ok(trasladoService.aprobar(id));
    }

    @GetMapping("/{id}/rechazar")
    public ResponseEntity<Traslado> rechazar(
            @PathVariable Long id,
            @RequestParam String motivo) {

        return ResponseEntity.ok(trasladoService.rechazar(id, motivo));
    }

    @GetMapping("/{id}/cancelar")
    public ResponseEntity<Traslado> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(trasladoService.cancelar(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Traslado> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(trasladoService.obtener(id));
    }

    @PostMapping
    public ResponseEntity<Traslado> crear(@RequestBody Traslado traslado) {
        // Aquí podrías agregar lógica para validar el traslado antes de guardarlo
        return ResponseEntity.ok(trasladoService.crear(traslado));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Traslado> actualizar(@PathVariable Long id, @RequestBody Traslado traslado) {
        // Aquí podrías agregar lógica para validar el traslado antes de actualizarlo
        return ResponseEntity.ok(trasladoService.actualizar(traslado));
    }

    @GetMapping("/{id}/finalizar")
    public ResponseEntity<Traslado> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(trasladoService.finalizar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        trasladoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping ("/listar")
    public ResponseEntity<Iterable<Traslado>> listar() {
        return ResponseEntity.ok(trasladoService.listar());
    }



}
